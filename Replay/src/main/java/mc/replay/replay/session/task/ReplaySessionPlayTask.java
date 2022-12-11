package mc.replay.replay.session.task;

import lombok.AccessLevel;
import lombok.Getter;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.api.replay.session.ReplayPlayer;
import mc.replay.common.recordables.RecordableEntity;
import mc.replay.common.recordables.RecordableOther;
import mc.replay.packetlib.network.packet.ClientboundPacket;
import mc.replay.replay.ReplaySessionImpl;
import mc.replay.replay.session.entity.ReplaySessionEntityHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

@Getter(AccessLevel.PACKAGE)
public final class ReplaySessionPlayTask implements Runnable {

    private final ReplaySessionImpl replaySession;
    private final NavigableMap<Long, List<CachedRecordable>> recordables;

    private final ReplaySessionEntityHandler entityCache;

    private final long startTime, endTime;

    private long currentTime;

    public ReplaySessionPlayTask(ReplaySessionImpl replaySession) {
        this.replaySession = replaySession;
        this.recordables = replaySession.getRecording().recordables();

        this.entityCache = new ReplaySessionEntityHandler(this.replaySession);

        this.startTime = this.currentTime = this.recordables.firstKey();
        this.endTime = this.recordables.lastKey();
    }

    @Override
    public void run() {
        if (this.replaySession.isPaused()) return;

        List<CachedRecordable> recordables = new ArrayList<>();
        long nextTime = this.currentTime + ((long) (Math.ceil(this.replaySession.getSpeed() * 50D)));
        for (long i = this.currentTime; i < nextTime; i++) {
            recordables.addAll(this.recordables.getOrDefault(i, new ArrayList<>()));
        }

        for (CachedRecordable recordable : recordables) {
            if (recordable.recordable() instanceof RecordableOther recordableOther) {
                this.entityCache.handleOtherRecordable(recordableOther);
                continue;
            }

            if (recordable.recordable() instanceof RecordableEntity recordableEntity) {
                this.sendPackets(this.entityCache.handleEntityRecordable(recordableEntity));
                continue;
            }

            this.sendPackets(recordable.recordable().functionlessReplayPackets());
        }

        this.currentTime += nextTime - this.currentTime;
        if (this.currentTime >= this.endTime) {
            this.replaySession.stop();
        }
    }

    private void sendPackets(List<ClientboundPacket> packets) {
        for (ReplayPlayer replayPlayer : this.replaySession.getAllPlayers()) {
            for (ClientboundPacket packet : packets) {
                MCReplayAPI.getPacketLib().sendPacket(replayPlayer.player(), packet);
            }
        }
    }
}