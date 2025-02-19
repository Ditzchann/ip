import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task{
	private LocalDate by;
	public DeadlineTask(String name, String by) throws InvalidDateTimeAngelaException {
		super(name);
		this.by = parseTime(by);
	}
	//probably need to edit the output
	@Override
	public String toString() {
		return "[D]" + super.toString()  + " (by: " + dateToString(this.by) + ")";
	}

	@Override
	public String stringify() {
		return "D||" + super.stringify() + "||" + dateToData(this.by);
	}
}
