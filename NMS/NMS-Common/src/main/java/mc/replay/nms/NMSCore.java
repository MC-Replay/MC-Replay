package mc.replay.nms;

import mc.replay.nms.player.RecordingFakePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface NMSCore {

    void setPacketOutDispatcher(@NotNull Consumer<Object> consumer);

    @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target);
}