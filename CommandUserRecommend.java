public class CommandUserRecommend  implements Commands{

    String idUser;
    String streamType;
    int wantedType;

    public CommandUserRecommend(String idUser, String streamType) {
        this.idUser = idUser;
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

        Database.output = "[";

        User user = Database.instance().getUserById(idUser);
        for (String usersStreams : user.streams) {
            for (Streams stream : Database.instance().getStreams()) {
                if (usersStreams.equals(Long.toString(stream.id))) {    // pt fiecare stream al userului
                    String ret = stream.getRecommend(wantedType);
                    if (ret != null) {
                        if (Database.output.length() > 2)
                            Database.output += ",";
                        Database.output += ret;
                    }
                }
            }
        }
        if (Database.output.length() > 2){
            Database.output += "]" + '\n';
        }
        Database.concat += Database.output;
    }
}
