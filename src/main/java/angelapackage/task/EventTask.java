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

    public EventTask(String name, LocalDate from, LocalDate to){
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public Task doTask() {
        Task t = new EventTask(this.name, this.from, this.to);
        t.done = true;
        return t;
    }

    @Override
    public Task undoTask() {
        return new EventTask(this.name, this.from, this.to);
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
