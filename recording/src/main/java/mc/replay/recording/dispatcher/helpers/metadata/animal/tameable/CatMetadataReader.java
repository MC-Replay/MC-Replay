package mc.replay.recording.dispatcher.helpers.metadata.animal.tameable;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatCollarColor;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatLying;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecCatRelaxed;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.tameable.CatMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.tameable.CatMetadata.*;

public final class CatMetadataReader implements MetadataReader<CatMetadata> {

    @Override
    public List<Recordable> read(CatMetadata before, CatMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(COLOR_INDEX) != null) {
            if (metadata.getColor() != before.getColor()) {
                recordables.add(
                        new RecEntityVariant(
                                entityId,
                                metadata.getColor().ordinal()
                        )
                );
            }
        }

        if (entries.remove(LYING_INDEX) != null) {
            if (metadata.isLying() != before.isLying()) {
                recordables.add(
                        new RecCatLying(
                                entityId,
                                metadata.isLying()
                        )
                );
            }
        }

        if (entries.remove(RELAXED_INDEX) != null) {
            if (metadata.isRelaxed() != before.isRelaxed()) {
                recordables.add(
                        new RecCatRelaxed(
                                entityId,
                                metadata.isRelaxed()
                        )
                );
            }
        }

        if (entries.remove(COLLAR_COLOR_INDEX) != null) {
            if (metadata.getCollarColor() != before.getCollarColor()) {
                recordables.add(
                        new RecCatCollarColor(
                                entityId,
                                metadata.getCollarColor()
                        )
                );
            }
        }

        return recordables;
    }
}