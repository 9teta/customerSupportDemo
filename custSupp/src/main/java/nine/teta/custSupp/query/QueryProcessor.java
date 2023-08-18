package nine.teta.custSupp.query;

import java.util.List;

import nine.teta.custSupp.line.QueryLine;
import nine.teta.custSupp.line.WaitingTimelineLine;
import nine.teta.custSupp.result.Results;

public interface QueryProcessor {
	
	public void process( 
			QueryLine queryLine, 
			List<WaitingTimelineLine> list, 
			Results result );

}
