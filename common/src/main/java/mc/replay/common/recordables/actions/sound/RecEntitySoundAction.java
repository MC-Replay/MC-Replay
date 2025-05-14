package mc.replay.common.recordables.actions.sound;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.sound.RecEntitySound;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundEntitySoundEffectPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RecEntitySoundAction implements EmptyRecordableAction<RecEntitySound> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecEntitySound recordable, @NotNull Void data) {
        return List.of(
                new ClientboundEntitySoundEffectPacket(
                        recordable.soundId(),
                        recordable.sourceId(),
                        recordable.entityId(),
                        recordable.volume(),
                        recordable.pitch()
                )
        );
    }
}