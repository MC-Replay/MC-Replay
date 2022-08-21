package mc.replay.nms.global.recordable;

import mc.replay.common.recordables.RecordablePlayerAction;
import mc.replay.common.replay.EntityId;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record RecPlayerCommand(EntityId entityId, String playerName, String command) implements RecordablePlayerAction {

    public static RecPlayerCommand of(EntityId entityId, String playerName, String command) {
        System.out.println("REPLAY CREATED: " + command);
        return new RecPlayerCommand(entityId, playerName, command);
    }

    @Override
    public void play(Player viewer) {
        System.out.println("CHAT: " + this.command);
        viewer.sendMessage(ChatColor.WHITE + "[Replay-command] " + this.playerName + ": " + this.command);
    }
}