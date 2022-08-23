//package mc.replay.replay.session.entity;
//
//import lombok.Getter;
//import mc.replay.common.utils.EntityPacketUtils;
//import org.bukkit.Location;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Player;
//
//import java.util.Collection;
//
//@Getter
//public final class ReplayMob extends ReplayEntity<ReplayMob> {
//
//    private final EntityType entityType;
//    private final Location startLocation;
//
//    public ReplayMob(int originalEntityId, Collection<Player> viewers, EntityType entityType, Location startLocation) {
//        super(originalEntityId, viewers);
//
//        this.entityType = entityType;
//        this.startLocation = startLocation;
//    }
//
//    @Override
//    public void spawn(Player viewer) {
//        Location spawnLocation = new Location(
//                viewer.getWorld(),
//                this.startLocation.getX(),
//                this.startLocation.getY(),
//                this.startLocation.getZ(),
//                this.startLocation.getYaw(),
//                this.startLocation.getPitch()
//        );
//
//        Object entity = EntityPacketUtils.spawnEntity(viewer, spawnLocation, this.entityType);
//        this.viewers.put(viewer, entity);
//    }
//
//    @Override
//    public void destroy(Player viewer) {
//        Object entity = this.viewers.get(viewer);
//        if (entity == null) return;
//
//        EntityPacketUtils.destroy(viewer, entity);
//    }
//}