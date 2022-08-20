package mc.replay.dispatcher.packet;

import lombok.Getter;
import mc.replay.MCReplayPlugin;
import mc.replay.common.dispatcher.DispatcherPacketIn;
import mc.replay.common.dispatcher.DispatcherPacketOut;
import mc.replay.dispatcher.ReplayDispatcher;
import mc.replay.nms.v1_16_5.dispatcher.packet.animation.AnimationPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.block.BlockActionPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.block.BlockBreakPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.block.BlockChangePacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.block.MultiBlockChangePacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.particles.WorldParticlesPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.sound.CustomSoundEffectPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.sound.EntitySoundPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.sound.NamedSoundEffectPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.sound.StopSoundPacketOutConverter;
import mc.replay.nms.v1_16_5.dispatcher.packet.world.WorldEventPacketOutConverter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class ReplayPacketDispatcher extends ReplayDispatcher {

    private boolean active;

    private final Map<String, DispatcherPacketIn<?>> packetInConverters = new HashMap<>();
    private final Map<String, DispatcherPacketOut<?>> packetOutConverters = new HashMap<>();

    public ReplayPacketDispatcher(MCReplayPlugin plugin) {
        super(plugin);

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPipelineListener(this), plugin);

        this.registerInConverters();
        this.registerOutConverters();
    }

    private void registerInConverters() {
//        this.registerPacketInConverter(new ArmAnimationPacketInConverter());
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

    public void registerPacketInConverter(DispatcherPacketIn<?> converter) {
        this.packetInConverters.put(converter.getInputClass().getSimpleName().toLowerCase(), converter);
    }

    void registerPacketOutConverter(DispatcherPacketOut<?> converter) {
        this.packetOutConverters.put(converter.getInputClass().getSimpleName().toLowerCase(), converter);
    }

    @Override
    public void start() {
        this.active = true;
    }

    @Override
    public void stop() {
        this.active = false;
    }
}