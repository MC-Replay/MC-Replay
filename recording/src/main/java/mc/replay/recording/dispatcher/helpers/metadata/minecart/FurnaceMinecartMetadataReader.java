package mc.replay.recording.dispatcher.helpers.metadata.minecart;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.minecart.RecFurnaceMinecartFuel;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.minecart.FurnaceMinecartMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.minecart.FurnaceMinecartMetadata.FUEL_INDEX;

public final class FurnaceMinecartMetadataReader implements MetadataReader<FurnaceMinecartMetadata> {

    @Override
    public List<Recordable> read(FurnaceMinecartMetadata before, FurnaceMinecartMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(FUEL_INDEX) != null) {
            if (metadata.hasFuel() != before.hasFuel()) {
                recordables.add(
                        new RecFurnaceMinecartFuel(
                                entityId,
                                metadata.hasFuel()
                        )
                );
            }
        }

        return recordables;
    }
}