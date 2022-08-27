package mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.utils.reflection.JavaReflections;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntitySwingHandAnimation(EntityId entityId, EquipmentSlot hand) implements RecordableEntity {

    public static RecEntitySwingHandAnimation of(EntityId entityId, EquipmentSlot hand) {
        return new RecEntitySwingHandAnimation(
                entityId,
                hand
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "a").set(packetPlayOutAnimation, data.entityId());
        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "b").set(packetPlayOutAnimation, (this.hand == EquipmentSlot.HAND) ? 0 : 3);

        return List.of(packetPlayOutAnimation);
    }
}