import java.io.*;

public class ProiectPOO {

    public static void main(String[] args) throws IOException {

        if (args == null) {
            System.out.println("Nothing to read here");
        }
        else {

            final String path = "src/main/resources/";
            File fileStreamers = new File(path + args[0]);
            File fileStreams = new File(path + args[1]);
            File fileUsers = new File(path + args[2]);
            File fileCommands = new File(path + args[3]);

            Database spotify = Database.instance();   // instantiez database si copiez fisierele in listele corespunzatoare
            spotify.addUsersInSpotify(fileUsers);
            spotify.addStreamersInSpotify(fileStreamers);
            spotify.addStreamsInSpotify(fileStreams);
            CommandControl control = null;

            try (BufferedReader br = new BufferedReader(new FileReader((fileCommands)))) {
                String line = new String();
                while ((line =br.readLine())!=null){

                    control = null;

                    if (line.split(" ")[1].equals(("LIST"))) {
                        if (spotify.getUserById(line.split(" ")[0]) != null) {    // id ul este al unui user
                            control = new CommandControl(new CommandUserListStreams(line.split(" ")[0]));
                            control.insertCommand();
                        }
                        else {                                                          // id ul este al unui streamer
                            control = new CommandControl(new CommandStreamerListStream(line.split(" ")[0]));
                            control.insertCommand();
                        }
                    }

                    else if (line.split(" ")[1].equals(("ADD"))) {
                        String name = "";
                        for (int i = 6; i < line.split(" ").length; i++){
                            if(line.split(" ")[i] != null)
                                name += line.split(" ")[i] + " ";
                        }
                        name = name.trim();
                        control = new CommandControl(new CommandStreamerAddStream(line.split(" ")[0], line.split(" ")[2],
                                line.split(" ")[3], line.split(" ")[4], line.split(" ")[5], name));
                        control.insertCommand();
                    }

                    else if (line.split(" ")[1].equals(("DELETE"))) {
                        control = new CommandControl(new CommandStreamerDeleteStream(line.split(" ")[0], line.split(" ")[2]));
                        control.insertCommand();
                    }

                    else if (line.split(" ")[1].equals(("LISTEN"))) {
                        control = new CommandControl(new CommandUserListen(line.split(" ")[0], line.split(" ")[2]));
                        control.insertCommand();
                    }

                    else if (line.split(" ")[1].equals(("RECOMMEND"))) {
                        control = new CommandControl(new CommandUserRecommend(line.split(" ")[0], line.split(" ")[2]));
                        control.insertCommand();
                    }

                    else if (line.split(" ")[1].equals(("SURPRISE"))) {
                        control = new CommandControl(new CommandUserSurprise(line.split(" ")[0], line.split(" ")[2]));
                        control.insertCommand();
                    }
                }

            }
            catch (IOException e) {}

            assert control != null;
            control.executeCommand();
            System.out.println(Database.concat);
            Database.concat = "";

            Database.eraseInstance();

            print();
        }
    }

    public static String str = "";
    public static void print () throws IOException {
        File fileOut = new File("src/main/java/output/");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileOut)))) {
            out.println(str);
        }
    }

}