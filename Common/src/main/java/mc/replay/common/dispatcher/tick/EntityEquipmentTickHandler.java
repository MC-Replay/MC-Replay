package mc.replay.common.dispatcher.tick;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.dispatcher.DispatcherTick;
import mc.replay.common.recordables.entity.miscellaneous.RecEntityEquipment;
import mc.replay.common.utils.FakePlayerUUID;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class EntityEquipmentTickHandler implements DispatcherTick {

    private final Map<LivingEntity, Map<EquipmentSlot, ItemStack>> lastEquipment = new HashMap<>();

    @Override
    public List<Recordable<? extends Function<?, ?>>> getRecordables(Integer currentTick) {
        List<Recordable<? extends Function<?, ?>>> recordables = new ArrayList<>();

        this.lastEquipment.entrySet().removeIf((entry) -> entry.getKey() == null || entry.getKey().isDead());

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity livingEntity) || !world.isChunkLoaded(entity.getLocation().getChunk()))
                    continue;

                net.minecraft.server.v1_16_R3.EntityLiving entityLiving = (EntityLiving) ((CraftEntity) livingEntity).getHandle();
                if (FakePlayerUUID.UUIDS.contains(entity.getUniqueId())) continue;

                EntityId entityId = EntityId.of(livingEntity.getUniqueId(), livingEntity.getEntityId());

                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    try {
                        Map<EquipmentSlot, ItemStack> equipment = this.lastEquipment.putIfAbsent(livingEntity, new HashMap<>());
                        if (equipment == null) equipment = new HashMap<>();

                        ItemStack lastItem = equipment.get(equipmentSlot);
                        ItemStack currentItem = CraftItemStack.asBukkitCopy(entityLiving.getEquipment(this.getSlot(equipmentSlot)));
                        if (lastItem == null && currentItem.getType().isAir()) continue;

                        if (!currentItem.isSimilar(lastItem)) {
                            equipment.put(equipmentSlot, currentItem);
                            recordables.add(RecEntityEquipment.of(entityId, equipmentSlot, currentItem));
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        return recordables;
    }

    private EnumItemSlot getSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HAND -> EnumItemSlot.MAINHAND;
            case OFF_HAND -> EnumItemSlot.OFFHAND;
            case HEAD -> EnumItemSlot.HEAD;
            case CHEST -> EnumItemSlot.CHEST;
            case LEGS -> EnumItemSlot.LEGS;
            case FEET -> EnumItemSlot.FEET;
        };
    }
}