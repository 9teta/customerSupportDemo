package nine.teta.custSupp.line;

public class Line {
	
	private String lineType;

	private String serviceId;
	private String serviceVariationId;
	
	private String questionTypeId;
	private String questionCategoryId;
	private String questionSubCategoryId;
	
	private String responseType;
	

	public void setServiceIdGroup(String[] serviceIdArray) {
		serviceId = serviceIdArray[0];
		if (serviceIdArray.length > 1) {
			serviceVariationId = serviceIdArray[1];
		}
	}
	
	public void setQuestionIdGroup(String[] questionIdArray) {
		questionTypeId = questionIdArray[0];
		if (questionIdArray.length == 2) {
			questionCategoryId = questionIdArray[1];
		} else if (questionIdArray.length > 2) {
			questionCategoryId = questionIdArray[1];
			questionSubCategoryId = questionIdArray[2];
		}
	}

	
	//====================== Getters & Setters start ======================
	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceVariationId() {
		return serviceVariationId;
	}

	public void setServiceVariationId(String serviceVariationId) {
		this.serviceVariationId = serviceVariationId;
	}

	public String getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(String questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionCategoryId() {
		return questionCategoryId;
	}

	public void setQuestionCategoryId(String questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}

	public String getQuestionSubCategoryId() {
		return questionSubCategoryId;
	}

	public void setQuestionSubCategoryId(String questionSubCategoryId) {
		this.questionSubCategoryId = questionSubCategoryId;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	//=======================Getters & Setters end ========================
	
	
	

}
