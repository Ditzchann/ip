package angelapackage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parseCommand_withCommandOnly() {
        Parser parser = new Parser();
        Command c1 = new Command("test", "");
        Command c2 = new Command("test123", "");
        assertEquals(c1, parser.parseCommand("test"));
        assertEquals(c2, parser.parseCommand("test123"));
    }

    @Test
    public void parseCommand_singleArg() {
        Parser parser = new Parser();
        Command c1 = new Command("test", "12345");
        Command c2 = new Command("test123", "abcdefghijk");
        Command c3 = new Command("test", "a b c d e f g h");
        Command c4 = new Command("test", "a");
        assertEquals(c1, parser.parseCommand("test 12345"));
        assertEquals(c2, parser.parseCommand("test123 abcdefghijk"));
        assertEquals(c3, parser.parseCommand("test a b c d e f g h"));
        assertEquals(c4, parser.parseCommand("test a"));
    }

    @Test
    public void parseCommand_singleArg_oneFlag() {
        Parser parser = new Parser();
        Command c1 = new Command("test", "12345");
        c1.addArg("a", "1234");
        Command c2 = new Command("test", "12345");
        c2.addArg("aaaaaaaaaaaaaaaa", "1234");
        Command c3 = new Command("test", "12345");
        c3.addArg("a", "1 2 3 4 5 6 7 8 9");
        Command c4 = new Command("test", "12345");
        c4.addArg("a", "");
        assertEquals(c1, parser.parseCommand("test 12345 /a 1234"));
        assertEquals(c2, parser.parseCommand("test 12345 /aaaaaaaaaaaaaaaa 1234"));
        assertEquals(c3, parser.parseCommand("test 12345 /a 1 2 3 4 5 6 7 8 9"));
        assertEquals(c4, parser.parseCommand("test 12345 /a"));
    }

    @Test
    public void parseCommand_singleArg_multipleFlag() {
        Parser parser = new Parser();
        Command c1 = new Command("test", "12345");
        c1.addArg("a", "1234");
        c1.addArg("aaaaaaaaaaaaaaaa", "1234");
        Command c2 = new Command("test", "12345");
        c2.addArg("a", "1 2 3 4 5 6 7 8 9");
        c2.addArg("b", "");
        assertEquals(c1, parser.parseCommand("test 12345 /a 1234 /aaaaaaaaaaaaaaaa 1234"));
        assertEquals(c2, parser.parseCommand("test 12345 /a 1 2 3 4 5 6 7 8 9 /b"));
    }

    @Test
    public void parseCommand_empty() {
        Parser parser = new Parser();
        assertEquals(new Command("", ""), parser.parseCommand(""));
    }
}
