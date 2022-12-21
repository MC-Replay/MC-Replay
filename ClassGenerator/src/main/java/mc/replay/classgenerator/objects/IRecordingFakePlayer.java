package mc.replay.classgenerator.objects;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IRecordingFakePlayer {

    Player target();

    UUID uuid();

    void spawn();

    void remove();

    void setRecording(boolean recording);

    boolean isRecording();
}