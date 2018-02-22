package Detector;

public class Data {
	private String[] valueOfAttributes;
	
	public Data(String[] valueOfAttributes) {
		this.valueOfAttributes = valueOfAttributes;
	}
	
	public Data() {
	}
	
	public String getValueInIndex(int index) {
		return valueOfAttributes[index];
	}
	
	public String[] getAllDataValue() {
		return valueOfAttributes;
	}
	
	public int getColumnSize(){
		return valueOfAttributes.length;
	}
	
}
