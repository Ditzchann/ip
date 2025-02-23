package angelapackage.task;

import java.time.LocalDate;

import angelapackage.exception.AngelaException;
import angelapackage.exception.InvalidDateTimeAngelaException;

public class DeadlineTask extends Task {
    private LocalDate by;

    public DeadlineTask(String name, String by) throws InvalidDateTimeAngelaException {
        super(name);
        this.by = parseTime(by);
    }

    public DeadlineTask(String name, LocalDate by) {
        super(name);
        this.by = by;
    }

    @Override
    public Task doTask() {
        Task t = new DeadlineTask(this.name, this.by);
        t.done = true;
        return t;
    }

    @Override
    public Task undoTask() {
        return new DeadlineTask(this.name, this.by);
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
