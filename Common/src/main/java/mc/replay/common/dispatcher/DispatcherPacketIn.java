package mc.replay.common.dispatcher;

import mc.replay.api.recording.recordables.Recordable;
import mc.replay.common.utils.PacketConverter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public interface DispatcherPacketIn<T> extends Dispatcher<T> {

    default PacketConverter.ConvertedPacket convert(T packet) {
        return PacketConverter.convert(packet);
    }

    //Use getRecordables(Player player, T obj) instead
    default List<Recordable<? extends Function<?, ?>>> getRecordables(T obj) {
        return null;
    }

    List<Recordable<? extends Function<?, ?>>> getRecordables(Player player, T obj);
}