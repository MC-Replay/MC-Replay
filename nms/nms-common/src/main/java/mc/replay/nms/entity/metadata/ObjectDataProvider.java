package mc.replay.nms.entity.metadata;

public interface ObjectDataProvider {

    int getObjectData();

    boolean requiresVelocityPacketAtSpawn();
}