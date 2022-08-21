package mc.replay.common.dispatcher;

import mc.replay.common.recordables.Recordable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface Dispatcher<T> {

    List<Recordable> getRecordables(T obj);

    default Class<?> getInputClass() {
        return (Class<?>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}