import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task{
	private LocalDateTime by;
	public DeadlineTask(String name, String by) throws InvalidDateTimeAngelaException {
		super(name);
		this.by = parseTime(by);
	}

	@Override
	public String toString() {
		return "[D]" + super.toString()  + " (by: " + by + ")";
	}

	@Override
	public String stringify() {
		return "D||" + super.stringify() + "||" + this.by;
	}
}
