package Angela.exception;

public class OutOfBoundsAngelaException extends AngelaException {

    public OutOfBoundsAngelaException() {
        super("Manager, the abnormality, agent or task you\n"
                + "are referring to does not exist.\n"
                + "Do you require a mental corruption evaluation?");
    }
}
