package mc.replay.nms.entity;

import mc.replay.nms.MCReplayNMS;
import mc.replay.nms.entity.metadata.EntityMetadata;
import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class REntity {

    protected int id;
    protected final UUID uuid;

    protected final EntityType entityType;

    protected Metadata metadata = new Metadata();
    protected EntityMetadata entityMetadata;

    protected Pos position;

    protected Vector velocity;

    public REntity(@NotNull EntityType entityType, int entityId, @NotNull UUID uuid) {
        this.id = entityId;
        this.uuid = uuid;
        this.entityType = entityType;

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

    public int getEntityId() {
        return this.id;
    }

    public @NotNull UUID getUniqueId() {
        return this.uuid;
    }

    public @NotNull EntityType getType() {
        return this.entityType;
    }

    public EntityMetadata getMetadata() {
        return this.entityMetadata;
    }

    public @NotNull Pos getPosition() {
        return this.position;
    }

    public @NotNull Vector getVelocity() {
        return this.velocity;
    }

    public void setPosition(@NotNull Pos position) {
        this.position = position;
    }

    public void setPosition(double x, double y, double z, float yaw, float pitch) {
        this.setPosition(new Pos(x, y, z, yaw, pitch));
    }

    public void setPosition(double x, double y, double z) {
        this.setPosition(new Pos(x, y, z, this.position.yaw(), this.position.pitch()));
    }

    public void setVelocity(@NotNull Vector velocity) {
        this.velocity = velocity;
    }

    public final <T extends EntityMetadata> @NotNull T getMetaData(@NotNull Class<T> clazz) {
        if (clazz.isAssignableFrom(this.entityMetadata.getClass())) {
            return clazz.cast(this.entityMetadata);
        }

        throw new IllegalArgumentException("The metadata class " + clazz.getName() + " is not assignable from " + this.entityMetadata.getClass().getName());
    }

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