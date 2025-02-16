public class InvalidArgumentAngelaException extends AngelaException {

    public InvalidArgumentAngelaException(String command) {
        super("Manager, I believe the instructions to use the "
                + command + " command was written in the manual.\n"
                + "Please provide meaningful arguments to the command.");
    }
}
