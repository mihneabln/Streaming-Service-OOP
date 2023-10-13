import java.util.ArrayList;
import java.util.List;

public class CommandUserSurprise  implements Commands{

    String userId;
    String streamType;
    int wantedType;

    public CommandUserSurprise(String idUser, String streamType) {
        this.userId = idUser;
        this.streamType = streamType;
    }

    @Override
    public void execute() {

        if (streamType.equals("SONG"))
            wantedType = 1;
        if (streamType.equals("PODCAST"))
            wantedType = 2;
        if (streamType.equals("AUDIOBOOK"))
            wantedType = 3;

        List<Streamers> streamersListened = new ArrayList<>();
        List<Streamers> streamersNotListened = new ArrayList<>();
        List<Streams> allUnlistenedStreams = new ArrayList<>();
        List<Streams> latestStreams = new ArrayList<>();

        User user = Database.instance().getUserById(userId);
        // creez lista de streameri ascultati de user
        for (String usersStreams : user.streams) {
            for (Streams stream : Database.instance().getStreams()) {
                if (usersStreams.equals(Integer.toString(stream.id)))
                    streamersListened.add(Database.instance().getStreamerById(Integer.toString(stream.streamerId)));
            }
        }
        // creez lista de streameri neascultati de user
        int exista;
        for (Streamers anyStreamer : Database.instance().getStreamers()) {
            exista = 0;
            for (Streamers listened : streamersListened) {
                if (anyStreamer.id.equals(listened.id))
                    exista++;
            }
            if (exista == 0)
                streamersNotListened.add(anyStreamer);
        }
        // creez lista cu streamuri de interes
        for (Streamers streamers : streamersNotListened) {
            Integer id = Integer.parseInt(streamers.id);
            for (Streams streams : Database.instance().getStreams()) {
                if (streams.streamerId.equals(id) && streams.streamType.equals(wantedType)) {
                    allUnlistenedStreams.add(streams);
                }
            }
        }

        // creez lista cu cele mai noi hituri
        Streams latest = allUnlistenedStreams.get(0);
        getNewestStreams(allUnlistenedStreams, latestStreams, latest);

        latest = allUnlistenedStreams.get(0);
        getNewestStreams(allUnlistenedStreams, latestStreams, latest);

        latest = allUnlistenedStreams.get(0);
        getNewestStreams(allUnlistenedStreams, latestStreams, latest);

        // afisare
        Database.output = "[";
        int i = 0;
        for (Streams stream : latestStreams) {
            if (i < 3 && stream.streamType.equals(wantedType))
                if (Database.output.length() > 2)
                        Database.output += ",";
            Database.output += stream.jsonConversion();
            i++;
        }
        Database.output += "]" + '\n';
        Database.concat += Database.output;
    }

    private void getNewestStreams(List<Streams> allUnlistenedStreams, List<Streams> latestStreams, Streams latest) {
        int exista;
        for (Streams streams2 : allUnlistenedStreams) {
            exista = 0;
            for (Streams mines : latestStreams) {
                if ((latest.compare(streams2)).equals(mines)) {
                    exista++;
                }
            }
            if(exista == 0)
                latest = latest.compare(streams2);
        }

        latestStreams.add(latest);
    }
}
