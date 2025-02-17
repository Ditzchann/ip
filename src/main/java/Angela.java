import java.util.Scanner;
import java.util.List;

public class Angela {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Processor process = new Processor(storage);
        Output.introOutput();
        process.start();
        exit();
    }

    public static void exit() {
        Output.exitOutput();
    }
}
