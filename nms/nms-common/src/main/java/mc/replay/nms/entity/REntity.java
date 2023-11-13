package mc.replay.nms.entity;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.api.data.entity.IREntity;
import mc.replay.mappings.mapped.MappedEntityType;
import mc.replay.mappings.objects.EntityTypeMapping;
import mc.replay.nms.MCReplayNMS;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class REntity implements IREntity {

    protected int id;
    protected final UUID uuid;

    protected final MappedEntityType entityType;

    protected Metadata metadata = new Metadata();
    protected RMetadata entityMetadata;

    protected Pos position;

    protected Vector velocity;

    public REntity(@NotNull EntityType entityType, int entityId, @NotNull UUID uuid) {
        this.id = entityId;
        this.uuid = uuid;
        this.entityType = new MappedEntityType(entityType);

        this.position = new Pos(0, 0, 0, 0, 0);
        this.velocity = new Vector(0, 0, 0);

        this.entityMetadata = EntityTypes.createMetadata(entityType, this.metadata);
    }

    public REntity(@NotNull EntityType entityType, @NotNull UUID uuid) {
        this(entityType, MCReplayNMS.getInstance().getNewEntityId(), uuid);
    }

    public REntity(@NotNull Entity entity) {
        this(entity.getType(), entity.getEntityId(), entity.getUniqueId());

        this.position = Pos.from(entity.getLocation());
        this.velocity = entity.getVelocity();

        this.readDataWatcherItems(entity);
    }

    @Override
    public int getEntityId() {
        return this.id;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public @NotNull EntityType getType() {
        return this.entityType.bukkit();
    }

    public @NotNull EntityTypeMapping getMappedType() {
        return this.entityType.mapping();
    }

    @Override
    public @NotNull RMetadata getMetadata() {
        return this.entityMetadata;
    }

    @Override
    public @NotNull Pos getPosition() {
        return this.position;
    }

    @Override
    public @NotNull Vector getVelocity() {
        return this.velocity;
    }

    @Override
    public void setPosition(@NotNull Pos position) {
        this.position = position;
    }

    @Override
    public void setPosition(double x, double y, double z, float yaw, float pitch) {
        this.setPosition(new Pos(x, y, z, yaw, pitch));
    }

    @Override
    public void setPosition(double x, double y, double z) {
        this.setPosition(new Pos(x, y, z, this.position.yaw(), this.position.pitch()));
    }

    @Override
    public void setVelocity(@NotNull Vector velocity) {
        this.velocity = velocity;
    }

    public final <T extends RMetadata> @NotNull T getMetaData(@NotNull Class<T> clazz) {
        if (clazz.isAssignableFrom(this.entityMetadata.getClass())) {
            return clazz.cast(this.entityMetadata);
        }

        throw new IllegalArgumentException("The metadata class " + clazz.getName() + " is not assignable from " + this.entityMetadata.getClass().getName());
    }

    @Override
    public void addMetadata(@NotNull Map<@NotNull Integer, Metadata.Entry<?>> entries) {
        for (Map.Entry<Integer, Metadata.Entry<?>> entry : entries.entrySet()) {
            this.metadata.setIndex(entry.getKey(), entry.getValue());
        }
    }

    private void readDataWatcherItems(@NotNull Entity entity) {
        Map<Integer, Metadata.Entry<?>> entries = MCReplayNMS.getInstance().readDataWatcher(entity);
        for (Map.Entry<Integer, Metadata.Entry<?>> entry : entries.entrySet()) {
            this.metadata.setIndex(entry.getKey(), entry.getValue());
        }
    }

    public @NotNull REntity withUniqueId() {
        this.id = MCReplayNMS.getInstance().getNewEntityId();
        return this;
    }
}