package mc.replay.nms.block;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import org.jetbrains.annotations.Nullable;

public record BlockData(int blockStateId, @Nullable CompoundTag blockEntityTag) {
}