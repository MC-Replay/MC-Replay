package mc.replay.common.dispatcher.tick;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.types.entity.action.RecEntityCombust;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityMetadataTickDispatcher implements DispatcherTick {

    private final Map<Entity, Integer> lastFireTicks = new HashMap<>();

    @Override
    public void onTickGlobal(Integer currentTick) {
        this.lastFireTicks.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());
    }

    @Override
    public List<Recordable> getRecordables(int currentTick, Entity entity) {
        List<Recordable> recordables = new ArrayList<>();

        EntityId entityId = EntityId.of(entity.getUniqueId(), entity.getEntityId());

        int fireTicks = this.lastFireTicks.getOrDefault(entity, -1);
        if (fireTicks <= 0 && entity.getFireTicks() > 0) {
            recordables.add(new RecEntityCombust(entityId, true));
        } else if (fireTicks > 0 && entity.getFireTicks() <= 0) {
            recordables.add(new RecEntityCombust(entityId, false));
        }

        this.lastFireTicks.put(entity, entity.getFireTicks());
        return recordables;
    }
}