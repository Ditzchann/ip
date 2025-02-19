import java.time.LocalDate;
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

	public LocalDate parseTime(String time) throws InvalidDateTimeAngelaException {
		LocalDate result;
		DateTimeFormatter formatter;
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try {
			result = LocalDate.parse(time.trim(), formatter);
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

	public String dateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		return date.format(formatter);
	}


	public String dateToData(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(formatter);
	}
}
