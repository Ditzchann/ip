import java.util.ArrayList;
import java.util.List;
public class Output {
	public Output() {

	}

	private static void output(String text, Boolean head) {
		if (head) {
			System.out.println("____________________________________________________________");
		}
		output(text);
	}

	private static void output(String text) {
		System.out.println(text);
		System.out.println("____________________________________________________________");
	}

	public static void introOutput() {
		output("""
				Hello X. I am Angela, your advisor and secretary. \s 
				My role as an AI is to assist you in adjusting to your \s
				new workplace.\s
				What can I do for you, Manager?""", true);
	}

	public static void exitOutput() {
		output("Thank you for your hard work, Manager.");
	}

	public static void addTaskOutput(int tasksLeft, Task task, String taskType) {
		switch (taskType) {
			case "todo":
				output("Did the Sephira give you a new task again?\n" + task
						+ "\nTask added, you now have " + tasksLeft
						+ " tasks left.", true);
                break;
			case "deadline":
				output("A new energy quota to achieve. Please work hard, Manager.\n" + task
						+ "\nTask added, you now have " + tasksLeft
						+ " tasks left.", true);
                break;
			case "event":
				output("Dawn arrives, with it comes a new Ordeal. "
						+ "Please take care of it, Manager.\n" + task
						+ "\nTask added, you now have " + tasksLeft
						+ " tasks left.", true);
                break;
		}
	}

    public static void listOutput(List<Task> store) {
        String out = "";
        int i = 1;
        for (Task t: store) {
            out = out.concat(i + ". " + t.toString() + "\n");
            i++;
        }
        output(out.trim(), true);
    }

    public static void doneOutput(Task task) {
        output("Exceptional work Manager. The task has been marked as done.\n" + task);
    }

    public static void undoneOutput(Task task) {
        output("More work for the Manager. The task has been undone.\n" + task);
    }
}