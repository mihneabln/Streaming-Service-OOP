
public class CommandStreamerListStream implements Commands{

    String idStreamer;

    public CommandStreamerListStream(String idStreamers) {
        this.idStreamer = idStreamers;
    }

    @Override
    public void execute() {
        Database.output = "[";
        for (Streams stream : Database.instance().getStreams()){
            if (stream.streamerId == Integer.parseInt(idStreamer)) {
                if (Database.output.length() > 2)
                    Database.output += ",";
                Database.output += stream.jsonConversion();
            }
        }
        if (Database.output.length() > 2){
            Database.output += "]" + '\n';
        }
        Database.concat += Database.output;
    }
}
