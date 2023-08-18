package nine.teta.custSupp.line;

import java.time.LocalDate;

public class QueryLine extends Line {

	private LocalDate dateFrom;
	private LocalDate dateTo;

	public void setDateFromAndDateToGroup(LocalDate[] dateFromAndDateToArray) {
		dateFrom = dateFromAndDateToArray[0];
		if (dateFromAndDateToArray.length > 1) {
			dateTo = dateFromAndDateToArray[1];
		}
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	
	

}
