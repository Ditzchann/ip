package angelapackage;

import java.util.List;

import angelapackage.exception.AngelaException;
import angelapackage.gui.Output;
import angelapackage.task.Task;

public class Angela {
    private Storage storageManager;
    private TaskManager manager;
    private Parser parser;
    private Command lastFunction;

    public Angela() {
        storageManager = new Storage();
        manager = new TaskManager();
        parser = new Parser();
        lastFunction = null;
        Output.introOutput();
        try {
            List<Task> existingTasks = storageManager.init();
            manager.init(existingTasks);
        } catch (AngelaException e) { //should be thrown to gui
            Output.errorOutput(e);
        }
    }

    /**
     * @param input User input as a string
     * @return true if exit command is given
     */
    public boolean processCommand(String input) {
        try {
            Command command = parser.parseCommand(input);
            boolean bBadUndo = false;
            boolean bDataChanged = true;
            manager.archive(command);
            switch (command.getName()) {
            case "list":
                manager.displayList();
                bDataChanged = false;
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
                bDataChanged = false;
                break;
            case "undo":
                bBadUndo = undo();
                break;
            case "bye":
                return true;
            default:
                Output.invalidCommandOutput();
                bBadUndo = true;
            }
            if (bBadUndo) {
                lastFunction = new Command("badUndo", "");
            } else if (bDataChanged){
                lastFunction = command;
            }
            storageManager.save(manager.tasksToString());
            return false;
        } catch (AngelaException e) {
            Output.errorOutput(e);
            lastFunction = new Command("badUndo", "");
            return false;
        }
    }

    public static void exit() {
        Output.exitOutput();
    }

    public boolean undo() {
        boolean bBadUndo = false;
        if (lastFunction == null) {
            Output.emptyUndo();
            return true;
        }
        switch (lastFunction.getName()) {
        case "todo":
        case "deadline":
        case "event":
        case "mark":
        case "unmark":
        case "undo":
        case "delete":
            manager.undo();
            break;
        case "error":
            Output.badUndo();
            bBadUndo = true;
            break;
        case "badUndo":
            Output.worseUndo();
            break;
        default:
            assert false; //code should not come here - independent of user input
        }
        return bBadUndo;
    }
}
