package Detector;

public class Data {
	private String[] valueOfAttributes;

	public Data(String[] valueOfAttributes) {
		this.valueOfAttributes = valueOfAttributes;
	}



	public String getValueInIndex(int index) {
		return valueOfAttributes[index];
	}

	public int getColumnSize(){
		return valueOfAttributes.length;
	}



	public String[] getAllDataValue() {
		// TODO Auto-generated method stub
		return valueOfAttributes;
	}

}
