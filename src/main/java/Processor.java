import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor {
	private List<Task> store;

	public Processor()  {
		store = new ArrayList<>();
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

	public void start() {
		Scanner textIn = new Scanner(System.in);
		String input;
        boolean bExit = false;
        while (!bExit) {
            input = textIn.nextLine();
            List<String> command = splitInput(input);
            switch (command.get(0)) {
                case "list":
                    printList();
                    break;
                case "mark":
                    markDone(Integer.parseInt(command.get(1)));
                    break;
                case "unmark":
                    markUndone(Integer.parseInt(command.get(1)));
                    break;
                case "todo":
                    addTask(command.get(1));
                    break;
                case "deadline":
                    addTask(command.get(1), command.get(3));
                    break;
                case "event":
                    addTask(command.get(1), command.get(3), command.get(5));
                    break;
                case "bye":
                    bExit = true;
                    break;
            }
        }
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

	public List<String> splitInput(String input) {
		List<String> args = new ArrayList<>();
		int i = 0;
		i = getEndIndexOfWord(i, input, args);
		i = getEndIndexOfArg(i, input, args);
		while (i < input.length()) {
			i = getEndIndexOfWord(i, input, args);
            i = getEndIndexOfArg(i, input, args);
		}
        return args;
	}
	public int getEndIndexOfWord(int start, String input, List<String> args) {
		int i = start;
		while (i < input.length() && input.charAt(i) != ' ') {
			i++;
		}
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
		return i + 1;
	}

    public int getEndIndexOfArg(int start, String input, List<String> args) {
        int i = start;
        while (i < input.length() && input.charAt(i) != '/') {
            i++;
        }
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
        return i + 1;
    }
}
