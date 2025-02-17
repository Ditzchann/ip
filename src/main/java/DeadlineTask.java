public class DeadlineTask extends Task{
	private String by;
	public DeadlineTask(String name, String by) {
		super(name);
		this.by = by;
	}

	@Override
	public String toString() {
		return "[D]" + super.toString()  + " (by: " + by + ")";
	}

	@Override
	public String stringify() {
		return "D||" + super.stringify() + "||" + this.by;
	}
}
