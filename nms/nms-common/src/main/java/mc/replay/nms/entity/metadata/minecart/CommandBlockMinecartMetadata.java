package mc.replay.nms.entity.metadata.minecart;

import mc.replay.nms.entity.metadata.MetadataTypes;
import mc.replay.packetlib.data.entity.Metadata;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class CommandBlockMinecartMetadata extends AbstractMinecartMetadata {

    public static final int OFFSET = AbstractMinecartMetadata.MAX_OFFSET;
    public static final int MAX_OFFSET = OFFSET + 2;

    public static final int COMMAND_INDEX = OFFSET;
    public static final int LAST_OUTPUT_INDEX = OFFSET + 1;

    public CommandBlockMinecartMetadata(@NotNull Metadata metadata) {
        super(metadata);
    }

    public void setCommand(@NotNull String value) {
        super.metadata.setIndex(COMMAND_INDEX, MetadataTypes.String(value));
    }

    public void setLastOutput(@NotNull Component value) {
        super.metadata.setIndex(LAST_OUTPUT_INDEX, MetadataTypes.Chat(value));
    }

    public @NotNull String getCommand() {
        return super.metadata.getIndex(COMMAND_INDEX, "");
    }

    public @NotNull Component getLastOutput() {
        return super.metadata.getIndex(LAST_OUTPUT_INDEX, Component.empty());
    }

    @Override
    public int getObjectData() {
        return 6;
    }
}