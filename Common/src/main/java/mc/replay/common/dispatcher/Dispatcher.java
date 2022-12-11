package mc.replay.common.dispatcher;

import mc.replay.api.recording.recordables.Recordable;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;

public interface Dispatcher<T> {

    List<Recordable<? extends Function<?, ?>>> getRecordables(T obj);

    @SuppressWarnings("unchecked")
    default Class<T> getInputClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}