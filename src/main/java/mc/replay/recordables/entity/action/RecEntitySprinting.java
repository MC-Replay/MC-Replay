package mc.replay.recordables.entity.action;

import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import org.bukkit.entity.Player;

public record RecEntitySprinting(EntityId entityId, boolean sprinting) implements EntityRecordable {

    public static RecEntitySprinting of(EntityId entityId, boolean sprinting) {
        return new RecEntitySprinting(entityId, sprinting);
    }

    @Override
    public ReplayEntity<?> play(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId) {
        Object packet = this.createPacket(entityId, entity);
        MinecraftPlayerNMS.sendPacket(viewer, packet);
        return replayEntity;
    }

    @Override
    public ReplayEntity<?> jumpInTime(Player viewer, ReplayEntity<?> replayEntity, Object entity, int entityId, boolean forward) {
        return this.play(viewer, replayEntity, entity, entityId);
    }

    private Object createPacket(int entityId, Object entity) {
        EntityPlayer entityPlayer = (EntityPlayer) entity;
        entityPlayer.setFlag(3, this.sprinting);

        return new PacketPlayOutEntityMetadata(
                entityId,
                entityPlayer.getDataWatcher(),
                true
        );
    }
}