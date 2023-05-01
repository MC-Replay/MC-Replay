package mc.replay.recording.dispatcher.helpers;

import lombok.Getter;
import mc.replay.recording.dispatcher.helpers.metadata.MetadataHelper;

@Getter
public final class DispatcherHelpers {

    private final MetadataHelper metadataHelper;

    public DispatcherHelpers() {
        this.metadataHelper = new MetadataHelper();
    }
}