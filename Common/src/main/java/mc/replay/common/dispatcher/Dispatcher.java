package mc.replay.common.dispatcher;

import mc.replay.common.recordables.Recordable;

import java.lang.reflect.ParameterizedType;

public interface Dispatcher<T> {

    Recordable getRecordable(T obj);

    default Class<?> getInputClass() {
        return (Class<?>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
