package mc.replay.api.recordable;

import org.jetbrains.annotations.NotNull;

public interface Recordable {

    boolean match(@NotNull Object object);
}