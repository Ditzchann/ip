import java.util.HashMap;
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
}
