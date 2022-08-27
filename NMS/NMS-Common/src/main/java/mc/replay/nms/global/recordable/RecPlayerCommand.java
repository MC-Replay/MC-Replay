package mc.replay.nms.global.recordable;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordablePlayerAction;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import net.minecraft.server.v1_16_R3.SystemUtils;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record RecPlayerCommand(EntityId entityId, String playerName, String command) implements RecordablePlayerAction {

    public static RecPlayerCommand of(EntityId entityId, String playerName, String command) {
        return new RecPlayerCommand(entityId, playerName, command);
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
        List<Object> packets = new ArrayList<>();

        String message = ChatColor.WHITE + "[Replay-command] " + this.playerName + ": " + this.command;

        IChatBaseComponent[] components = CraftChatMessage.fromString(message);
        for (IChatBaseComponent component : components) {
            packets.add(new PacketPlayOutChat(component, ChatMessageType.SYSTEM, SystemUtils.b));
        }

        return packets;
    }
}