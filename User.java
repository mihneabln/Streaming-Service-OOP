import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

    String id;
    String name;
    List<String> streams = new ArrayList<>();

    public User(String id, String name, String[] streams) {
        this.id = id;
        this.name = name;
        this.streams.addAll(Arrays.asList(streams));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getStreams() {
        return streams;
    }
}
