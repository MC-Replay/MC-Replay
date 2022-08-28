package mc.replay.common.recordables;

import mc.replay.api.recording.RecordingSession;
import mc.replay.api.recording.recordables.Recordable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface RecordableOther extends Recordable<Function<Void, Void>> {

    @Override
    @NotNull
    default Function<@NotNull RecordingSession, @NotNull Boolean> shouldRecord() {
        // TODO
        return Recordable.super.shouldRecord();
    }
}