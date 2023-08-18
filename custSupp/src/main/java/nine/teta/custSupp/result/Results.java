package nine.teta.custSupp.result;

public class Results {
	
	private StringBuilder textualResult = new StringBuilder();
	
	public void add(String result) {
		textualResult.append(result);
		textualResult.append("\n");
	}
	
	public String get() {
		return textualResult.toString();
	}

}
