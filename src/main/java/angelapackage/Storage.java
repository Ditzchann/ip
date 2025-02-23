package angelapackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import angelapackage.exception.AngelaException;
import angelapackage.task.DeadlineTask;
import angelapackage.task.EventTask;
import angelapackage.task.Task;
import angelapackage.task.ToDoTask;

/**
 * Class that handles interactions with local storage.
 * Stores and retrieves tasks from local storage
 */

public class Storage {
    private Path filePath;
    private Path dirPath;

    public Storage() {
        dirPath = Paths.get("data");
        filePath = Paths.get("data", "db.txt");
    }

    /**
     * Initializes storage and loads task list from it
     * @return List of tasks loaded
     * @throws AngelaException If file cannot be created
     */
    public List<Task> init() throws AngelaException {
        createDir();
        if (!Files.exists(filePath)) {
            createSave();
        }
        return loadSave();
    }

    /**
     * Creates a directory for saved data if not present
     * @throws AngelaException If directory cannot be created
     */
    public void createDir() throws AngelaException {
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new AngelaException("");
        }
    }

    /**
     * Creates file to save data if not present
     * @throws AngelaException If file cannot be created
     */
    public void createSave() throws AngelaException {
        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            throw new AngelaException("");
        }
    }

    /**
     * Returns loaded tasks from file at filePath
     * If tasks do not exist, empty List is returned
     * @return List of tasks retrieved from file
     * @throws AngelaException If directory cannot be created
     */
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
            throw new AngelaException("");
        }
        return store;
    }

    /**
     * Reads given line from file and converts it into a Task object
     * Returns Task object converted from line
     * @param line Line read from file
     * @return Processed task
     * @throws AngelaException If file data not in correct format
     */
    public Task processDb(String line) throws AngelaException {
        Task t;
        String[] lineArr = line.split("\\|\\|"); //no way malicious injection is real
        t = switch (lineArr[0]) { //as we all know nothing bad ever happens when we save data as text
        case "T" -> new ToDoTask(lineArr[2]);
        case "D" -> new DeadlineTask(lineArr[2], lineArr[3]);
        case "E" -> new EventTask(lineArr[2], lineArr[3], lineArr[4]);
        default -> throw new AngelaException("");
        };
        if (lineArr[1].equals("1")) {
            t.doTask();
        }
        return t;
    }

    /**
     * Saves data string into file
     * @param toWrite String data to be written into file
     * @throws AngelaException If write cannot be performed
     */
    public void save(String toWrite) throws AngelaException {
        try {
            Files.writeString(filePath, toWrite,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new AngelaException("");
        }
    }
}
