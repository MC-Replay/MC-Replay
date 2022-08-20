package mc.replay.dispatcher.event.listeners.player;

import com.mojang.authlib.properties.Property;
import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.event.ReplayEventListener;
import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.connection.RecPlayerJoin;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class ReplayPlayerJoinEventListener implements ReplayEventListener<PlayerJoinEvent> {

    @Override
    public Class<PlayerJoinEvent> eventClass() {
        return PlayerJoinEvent.class;
    }

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public void listen(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Property skinTexture = entityPlayer.getProfile().getProperties().get("textures").stream().findFirst().orElse(null);

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        RecPlayerJoin recPlayerJoin = RecPlayerJoin.of(entityId, player.getName(), skinTexture, player.getLocation());

        MCReplayPlugin.getInstance().getReplayStorage().addRecordable(recPlayerJoin);
    }
}