package mc.replay.common.recordables.actions.entity.metadata.animal;

import mc.replay.common.recordables.actions.internal.InternalEntityMetadataRecordableAction;
import mc.replay.common.recordables.types.entity.metadata.animal.RecPandaHiddenGene;
import mc.replay.api.data.entity.RMetadata;
import mc.replay.nms.entity.metadata.animal.PandaMetadata;
import org.jetbrains.annotations.NotNull;

public final class RecPandaHiddenGeneAction implements InternalEntityMetadataRecordableAction<RecPandaHiddenGene> {

    @Override
    public void writeMetadata(@NotNull RecPandaHiddenGene recordable, @NotNull RMetadata entityMetadata) {
        if (entityMetadata instanceof PandaMetadata pandaMetadata) {
            pandaMetadata.setHiddenGene(PandaMetadata.Gene.values()[recordable.gene()]);
        }
    }
}