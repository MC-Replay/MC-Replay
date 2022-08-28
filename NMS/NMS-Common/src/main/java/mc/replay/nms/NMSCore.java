package mc.replay.nms;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.nms.player.RecordingFakePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public interface NMSCore {

    void setPacketOutDispatcher(@NotNull Consumer<Object> consumer);

    @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target);

    @Nullable Recordable<? extends Function<?, ?>> createDestroyPlayerRecordable(@NotNull Player player);

    @Nullable Recordable<? extends Function<?, ?>> createSpawnPlayerRecordable(@NotNull Player player);

    @Nullable Recordable<? extends Function<?, ?>> createDestroyEntityRecordable(@NotNull Entity entity);

    @Nullable Recordable<? extends Function<?, ?>> createSpawnEntityRecordable(@NotNull Entity entity);
}