package mc.replay;

import lombok.Getter;
import mc.replay.api.MCReplay;
import mc.replay.api.MCReplayAPI;
import mc.replay.api.utils.config.templates.ReplayMessages;
import mc.replay.api.utils.config.templates.ReplaySettings;
import mc.replay.commands.ReplayTestCommand;
import mc.replay.common.MCReplayInternal;
import mc.replay.common.recordables.RecordableRegistry;
import mc.replay.common.utils.config.ReplayConfigProcessor;
import mc.replay.common.utils.reflection.JavaReflections;
import mc.replay.nms.*;
import mc.replay.nms.fakeplayer.FakePlayerHandler;
import mc.replay.packetlib.PacketLib;
import mc.replay.packetlib.utils.ProtocolVersion;
import mc.replay.recording.RecordingHandler;
import mc.replay.recording.dispatcher.RecordingDispatcherManager;
import mc.replay.replay.ReplayHandler;
import nl.odalitadevelopments.menus.OdalitaMenus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MCReplayPlugin extends JavaPlugin implements MCReplayInternal {

    @Getter
    private static MCReplayPlugin instance;

    private PacketLib packetLib;
    private OdalitaMenus menuHandler;

    private ReplayConfigProcessor<ReplayMessages> messagesProcessor;
    private ReplayConfigProcessor<ReplaySettings> settingsProcessor;

    private RecordingHandler recordingHandler;
    private FakePlayerHandler fakePlayerHandler;
    private RecordableRegistry recordableRegistry;

    private ReplayHandler replayHandler;

    private RecordingDispatcherManager dispatchManager;

    @Override
    public void onLoad() {
        instance = this;

        JavaReflections.getField(MCReplayAPI.class, MCReplay.class, "mcReplay").set(null, this);
    }

    @Override
    public void onEnable() {
        this.packetLib = PacketLib.builder()
                .player()
                .listenServerbound(true)
                .listenMinecraftClientbound(false) // We use our fake player to listen to clientbound minecraft packets
                .listenPacketLibClientbound(false)
                .inject(this);

        try {
            Class<?> nmsInstanceClass = JavaReflections.getClass("mc.replay.nms.MCReplayNMSInstance");

            MCReplayNMS instance = switch (ProtocolVersion.getServerVersion()) {
                case MINECRAFT_1_16_5 -> new MCReplayNMS_v1_16_R3();
                case MINECRAFT_1_17_1 -> new MCReplayNMS_v1_17_R1();
                case MINECRAFT_1_18_2 -> new MCReplayNMS_v1_18_R2();
                case MINECRAFT_1_19_4 -> new MCReplayNMS_v1_19_R3();
                case MINECRAFT_1_20_1 -> new MCReplayNMS_v1_20_R1();
                default ->
                        throw new IllegalStateException("Unsupported server version " + ProtocolVersion.getServerVersion());
            };

            JavaReflections.getMethod(nmsInstanceClass, "init", MCReplayNMS.class).invoke(null, instance);
        } catch (Exception exception) {
            System.out.println("Failed to initialize MCReplayNMS");
            Bukkit.shutdown();
            return;
        }

        this.menuHandler = OdalitaMenus.createInstance(this);

        //Load replay configuration files
        try {
            this.settingsProcessor = new ReplayConfigProcessor<>(this, "config.yml", ReplaySettings.class);
            this.messagesProcessor = new ReplayConfigProcessor<>(this, "messages.yml", ReplayMessages.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }

        this.recordingHandler = new RecordingHandler();
        this.fakePlayerHandler = new FakePlayerHandler(this);
        this.recordableRegistry = new RecordableRegistry();

        this.replayHandler = new ReplayHandler(this);

        this.getCommand("replaytest").setExecutor(new ReplayTestCommand());

        this.dispatchManager = new RecordingDispatcherManager(this);

        this.enable();
    }

    public void enable() {
        this.dispatchManager.start();
    }

    public void disable() {
        this.dispatchManager.stop();
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}