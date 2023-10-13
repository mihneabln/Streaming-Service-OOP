
public class CommandControl {

    Commands c;

    public CommandControl(Commands c) {
        this.c = c;
    }

    public Commands getC() {
        return c;
    }

    public void insertCommand(){
        Database.instance().getCommands().add(c);
    }

    public void executeCommand(){
        for (Commands command : Database.instance().getCommands()) {
            command.execute();
        }
    }
}
