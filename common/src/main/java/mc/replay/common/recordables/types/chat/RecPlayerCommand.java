package mc.replay.common.recordables.types.chat;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.packetlib.network.ReplayByteBuffer;
import org.jetbrains.annotations.NotNull;

import static mc.replay.packetlib.network.ReplayByteBuffer.STRING;

public record RecPlayerCommand(EntityId entityId, String playerName, String command) implements Recordable {

    public RecPlayerCommand(@NotNull ReplayByteBuffer reader) {
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
        writer.write(STRING, this.command);
    }
}