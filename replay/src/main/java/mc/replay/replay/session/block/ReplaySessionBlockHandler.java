package mc.replay.replay.session.block;

import lombok.RequiredArgsConstructor;
import mc.replay.common.replay.IReplayBlockProvider;
import mc.replay.nms.MCReplayNMS;
import mc.replay.nms.block.BlockData;
import mc.replay.replay.ReplaySession;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public final class ReplaySessionBlockHandler implements IReplayBlockProvider {

    private final ReplaySession replaySession;
    private final Map<Vector, BlockData> defaultBlockData = new HashMap<>();

    @Override
    public void setBlockDefault(Vector position) {
        if (this.defaultBlockData.containsKey(position)) {
            return;
        }

        World replayWorld = this.replaySession.getReplayWorld();
        BlockData blockData = MCReplayNMS.getInstance().getBlockData(replayWorld, position);

        this.defaultBlockData.put(position, blockData);
    }
}