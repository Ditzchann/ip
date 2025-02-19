import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class Processor {
	private List<Task> store;
    private Storage storageManager;
    private TaskManager manager;
    private Parser parser;

	public Processor(Storage storageManager, TaskManager manager, Parser parser)  {
		store = new ArrayList<>();
        this.storageManager = storageManager;
        this.parser = parser;
        this.manager = manager;
	}

	public void start() {
		Scanner textIn = new Scanner(System.in);
		String input;
        boolean bExit = false;
        try {
            store = storageManager.init();
        } catch (AngelaException e) {
            Output.errorOutput(e);
            bExit = true;
        }
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

	public void addTodoTask(Command command) throws MissingArgumentAngelaException {
        if (command.getMainArg().isEmpty()) {
            throw new MissingArgumentAngelaException("todo");
        }
        String input = command.getMainArg();
		Task t = new ToDoTask((input));
		store.add(t);
		Output.addTaskOutput(store.size(), t, "todo");
	}

	public void addDeadlineTask(Command command) throws AngelaException {
		try {
			List<String> args = getArguments(command, List.of("by"));
			Task t = new DeadlineTask(command.getMainArg(), args.get(0));
			store.add(t);
			Output.addTaskOutput(store.size(), t, "deadline");
		} catch (MissingArgumentAngelaException e) {
			throw new MissingArgumentAngelaException("deadline");
		}
	}

	public void addEventTask(Command command) throws AngelaException {
		try {
			List<String> args = getArguments(command, List.of("from", "to"));
			Task t = new EventTask(command.getMainArg(), args.get(0), args.get(1));
			store.add(t);
			Output.addTaskOutput(store.size(), t, "event");
		} catch (MissingArgumentAngelaException e) {
			throw new MissingArgumentAngelaException("event");
		}
	}

	public List<String> getArguments(Command command, List<String> params) throws MissingArgumentAngelaException {
        List<String> args = Arrays.asList(new String[params.size()]);
		for (int i = 0; i < args.size(); i++) {
            args.set(i, command.getArg(params.get(i)));
        }
		if (args.contains(null)) {
			throw new MissingArgumentAngelaException(command.getName());
		} else {
			return args;
		}
	}

	public void markDone(Command command) throws AngelaException {
        if (command.getMainArg().isBlank()) {
            throw new MissingArgumentAngelaException("mark");
        }
        try {
            int input = Integer.parseInt(command.getMainArg());
            store.get(input - 1).doTask();
            Output.doneOutput(store.get(input - 1));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentAngelaException("mark");
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsAngelaException();
        }
	}

	public void markUndone(Command command) throws AngelaException {
        if (command.getMainArg().isBlank()) {
            throw new MissingArgumentAngelaException("unmark");
        }
        try {
            int input = Integer.parseInt(command.getMainArg());
            store.get(input - 1).undoTask();
            Output.undoneOutput(store.get(input - 1));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentAngelaException("unmark");
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfBoundsAngelaException();
        }
	}

    public void deleteTask(Command command) throws AngelaException {
        if (command.getMainArg().isBlank()) {
            throw new MissingArgumentAngelaException("delete");
        }
        try {
            int input = Integer.parseInt(command.getMainArg());
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
        List<String> commandList;
        commandList = splitInput(input);
        Command command = listToCommand(commandList);
        String commandName = command.getName();
        switch (commandName) {
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
        storageManager.save(store);
        return false;
    }

    public Command listToCommand(List<String> commandList) {
        try {
            Command command;
            if (commandList.size() >= 2){
                command = new Command(commandList.get(0), commandList.get(1));
            } else {
                command = new Command(commandList.get(0), "");
            }
            for (int i = 2; i < commandList.size(); i += 2) {
                String para = commandList.get(i);
                String arg;
                if (commandList.size() < i + 1) {
                    arg = "";
                } else {
                    arg = commandList.get(i + 1);
                }
                command.addArg(para, arg);
            }
            return command;
        } catch (IndexOutOfBoundsException e) {
            return new Command("", "");
        }
    }
}
