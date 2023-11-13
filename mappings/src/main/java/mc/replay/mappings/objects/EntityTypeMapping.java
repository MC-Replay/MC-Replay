package mc.replay.mappings.objects;

public record EntityTypeMapping(String key, int id, PacketType packetType) implements Mapping {

    public boolean isBase() {
        return this.packetType == PacketType.BASE;
    }

    public boolean isExperienceOrb() {
        return this.packetType == PacketType.EXPERIENCE_ORB;
    }

    public boolean isLiving() {
        return this.packetType == PacketType.LIVING;
    }

    public boolean isPainting() {
        return this.packetType == PacketType.PAINTING;
    }

    public boolean isPlayer() {
        return this.packetType == PacketType.PLAYER;
    }

    public enum PacketType {
        BASE, EXPERIENCE_ORB, LIVING, PAINTING, PLAYER
    }
}