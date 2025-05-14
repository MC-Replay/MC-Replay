package mc.replay.recording.dispatcher.dispatchers;

import mc.replay.api.recordables.Recordable;
import mc.replay.packetlib.network.packet.serverbound.ServerboundPacket;
import mc.replay.recording.RecordingSession;
import mc.replay.recording.dispatcher.helpers.DispatcherHelpers;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class DispatcherPacketIn<T extends ServerboundPacket> extends Dispatcher<T> {

    public DispatcherPacketIn(DispatcherHelpers helpers) {
        super(helpers);
    }

    public abstract List<Recordable> getRecordables(RecordingSession session, Player player, T obj);

    // Use getRecordables(Player player, T obj) instead
    @Override
    public final List<Recordable> getRecordables(RecordingSession session, T obj) {
        return null;
    }
}