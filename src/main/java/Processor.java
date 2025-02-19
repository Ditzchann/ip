import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

public class Processor {
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
            manager.init(storageManager.init());
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

    public boolean processCommand(String input) throws AngelaException {
        Command command = parser.parseCommand(input);
        switch (command.getName()) {
        case "list":
            manager.displayList();
            break;
        case "mark":
            manager.markDone(command);
            break;
        case "unmark":
            manager.markUndone(command);
            break;
        case "todo":
            manager.addTodoTask(command);
            break;
        case "deadline":
            manager.addDeadlineTask(command);
            break;
        case "event":
            manager.addEventTask(command);
            break;
        case "delete":
            manager.deleteTask(command);
            break;
        case "bye":
            return true;
        default:
            Output.invalidCommandOutput();
        }
        storageManager.save(manager.tasksToString());
        return false;
    }
}
