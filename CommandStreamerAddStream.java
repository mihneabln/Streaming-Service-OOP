import java.time.Instant;

public class CommandStreamerAddStream implements Commands {

    String name;
    String id;
    String streamGenre;
    String streamerId;
    String noStreams;
    String length;
    String dateAdded;
    String streamType;

    protected CommandStreamerAddStream(String streamerId, String streamType, String id, String streamGenre, String length, String name){
        this.streamType = streamType;
        this.name = name;
        this.id = id;
        this.streamGenre = streamGenre;
        this.streamerId = streamerId;
        this.length = length;
        this.noStreams = "0";
        this.dateAdded = Long.toString(Instant.now().getEpochSecond());
    }

    @Override
    public void execute() {
        Streams stream = new Streams(Integer.parseInt(streamType),name, Integer.parseInt(id), Integer.parseInt(streamGenre),
                Integer.parseInt(streamerId), Long.parseLong(noStreams), length, dateAdded) {
            @Override
            public genreName getGenre() {
                return null;
            }
        };

        Factory factory = new Factory();
        Streams factorised = factory.getType(name, streamType, Integer.parseInt(id), Integer.parseInt(streamGenre), Integer.parseInt(streamerId), Long.parseLong(noStreams), length, dateAdded);

        stream.dateAdded = stream.parseDateAdded();
        stream.length = stream.duration(Long.parseLong(stream.length));
        Database.instance().getStreams().add(stream);
    }
}
