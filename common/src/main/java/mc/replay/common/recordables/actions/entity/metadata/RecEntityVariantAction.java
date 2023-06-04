package mc.replay.common.recordables.actions.entity.metadata;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.RecEntityVariant;
import mc.replay.wrapper.entity.metadata.EntityMetadata;
import mc.replay.wrapper.entity.metadata.animal.FoxMetadata;
import mc.replay.wrapper.entity.metadata.animal.LlamaMetadata;
import mc.replay.wrapper.entity.metadata.animal.MooshroomMetadata;
import mc.replay.wrapper.entity.metadata.animal.RabbitMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.CatMetadata;
import mc.replay.wrapper.entity.metadata.animal.tameable.ParrotMetadata;
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
        } else if (entityMetadata instanceof FoxMetadata foxMetadata) {
            foxMetadata.setType(FoxMetadata.Type.values()[recordable.variant()]);
        } else if (entityMetadata instanceof LlamaMetadata llamaMetadata) {
            llamaMetadata.setVariant(LlamaMetadata.Variant.values()[recordable.variant()]);
        } else if (entityMetadata instanceof MooshroomMetadata mooshroomMetadata) {
            mooshroomMetadata.setVariant(MooshroomMetadata.Variant.values()[recordable.variant()]);
        } else if (entityMetadata instanceof RabbitMetadata rabbitMetadata) {
            rabbitMetadata.setType(RabbitMetadata.Type.values()[recordable.variant()]);
        } else if (entityMetadata instanceof CatMetadata catMetadata) {
            catMetadata.setColor(CatMetadata.Color.values()[recordable.variant()]);
        } else if (entityMetadata instanceof ParrotMetadata parrotMetadata) {
            parrotMetadata.setColor(ParrotMetadata.Color.values()[recordable.variant()]);
        }
    }
}