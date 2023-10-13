public class CommandUserListen  implements Commands{

    String userId, streamId;

    public CommandUserListen(String userId, String streamId) {
        this.userId = userId;
        this.streamId = streamId;
    }

    @Override
    public void execute() {
        for (Streams stream : Database.instance().getStreams()) {
            if (Integer.toString(stream.id).equals(streamId)) {
                stream.noStreams++;
            }
        }
        for (User user : Database.instance().getUsers()) {
            if (user.id.equals(userId)) {
                user.streams.add(streamId);
            }
        }
    }
}
