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
}
