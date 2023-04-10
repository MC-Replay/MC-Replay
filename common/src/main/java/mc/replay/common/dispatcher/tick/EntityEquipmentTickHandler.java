package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.api.utils.FakePlayerUUID;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.types.entity.miscellaneous.RecEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityEquipmentTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Map<EquipmentSlot, ItemStack>> lastEquipment = new HashMap<>();

    @Override
    public List<Recordable> getRecordables(Integer currentTick) {
        List<Recordable> recordables = new ArrayList<>();

        this.lastEquipment.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                if (FakePlayerUUID.UUIDS.contains(entity.getUniqueId())) continue;
                if (!world.isChunkLoaded(entity.getLocation().getChunk())) continue;

                EntityEquipment entityEquipment = livingEntity.getEquipment();
                if (entityEquipment == null) continue;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    try {
                        Map<EquipmentSlot, ItemStack> equipment = this.lastEquipment.putIfAbsent(livingEntity, new HashMap<>());
                        if (equipment == null) equipment = new HashMap<>();

                        ItemStack lastItem = equipment.get(equipmentSlot);
                        ItemStack currentItem = entityEquipment.getItem(equipmentSlot);
                        if (lastItem == null && currentItem.getType().isAir()) continue;

                        if (!currentItem.isSimilar(lastItem)) {
                            equipment.put(equipmentSlot, currentItem);
                            recordables.add(new RecEntityEquipment(entityId, equipmentSlot, currentItem));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        return recordables;
    }
}