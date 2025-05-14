package mc.replay.common.recordables.actions.chat;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.chat.RecPlayerChat;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSystemChatMessagePacket;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RecPlayerChatAction implements EmptyRecordableAction<RecPlayerChat> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerChat recordable, @NotNull Void data) {
        String message = ChatColor.WHITE + "[Replay-chat] " + recordable.playerName() + ": " + recordable.message();

        return List.of(
                new ClientboundSystemChatMessagePacket(
                        message,
                        ClientboundSystemChatMessagePacket.ChatPosition.SYSTEM
                )
        );
    }
}