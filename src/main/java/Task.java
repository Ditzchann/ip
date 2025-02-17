import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
	private final String name;
	private boolean done;

	public Task(String name) {
		this.name = name;
		this.done = false;
	}

	public void doTask() {
		this.done = true;
	}

	public void undoTask() {
		this.done = false;
	}

	public LocalDateTime parseTime(String time) throws InvalidDateTimeAngelaException {
		LocalDateTime result;
		DateTimeFormatter formatter;
		if (time.length() <= 10) {
			formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		} else {
			formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		}
		time = time.replaceAll("/","-");
		try {
			result = LocalDateTime.parse(time, formatter);
			return result;
		} catch (DateTimeParseException e) {
			throw new InvalidDateTimeAngelaException();
		}
	}

	@Override
	public String toString() {
		String out = "[";
		if (this.done) {
			out = out.concat("X");
		} else {
			out = out.concat(" ");
		}
		out = out.concat("] ");
		return out + this.name;
	}

	public String stringify() {
		String doneString = this.done ? "1" : "0";
		return this.name + "||" +  doneString;
	}
}
