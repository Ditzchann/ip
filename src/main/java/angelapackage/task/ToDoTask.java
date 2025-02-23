package angelapackage.task;




public class ToDoTask extends Task {
    public ToDoTask(String name) {
        super(name);
    }

    @Override
    public Task doTask() {
        Task t = new ToDoTask(this.name);
        t.done = true;
        return t;
    }

    @Override
    public Task undoTask() {
        return new ToDoTask(this.name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String stringify() {
        return "T||" + super.stringify();
    }
}
