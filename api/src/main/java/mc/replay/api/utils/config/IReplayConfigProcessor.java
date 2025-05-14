package mc.replay.api.utils.config;

import mc.replay.api.utils.config.templates.IReplayConfigStructure;

public interface IReplayConfigProcessor<T extends Enum<? extends IReplayConfigStructure>> {

    void set(T type, Object value) throws Exception;

    Object get(T type);

    String getString(T type);

    boolean getBoolean(T type);

    int getInteger(T type);

    void reload(T... types) throws Exception;

}
