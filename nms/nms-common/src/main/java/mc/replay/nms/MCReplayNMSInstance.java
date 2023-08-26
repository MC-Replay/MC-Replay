package mc.replay.nms;

final class MCReplayNMSInstance {

    private MCReplayNMSInstance() {
    }

    static MCReplayNMS INSTANCE;

    public static void init(MCReplayNMS instance) {
        if (INSTANCE != null) throw new IllegalStateException("MCReplayNMSInstance already initialized");

        INSTANCE = instance;
        instance.init();
    }
}