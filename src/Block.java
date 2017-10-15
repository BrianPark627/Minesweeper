public class Block {
	Boolean isMine;
	int value;
	String val;

	public Block() {
		value = 0;
		val = "";
		isMine = false;
	}

	public void setMine() {
		isMine = true;
	}

	public Boolean isBomb() {
		return isMine;
	}

	public int getValue() {
		return value;
	}

	public String getStringValue() {
		return val;
	}

	public void setValue(int num) {
		value = num;
		if (value == 0) {
			val = " ";
		} else
			val = Integer.toString(num);
	}
}
