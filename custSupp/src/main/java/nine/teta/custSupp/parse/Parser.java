package nine.teta.custSupp.parse;

import java.util.ArrayList;
import java.util.List;

import nine.teta.custSupp.line.Line;
import nine.teta.custSupp.line.QueryLine;
import nine.teta.custSupp.line.WaitingTimelineLine;
import nine.teta.custSupp.query.QueryProcessor;
import nine.teta.custSupp.query.QueryProcessorImpl;
import nine.teta.custSupp.result.Results;

public class Parser {
	
	private Tokenizer tokenizer;
	private List<WaitingTimelineLine> waitingTimelineLines = new ArrayList<WaitingTimelineLine>();;
	
	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
	
	public void parseAndProcess(List<String> listOfStrings, Results results) {
		for (int i = 0; i < listOfStrings.size(); i++) {
			try {
				Line line = tokenizer.tokenize(listOfStrings.get(i));
				if (TokenizerImpl.WAITING_TIMELINE_SYMBOL.equals( line.getLineType() )) {
					WaitingTimelineLine waitingTimelineLine = (WaitingTimelineLine) line;
					waitingTimelineLines.add(waitingTimelineLine);
				} else if (TokenizerImpl.REQUEST_QUERY_SYMBOL.equals( line.getLineType() )) {
					QueryLine queryLine = (QueryLine) line;
					queryOperation(queryLine, results);
				} else {
					throw new RuntimeException("Error in parser. Can't find type of Line");
				}
			} catch (WrongLineException e) {
				// logging
				String mess = "Parse error on line %s with message: %s. Skipped".formatted(i, e.getMessage());
				System.err.println(mess);
			}
		}
	}

	private void queryOperation(QueryLine queryLine, Results results) {
		QueryProcessor queryProcessor = new QueryProcessorImpl();
		queryProcessor.process(queryLine, waitingTimelineLines, results);
	}

}
