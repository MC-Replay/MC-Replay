package mc.replay.nms.entity.metadata.other;

import com.google.common.base.Preconditions;
import mc.replay.api.data.entity.EntityMetadata;
import mc.replay.packetlib.data.entity.Metadata;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

public class PaintingMetadata extends EntityMetadata {

    public static final int OFFSET = EntityMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 0;

    private Motive motive = Motive.KEBAB;
    private BlockFace direction = BlockFace.SOUTH;

    public PaintingMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setMotive(@NotNull Motive value) {
        this.motive = value;
    }

    public void setDirection(@NotNull BlockFace value) {
        Preconditions.checkArgument(value == BlockFace.UP || value == BlockFace.DOWN, "Painting can't look up or down");
        this.direction = value;
    }

    public @NotNull Motive getMotive() {
        return this.motive;
    }

    public @NotNull BlockFace getDirection() {
        return this.direction;
    }

    public enum Motive {

        KEBAB(0, 0, 16, 16),
        AZTEC(16, 0, 16, 16),
        ALBAN(32, 0, 16, 16),
        AZTEC2(48, 0, 16, 16),
        BOMB(64, 0, 16, 16),
        PLANT(80, 0, 16, 16),
        WASTELAND(96, 0, 16, 16),
        POOL(0, 32, 32, 16),
        COURBET(32, 32, 32, 16),
        SEA(64, 32, 32, 16),
        SUNSET(96, 32, 32, 16),
        CREEBET(128, 32, 32, 16),
        WANDERER(0, 64, 16, 32),
        GRAHAM(16, 64, 16, 32),
        MATCH(0, 128, 32, 32),
        BUST(32, 128, 32, 32),
        STAGE(64, 128, 32, 32),
        VOID(96, 128, 32, 32),
        SKULL_AND_ROSES("skull_and_roses", 128, 128, 32, 32),
        WITHER(160, 128, 32, 32),
        FIGHTERS(0, 96, 64, 32),
        POINTER(0, 192, 64, 64),
        PIGSCENE(64, 192, 64, 64),
        BURNING_SKULL(128, 192, 64, 64),
        SKELETON(192, 64, 64, 48),
        DONKEY_KONG(192, 112, 64, 48);

        private final String name;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        Motive(String name, int x, int y, int width, int height) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        Motive(int x, int y, int width, int height) {
            this.name = "minecraft:" + name().toLowerCase();
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public String getName() {
            return this.name;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }
}