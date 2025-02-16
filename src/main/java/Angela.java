import java.util.Scanner;
import java.util.List;

public class Angela {
    public static void main(String[] args) {
        Processor process = new Processor();
        Output.introOutput();
        process.start();
        exit();
    }

    public static void exit() {
        Output.exitOutput();
    }
}
