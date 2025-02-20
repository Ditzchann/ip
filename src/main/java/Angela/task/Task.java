package Angela.task;

import Angela.exception.InvalidDateTimeAngelaException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a task that the user can do. A Task object is represented
 * by its name and whether it is done
 */
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
		return doneString + "||" + this.name ;
	}

	public String dateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		return date.format(formatter);
	}


	public String dateToData(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(formatter);
	}

	/**
	 * Checks if task name contains given keyword
	 * @param keyword Keyword to check
	 * @return true if this.name contains keyword
	 */
	public boolean containsKeyword(String keyword) {
		return this.name.contains(keyword);
	}
}
