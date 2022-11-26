package mc.replay.nms.v1_16_R3.dispatcher.event.entity;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherEvent;
import mc.replay.nms.global.recordable.RecEntitySpawn;
import mc.replay.nms.v1_16_R3.player.RecordingFakePlayerImpl;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.function.Function;

public final class ReplayEntitySpawnEventListener implements DispatcherEvent<EntitySpawnEvent> {

    @Override
    public EventPriority getPriority() {
        return EventPriority.MONITOR;
    }

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        net.minecraft.server.v1_16_R3.Entity craftEntity = ((CraftEntity) entity).getHandle();
        if (entity instanceof Player || entity instanceof NPC || craftEntity instanceof RecordingFakePlayerImpl)
            return null;

        // TODO check if entity is replay entity

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());
        return List.of(RecEntitySpawn.of(entityId, entity.getType(), entity.getLocation(), craftEntity.getDataWatcher(), entity.getVelocity()));
    }
}