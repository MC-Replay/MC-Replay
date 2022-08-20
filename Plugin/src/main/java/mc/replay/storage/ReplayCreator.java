package mc.replay.storage;

import mc.replay.MCReplayPlugin;
import mc.replay.common.recordables.Recordable;
import mc.replay.recordables.entity.movement.RecEntityRelMoveLook;
import mc.replay.replay.Replay;
import net.minecraft.server.v1_16_R3.ChunkRegionLoader;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.entity.Player;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;

public record ReplayCreator(MCReplayPlugin mcReplay) {

    public Replay createReplay(Player player) {
        String replayId = this.createReplayId(player);
        Collection<Chunk> chunks = this.getChunksOfPlayer(player);

        NBTTagCompound compound = new NBTTagCompound();

        for (Chunk chunk : chunks) {
            net.minecraft.server.v1_16_R3.Chunk nmsChunk = ((CraftChunk) chunk).getHandle();

            NBTTagCompound chunkCompound = ChunkRegionLoader.saveChunk(nmsChunk.getWorld().getMinecraftWorld(), nmsChunk);
            chunkCompound.remove("Entities");

            compound.set(chunk.getX() + ";" + chunk.getZ(), chunkCompound);
        }

        this.saveChunks(replayId, compound);

        return null;
    }

    private String createReplayId(Player player) {
        return player.getUniqueId().toString();
    }

    private Collection<Chunk> getChunksOfPlayer(Player player) {
        World world = player.getWorld();
        Collection<Chunk> chunks = new HashSet<>();

        NavigableMap<Long, List<Recordable>> recordables = this.mcReplay.getReplayStorage().getTypeRecordables(RecEntityRelMoveLook.class, player.getUniqueId());

        for (List<Recordable> recordableList : recordables.values()) {
            for (Recordable recordable : recordableList) {
                if (!(recordable instanceof RecEntityRelMoveLook entityRelMoveLook)) continue;

                Location current = entityRelMoveLook.current();
                if (current.getWorld() == null || !current.getWorld().equals(world)) continue;

                Chunk chunk = current.getChunk();
                if (!chunks.contains(chunk)) {
                    chunks.add(chunk);
                }
            }
        }

        return chunks;
    }

    private void saveChunks(String replayId, NBTTagCompound compound) {
        File file = new File(this.mcReplay.getDataFolder() + "/replays/" + replayId, "chunks.replaychunks");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

            compound.write(dataOutputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}