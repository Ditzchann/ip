package angelapackage;

import java.util.ArrayList;
import java.util.List;

import angelapackage.exception.AngelaException;
import angelapackage.exception.InvalidArgumentAngelaException;
import angelapackage.exception.InvalidDateTimeAngelaException;
import angelapackage.exception.MissingArgumentAngelaException;
import angelapackage.exception.OutOfBoundsAngelaException;
import angelapackage.gui.Output;
import angelapackage.task.DeadlineTask;
import angelapackage.task.EventTask;
import angelapackage.task.Task;
import angelapackage.task.ToDoTask;

/**
 * Helper class to manage and store tasks.
 */
public class TaskManager {

    private List<Task> store;

    public TaskManager() {
        store = new ArrayList<>();
    }

    public void init(List<Task> store) {
        this.store = store;
    }

    public void displayList() {
        Output.listOutput(store);
    }

    /**
     * Sets task to done after validating input from user
     * @param command Parsed command from user
     * @throws AngelaException If input is invalid
     */
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

    /**
     * Sets task to not done after validating input from user
     * @param command Parsed command from user
     * @throws AngelaException If input is invalid
     */
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

    /**
     * Deletes task from storage
     * @param command Parsed command from user
     * @throws AngelaException If input is invalid
     */
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

    /**
     * Adds a ToDoTask to the storage
     * @param command Parsed command from user
     * @throws MissingArgumentAngelaException If not enough arguments are provided
     */
    public void addTodoTask(Command command) throws MissingArgumentAngelaException {
        if (command.getMainArg().isEmpty()) {
            throw new MissingArgumentAngelaException("todo");
        }
        String input = command.getMainArg();
        Task t = new ToDoTask((input));
        store.add(t);
        Output.addTaskOutput(store.size(), t, "todo");
    }

    /**
     * Adds a DeadlineTask to the storage
     * @param command Parsed command from user
     * @throws MissingArgumentAngelaException If not enough arguments are provided
     * @throws InvalidDateTimeAngelaException If time format is wrong
     */
    public void addDeadlineTask(Command command) throws AngelaException {
        try {
            List<String> args = command.getArguments(List.of("by"));
            Task t = new DeadlineTask(command.getMainArg(), args.get(0));
            store.add(t);
            Output.addTaskOutput(store.size(), t, "deadline");
        } catch (MissingArgumentAngelaException e) {
            throw new MissingArgumentAngelaException("deadline");
        }
    }

    /**
     * Adds a EventTask to the storage
     * @param command Parsed command from user
     * @throws MissingArgumentAngelaException If not enough arguments are provided
     * @throws InvalidDateTimeAngelaException If time format is wrong
     */
    public void addEventTask(Command command) throws AngelaException {
        try {
            List<String> args = command.getArguments(List.of("from", "to"));
            Task t = new EventTask(command.getMainArg(), args.get(0), args.get(1));
            store.add(t);
            Output.addTaskOutput(store.size(), t, "event");
        } catch (MissingArgumentAngelaException e) {
            throw new MissingArgumentAngelaException("event");
        }
    }

    /**
     * Searches and outputs Task objects that match provided keyword
     * @param command Parsed command from user
     * @throws MissingArgumentAngelaException If not enough arguments are provided
     */
    public void findTask(Command command) throws MissingArgumentAngelaException {
        if (command.getMainArg().isEmpty()) {
            throw new MissingArgumentAngelaException("find");
        }
        String find = command.getMainArg();
        List<Task> found = new ArrayList<>();
        for (Task t: store) {
            if (t.containsKeyword(find)) {
                found.add(t);
            }
        }
        Output.findOutput(found);
    }

    /**
     * Returns the string format of how tasks are represented when stored in storage
     * @return Tasks represented as string data
     */
    public String tasksToString() {
        StringBuilder toWrite = new StringBuilder();
        for (Task t: store) {
            toWrite.append(t.stringify()).append("\n");
        }
        return toWrite.toString();
    }
}
