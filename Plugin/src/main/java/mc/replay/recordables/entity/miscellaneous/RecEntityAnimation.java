package mc.replay.recordables.entity.miscellaneous;

import mc.replay.common.replay.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.entity.Player;

public record RecEntityAnimation(EntityId entityId, int animation) implements EntityRecordable {

    public static RecEntityAnimation of(EntityId entityId, int animation) {
        return new RecEntityAnimation(
                entityId,
                animation
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
        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "a").set(packetPlayOutAnimation, entityId);
        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "b").set(packetPlayOutAnimation, this.animation);

        return packetPlayOutAnimation;
    }
}