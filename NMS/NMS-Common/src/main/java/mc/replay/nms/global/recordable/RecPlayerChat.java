package mc.replay.nms.global.recordable;

import mc.replay.common.recordables.RecordablePlayerAction;
import mc.replay.api.recording.recordables.entity.EntityId;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record RecPlayerChat(EntityId entityId, String playerName, String message) implements RecordablePlayerAction {

    public static RecPlayerChat of(EntityId entityId, String playerName, String message) {
        return new RecPlayerChat(entityId, playerName, message);
    }

    @Override
    public void play(Player viewer) {
        viewer.sendMessage(ChatColor.WHITE + "[Replay-chat] " + this.playerName + ": " + this.message);
    }
}