package angelapackage.gui;

import java.util.List;

import angelapackage.exception.AngelaException;
import angelapackage.task.Task;


/**
 * Class that handles output to user
 */
public class Output {

    private static String lastOut;

    public Output() {

    }
    private static void output(String text, Boolean head) {
        if (head) {
            System.out.println("____________________________________________________________");
        }
        output(text);
    }

    private static void output(String text) {
        System.out.println(text);
        lastOut = text;
        System.out.println("____________________________________________________________");
    }

    public static void introOutput() {
        output("""
                Hello X. I am Angela, your advisor and secretary.
                My role as an AI is to assist you in adjusting to your
                new workplace.
                What can I do for you, Manager?""", true);
    }

    public static void exitOutput() {
        output("Thank you for your hard work, Manager.");
    }

    public static void addTaskOutput(int tasksLeft, Task task, String taskType) {
        switch (taskType) {
        case "todo":
            output("Did the Sephira give you a new task again?\n" + task
                    + "\nTask added, you now have " + tasksLeft
                    + " task(s) left.", true);
            break;
        case "deadline":
            output("A new energy quota to achieve. Please work hard, Manager.\n" + task
                    + "\nTask added, you now have " + tasksLeft
                    + " task(s) left.", true);
            break;
        case "event":
            output("Dawn arrives, with it comes a new Ordeal. "
                    + "Please take care of it, Manager.\n" + task
                    + "\nTask added, you now have " + tasksLeft
                    + " task(s) left.", true);
            break;

        default:
        }
    }

    public static void listOutput(List<Task> store) {
        output(storeToString(store).trim(), true);
    }

    public static String storeToString(List<Task> store) {
        String out = "";
        int i = 1;
        for (Task t: store) {
            out = out.concat(i + ". " + t.toString() + "\n");
            i++;
        }
        return out;
    }

    public static void doneOutput(Task task) {
        output("Exceptional work Manager. The task has been marked as done.\n" + task);
    }

    public static void undoneOutput(Task task) {
        output("More work for the Manager. The task has been undone.\n" + task);
    }

    public static void idleOutput() {
        output("Did you say something, Manager?", true);
    }

    public static void errorOutput(AngelaException e) {
        output(e.getMessage(), true);
    }

    public static void invalidCommandOutput() {
        output("Manager, I cannot comprehend your incoherent blabbering.\n"
                + "Do you require a mental corruption evaluation?", true);
    }

    public static void deleteOutput(Task task, int tasksLeft) {
        output("As tonight, again the stars are brushed away by the wind.\n"
                + task + "\nTask removed, you now have " + tasksLeft
                + " task(s) left.", true);
    }

    public static void findOutput(List<Task> store) {
        output("Save your thanks. Such a task is trivial for an AI like me.\n"
                + storeToString(store), true);
    }

    public static String getLastOut() {
        return lastOut;
    }

    public static void emptyUndo() {
        output("The memory repository was freshly imprinted.\n"
                + "There's no turning back from here.");
    }

    public static void badUndo() {
        output("It appears that some of your actions cannot\n"
                + "be undone through the memory repository."
                + "Is this what you are trying to achieve, A?");
    }

    public static void worseUndo() {
        output("Why repeat this endless cycle? There is no light\n"
                + "to be found at the end of this 50 days.");
    }

    public static void undo(List<Task> store) {
        output("""
                Time has been rewound to the memory repository.
                The current task list is as follows:
                """ + storeToString(store));
    }
}
