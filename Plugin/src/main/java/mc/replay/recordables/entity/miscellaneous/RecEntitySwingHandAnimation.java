package mc.replay.recordables.entity.miscellaneous;

import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public record RecEntitySwingHandAnimation(EntityId entityId, EquipmentSlot hand) implements EntityRecordable {

    public static RecEntitySwingHandAnimation of(EntityId entityId, EquipmentSlot hand) {
        return new RecEntitySwingHandAnimation(
                entityId,
                hand
        );
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        Object packet = this.createPacket(entityId);
        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        return this.play(viewer, replayEntity, entity, entityId);
    }

    private Object createPacket(int entityId) {
        if (this.hand != EquipmentSlot.HAND && this.hand != EquipmentSlot.OFF_HAND) return null;

        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "a").set(packetPlayOutAnimation, entityId);
        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "b").set(packetPlayOutAnimation, (this.hand == EquipmentSlot.HAND) ? 0 : 3);

        return packetPlayOutAnimation;
    }
}