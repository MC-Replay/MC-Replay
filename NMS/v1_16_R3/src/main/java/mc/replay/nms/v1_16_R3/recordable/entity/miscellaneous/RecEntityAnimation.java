package mc.replay.nms.v1_16_R3.recordable.entity.miscellaneous;

import mc.replay.api.recording.recordables.entity.EntityId;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.utils.reflection.JavaReflections;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public record RecEntityAnimation(EntityId entityId, int animation) implements RecordableEntity {

    public static RecEntityAnimation of(EntityId entityId, int animation) {
        return new RecEntityAnimation(
                entityId,
                animation
        );
    }

    @Override
    public @NotNull List<@NotNull Object> createReplayPackets(@NotNull Function<Integer, RecordableEntityData> function) {
        RecordableEntityData data = function.apply(this.entityId.entityId());

        PacketPlayOutAnimation packetPlayOutAnimation = new PacketPlayOutAnimation();

        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "a").set(packetPlayOutAnimation, data.entityId());
        JavaReflections.getField(packetPlayOutAnimation.getClass(), int.class, "b").set(packetPlayOutAnimation, this.animation);

        return List.of(packetPlayOutAnimation);
    }
}