package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.types.entity.action.RecEntityCombust;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataTickHandler implements DispatcherTick {

    private final Map<Entity, Integer> lastFireTicks = new HashMap<>();

    @Override
    public List<Recordable> getRecordables(Integer currentTick) {
        List<Recordable> recordables = new ArrayList<>();

        this.lastFireTicks.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!world.isChunkLoaded(entity.getLocation().getChunk())) continue;

                EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());

                int fireTicks = this.lastFireTicks.getOrDefault(entity, -1);
                if (fireTicks <= 0 && entity.getFireTicks() > 0) {
                    recordables.add(new RecEntityCombust(entityId, true));
                } else if (fireTicks > 0 && entity.getFireTicks() <= 0) {
                    recordables.add(new RecEntityCombust(entityId, false));
                }

                this.lastFireTicks.put(entity, entity.getFireTicks());
            }
        }

        return recordables;
    }
}