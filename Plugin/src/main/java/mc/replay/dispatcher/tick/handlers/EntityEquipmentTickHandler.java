package mc.replay.dispatcher.tick.handlers;

import mc.replay.MCReplayPlugin;
import mc.replay.dispatcher.tick.ReplayTickHandler;
import mc.replay.common.replay.EntityId;
import mc.replay.nms.v1_16_5.recordable.entity.miscellaneous.RecEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public final class EntityEquipmentTickHandler implements ReplayTickHandler {

    private final Map<LivingEntity, Map<EquipmentSlot, ItemStack>> lastEquipment = new HashMap<>();

    @Override
    public void handle(int currentTick) {
        this.lastEquipment.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity) || !world.isChunkLoaded(entity.getLocation().getChunk()))
                    continue;

                EntityEquipment entityEquipment = livingEntity.getEquipment();
                if (entityEquipment == null) break;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    try {
                        Map<EquipmentSlot, ItemStack> equipment = this.lastEquipment.putIfAbsent(livingEntity, new HashMap<>());
                        if (equipment == null) equipment = new HashMap<>();

                        ItemStack lastItem = equipment.get(equipmentSlot);
                        ItemStack currentItem = entityEquipment.getItem(equipmentSlot);
                        if (lastItem == null && currentItem.getType().isAir()) continue;

                        if (lastItem == null || !lastItem.isSimilar(currentItem)) {
                            RecEntityEquipment recEntityEquipment = RecEntityEquipment.of(entityId, equipmentSlot, currentItem);
                            MCReplayPlugin.getInstance().getReplayStorage().addRecordable(currentTick, recEntityEquipment);
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }
}