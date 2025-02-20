package Angela;
import Angela.exception.AngelaException;
import Angela.exception.MissingArgumentAngelaException;
import Angela.exception.InvalidArgumentAngelaException;
import Angela.exception.InvalidDateTimeAngelaException;
import Angela.exception.OutOfBoundsAngelaException;

import java.util.Scanner;

/**
 * Central class responsible for handling information passing from one class to another.
 * Takes in input from user and calls corresponding method in associated class.
 */
public class Processor {
    private Storage storageManager;
    private TaskManager manager;
    private Parser parser;

	public Processor(Storage storageManager, TaskManager manager, Parser parser)  {
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

    /**
     *
     * @param input User input as a string
     * @return true if exit command is given
     * @throws MissingArgumentAngelaException If argument is missing
     * @throws InvalidDateTimeAngelaException If dateTime not in correct format
     * @throws OutOfBoundsAngelaException If index given is out of bounds
     * @throws InvalidArgumentAngelaException If argument is not a number
     */
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
        case "find":
            manager.findTask(command);
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
