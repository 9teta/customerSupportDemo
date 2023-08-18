package nine.teta.custSupp.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


/**
 * Class for reading data from files
 */
public class FileReader {
	
	/**
	 * from file to list of string lines
	 */
	public List<String> read(Path path) {
		try {
			return Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * from file to list of string lines
	 */
	public List<String> read(String stringPath) {
		Path path = Path.of(stringPath);
		return read(path);
	}

}
