package mc.replay.classgenerator.objects;

import org.bukkit.entity.Player;

public interface IRecordingFakePlayer {

    Player target();

    void spawn();

    void remove();

    void setRecording(boolean recording);

    boolean isRecording();
}