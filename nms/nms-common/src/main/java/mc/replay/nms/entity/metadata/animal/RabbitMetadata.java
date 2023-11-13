package mc.replay.nms.entity.metadata.animal;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class RabbitMetadata extends AnimalMetadata {

    public static final int OFFSET = AnimalMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 1;

    public static final int TYPE_INDEX = OFFSET;

    public RabbitMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setType(@NotNull Type value) {
        int id = value == Type.KILLER_BUNNY ? 99 : value.ordinal();
        super.metadata.setIndex(TYPE_INDEX, MetadataTypes.VarInt(id));
    }

    public @NotNull Type getType() {
        int id = super.metadata.getIndex(TYPE_INDEX, 0);
        return (id == 99) ? Type.KILLER_BUNNY : Type.VALUES[id];
    }

    public enum Type {

        BROWN,
        WHITE,
        BLACK,
        BLACK_AND_WHITE,
        GOLD,
        SALT_AND_PEPPER,
        KILLER_BUNNY;

        private static final Type[] VALUES = values();
    }
}