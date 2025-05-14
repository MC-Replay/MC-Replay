package mc.replay.common.recordables.types.chat;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.STRING;

public record RecPlayerChat(EntityId entityId, String playerName, String message) implements Recordable {

    public RecPlayerChat(@NotNull ReplayByteBuffer reader) {
        this(
                new EntityId(reader),
                reader.read(STRING),
                reader.read(STRING)
        );
    }

    @Override
    public void write(@NotNull ReplayByteBuffer writer) {
        writer.write(this.entityId);
        writer.write(STRING, this.playerName);
        writer.write(STRING, this.message);
    }
}