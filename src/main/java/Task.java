public class Task {
	private final String name;
	private boolean done;

	public Task(String name) {
		this.name = name;
		this.done = false;
	}

	public void doTask() {
		this.done = true;
	}

	public void undoTask() {
		this.done = false;
	}

	@Override
	public String toString() {
		String out = "[";
		if (this.done) {
			out = out.concat("X");
		} else {
			out = out.concat(" ");
		}
		out = out.concat("] ");
		return out + this.name;
	}

	public String stringify() {
		String doneString = this.done ? "1" : "0";
		return this.name + "||" +  doneString;
	}
}
