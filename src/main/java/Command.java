import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean checkParameter(String parameter) {
        return args.containsKey(parameter);
    }

    public String getArg(String parameter) {
        return args.get(parameter);
    }

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
}
