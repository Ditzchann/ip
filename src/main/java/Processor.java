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
}
