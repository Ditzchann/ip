import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Processor {
	private List<Task> store;

	public Processor()  {
		store = new ArrayList<>();
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
                    Output.listOutput(store);
                    break;
                case "mark":
                    markDone(Integer.parseInt(command.get(1)));
                    break;
                case "unmark":
                    markUndone(Integer.parseInt(command.get(1)));
                    break;
                case "todo":
                    addTodoTask(command.get(1));
                    break;
                case "deadline":
                    addDeadlineTask(command.get(1), command.get(3));
                    break;
                case "event":
                    addEventTask(command.get(1), command.get(3), command.get(5));
                    break;
                case "bye":
                    bExit = true;
                    break;
            }
        }
	}

	public void addTodoTask(String input) {
		Task t = new ToDoTask((input));
		store.add(t);
		Output.addTaskOutput(store.size(), t, "todo");
	}

	public void addDeadlineTask(String input, String by) {
		Task t = new DeadlineTask(input, by);
		store.add(t);
        Output.addTaskOutput(store.size(), t, "deadline");
	}

	public void addEventTask(String input, String from, String to) {
		Task t = new EventTask(input, from, to);
		store.add(t);
        Output.addTaskOutput(store.size(), t, "event");
	}

	public void markDone(int input) {
		store.get(input - 1).doTask();
        Output.doneOutput(store.get(input - 1));
	}

	public void markUndone(int input) {
		store.get(input - 1).undoTask();
        Output.undoneOutput(store.get(input - 1));
	}

    /*
	public void printError(int err) {
		if (err == 0) {
			output("Manager, I believe the instructions to use the command was written in the manual.\n" +
					"Please enter the command with the correct syntax.");
		}
	}
    */

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
