package Angela.exception;

public class MissingArgumentAngelaException extends AngelaException {

    public MissingArgumentAngelaException(String command) {
        super("Manager, I believe the instructions to use the "
                + command + "\ncommand was written in the manual.\n"
                + "Please provide the missing arguments.");
    }
}
