//package mc.replay.common.recordables.entity.action;
//
//import mc.replay.api.recording.recordables.entity.EntityId;
//import mc.replay.common.recordables.RecordableEntity;
//import net.minecraft.server.v1_16_R3.EntityPlayer;
//import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//import java.util.function.Function;
//
//public record RecEntityGliding(EntityId entityId, boolean gliding) implements RecordableEntity {
//
//    public static RecEntityGliding of(EntityId entityId, boolean gliding) {
//        return new RecEntityGliding(entityId, gliding);
//    }
//
//    @Override
//    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
//        RecordableEntityData data = function.apply(this.entityId.entityId());
//
//        EntityPlayer entityPlayer = (EntityPlayer) data.entityPlayer();
//        entityPlayer.setFlag(7, this.gliding);
//
//        return List.of(new PacketPlayOutEntityMetadata(data.entityId(), entityPlayer.getDataWatcher(), true));
//    }
//}