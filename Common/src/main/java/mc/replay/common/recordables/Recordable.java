package mc.replay.common.recordables;

import org.jetbrains.annotations.NotNull;

public interface Recordable {

    boolean match(@NotNull Object object);
}