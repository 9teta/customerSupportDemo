package nine.teta.custSupp;

import java.util.List;

import nine.teta.custSupp.io.FileReader;
import nine.teta.custSupp.parse.Parser;
import nine.teta.custSupp.parse.Tokenizer;
import nine.teta.custSupp.parse.TokenizerImpl;
import nine.teta.custSupp.result.Results;

public class Main {
	
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// insert absolute path to file here
	// for example "c://test/test.txt"
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private static final String ABSOLUTE_PATH_TO_FILE = "insert_absolute_path_to_file_here";

	public static void main(String[] args) {
		FileReader fileReader = new FileReader();
		List<String> list = fileReader.read("c://test/test.txt");
		Tokenizer tokenizer = new TokenizerImpl();
		Parser parser = new Parser(tokenizer);
		Results results = new Results();
		parser.parseAndProcess(list, results);
		System.out.println(results.get());
	}

}
