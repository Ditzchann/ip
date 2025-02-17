import java.time.LocalDateTime;

public class EventTask extends Task {
	private LocalDateTime from;
	private LocalDateTime to;

	public EventTask(String name, String from, String to) throws InvalidDateTimeAngelaException {
		super(name);
		this.from = parseTime(from);
		this.to = parseTime(to);
	}

	@Override
	public String toString() {
		return "[E]" + super.toString()  + " (from: " + from + " to " + to + ")";
	}

	@Override
	public String stringify() {
		return "E||" + super.stringify() + "||" + this.from + "||" + this.to;
	}
}
