import java.nio.file.Files;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
	Path filePath;

	public Storage() {
		filePath = Paths.get("data", "db.txt");
	}
}
