package mc.replay.dispatcher.packet;

import lombok.Getter;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.dispatcher.packet.converters.ReplayPacketInConverter;
import mc.replay.dispatcher.packet.converters.ReplayPacketOutConverter;
import mc.replay.dispatcher.packet.converters.in.ArmAnimationPacketInConverter;
import mc.replay.dispatcher.packet.converters.out.animation.AnimationPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.block.BlockActionPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.block.BlockBreakPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.block.BlockChangePacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.block.MultiBlockChangePacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.particles.WorldParticlesPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.sound.CustomSoundEffectPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.sound.EntitySoundPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.sound.NamedSoundEffectPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.sound.StopSoundPacketOutConverter;
import mc.replay.dispatcher.packet.converters.out.world.WorldEventPacketOutConverter;
import mc.replay.recordables.Recordable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class ReplayPacketDispatcher extends ReplayDispatcher {

    private final PlayerPipelineHandler playerPipelineHandler;

    private final Map<String, ReplayPacketInConverter<? extends Recordable>> packetInConverters = new HashMap<>();
    private final Map<String, ReplayPacketOutConverter<? extends Recordable>> packetOutConverters = new HashMap<>();

    public ReplayPacketDispatcher(JavaPlugin javaPlugin) {
        super(javaPlugin);

        this.playerPipelineHandler = new PlayerPipelineHandler(javaPlugin);

        this.registerInConverters();
        this.registerOutConverters();
    }

    private void registerInConverters() {
        this.registerPacketInConverter(new ArmAnimationPacketInConverter());
    }

    private void registerOutConverters() {
        this.registerPacketOutConverter(new AnimationPacketOutConverter());

        this.registerPacketOutConverter(new BlockActionPacketOutConverter());
        this.registerPacketOutConverter(new BlockBreakPacketOutConverter());
        this.registerPacketOutConverter(new BlockChangePacketOutConverter());
        this.registerPacketOutConverter(new MultiBlockChangePacketOutConverter());

        this.registerPacketOutConverter(new WorldParticlesPacketOutConverter());

        this.registerPacketOutConverter(new CustomSoundEffectPacketOutConverter());
        this.registerPacketOutConverter(new EntitySoundPacketOutConverter());
        this.registerPacketOutConverter(new NamedSoundEffectPacketOutConverter());
        this.registerPacketOutConverter(new StopSoundPacketOutConverter());

        this.registerPacketOutConverter(new WorldEventPacketOutConverter());
    }

    public <R extends Recordable> void registerPacketInConverter(ReplayPacketInConverter<R> converter) {
        this.packetInConverters.put(converter.packetClassName().toLowerCase(), converter);
    }

    public <R extends Recordable> void registerPacketOutConverter(ReplayPacketOutConverter<R> converter) {
        this.packetOutConverters.put(converter.packetClassName(), converter);
    }

    @Override
    public void start() {
        this.playerPipelineHandler.setActive(true);
    }

    @Override
    public void stop() {
        this.playerPipelineHandler.setActive(false);
    }
}