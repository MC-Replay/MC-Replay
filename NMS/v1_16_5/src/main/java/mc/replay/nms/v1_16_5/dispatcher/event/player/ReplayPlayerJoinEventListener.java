package mc.replay.nms.v1_16_5.dispatcher.event.player;

import com.mojang.authlib.properties.Property;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.common.recordables.Recordable;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.global.recordable.RecPlayerJoin;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class ReplayPlayerJoinEventListener implements DispatcherEvent<PlayerJoinEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public boolean ignoreCancelled() {
        return true;
    }

    @Override
    public List<Recordable> getRecordable(Object eventClass) {
        PlayerJoinEvent event = (PlayerJoinEvent) eventClass;
        Player player = event.getPlayer();

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Property skinTexture = entityPlayer.getProfile().getProperties().get("textures").stream().findFirst().orElse(null);

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecPlayerJoin.of(entityId, player.getName(), skinTexture, player.getLocation()));
    }
}