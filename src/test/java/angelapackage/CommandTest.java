package angelapackage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import angelapackage.exception.MissingArgumentAngelaException;

public class CommandTest {
    @Test
    public void getArguments_oneArgument() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("test test /a 1");
        List<String> flags = new ArrayList<>();
        flags.add("a");
        List<String> exp = new ArrayList<>();
        exp.add("1");
        try {
            assertEquals(command.getArguments(flags), exp);
        } catch (MissingArgumentAngelaException e) {
            fail();
        }
    }

    @Test
    public void getArguments_multipleArguments_differentOrder() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("test test /a 1 /b 2 /c 3");
        List<String> flags = new ArrayList<>();
        flags.add("c");
        flags.add("a");
        flags.add("b");
        List<String> exp = new ArrayList<>();
        exp.add("3");
        exp.add("1");
        exp.add("2");
        try {
            assertEquals(command.getArguments(flags), exp);
        } catch (MissingArgumentAngelaException e) {
            fail();
        }
    }

    @Test
    public void getArguments_multipleArguments_specificArgument() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("test test /a 1 /b 2 /c 3");
        List<String> flags = new ArrayList<>();
        flags.add("b");
        List<String> exp = new ArrayList<>();
        exp.add("2");
        try {
            assertEquals(command.getArguments(flags), exp);
        } catch (MissingArgumentAngelaException e) {
            fail();
        }
    }

    @Test
    public void getArguments_multipleArguments_missingArgument() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("test test /a 1 /b 2 /c 3");
        List<String> flags = new ArrayList<>();
        flags.add("d");
        try {
            command.getArguments(flags);
            fail();
        } catch (MissingArgumentAngelaException e) {
            assertEquals(new MissingArgumentAngelaException("test").getMessage(), e.getMessage());
        }
    }

    @Test
    public void getArguments_noArguments_missingArgument() {
        Parser parser = new Parser();
        Command command = parser.parseCommand("test test");
        List<String> flags = new ArrayList<>();
        flags.add("d");
        try {
            command.getArguments(flags);
            fail();
        } catch (MissingArgumentAngelaException e) {
            assertEquals(new MissingArgumentAngelaException("test").getMessage(), e.getMessage());
        }
    }
}
