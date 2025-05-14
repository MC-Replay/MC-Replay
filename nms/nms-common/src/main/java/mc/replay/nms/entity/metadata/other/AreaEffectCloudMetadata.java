package mc.replay.nms.entity.metadata.other;

import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AreaEffectCloudMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 3;

    public static final int RADIUS_INDEX = OFFSET;
    public static final int COLOR_INDEX = OFFSET + 1;
    public static final int SINGLE_POINT_INDEX = OFFSET + 2;

    public AreaEffectCloudMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setRadius(float value) {
        super.metadata.setIndex(RADIUS_INDEX, Metadata.Float(value));
    }

    public void setColor(int value) {
        super.metadata.setIndex(COLOR_INDEX, Metadata.VarInt(value));
    }

    public void setSinglePoint(boolean value) {
        super.metadata.setIndex(SINGLE_POINT_INDEX, Metadata.Boolean(value));
    }

    public float getRadius() {
        return super.metadata.getIndex(RADIUS_INDEX, .5F);
    }

    public int getColor() {
        return super.metadata.getIndex(COLOR_INDEX, 0);
    }

    public boolean isSinglePoint() {
        return super.metadata.getIndex(SINGLE_POINT_INDEX, false);
    }
}