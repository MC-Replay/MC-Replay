package mc.replay.recording.file;

import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.CachedRecordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.recording.RecordingImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public final class RecordingFileProcessor {

    private final String directory = MCReplayAPI.getJavaPlugin().getDataFolder() + "/recordings/";

    public File createRecordingFile(Recording recording) {
        NavigableMap<Integer, List<CachedRecordable>> recordables = recording.recordables();

        ReplayByteBuffer writer = new ReplayByteBuffer(ByteBuffer.allocateDirect(0));

        writer.write(INT, RecordingFormat.RECORDING_VERSION);
        writer.write(INT, RecordingFormat.MINECRAFT_PROTOCOL_VERSION);

        writer.write(STRING, recording.id());
        writer.write(LONG, recording.startedAt());
        writer.write(LONG, recording.endedAt());

        for (Map.Entry<Integer, List<CachedRecordable>> entry : recordables.entrySet()) {
            writer.write(INT, entry.getKey());
            writer.writeCollection(entry.getValue(), (writer2, recordable) -> {
                int recordableId = MCReplayAPI.getRecordableRegistry().getRecordableId(recordable.recordable().getClass());
                writer2.write(INT, recordableId);
                writer2.write(recordable.recordable());
            });
        }
        writer.write(INT, 0xFF); // End

        File file = new File(this.getDirectory(), recording.id() + RecordingFormat.FILE_EXTENSION);
        try {
            if (!file.createNewFile()) {
                throw new IllegalStateException("The file of the recording could not be created");
            }
        } catch (Exception exception) {
            throw new IllegalStateException("There went someting wrong while creating the recording file.", exception);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            writer.nioBuffer().rewind();
            byte[] bytes = new byte[writer.nioBuffer().remaining()];
            writer.nioBuffer().get(bytes);

            fileOutputStream.write(bytes);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println("Created recording file: " + file.getAbsolutePath());
        return file;
    }

    public Recording loadRecording(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = fileInputStream.readAllBytes();
            ReplayByteBuffer reader = new ReplayByteBuffer(ByteBuffer.wrap(bytes));

            int version = reader.read(INT);
            if (version != RecordingFormat.RECORDING_VERSION) {
                // TODO old version support
                return null;
            }

            int protocolVersion = reader.read(INT); // Minecraft protocol version

            String id = reader.read(STRING);
            long startedAt = reader.read(LONG);
            long endedAt = reader.read(LONG);

            NavigableMap<Integer, List<CachedRecordable>> recordables = new TreeMap<>();
            int time;
            while ((time = reader.read(INT)) != 0xFF) {
                List<CachedRecordable> recordableList = reader.readCollection((reader2) -> {
                    int recordableId = reader.read(INT);
                    return new CachedRecordable(MCReplayAPI.getRecordableRegistry().getRecordable(recordableId, reader2));
                });
                recordables.put(time, recordableList);
            }

            return new RecordingImpl(id, Duration.ofMillis(endedAt - startedAt), startedAt, endedAt, recordables);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private File getDirectory() {
        File file = new File(this.directory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}