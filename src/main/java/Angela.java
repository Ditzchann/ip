import java.util.Scanner;
import java.util.List;

public class Angela {
    public static void main(String[] args) {
        Storage storage = new Storage();
        TaskManager manager = new TaskManager();
        Parser parser = new Parser(storage);
        Processor process = new Processor(storage, manager, parser);
        Output.introOutput();
        process.start();
        exit();
    }

    public static void exit() {
        Output.exitOutput();
    }
}
