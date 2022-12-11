package mc.replay.nms.v1_16_R3;

import mc.replay.nms.NMSCore;
import mc.replay.nms.player.RecordingFakePlayer;
import mc.replay.nms.v1_16_R3.player.RecordingFakePlayerImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class NMSCoreImpl implements NMSCore {

    @Override
    public @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target) {
        return new RecordingFakePlayerImpl(target);
    }
}