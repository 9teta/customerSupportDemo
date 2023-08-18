package nine.teta.custSupp.query;

import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;

import nine.teta.custSupp.line.QueryLine;
import nine.teta.custSupp.line.WaitingTimelineLine;
import nine.teta.custSupp.result.Results;

public class QueryProcessorImpl implements QueryProcessor {

	public static final String ACCEPT_ALL_SYMBOL = "*";

	@Override
	public void process(QueryLine queryLine, List<WaitingTimelineLine> list, Results results) {
		// filter chain
		OptionalDouble optionalAverage = list.stream()
		.filter( e -> filterServiceIdGroup(queryLine.getServiceId(), queryLine.getServiceVariationId(), e) )
		.filter( e -> filterQuestionIdGroup(queryLine.getQuestionTypeId(), queryLine.getQuestionCategoryId(), queryLine.getQuestionSubCategoryId(), e) )
		.filter( e -> filterResponseType(queryLine.getResponseType(), e) )
		.filter( e -> filterDateFrom(queryLine.getDateFrom(), e) )
		.filter( e -> filterDateTo(queryLine.getDateTo(), e) )
		//count average waiting time
		.mapToInt( e -> e.getTime() )
		.average();
		
		if ( optionalAverage.isEmpty() ) {
			results.add("-");
		} else {
			double asDouble = optionalAverage.getAsDouble();
			int average = (int) Math.round(asDouble);
			results.add(String.valueOf(average));
		}
			
		
		
		
		
	}

	private boolean filterServiceIdGroup(String serviceIdCondition, String serviceVariationIdCondition,	WaitingTimelineLine line) {
		if (serviceIdCondition == null || "".equals(serviceIdCondition)) throw new WrongQueryException("null or empty condition for service id");

		if (ACCEPT_ALL_SYMBOL.equals(serviceIdCondition)) return true;

		String serviceId = line.getServiceId();
		if (serviceId == null) return false;

		if (serviceIdCondition.equals(serviceId)) {
			if (serviceVariationIdCondition == null) return true;

			String serviceVariationId = line.getServiceVariationId();
			if (serviceVariationIdCondition.equals(serviceVariationId)) {
				return true;
			}
		}

		return false;
	}

	private boolean filterQuestionIdGroup(String questionTypeIdCondition, String questionCategoryIdCondition, String questionSubCategoryIdCondition, WaitingTimelineLine line) {
		if (questionTypeIdCondition == null || "".equals(questionTypeIdCondition)) throw new WrongQueryException("null or empty condition for question id");

		if (ACCEPT_ALL_SYMBOL.equals(questionTypeIdCondition)) return true;

		String questionTypeId = line.getQuestionTypeId();
		if (questionTypeId == null) return false;

		if (questionTypeIdCondition.equals(questionTypeId)) {
			if (questionCategoryIdCondition == null) return true;

			String questionCategoryId = line.getQuestionCategoryId();
			if (questionCategoryIdCondition.equals(questionCategoryId)) {
				if (questionSubCategoryIdCondition == null) return true;

				String questionSubCategoryId = line.getQuestionSubCategoryId();
				if (questionSubCategoryIdCondition.equals(questionSubCategoryId)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean filterResponseType(String responseTypeCondition, WaitingTimelineLine line) {
		if (responseTypeCondition == null || "".equals(responseTypeCondition)) throw new WrongQueryException("null or empty condition for response type");

		String responseType = line.getResponseType();
		if (responseType == null) return false;

		if (responseTypeCondition.equals(responseType)) {
			return true;
		}

		return false;
	}
	
	private boolean filterDateFrom(LocalDate dateFromCondition, WaitingTimelineLine line) {
		if (dateFromCondition == null ) throw new WrongQueryException("from date is null");
		
		LocalDate date = line.getDate();
		if ( dateFromCondition.isEqual(date) ) return true;
		if ( dateFromCondition.isBefore(date) ) {
			return true;
		}
		return false;
	}
	
	private boolean filterDateTo(LocalDate dateToCondition, WaitingTimelineLine line) {
		if (dateToCondition == null ) return true;
		
		LocalDate date = line.getDate();
		if ( dateToCondition.isEqual(date) ) return true;
		if ( dateToCondition.isAfter(date) ) {
			return true;
		}
		return false;
	}
	
	
	

}
