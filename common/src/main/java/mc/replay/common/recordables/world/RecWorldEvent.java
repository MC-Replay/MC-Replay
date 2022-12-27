package mc.replay.common.recordables.world;

import mc.replay.common.recordables.RecordableWorldEvent;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundWorldEventPacket;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecWorldEvent(int effectId, Vector position, int data,
                            boolean disableRelativeVolume) implements RecordableWorldEvent {

    public static RecWorldEvent of(int effectId, Vector position, int data, boolean disableRelativeVolume) {
        return new RecWorldEvent(
                effectId,
                position,
                data,
                disableRelativeVolume
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of(new ClientboundWorldEventPacket(
                this.effectId,
                this.position,
                this.data,
                this.disableRelativeVolume
        ));
    }
}