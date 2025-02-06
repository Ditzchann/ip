import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
	private List<Task> store = new ArrayList<>();

	public Processor()  {

	}

	public static void output(String text, Boolean head) {
		if (head) {
			System.out.println("____________________________________________________________");
		}
		output(text);
	}

	public static void output(String text) {
		System.out.println(text);
		System.out.println("____________________________________________________________");
	}

	public void start() { //move stuff here to Angela.java
		Scanner textIn = new Scanner(System.in);
		String input;
		do {
			input = textIn.nextLine();
			if (input.equalsIgnoreCase("list")) {
				printList();
			} else if (input.length() >= 4 && input.substring(0, 4).equalsIgnoreCase("mark")) { //probably move this in the future
				if (input.length() < 6 || !input.substring(5).matches("[0-9]+")) {
					printError(0);
				} else {
					markDone(Integer.parseInt(input.substring(5)));
				}
			} else if (input.length() >= 6 && input.substring(0, 6).equalsIgnoreCase("unmark")) {
				if (input.length() < 8 || !input.substring(7).matches("[0-9]+")) {
					printError(0);
				} else {
					markUndone(Integer.parseInt(input.substring(7)));
				}
			} else if (input.length() >= 4 && input.substring(0, 4).equalsIgnoreCase("todo")) {
				String name = trimInput(input);
				addTask(name);
			} else if (input.length() >= 8 && input.substring(0, 8).equalsIgnoreCase("deadline")) {
				String name = trimInput(input);
				String by = trimInput(input, "by");
				addTask(name, by);
			} else if (input.length() >= 5 && input.substring(0, 5).equalsIgnoreCase("event")) {
				String name = trimInput(input);
				String by = trimInput(input, "from"); //input validation zzzz
				String to = trimInput(input, "to");
				addTask(name, by, to);
			} else if (!input.equalsIgnoreCase("bye")) {
				addTask(input);
			}
		} while (!input.equals("bye"));
	}

	public void printList() {
		String out = "";
		int i = 1;
		for (Task t: store) {
			out = out.concat(i + ". " + t.toString() + "\n");
			i++;
		}
		output(out.trim(), true);
	}

	public void addTask(String input) {
		Task t = new ToDoTask((input));
		store.add(t);
		output("Did the Sephira give you a new task again?\n" + t +
				"\nTask added, you now have " + store.size() + " tasks left.", true);
	}

	public void addTask(String input, String by) {
		Task t = new DeadlineTask(input, by);
		store.add(t);
		output("A new energy quota to achieve. Please work hard, Manager.\n" + t +
				"\nTask added, you now have " + store.size() + " tasks left.", true);
	}

	public void addTask(String input, String from, String to) {
		Task t = new EventTask(input, from, to);
		store.add(t);
		output("Dawn arrives, with it comes a new Ordeal. Please take care of it, Manager.\n" + t +
				"\nTask added, you now have " + store.size() + " tasks left.", true);
	}

	public void markDone(int input) {
		//move validation here
		store.get(input - 1).doTask();
		output("Exceptional work Manager. The task has been marked as done.\n" + store.get(input - 1));
	}

	public void markUndone(int input) {
		store.get(input - 1).undoTask();
		output("More work for the Manager. The task has been undone.\n" + store.get(input - 1));
	}

	public void printError(int err) {
		if (err == 0) {
			output("Manager, I believe the instructions to use the command was written in the manual.\n" +
					"Please enter the command with the correct syntax.");
		}
	}

	public String trimInput(String input) {
		int i = 0;
		while (i < input.length() && input.charAt(i) != ' ') {
			i++;
		}
		i++;
		int start = i;
		while (i < input.length() && input.charAt(i) != '/') {
			i++;
		}
		return input.substring(start, i);
	}
	public String trimInput(String input, String arg) {
		Pattern searchFor = Pattern.compile("/" + arg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = searchFor.matcher(input);
		if (matcher.find()) {
			int i = matcher.end();
			while (i < input.length() && input.charAt(i) != '/') {
				i++;
			}
			return input.substring(matcher.end() + 1, i);
		}
		return "";
	}
}
