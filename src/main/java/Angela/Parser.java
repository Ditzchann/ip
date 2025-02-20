package Angela;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public Parser() {
    }

    public Command parseCommand(String input){
        List<String> commandList;
        commandList = splitInput(input);
        return listToCommand(commandList);
    }

    public List<String> splitInput(String input) {
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

    public int getEndIndexOfWord(int start, String input, List<String> args) {
        int i = start;
        while (i < input.length() && input.charAt(i) != ' ') {
            i++;
        }
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
        return i + 1;
    }

    public int getEndIndexOfArg(int start, String input, List<String> args) {
        int i = start;
        while (i < input.length() && input.charAt(i) != '/') {
            i++;
        }
        if (start != i) {
            args.add(input.substring(start, i).trim());
        }
        return i + 1;
    }

    public Command listToCommand(List<String> commandList) {
        try {
            Command command;
            if (commandList.size() >= 2){
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
