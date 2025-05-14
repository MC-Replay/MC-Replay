package mc.replay.nms.entity;

import mc.replay.nms.entity.metadata.LivingEntityMetadata;
import mc.replay.nms.inventory.RItem;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RLivingEntity extends REntity {

    protected final Map<EquipmentSlot, RItem> equipment = new HashMap<>();

    public RLivingEntity(@NotNull EntityType entityType, int entityId, @NotNull UUID uuid) {
        super(entityType, entityId, uuid);
    }

    public RLivingEntity(@NotNull EntityType entityType, @NotNull UUID uuid) {
        super(entityType, uuid);
    }

    public RLivingEntity(@NotNull LivingEntity entity) {
        super(entity);

        if (entity.getEquipment() != null) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack item = entity.getEquipment().getItem(slot);
                if (item == null || item.getType().isAir()) continue;

                this.equipment.put(slot, new RItem(item));
            }
        }
    }

    public @Nullable RItem getEquipment(@NotNull EquipmentSlot slot) {
        return this.equipment.get(slot);
    }

    public @NotNull Map<EquipmentSlot, RItem> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(@NotNull EquipmentSlot slot, @NotNull RItem item) {
        this.equipment.put(slot, item);
    }

    @Override
    public @NotNull LivingEntityMetadata getMetadata() {
        return this.getMetaData(LivingEntityMetadata.class);
    }

    @Override
    public @NotNull RLivingEntity withUniqueId() {
        return (RLivingEntity) super.withUniqueId();
    }
}