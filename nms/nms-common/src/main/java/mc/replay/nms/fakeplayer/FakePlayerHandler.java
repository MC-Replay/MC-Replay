package mc.replay.nms.fakeplayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FakePlayerHandler implements Listener {

    private final JavaPlugin plugin;
    private final Map<UUID, IRecordingFakePlayer> fakePlayers = new HashMap<>();

    public FakePlayerHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addFakePlayer(@NotNull IRecordingFakePlayer fakePlayer) {
        this.fakePlayers.put(fakePlayer.uuid(), fakePlayer);
    }

    public void removeFakePlayer(@NotNull IRecordingFakePlayer fakePlayer) {
        this.fakePlayers.remove(fakePlayer.uuid());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(this.plugin)) return;

        for (IRecordingFakePlayer recordingFakePlayer : new HashMap<>(this.fakePlayers).values()) {
            recordingFakePlayer.setRecording(false);
            recordingFakePlayer.remove();
        }
    }
}