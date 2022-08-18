package mc.replay.recordables.entity.action;

import mc.replay.recordables.entity.EntityId;
import mc.replay.recordables.entity.EntityRecordable;
import mc.replay.replay.entity.ReplayEntity;
import mc.replay.utils.reflection.nms.MinecraftPlayerNMS;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import org.bukkit.entity.Player;

public record RecEntitySwimming(EntityId entityId, boolean swimming) implements EntityRecordable {

    public static RecEntitySwimming of(EntityId entityId, boolean swimming) {
        return new RecEntitySwimming(entityId, swimming);
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
        entityPlayer.setFlag(4, this.swimming);

        return new PacketPlayOutEntityMetadata(
                entityId,
                entityPlayer.getDataWatcher(),
                true
        );
    }
}