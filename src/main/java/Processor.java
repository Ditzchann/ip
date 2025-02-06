import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

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
		store.add(new Task(input));
		output("added: " + input, true);
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
}
