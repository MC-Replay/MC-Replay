package mc.replay.recording.dispatcher.helpers.metadata.animal.tameable;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfAngry;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfBegging;
import mc.replay.common.recordables.types.entity.metadata.animal.tameable.RecWolfCollarColor;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.wrapper.entity.metadata.animal.tameable.WolfMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.wrapper.entity.metadata.animal.tameable.WolfMetadata.*;

public final class WolfMetadataReader implements MetadataReader<WolfMetadata> {

    @Override
    public List<Recordable> read(WolfMetadata before, WolfMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(BEGGING_INDEX) != null) {
            if (metadata.isBegging() != before.isBegging()) {
                recordables.add(
                        new RecWolfBegging(
                                entityId,
                                metadata.isBegging()
                        )
                );
            }
        }

        if (entries.remove(COLLAR_COLOR_INDEX) != null) {
            if (metadata.getCollarColor() != before.getCollarColor()) {
                recordables.add(
                        new RecWolfCollarColor(
                                entityId,
                                metadata.getCollarColor()
                        )
                );
            }
        }

        if (entries.remove(ANGER_TIME_INDEX) != null) {
            if (metadata.getAngerTime() != before.getAngerTime()) {
                if (metadata.getAngerTime() == 0 || (metadata.getAngerTime() > 0 && before.getAngerTime() == 0)) {
                    recordables.add(
                            new RecWolfAngry(
                                    entityId,
                                    metadata.getAngerTime() != 0
                            )
                    );
                }
            }
        }

        return recordables;
    }
}