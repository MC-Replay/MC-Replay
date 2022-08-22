package mc.replay.common.utils;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;

public final class ChunkUtils {

    private ChunkUtils() {
    }

    public static Collection<Chunk> getChunksAroundPlayer(Player player, int radius) {
        Collection<Chunk> chunksAroundPlayer = new HashSet<>();

        Chunk currentChunk = player.getLocation().getChunk();
        int currentChunkX = currentChunk.getX();
        int currentChunkZ = currentChunk.getZ();

        for (int x = currentChunkX - radius; x < currentChunkX + radius; x++) {
            for (int z = currentChunkZ - radius; z < currentChunkZ + radius; z++) {
                chunksAroundPlayer.add(player.getWorld().getChunkAt(x, z));
            }
        }

        return chunksAroundPlayer;
    }
}