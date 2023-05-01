package mc.replay.replay.session.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recordables.Recordable;
import mc.replay.api.recordables.RecordableDefinition;
import mc.replay.api.recordables.action.EmptyRecordableAction;
import mc.replay.api.recordables.action.EntityRecordableAction;
import mc.replay.api.recordables.action.RecordableAction;
import mc.replay.api.replay.session.IReplayPlayer;
import mc.replay.common.recordables.actions.internal.InternalEntityRecordableAction;
import mc.replay.packetlib.network.packet.clientbound.ClientboundPacket;
import mc.replay.replay.ReplaySession;
import mc.replay.replay.session.entity.ReplaySessionEntityHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Getter(AccessLevel.PACKAGE)
public final class ReplaySessionPlayTask implements Runnable {

    private final MCReplay instance;
    private final ReplaySession replaySession;
    private final NavigableMap<Integer, List<Recordable>> recordables;

    private final ReplaySessionEntityHandler entityCache;

    private final long startTime, endTime;

    @Getter
    @Setter
    private int currentTime;

    public ReplaySessionPlayTask(MCReplay instance, ReplaySession replaySession) {
        this.instance = instance;
        this.replaySession = replaySession;
        this.recordables = replaySession.getRecording().recordables();

        this.entityCache = new ReplaySessionEntityHandler(this.replaySession);

        this.startTime = this.currentTime = this.recordables.firstKey();
        this.endTime = this.recordables.lastKey();
    }

    public @NotNull ReplaySessionEntityHandler getEntityCache() {
        return this.entityCache;
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
                this.sendPackets(internalEntityRecordableAction.createPackets(recordable, this.entityCache));
                continue;
            }

            if (action instanceof EntityRecordableAction entityRecordableAction) {
                this.sendPackets(entityRecordableAction.createPackets(recordable, this.entityCache));
                continue;
            }

            if (action instanceof EmptyRecordableAction emptyRecordableAction) {
                this.sendPackets(emptyRecordableAction.createPackets(recordable, null));
            }
        }

        this.currentTime += nextTime - this.currentTime;
        if (this.currentTime >= this.endTime) {
            this.instance.getReplayHandler().stopReplay(this.replaySession.getSessionUuid());
        }
    }

    public void sendPackets(List<ClientboundPacket> packets) {
        for (IReplayPlayer replayPlayer : this.replaySession.getAllPlayers()) {
            for (ClientboundPacket packet : packets) {
                MCReplayAPI.getPacketLib().sendPacket(replayPlayer.player(), packet);
            }
        }
    }
}