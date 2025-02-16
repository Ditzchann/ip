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
            if (input.isEmpty()) {
                Output.idleOutput();
            } else {
                try {
                    bExit = processCommand(input);
                } catch (AngelaException e) {
                    Output.errorOutput(e);
                }

            }
        }
	}

	public void addTodoTask(List<String> command) throws MissingArgumentAngelaException {
        if (command.size() < 2) {
            throw new MissingArgumentAngelaException("todo");
        }
        String input = command.get(1);
		Task t = new ToDoTask((input));
		store.add(t);
		Output.addTaskOutput(store.size(), t, "todo");
	}

	public void addDeadlineTask(List<String> command) throws MissingArgumentAngelaException {
        if (command.size() < 4) {
            throw new MissingArgumentAngelaException("deadline");
        }
        String input = command.get(1);
        String by = command.get(3); //check if flag corresponds to correct flag : TBA
		Task t = new DeadlineTask(input, by);
		store.add(t);
        Output.addTaskOutput(store.size(), t, "deadline");
	}

	public void addEventTask(List<String> command) throws MissingArgumentAngelaException {
        if (command.size() < 6) {
            throw new MissingArgumentAngelaException("event");
        }
        String input = command.get(1);
        String from = command.get(3);
        String to = command.get(5);
		Task t = new EventTask(input, from, to);
		store.add(t);
        Output.addTaskOutput(store.size(), t, "event");
	}

	public void markDone(List<String> command) throws AngelaException {
        if (command.size() < 2) {
            throw new MissingArgumentAngelaException("mark");
        }
        try {
            int input = Integer.parseInt(command.get(1));
            store.get(input - 1).doTask();
            Output.doneOutput(store.get(input - 1));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentAngelaException("mark");
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsAngelaException();
        }
	}

	public void markUndone(List<String> command) throws AngelaException {
        if (command.size() < 2) {
            throw new MissingArgumentAngelaException("unmark");
        }
        try {
            int input = Integer.parseInt(command.get(1));
            store.get(input - 1).undoTask();
            Output.undoneOutput(store.get(input - 1));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentAngelaException("unmark");
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsAngelaException();
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

    public boolean processCommand(String input) throws AngelaException {
        List<String> command;
        command = splitInput(input);
        switch (command.get(0)) {
        case "list":
            Output.listOutput(store);
            break;
        case "mark":
            markDone(command);
            break;
        case "unmark":
            markUndone(command);
            break;
        case "todo":
            addTodoTask(command);
            break;
        case "deadline":
            addDeadlineTask(command);
            break;
        case "event":
            addEventTask(command);
            break;
        case "bye":
            return true;
        default:
            Output.invalidCommandOutput();
        }
        return false;
    }
}
