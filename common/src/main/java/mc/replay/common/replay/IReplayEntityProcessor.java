package mc.replay.common.replay;

import mc.replay.common.recordables.types.entity.RecEntityDestroy;
import mc.replay.common.recordables.types.entity.RecEntitySpawn;
import mc.replay.common.recordables.types.entity.RecPlayerDestroy;
import mc.replay.common.recordables.types.entity.RecPlayerSpawn;
import org.jetbrains.annotations.NotNull;

public interface IReplayEntityProcessor {

    void spawnPlayer(@NotNull RecPlayerSpawn recordable);

    void destroyPlayer(@NotNull RecPlayerDestroy recordable);

    void spawnEntity(@NotNull RecEntitySpawn recordable);

    void destroyEntity(@NotNull RecEntityDestroy recordable);
}