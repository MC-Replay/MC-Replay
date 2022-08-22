package mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous;

import com.mojang.datafixers.util.Pair;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public record RecEntityEquipment(EntityId entityId, EquipmentSlot slot,
                                 org.bukkit.inventory.ItemStack itemStack) implements RecordableEntity {

    public static RecEntityEquipment of(EntityId entityId, EquipmentSlot slot, org.bukkit.inventory.ItemStack itemStack) {
        return new RecEntityEquipment(
                entityId,
                slot,
                itemStack
        );
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        EntityLiving livingEntity = (EntityLiving) entity;
        EntityEquipment equipment = ((CraftLivingEntity) livingEntity.getBukkitEntity()).getEquipment();
        if (equipment == null) return replayEntity;

        equipment.setItem(this.slot, this.itemStack);

        Object packet = this.createPacket(entityId, equipment);
        if (packet == null) return replayEntity;

        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        return this.play(viewer, replayEntity, entity, entityId);
    }

    private Object createPacket(int entityId, EntityEquipment equipment) {
        try {
            PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment();

            List<Pair<EnumItemSlot, ItemStack>> equipments = new ArrayList<>();
            equipments.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(equipment.getHelmet())));
            equipments.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(equipment.getChestplate())));
            equipments.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(equipment.getLeggings())));
            equipments.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(equipment.getBoots())));
            equipments.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(equipment.getItemInMainHand())));
            equipments.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(equipment.getItemInOffHand())));

            JavaReflections.getField(packetPlayOutEntityEquipment.getClass(), int.class, "a").set(packetPlayOutEntityEquipment, entityId);
            JavaReflections.getField(packetPlayOutEntityEquipment.getClass(), List.class, "b").set(packetPlayOutEntityEquipment, equipments);

            return packetPlayOutEntityEquipment;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}