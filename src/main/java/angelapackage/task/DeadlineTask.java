package angelapackage.task;

import java.time.LocalDate;

import angelapackage.exception.InvalidDateTimeAngelaException;

public class DeadlineTask extends Task {
    private LocalDate by;

    public DeadlineTask(String name, String by) throws InvalidDateTimeAngelaException {
        super(name);
        this.by = parseTime(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateToString(this.by) + ")";
    }

    @Override
    public String stringify() {
        return "D||" + super.stringify() + "||" + dateToData(this.by);
    }
}
