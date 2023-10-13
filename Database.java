import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Database {

    private static Database uniqueInstance;
    private List<Streamers> streamers = new ArrayList<>();
    private List<Streams> streams = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Commands> commands = new ArrayList<>();
    public static String output;
    public static String concat = "";

    public static Database instance() {
        if (uniqueInstance == null)
            uniqueInstance = new Database();
        return uniqueInstance;
    }

    public void addUsersInSpotify(File userFile) {       // adaug userii din fisier csv in lista
        try (BufferedReader br = new BufferedReader(new FileReader((userFile)))) {
            String line;
            line =br.readLine();
            while ((line = br.readLine())!=null){
               String id = line.split("\"")[1];
               String name = line.split("\"")[3];
               String[] streams = line.split("\"")[5].split(" ");
                User user = new User(id, name ,streams);
                this.users.add(user);
            }
        }
        catch (IOException e) {}
    }

    public void addStreamsInSpotify(File streamsFile) {       // adaug streamurile din fisier csv in lista
        try (BufferedReader br = new BufferedReader(new FileReader((streamsFile)))) {
            String line;
            line =br.readLine();

            while ((line = br.readLine())!=null){
                int streamType = Integer.parseInt(line.split("\"")[1]);
                int id = Integer.parseInt(line.split("\"")[3]);
                int streamGenre = Integer.parseInt(line.split("\"")[5]);
                long noStreams = Long.parseLong(line.split("\"")[7]);
                int streamerId = Integer.parseInt(line.split("\"")[9]);
                long length = Long.parseLong(line.split("\"")[11]);
                long dateAdded = Long.parseLong(line.split("\"")[13]);
                String name = line.split("\"")[15];
                Streams stream = new Streams(streamType, name, id, streamGenre, streamerId, noStreams, Long.toString(length), Long.toString(dateAdded)) {
                    @Override
                    public genreName getGenre() {
                        return null;
                    }
                };
                stream.dateAdded = stream.parseDateAdded();
                stream.length = stream.duration(Long.parseLong(stream.length));
                this.streams.add(stream);
            }
        }
        catch (IOException e) {}
    }

    public void addStreamersInSpotify(File streamersFile) {       // adaug streamerii din fisier csv in lista
        try (BufferedReader br = new BufferedReader(new FileReader((streamersFile)))) {
            String line;
            line =br.readLine();
            while ((line = br.readLine())!=null){
               String streamerType = line.split("\"")[1];
               String id = line.split("\"")[3];
               String name = line.split("\"")[5];
               Streamers streamer = new Streamers(streamerType, id, name);
               this.streamers.add(streamer);
            }
        }
        catch (IOException e) {}
    }

    public List<Commands> getCommands() {
        return commands;
    }

    public List<Streamers> getStreamers() {
        return streamers;
    }

    public List<Streams> getStreams() {
        return streams;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(String id){
        for (User i : users) {
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }

    public Streamers getStreamerById(String id){
        for (Streamers i : streamers) {
            if (i.id.equals(id))
                return i;
        }
        return null;
    }

    public static void eraseInstance(){
        uniqueInstance = null;
    }
}
