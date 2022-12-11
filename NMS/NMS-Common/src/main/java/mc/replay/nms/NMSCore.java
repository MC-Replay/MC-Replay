package mc.replay.nms;

import mc.replay.nms.player.RecordingFakePlayer;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface NMSCore {

    @NotNull RecordingFakePlayer createFakeRecordingPlayer(@NotNull Player target);
}