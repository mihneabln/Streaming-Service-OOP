import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

abstract class Streams {

    String name;
    Integer id;
    Integer streamGenre;
    Integer streamerId;
    Long noStreams;
    String length;
    String dateAdded;
    Integer streamType;

    protected Streams(Integer streamType, String name, Integer id, Integer streamGenre, Integer streamerId, Long noStreams, String length, String dateAdded){
        this.streamType = streamType;
        this.name = name;
        this.id = id;
        this.streamGenre = streamGenre;
        this.streamerId = streamerId;
        this.noStreams = noStreams;
        this.length = length;
        this.dateAdded = dateAdded;
    }

    enum genreName {
        POP, LATIN, HOUSE, DANCE, TRAP,
        DOCUMENTARY, CELEBRITIES, TECH,
        FICTION, PERSONAL_DEVELOPMENT, CHILDREN;

        public static genreName fromInteger (int x){
            switch(x) {
                case 1: return POP;
                case 2: return LATIN;
                case 3: return HOUSE;
                case 4: return DANCE;
                case 5: return TRAP;
                case 6: return DOCUMENTARY;
                case 7: return CELEBRITIES;
                case 8: return TECH;
                case 9: return FICTION;
                case 10: return PERSONAL_DEVELOPMENT;
                case 11: return CHILDREN;
            }
            return null;
        }

    }

    public abstract genreName getGenre();

    public String duration(long total){
        long hours = total / 3600;
        if (hours != 0)
            total %= 60;
        long minutes = total / 60;
        long seconds = total % 60;

        String str = "";
        if (hours > 0)
            str += (Long.toString(hours) + ":");
        if (minutes < 10)
            str += "0";
        str += (Long.toString(minutes) + ":");
        if (seconds < 10)
            str += "0";
        return str + Long.toString(seconds);
    }

    public String parseDateAdded() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return fmt.format(new Date(Long.parseLong(dateAdded) * 1000));
    }

    public String jsonConversion(){
        String out = "{";
        out += "\"id\":\"" + id + "\",";
        out += "\"name\":\"" + name + "\",";
        out += "\"streamerName\":\"" + Database.instance().getStreamerById(Integer.toString(streamerId)).name + "\",";
        out += "\"noOfListenings\":\"" + noStreams + "\",";
        out += "\"length\":\"" + length + "\",";
        out += "\"dateAdded\":\"" + dateAdded + "\"";
        out += "}";
        return out;
    }
    public String getRecommend(Integer wantedType){
        Integer StreamerId = this.streamerId;
        for (Streams stream : Database.instance().getStreams()) {
            if (Objects.equals(stream.streamType, wantedType) && stream.streamerId.equals(this.streamerId) && !stream.id.equals(this.id))
                return stream.jsonConversion();
        }
        return null;
    }

    public Streams compare(Streams stream){
        if (Integer.parseInt(this.dateAdded.split("-")[2]) < Integer.parseInt(stream.dateAdded.split("-")[2]))
            return stream;
        if (Integer.parseInt(this.dateAdded.split("-")[2]) > Integer.parseInt(stream.dateAdded.split("-")[2]))
            return this;
        if (Integer.parseInt(this.dateAdded.split("-")[1]) < Integer.parseInt(stream.dateAdded.split("-")[1]))
            return stream;
        if (Integer.parseInt(this.dateAdded.split("-")[1]) > Integer.parseInt(stream.dateAdded.split("-")[1]))
            return this;
        if (Integer.parseInt(this.dateAdded.split("-")[0]) < Integer.parseInt(stream.dateAdded.split("-")[0]))
            return stream;
        if (Integer.parseInt(this.dateAdded.split("-")[0]) > Integer.parseInt(stream.dateAdded.split("-")[0]))
            return this;
        if (this.noStreams > stream.noStreams)
            return this;
        return stream;
    }
}

class PiesaMuzicala extends Streams {

    public PiesaMuzicala(String name, Integer id, Integer streamGenre, Integer streamerId, Long noStreams, String length, String dateAdded) {
        super(1, name, id, streamGenre, streamerId, noStreams, length, dateAdded);
    }

    @Override
    public genreName getGenre() {
        if (streamGenre == 1) return genreName.POP;
        if (streamGenre == 2) return genreName.LATIN;
        if (streamGenre == 3) return genreName.HOUSE;
        if (streamGenre == 4) return genreName.DANCE;
        if (streamGenre == 5) return genreName.TRAP;
        return null;
    }
}

class Podcast extends Streams {

    public Podcast(String name, Integer id, Integer streamGenre, Integer streamerId, Long noStreams, String length, String dateAdded) {
        super(2, name, id, streamGenre, streamerId, noStreams, length, dateAdded);
    }

    @Override
    public genreName getGenre() {
        if (streamGenre == 1) return genreName.DOCUMENTARY;
        if (streamGenre == 2) return genreName.CELEBRITIES;
        if (streamGenre == 3) return genreName.TECH;
        return null;
    }
}

class Audiobook extends Streams {

    public Audiobook(String name, Integer id, Integer streamGenre, Integer streamerId, Long noStreams, String length, String dateAdded) {
        super(3, name, id, streamGenre, streamerId, noStreams, length, dateAdded);
    }

    @Override
    public genreName getGenre() {
        if (streamGenre == 1) return genreName.FICTION;
        if (streamGenre == 2) return genreName.PERSONAL_DEVELOPMENT;
        if (streamGenre == 3) return genreName.CHILDREN;
        return null;
    }
}

class Factory {
    public Streams getType (String name, String type, Integer id, Integer streamGenre, Integer streamerId, Long noOfStreams, String length, String dateAdded){
        switch (type) {
            case "Piesa_Muzicala": return new PiesaMuzicala(name, id, streamGenre, streamerId, noOfStreams, length, dateAdded);
            case "Podcast": return new Podcast(name, id, streamGenre, streamerId, noOfStreams, length, dateAdded);
            case "Audiobook": return new Audiobook(name, id, streamGenre, streamerId, noOfStreams, length, dateAdded);
        }
        return null;
    }

}
