package mc.replay.common.recordables;

import mc.replay.api.recording.recordables.Recordable;

import java.util.function.Function;

public interface RecordableBlock extends Recordable<Function<Void, Void>> {
}