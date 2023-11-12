package mc.replay.nms.entity.metadata.other;

import mc.replay.nms.entity.REntity;
import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.nms.entity.metadata.ObjectDataProvider;
import mc.replay.nms.entity.metadata.ShooterProvider;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishingHookMetadata extends EntityMetadata implements ShooterProvider, ObjectDataProvider {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int HOOKED_ENTITY_INDEX = OFFSET;
    public static final int CATCHABLE_INDEX = OFFSET + 1;

    private REntity hooked;
    private REntity owner;
    private Integer ownerId = null;

    public FishingHookMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setHookedEntity(@Nullable REntity value) {
        this.hooked = value;
        int entityId = (value == null) ? 0 : value.getEntityId() + 1;
        super.metadata.setIndex(HOOKED_ENTITY_INDEX, Metadata.VarInt(entityId));
    }

    public void setCatchable(boolean value) {
        super.metadata.setIndex(CATCHABLE_INDEX, Metadata.Boolean(value));
    }

    public void setOwnerEntity(@Nullable REntity value) {
        this.owner = value;
        this.ownerId = (value == null) ? null : value.getEntityId();
    }

    public @Nullable REntity getHookedEntity() {
        return this.hooked;
    }

    public boolean isCatchable() {
        return super.metadata.getIndex(CATCHABLE_INDEX, false);
    }

    public @Nullable REntity getOwnerEntity() {
        return this.owner;
    }

    @Override
    public @Nullable Integer getShooterId() {
        return this.ownerId;
    }

    @Override
    public void setShooterId(@Nullable Integer shooterId) {
        this.ownerId = shooterId;
    }

    @Override
    public int getObjectData() {
        return (this.ownerId == null) ? 0 : this.ownerId;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}