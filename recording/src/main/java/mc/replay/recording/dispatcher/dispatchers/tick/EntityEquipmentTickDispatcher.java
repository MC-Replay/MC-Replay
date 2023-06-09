package mc.replay.recording.dispatcher.dispatchers.tick;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.equipment.*;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.dispatchers.DispatcherTick;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EntityEquipmentTickDispatcher extends DispatcherTick {

    private final Map<LivingEntity, Map<EquipmentSlot, ItemStack>> lastEquipment = new HashMap<>();

    private EntityEquipmentTickDispatcher(DispatcherHelpers helpers) {
        super(helpers);
    }

    @Override
    public void onTickGlobal(Integer currentTick) {
        this.lastEquipment.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());
    }

    @Override
    public List<Recordable> getRecordables(RecordingSession session, int currentTick, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) return null;

        List<Recordable> recordables = new ArrayList<>();

        EntityEquipment entityEquipment = livingEntity.getEquipment();
        if (entityEquipment == null) return null;

        EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            Map<EquipmentSlot, ItemStack> equipment = this.lastEquipment.putIfAbsent(livingEntity, new HashMap<>());
            if (equipment == null) equipment = new HashMap<>();

            ItemStack lastItem = equipment.get(equipmentSlot);
            ItemStack currentItem = entityEquipment.getItem(equipmentSlot);
            if (lastItem == null && (currentItem == null || currentItem.getType().isAir())) continue;

            if (currentItem == null || !currentItem.isSimilar(lastItem)) {
                if (currentItem == null) currentItem = new ItemStack(Material.AIR);

                equipment.put(equipmentSlot, currentItem);
                Recordable recordable = switch (equipmentSlot) {
                    case HAND -> new RecEntityHand(entityId, currentItem);
                    case OFF_HAND -> new RecEntityOffHand(entityId, currentItem);
                    case FEET -> new RecEntityBoots(entityId, currentItem);
                    case LEGS -> new RecEntityLeggings(entityId, currentItem);
                    case CHEST -> new RecEntityChestplate(entityId, currentItem);
                    case HEAD -> new RecEntityHelmet(entityId, currentItem);
                };

                recordables.add(recordable);
            }
        }

        return recordables;
    }
}