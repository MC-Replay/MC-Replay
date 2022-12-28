package mc.replay.common.recordables.interfaces;

import mc.replay.api.recording.recordables.Recordable;

import java.util.function.Function;

public interface RecordableWorldEvent extends Recordable<Function<Void, Void>> {
}