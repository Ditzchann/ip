import java.util.Scanner;
public class Angela {
    public static void main(String[] args) {
        Scanner textIn = new Scanner(System.in);
        String input;
        output("""
				Hello X. I am Angela, your advisor and secretary.\s
				My role as an AI is to assist you in adjusting to your \s
				new workplace.\s
				What can I do for you, Manager?""", true);
        do {
            input = textIn.nextLine();
            if (input != "bye") {
                output(input, true);
            }
        } while (input != "bye"));
        exit();
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

    public static void exit() {
        output("Thank you for your hard work, Manager.");
    }
}
