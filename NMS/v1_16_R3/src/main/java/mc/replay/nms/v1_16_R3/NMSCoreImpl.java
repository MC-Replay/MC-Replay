package mc.replay.nms.v1_16_R3;

import mc.replay.nms.NMSCore;
import mc.replay.nms.player.RecordingFakePlayer;
import mc.replay.nms.v1_16_R3.player.RecordingFakePlayerImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class NMSCoreImpl implements NMSCore {

    private Consumer<Object> packetOutDispatcher = null;

    @Override
    public void setPacketOutDispatcher(@NotNull Consumer<Object> consumer) {
        this.packetOutDispatcher = consumer;
    }

    @Override
    public @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target) {
        RecordingFakePlayerImpl recordingFakePlayer = new RecordingFakePlayerImpl(target);
        if (this.packetOutDispatcher != null) {
            recordingFakePlayer.setPacketOutDispatcher(this.packetOutDispatcher);
        }
        return recordingFakePlayer;
    }
}