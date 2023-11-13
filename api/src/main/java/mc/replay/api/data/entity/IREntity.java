package mc.replay.api.data.entity;

import mc.replay.packetlib.data.Pos;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface IREntity {

    int getEntityId();

    @NotNull UUID getUniqueId();

    @NotNull EntityType getType();

    @NotNull RMetadata getMetadata();

    @NotNull Pos getPosition();

    @NotNull Vector getVelocity();

    void setPosition(@NotNull Pos position);

    void setPosition(double x, double y, double z, float yaw, float pitch);

    void setPosition(double x, double y, double z);

    void setVelocity(@NotNull Vector velocity);

    void addMetadata(@NotNull Map<@NotNull Integer, Metadata.Entry<?>> entries);
}