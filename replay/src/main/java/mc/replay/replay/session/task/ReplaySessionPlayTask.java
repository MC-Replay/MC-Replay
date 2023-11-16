package mc.replay.replay.session.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mc.replay.api.MCReplay;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.common.recordables.actions.internal.InternalBlockRecordableAction;
import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.replay.ReplaySession;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Getter(AccessLevel.PACKAGE)
public final class ReplaySessionPlayTask implements Runnable {

    private final MCReplay instance;
    private final ReplaySession replaySession;
    private final NavigableMap<Integer, List<Recordable>> recordables;

    private final long startTime, endTime;

    @Getter
    @Setter
    private int currentTime;

    public ReplaySessionPlayTask(MCReplay instance, ReplaySession replaySession) {
        this.instance = instance;
        this.replaySession = replaySession;
        this.recordables = replaySession.getRecording().recordables();

        this.startTime = this.currentTime = this.recordables.firstKey();
        this.endTime = this.recordables.lastKey();
    }

    @SuppressWarnings("rawtypes, unchecked")
    @Override
    public void run() {
        if (this.replaySession.isPaused()) return;

        List<Recordable> recordables = new ArrayList<>();
        int nextTime = this.currentTime + ((int) (Math.ceil(this.replaySession.getSpeed() * 50D)));
        for (int i = this.currentTime; i < nextTime; i++) {
            recordables.addAll(this.recordables.getOrDefault(i, new ArrayList<>()));
        }

        for (Recordable recordable : recordables) {
            RecordableDefinition<? extends Recordable> recordableDefinition = this.instance.getRecordableRegistry().getRecordableDefinition(recordable.getClass());
            if (recordableDefinition == null) {
                continue;
            }

            RecordableAction<? extends Recordable, ?> action = recordableDefinition.action();
            if (action instanceof InternalEntityRecordableAction internalEntityRecordableAction) {
                this.replaySession.sendPackets(internalEntityRecordableAction.createPackets(recordable, this.replaySession.getEntityCache()));
                continue;
            }

            if (action instanceof EntityRecordableAction entityRecordableAction) {
                this.replaySession.sendPackets(entityRecordableAction.createPackets(recordable, this.replaySession.getEntityCache()));
                continue;
            }

            if (action instanceof InternalBlockRecordableAction internalBlockRecordableAction) {
                this.replaySession.sendPackets(internalBlockRecordableAction.createPackets(recordable, this.replaySession.getBlockCache()));
                continue;
            }

            if (action instanceof EmptyRecordableAction emptyRecordableAction) {
                this.replaySession.sendPackets(emptyRecordableAction.createPackets(recordable, null));
            }
        }

        this.currentTime += nextTime - this.currentTime;
        if (this.currentTime >= this.endTime) {
            this.instance.getReplayHandler().stopReplay(this.replaySession.getSessionUuid());
        }
    }
}