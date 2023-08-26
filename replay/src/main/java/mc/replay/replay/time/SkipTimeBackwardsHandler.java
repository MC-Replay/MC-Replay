package mc.replay.replay.time;

import mc.replay.api.recordables.Recordable;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.types.internal.BlockRelatedRecordable;
import mc.replay.replay.ReplaySession;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.NavigableMap;

final class SkipTimeBackwardsHandler extends AbstractSkipTimeHandler {

    SkipTimeBackwardsHandler(MCReplayInternal instance) {
        super(instance);
    }

    @Override
    void skipTime(@NotNull ReplaySession session, int until, @NotNull NavigableMap<Integer, List<Recordable>> recordablesBetween) {
        List<Recordable> blockChangeRecordables = this.findRecordables(recordablesBetween, (recordable) -> recordable instanceof BlockRelatedRecordable);
    }
}