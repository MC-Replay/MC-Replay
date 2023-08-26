package mc.replay.nms;

final class MCReplayNMSInstance {

    private MCReplayNMSInstance() {
    }

    static MCReplayNMS INSTANCE;

    public static void init(MCReplayNMS instance) {
        INSTANCE = instance;
    }
}