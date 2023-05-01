package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.api.recordables.Recordable;
import mc.replay.recording.RecordingSession;

import java.lang.reflect.ParameterizedType;
import java.util.List;

interface Dispatcher<T> {

    List<Recordable> getRecordables(RecordingSession session, T obj);

    @SuppressWarnings("unchecked")
    default Class<T> getInputClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}