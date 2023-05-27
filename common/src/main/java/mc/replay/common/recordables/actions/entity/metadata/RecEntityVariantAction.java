package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.other.BoatMetadata;
import mc.replay.wrapper.entity.metadata.water.AxolotlMetadata;
import mc.replay.wrapper.entity.metadata.water.fish.TropicalFishMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecEntityVariantAction implements InternalEntityMetadataRecordableAction<RecEntityVariant> {

    @Override
    public void writeMetadata(@NotNull RecEntityVariant recordable, @NotNull EntityMetadata entityMetadata) {
        if (entityMetadata instanceof AxolotlMetadata axolotlMetadata) {
            axolotlMetadata.setVariant(AxolotlMetadata.Variant.values()[recordable.variant()]);
        } else if (entityMetadata instanceof TropicalFishMetadata tropicalFishMetadata) {
            tropicalFishMetadata.setVariant(TropicalFishMetadata.getVariantFromId(recordable.variant()));
        } else if (entityMetadata instanceof BoatMetadata boatMetadata) {
            boatMetadata.setType(BoatMetadata.Type.values()[recordable.variant()]);
        }
    }
}