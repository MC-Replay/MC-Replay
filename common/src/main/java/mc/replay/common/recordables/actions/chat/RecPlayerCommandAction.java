package mc.replay.common.recordables.actions.chat;

import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.common.recordables.types.chat.RecPlayerCommand;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.packetlib.network.packet.clientbound.play.ClientboundSystemChatMessagePacket;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RecPlayerCommandAction implements EmptyRecordableAction<RecPlayerCommand> {

    @Override
    public @NotNull List<@NotNull ClientboundPacket> createPackets(@NotNull RecPlayerCommand recordable, @NotNull Void data) {
        String message = ChatColor.WHITE + "[Replay-command] " + recordable.playerName() + ": " + recordable.command();

        return List.of(
                new ClientboundSystemChatMessagePacket(
                        message,
                        ClientboundSystemChatMessagePacket.ChatPosition.SYSTEM
                )
        );
    }
}