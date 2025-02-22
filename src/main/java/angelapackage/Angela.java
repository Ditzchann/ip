package angelapackage;

import java.util.Scanner;

import angelapackage.exception.AngelaException;
import angelapackage.exception.InvalidArgumentAngelaException;
import angelapackage.exception.InvalidDateTimeAngelaException;
import angelapackage.exception.MissingArgumentAngelaException;
import angelapackage.exception.OutOfBoundsAngelaException;

public class Angela {
    private static Storage storageManager;
    private static TaskManager manager;
    private static Parser parser;

    public Angela() {
    }

    public static void main(String[] args) {
        storageManager = new Storage();
        manager = new TaskManager();
        parser = new Parser();
        Output.introOutput();
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
        exit();
    }

    /**
     * @param input User input as a string
     * @return true if exit command is given
     * @throws MissingArgumentAngelaException If argument is missing
     * @throws InvalidDateTimeAngelaException If dateTime not in correct format
     * @throws OutOfBoundsAngelaException If index given is out of bounds
     * @throws InvalidArgumentAngelaException If argument is not a number
     */
    public static boolean processCommand(String input) throws AngelaException {
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

    public static void exit() {
        Output.exitOutput();
    }
}
