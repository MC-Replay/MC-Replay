package mc.replay.recording.dispatcher.helpers.metadata.animal;

import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.data.EntityId;
import mc.replay.common.recordables.types.entity.metadata.animal.*;
import mc.replay.packetlib.data.entity.Metadata;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataReader;
import mc.replay.nms.entity.metadata.animal.PandaMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mc.replay.nms.entity.metadata.animal.PandaMetadata.*;

public final class PandaMetadataReader implements MetadataReader<PandaMetadata> {

    @Override
    public List<Recordable> read(PandaMetadata before, PandaMetadata metadata, Map<Integer, Metadata.Entry<?>> entries, EntityId entityId) {
        List<Recordable> recordables = new ArrayList<>();

        if (entries.remove(EAT_TIMER_INDEX) != null) {
            if (metadata.getEatTimer() != before.getEatTimer()) {
                if (metadata.getEatTimer() == 0 || (metadata.getEatTimer() > 0 && before.getEatTimer() == 0)) {
                    recordables.add(
                            new RecPandaEating(
                                    entityId,
                                    metadata.getEatTimer() != 0
                            )
                    );
                }
            }
        }

        if (entries.remove(MAIN_GENE_INDEX) != null) {
            if (metadata.getMainGene() != before.getMainGene()) {
                recordables.add(
                        new RecPandaMainGene(
                                entityId,
                                metadata.getMainGene().ordinal()
                        )
                );
            }
        }

        if (entries.remove(HIDDEN_GENE_INDEX) != null) {
            if (metadata.getHiddenGene() != before.getHiddenGene()) {
                recordables.add(
                        new RecPandaHiddenGene(
                                entityId,
                                metadata.getHiddenGene().ordinal()
                        )
                );
            }
        }

        if (entries.remove(MASK_INDEX) != null) {
            if (metadata.isSneezing() != before.isSneezing()) {
                recordables.add(
                        new RecPandaSneezing(
                                entityId,
                                metadata.isSneezing()
                        )
                );
            }

            if (metadata.isRolling() != before.isRolling()) {
                recordables.add(
                        new RecPandaRolling(
                                entityId,
                                metadata.isRolling()
                        )
                );
            }

            if (metadata.isSitting() != before.isSitting()) {
                recordables.add(
                        new RecPandaSitting(
                                entityId,
                                metadata.isSitting()
                        )
                );
            }

            if (metadata.isOnBack() != before.isOnBack()) {
                recordables.add(
                        new RecPandaOnBack(
                                entityId,
                                metadata.isOnBack()
                        )
                );
            }
        }

        return recordables;
    }
}