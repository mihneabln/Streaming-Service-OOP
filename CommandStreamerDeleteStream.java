public class CommandStreamerDeleteStream implements Commands{

    String streamerId;
    String streamId;

    public CommandStreamerDeleteStream(String streamerId, String streamId) {
        this.streamerId = streamerId;
        this.streamId = streamId;
    }

    @Override
    public void execute() {

        // TODO functie lambda
        Database.instance().getStreams().removeIf(streams -> Integer.toString(streams.id).equals(streamId));
    }
}
