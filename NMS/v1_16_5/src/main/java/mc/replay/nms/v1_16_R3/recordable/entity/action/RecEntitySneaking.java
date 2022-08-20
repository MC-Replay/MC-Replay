package mc.replay.nms.v1_16_R3.recordable.entity.action;

import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.replay.EntityId;
import mc.replay.common.replay.ReplayEntity;
import mc.replay.common.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityPose;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import org.bukkit.entity.Player;

public record RecEntitySneaking(EntityId entityId, boolean sneaking) implements RecordableEntity {

    public static RecEntitySneaking of(EntityId entityId, boolean sneaking) {
        return new RecEntitySneaking(entityId, sneaking);
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
        entityPlayer.getDataWatcher().set(DataWatcherRegistry.s.a(6), this.sneaking ? EntityPose.CROUCHING : EntityPose.STANDING);

        return new PacketPlayOutEntityMetadata(
                entityId,
                entityPlayer.getDataWatcher(),
                true
        );
    }
}