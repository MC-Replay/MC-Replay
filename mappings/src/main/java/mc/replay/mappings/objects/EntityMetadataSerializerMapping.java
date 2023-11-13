package mc.replay.mappings.objects;

public record EntityMetadataSerializerMapping(int id, Type type) implements Mapping {

    public enum Type {
        BYTE,
        INT,
        LONG,
        FLOAT,
        STRING,
        COMPONENT,
        OPTIONAL_COMPONENT,
        ITEM_STACK,
        BOOLEAN,
        ROTATIONS,
        BLOCK_POS,
        OPTIONAL_BLOCK_POS,
        DIRECTION,
        OPTIONAL_UUID,
        BLOCK_STATE,
        OPTIONAL_BLOCK_STATE,
        COMPOUND_TAG,
        PARTICLE,
        VILLAGER_DATA,
        OPTIONAL_UNSIGNED_INT,
        POSE,
        CAT_VARIANT,
        FROG_VARIANT,
        OPTIONAL_GLOBAL_POS,
        PAINTING_VARIANT,
        SNIFFER_STATE,
        VECTOR3,
        QUATERNION
    }
}