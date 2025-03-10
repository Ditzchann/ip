package angelapackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Parsing class that handles conversion from String to Command
 */
public class Parser {

    public Parser() {
    }

    /**
     * Parses input string into Command object
     * Returns Command object of specified string
     * @param input Input string
     * @return Created command object
     */
    public Command parseCommand(String input) {
        List<String> commandList;
        commandList = splitInput(input);
        return listToCommand(commandList);
    }

    //breaks up input into list of words for processing into a Command object
    private List<String> splitInput(String input) {
        List<String> args = new ArrayList<>();
        int i = 0;
        i = getEndIndexOfWord(i, input, args);
        i = getEndIndexOfArg(i, input, args);
        while (i < input.length()) {
            i = getEndIndexOfWord(i, input, args);
            i = getEndIndexOfArg(i, input, args);
        }
        return args;
    }

    //attempts to get substring until next space, used to get last index of command/parameter names
    private int getEndIndexOfWord(int start, String input, List<String> args) {
        int i = start;
        while (i < input.length() && input.charAt(i) != ' ') {
            i++;
        }
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
        return i + 1;
    }

    //attempts to get substring until next /, used to get last index of arguments
    private int getEndIndexOfArg(int start, String input, List<String> args) {
        int i = start;
        while (i < input.length() && input.charAt(i) != '/') {
            i++;
        }
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
        return i + 1;
    }

    //converts list into a command based on its order {command, main argument, /parameter1, argument1, ...}
    private Command listToCommand(List<String> commandList) {
        try {
            Command command;
            if (commandList.size() >= 2) {
                command = new Command(commandList.get(0), commandList.get(1));
            } else {
                command = new Command(commandList.get(0), "");
            }
            for (int i = 2; i < commandList.size(); i += 2) {
                String para = commandList.get(i);
                String arg;
                if (i + 1 >= commandList.size()) {
                    arg = "";
                } else {
                    arg = commandList.get(i + 1);
                }
                command.addArg(para, arg);
            }
            return command;
        } catch (IndexOutOfBoundsException e) {
            return new Command("", "");
        }
    }
}
