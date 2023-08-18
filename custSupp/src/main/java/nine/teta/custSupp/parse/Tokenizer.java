package nine.teta.custSupp.parse;

import nine.teta.custSupp.line.Line;

public interface Tokenizer {
	
	public Line tokenize(String stringLine) throws WrongLineException;

}
