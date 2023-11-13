package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.api.data.entity.RMetadata;
import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPandaMainGene;
import mc.replay.nms.entity.metadata.animal.PandaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPandaMainGeneAction implements InternalEntityMetadataRecordableAction<RecPandaMainGene> {

    @Override
    public void writeMetadata(@NotNull RecPandaMainGene recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof PandaMetadata pandaMetadata) {
            pandaMetadata.setMainGene(PandaMetadata.Gene.values()[recordable.gene()]);
        }
    }
}