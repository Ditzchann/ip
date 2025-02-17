import java.util.ArrayList;
import java.util.Arrays;
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
		try {
			List<String> args = getArguments(command, List.of("by"));
			Task t = new DeadlineTask(args.get(0), args.get(1));
			store.add(t);
			Output.addTaskOutput(store.size(), t, "deadline");
		} catch (AngelaException e) {
			throw new MissingArgumentAngelaException("deadline");
		}
	}

	public void addEventTask(List<String> command) throws MissingArgumentAngelaException {
		try {
			List<String> args = getArguments(command, List.of("from", "to"));
			Task t = new EventTask(args.get(0), args.get(1), args.get(2));
			store.add(t);
			Output.addTaskOutput(store.size(), t, "event");
		} catch (AngelaException e) {
			throw new MissingArgumentAngelaException("event");
		}
	}

	public List<String> getArguments(List<String> command, List<String> params) throws AngelaException {
		List<String> args = Arrays.asList(new String[params.size() + 1]);
		if (command.size() < 2) {
			throw new MissingArgumentAngelaException(command.get(0));
		} else {
			args.set(0, command.get(1));
		}
		for (int k = 2; k < command.size(); k += 2) {
			String temp = command.get(k);
			if (params.contains(temp)) {
				args.set(params.indexOf(temp) + 1, command.get(k + 1));
			}
		}
		if (args.contains(null)) {
			throw new MissingArgumentAngelaException(command.get(0));
		} else {
			return args;
		}
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

    public void deleteTask(List<String> command) throws AngelaException {
        if (command.size() < 2) {
            throw new MissingArgumentAngelaException("delete");
        }
        try {
            int input = Integer.parseInt(command.get(1));
            Task t = store.remove(input - 1);
            Output.deleteOutput(t, store.size());
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
        case "delete":
            deleteTask(command);
            break;
        case "bye":
            return true;
        default:
            Output.invalidCommandOutput();
        }
        return false;
    }
}
