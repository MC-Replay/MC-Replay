package mc.replay.nms.global.recordable;

import mc.replay.common.recordables.RecordablePlayerAction;
import mc.replay.api.recording.recordables.entity.EntityId;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record RecPlayerCommand(EntityId entityId, String playerName, String command) implements RecordablePlayerAction {

    public static RecPlayerCommand of(EntityId entityId, String playerName, String command) {
        return new RecPlayerCommand(entityId, playerName, command);
    }

    @Override
    public void play(Player viewer) {
        viewer.sendMessage(ChatColor.WHITE + "[Replay-command] " + this.playerName + ": " + this.command);
    }
}