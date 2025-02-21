package angelapackage.task;

import java.time.LocalDate;

import angelapackage.exception.InvalidDateTimeAngelaException;

public class EventTask extends Task {
    private LocalDate from;
    private LocalDate to;

    public EventTask(String name, String from, String to) throws InvalidDateTimeAngelaException {
        super(name);
        this.from = parseTime(from);
        this.to = parseTime(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + dateToString(this.from) + " to " + dateToString(this.to) + ")";
    }

    @Override
    public String stringify() {
        return "E||" + super.stringify() + "||" + this.from + "||" + this.to;
    }
}
