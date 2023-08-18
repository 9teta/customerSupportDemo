package nine.teta.custSupp.parse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import nine.teta.custSupp.line.Line;
import nine.teta.custSupp.line.QueryLine;
import nine.teta.custSupp.line.WaitingTimelineLine;

public class TokenizerImpl implements Tokenizer {
	
	public static final String QUERY_DELIMITER = " ";
	public static final String WAITING_TIMELINE_SYMBOL = "C";
	public static final String REQUEST_QUERY_SYMBOL = "D";
	public static final String SERVICE_ID_DELIMITER = "\\.";
	public static final String QUESTION_ID_DELIMITER = "\\.";
	public static final DateTimeFormatter WAITING_TIMELINE_DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
	public static final DateTimeFormatter REQUEST_QUERY_DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
	public static final String REQUEST_QUERY_DATEFROM_DATETO_DELIMITER = "\\-";

	@Override
	public Line tokenize(String stringLine) throws WrongLineException {
		// assert input isn't empty
		if (stringLine == null || stringLine.length() == 0) new WrongLineException("empty line");
		
		String[] splited = stringLine.split(QUERY_DELIMITER);
		
		String firstToken = takeLineType(splited);
		
		if (WAITING_TIMELINE_SYMBOL.equals(firstToken)) {
			return processTimeline(splited);
		} else if (REQUEST_QUERY_SYMBOL.equals(firstToken)) {
			return processQuery(splited);
		} else {
			throw new WrongLineException("Get wrong symbol as a first token " + firstToken );
		}
	}
	
	private WaitingTimelineLine processTimeline(String[] splitted) throws WrongLineException {
		WaitingTimelineLine line = new WaitingTimelineLine();
		line.setLineType(WAITING_TIMELINE_SYMBOL);
		String[] serviceIdArray = takeServiceIdGroup(splitted);
		line.setServiceIdGroup(serviceIdArray);
		String[] questionIdArray = takeQuestionIdGroup(splitted);
		line.setQuestionIdGroup(questionIdArray);
		String responseType = takeResponseType(splitted);
		line.setResponseType(responseType);
		LocalDate waitingTimelineDate = takeWaitingTimelineDate(splitted);
		line.setDate(waitingTimelineDate);
		int time = takeWaitingTimelineTime(splitted);
		line.setTime(time);
		return line;
	}



	private QueryLine processQuery(String[] splitted) throws WrongLineException {
		QueryLine line = new QueryLine();
		line.setLineType(REQUEST_QUERY_SYMBOL);
		String[] serviceIdArray = takeServiceIdGroup(splitted);
		line.setServiceIdGroup(serviceIdArray);
		String[] questionIdArray = takeQuestionIdGroup(splitted);
		line.setQuestionIdGroup(questionIdArray);
		String responseType = takeResponseType(splitted);
		line.setResponseType(responseType);
		LocalDate[] dateFromAndDateToArray = takeDateFromAndDateTo(splitted);
		line.setDateFromAndDateToGroup(dateFromAndDateToArray);
		
		return line;
	}



	private String takeLineType(String[] splitted) {
		try {
			String firstToken = splitted[0];
			assertTokenNotNullNotEmpty(firstToken, 1);
			return firstToken;
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 1 in line");
		}
	}
	

	private String[] takeServiceIdGroup(String[] splitted) {
		try {
			String secondToken = splitted[1];
			assertTokenNotNullNotEmpty(secondToken, 2);
			return secondToken.split(SERVICE_ID_DELIMITER);
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 2 in line");
		}
	}
	

	private String[] takeQuestionIdGroup(String[] splitted) {
		try {
			String thirdToken = splitted[2];
			assertTokenNotNullNotEmpty(thirdToken, 3);
			return thirdToken.split(QUESTION_ID_DELIMITER);
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 3 in line");
		}
	}
	

	private String takeResponseType(String[] splitted) {
		try {
			String fourthToken  = splitted[3];
			assertTokenNotNullNotEmpty(fourthToken, 4);
			return fourthToken;
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 4 in line");
		}
	}
	

	private LocalDate takeWaitingTimelineDate(String[] splitted) {
		try {
			String fifthToken = splitted[4];
			assertTokenNotNullNotEmpty(fifthToken, 5);
			LocalDate date = LocalDate.parse(fifthToken, WAITING_TIMELINE_DATE_FORMAT);
			return date;
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 5 in line");
		} catch (DateTimeParseException e) {
			throw new WrongLineException("wrong date format");
		}
	}
	

	private int takeWaitingTimelineTime(String[] splitted) {
		try {
			String sixthToken = splitted[5];
			assertTokenNotNullNotEmpty(sixthToken, 6);
			int time = Integer.parseInt(sixthToken);
			return time;
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 5 in line");
		} catch (NumberFormatException e) {
			throw new WrongLineException("Time is not parseble");
		}
	}
	
	
	private LocalDate[] takeDateFromAndDateTo(String[] splitted) {
		try {
			String fifthToken = splitted[4];
			assertTokenNotNullNotEmpty(fifthToken, 5);
			String[] dateFromDateToArray = fifthToken.split(REQUEST_QUERY_DATEFROM_DATETO_DELIMITER);
			String dateFromString = dateFromDateToArray[0];
			String dateToString = null;
			if (dateFromDateToArray.length > 1) {
				dateToString = dateFromDateToArray[1];
			}
			LocalDate dateFrom = LocalDate.parse(dateFromString, REQUEST_QUERY_DATE_FORMAT);
			if (dateToString == null) {
				return new LocalDate[] { dateFrom };
			}
			LocalDate dateTo = LocalDate.parse(dateToString, REQUEST_QUERY_DATE_FORMAT);
			return new LocalDate[] { dateFrom, dateTo };
		} catch (DateTimeParseException e) {
			throw new WrongLineException("wrong date format of splitted %s".formatted(Arrays.toString(splitted)) );
		} catch (ArrayIndexOutOfBoundsException  e) {
			throw new WrongLineException("not found token 5 in line");
		}
		
	}
	
	
	private void assertTokenNotNullNotEmpty(String token, int n) {
		if (token == null || token.length() == 0) new WrongLineException("empty token " + n + " in line");
	}
	
	

}
