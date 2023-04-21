package mc.replay.recording.file;

import mc.replay.api.MCReplayAPI;
import mc.replay.api.recording.Recording;
import mc.replay.api.recording.recordables.Recordable;
import mc.replay.packetlib.network.ReplayByteBuffer;
import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.recording.RecordingImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.*;

import static mc.replay.packetlib.network.ReplayByteBuffer.*;

public final class RecordingFileProcessor {

    private final String directory = MCReplayAPI.getJavaPlugin().getDataFolder() + "/recordings/";

    public File createRecordingFile(Recording recording) {
        NavigableMap<Integer, List<Recordable>> recordables = recording.recordables();

        ReplayByteBuffer writer = new ReplayByteBuffer(ByteBuffer.allocateDirect(0));

        writer.write(BYTE, RecordingFormat.RECORDING_VERSION);
        writer.write(PROTOCOL_VERSION, RecordingFormat.MINECRAFT_PROTOCOL_VERSION);

        writer.write(STRING, recording.id());
        writer.write(LONG, recording.startedAt());
        writer.write(LONG, recording.endedAt());

        for (Map.Entry<Integer, List<Recordable>> entry : recordables.entrySet()) {
            writer.write(VAR_INT, entry.getKey());
            writer.writeCollection(entry.getValue(), (writer2, recordable) -> {
                byte recordableId = MCReplayAPI.getRecordableRegistry().getRecordableId(recordable.getClass());
                writer2.write(BYTE, recordableId);
                writer2.write(recordable);
            });
        }
        writer.write(VAR_INT, 0xFF); // End

        File file = new File(this.getDirectory(), recording.id() + RecordingFormat.FILE_EXTENSION);
        try {
            if (!file.createNewFile()) {
                throw new IllegalStateException("The file of the recording could not be created");
            }
        } catch (Exception exception) {
            throw new IllegalStateException("There went someting wrong while creating the recording file.", exception);
        }

        writer.nioBuffer().limit(writer.writeIndex());

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(writer.readBytes(writer.writeIndex()));
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

            byte version = reader.read(BYTE);
            if (version != RecordingFormat.RECORDING_VERSION) {
                // TODO old version support
                return null;
            }

            ProtocolVersion protocolVersion = reader.read(PROTOCOL_VERSION); // Minecraft protocol version
            if (protocolVersion == ProtocolVersion.NOT_SUPPORTED) {
                throw new IllegalStateException("This Minecraft version is no longer or never was supported.");
            }

            ProtocolVersion serverProtocolVersion = ProtocolVersion.getServerVersion();
            if (protocolVersion.ordinal() < serverProtocolVersion.ordinal()) {
                throw new IllegalStateException("Can't load a recording that was created on a newer Minecraft version.");
            }

            String id = reader.read(STRING);
            long startedAt = reader.read(LONG);
            long endedAt = reader.read(LONG);

            TreeMap<Integer, List<Recordable>> recordables = new TreeMap<>();
            int time;
            while ((time = reader.read(VAR_INT)) != 0xFF) {
                List<Recordable> recordableList = reader.readCollection((reader2) -> {
                    byte recordableId = reader.read(BYTE);
                    return MCReplayAPI.getRecordableRegistry().getRecordable(recordableId, reader2);
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