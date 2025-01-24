import java.util.Scanner;
import java.util.List;

public class Angela {
    public static void main(String[] args) {
        Processor process = new Processor();
        output("""
				Hello X. I am Angela, your advisor and secretary.\s 
				My role as an AI is to assist you in adjusting to your \s
				new workplace.\s
				What can I do for you, Manager?""", true);
        process.start();
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
