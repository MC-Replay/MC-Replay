package mc.replay.common.dispatcher;

import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public interface DispatcherPacket<T extends Packet> extends Dispatcher<T> {}