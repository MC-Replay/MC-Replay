package mc.replay.common.recordables.entity;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.wrapper.data.SkinTexture;
import mc.replay.wrapper.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record RecPlayerSpawn(EntityId entityId, String name, SkinTexture skinTexture,
                             Pos position, Map<Integer, Metadata.Entry<?>> metadata) implements RecordableOther {

    public static RecPlayerSpawn of(EntityId entityId, PlayerWrapper playerWrapper) {
        // TODO get all needed information (equipment, etc)
        return new RecPlayerSpawn(
                entityId,
                playerWrapper.getUsername(),
                playerWrapper.getSkin(),
                playerWrapper.getPosition(),
                playerWrapper.getMetadata().getEntries()
        );
    }

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createReplayPackets(@NotNull Function<Void, Void> function) {
        return List.of();
    }
}