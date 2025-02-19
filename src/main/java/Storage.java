import java.lang.annotation.AnnotationFormatError;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {
	Path filePath;
    Path dirPath;

	public Storage() {
        dirPath = Paths.get("data");
		filePath = Paths.get("data", "db.txt");
	}

	public List<Task> init() throws AngelaException {
        createDir();
        if (!Files.exists(filePath)) {
            createSave();
        }
        return loadSave();
	}

    public void createDir() throws AngelaException {
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new AngelaException("a");
        }
    }
    public void createSave() throws AngelaException {
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new AngelaException("b");
        }
    }

    public List<Task> loadSave() throws AngelaException {
        List<Task> store = new ArrayList<>();
        try {
            List<String> db = Files.readAllLines(filePath);
            for (String line: db) {
                if (!line.isEmpty()) {
                    Task t = processDb(line); //probably should handle some error here
                    store.add(t);
                }
            }
        } catch (IOException e) {
            throw new AngelaException("c");
        }
        return store;
    }

    public Task processDb(String line) throws AngelaException {
        Task t;
        String[] lineArr = line.split("\\|\\|"); //no way malicious injection is real
        t = switch (lineArr[0]) { //as we all know nothing bad ever happens when we save data as text
            case "T" -> new ToDoTask(lineArr[2]);
            case "D" -> new DeadlineTask(lineArr[2], lineArr[3]);
            case "E" -> new EventTask(lineArr[2], lineArr[3], lineArr[4]);
            default -> throw new AngelaException("d");
        };
        if (lineArr[1].equals("1")) t.doTask();
        return t;
    }

    public void save(List<Task> store) throws AngelaException {
        StringBuilder toWrite = new StringBuilder();
        for (Task t: store) {
            toWrite.append(t.stringify()).append("\n");
        }
        try  {
            Files.writeString(filePath, toWrite.toString(),
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new AngelaException("e");
        }
    }
}
