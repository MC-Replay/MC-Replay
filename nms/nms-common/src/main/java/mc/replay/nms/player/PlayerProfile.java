package mc.replay.nms.player;

import mc.replay.packetlib.data.PlayerProfileProperty;

import java.util.Map;
import java.util.UUID;

public interface PlayerProfile {

    UUID uuid();

    String name();

    Map<String, PlayerProfileProperty> properties();
}