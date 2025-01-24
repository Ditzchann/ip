import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Processor {
	public Processor()  {

	}
	public static void output(String text, Boolean head) {
		if (head) {
			System.out.println("____________________________________________________________");
		}
		output(text);
	}
	public static void output(String text) {
		System.out.println(text);
		System.out.println("____________________________________________________________");
	}
	public void start() {
		Scanner textIn = new Scanner(System.in);
		String input;
		List<String> store = new ArrayList<>();
		do {
			input = textIn.nextLine();
			if (input.equalsIgnoreCase("list")) {
				String out = "";
				int i = 1;
				for (String s: store) {
					out = out.concat(i + ". " + s + "\n");
					i++;
				}
				output(out, true);
			} else if (!input.equalsIgnoreCase("bye")) {
				store.add(input);
				output("added: " + input, true);
			}
		} while (!input.equals("bye"));
	}
}
