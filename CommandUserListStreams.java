
public class CommandUserListStreams implements Commands{

    String idUser;

    public CommandUserListStreams(String id) {
        this.idUser = id;
    }

    public User getUser(){
        return Database.instance().getUserById(idUser);
    }

    @Override
    public void execute() {
        Database.output = "[";
        for (String streamId : getUser().streams) {     // streamuri user curent
            for (Streams stream : Database.instance().getStreams()) {
                if (stream.id == Integer.parseInt(streamId)) {
                    if (Database.output.length() > 2)
                        Database.output += ",";
                    Database.output += stream.jsonConversion();
                }
            }
        }
        if (Database.output.length() >  2){
            Database.output += "]" + '\n';
        }
        Database.concat += Database.output;
    }
}
