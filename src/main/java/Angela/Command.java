package Angela;

import Angela.exception.MissingArgumentAngelaException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a command line given by the user. A command object is represented by its name,
 * main argument, and its additional arguments labeled by their parameters.
 */
public class Command {
    private String name;
    private String mainArg;
    private Map<String, String> args;

    public Command(String name, String mainArg) {
        this.name = name;
        this.mainArg = mainArg;
        this.args = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getMainArg() {
        return mainArg;
    }

    public void addArg(String parameter, String arg) {
        args.put(parameter, arg);
    }

    /**
     * Extracts arguments labelled by parameters in params from the command
     * and returns them in the same order.
     * @param params List of parameters to search
     * @return List of extracted arguments in the same order as the parameter list
     * @throws MissingArgumentAngelaException If command does not contain required parameters
     */

    public List<String> getArguments(List<String> params) throws MissingArgumentAngelaException {
        List<String> returnList = Arrays.asList(new String[params.size()]);
        for (int i = 0; i < params.size(); i++) {
            returnList.set(i, this.args.get(params.get(i)));
        }
        if (returnList.contains(null)) {
            throw new MissingArgumentAngelaException(this.name);
        } else {
            return returnList;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command command) {
            return this.name.equals(command.name) && this.mainArg.equals(command.mainArg)
                    && this.args.equals(command.args);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name + " " + this.mainArg
                + " " + this.args.toString();
    }
}
