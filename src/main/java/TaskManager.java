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

    /*public boolean execute(List<String> command, Storage storageManager) {
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
        storageManager.save(store);
    }*/
}
