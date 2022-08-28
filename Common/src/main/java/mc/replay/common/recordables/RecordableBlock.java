package mc.replay.common.recordables;

import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface RecordableBlock extends Recordable<Function<Void, Void>> {

    @NotNull Vector blockPosition();

    @Override
    @NotNull
    default Function<@NotNull RecordingSession, @NotNull Boolean> shouldRecord() {
        Vector position = this.blockPosition();
        return (session) -> session.isInsideRecordingRange(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }
}