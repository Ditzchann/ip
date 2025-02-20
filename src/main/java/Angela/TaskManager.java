package Angela;

import Angela.exception.AngelaException;
import Angela.exception.InvalidArgumentAngelaException;
import Angela.exception.MissingArgumentAngelaException;
import Angela.exception.OutOfBoundsAngelaException;
import Angela.task.DeadlineTask;
import Angela.task.EventTask;
import Angela.task.Task;
import Angela.task.ToDoTask;

import java.util.ArrayList;
import java.util.List;

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
            List<String> args = command.getArguments(List.of("by"));
            Task t = new DeadlineTask(command.getMainArg(), args.get(0));
            store.add(t);
            Output.addTaskOutput(store.size(), t, "deadline");
        } catch (MissingArgumentAngelaException e) {
            throw new MissingArgumentAngelaException("deadline");
        }
    }

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

    public String tasksToString() {
        StringBuilder toWrite = new StringBuilder();
        for (Task t: store) {
            toWrite.append(t.stringify()).append("\n");
        }
        return toWrite.toString();
    }
}
