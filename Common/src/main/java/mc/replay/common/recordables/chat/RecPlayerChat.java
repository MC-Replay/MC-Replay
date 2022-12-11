//package mc.replay.common.recordables.chat;
//
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.recordables.RecordablePlayerAction;
//import net.minecraft.server.v1_16_R3.ChatMessageType;
//import net.minecraft.server.v1_16_R3.IChatBaseComponent;
//import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
//import net.minecraft.server.v1_16_R3.SystemUtils;
//import org.bukkit.ChatColor;
//import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//public record RecPlayerChat(EntityId entityId, String playerName, String message) implements RecordablePlayerAction {
//
//    public static RecPlayerChat of(EntityId entityId, String playerName, String message) {
//        return new RecPlayerChat(entityId, playerName, message);
//    }
//
//    @Override
//    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Void, Void> function) {
//        List<Object> packets = new ArrayList<>();
//
//        String message = ChatColor.WHITE + "[Replay-chat] " + this.playerName + ": " + this.message;
//
//        IChatBaseComponent[] components = CraftChatMessage.fromString(message);
//        for (IChatBaseComponent component : components) {
//            packets.add(new PacketPlayOutChat(component, ChatMessageType.SYSTEM, SystemUtils.b));
//        }
//
//        return packets;
//    }
//}