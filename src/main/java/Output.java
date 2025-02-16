public class Output {
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
		System.out.println("____________________________________________________________");
	}

	public static void introOutput() {
		output("""
				Hello X. I am Angela, your advisor and secretary. \s 
				My role as an AI is to assist you in adjusting to your \s
				new workplace.\s
				What can I do for you, Manager?""", true);
	}

	public static void exitOutput() {
		output("Thank you for your hard work, Manager.");
	}
}