package angelapackage.exception;

public class InvalidDateTimeAngelaException extends AngelaException {
    public InvalidDateTimeAngelaException() {
        super("That day does not exist, just as the many days that\n"
                + "have passed in this endless cycle.");
    }
}
