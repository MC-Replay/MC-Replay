package mc.replay.nms.v1_16_R3.dispatcher.event.player;

import com.mojang.authlib.properties.Property;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.nms.global.recordable.RecPlayerSpawn;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayPlayerJoinEventListener implements DispatcherEvent<PlayerJoinEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        Property skinTexture = entityPlayer.getProfile().getProperties().get("textures").stream()
                .findFirst()
                .orElse(null);

        EntityId entityId = EntityId.of(player.getUniqueId(), player.getEntityId());
        return List.of(RecPlayerSpawn.of(entityId, player.getName(), skinTexture, player.getLocation()));
    }
}