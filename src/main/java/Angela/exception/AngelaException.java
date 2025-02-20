package Angela.exception;

public class AngelaException extends Exception {

    public AngelaException(String s) {
        super(s.isEmpty() ? "Are the Sephirah on meltdown again?\n"
                + "An error seems to have occurred." : s);
    }
}
