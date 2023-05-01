package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Dispatcher<T> {

    protected final DispatcherHelpers helpers;

    public Dispatcher(DispatcherHelpers helpers) {
        this.helpers = helpers;
    }

    public abstract List<Recordable> getRecordables(RecordingSession session, T obj);

    @SuppressWarnings("unchecked")
    public final Class<T> getInputClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}