package test.haianh;

public enum TitleEnums {
	/**
	 * @author haianh
	 * 
	 */
	
	PROGRAM(0, "ACCELERTOR"),
	X_1_AXIS(1,"SENSOR_1_X_AXIS"),
	Y_1_AXIS(2,"SENSOR_1_Y_AXIS"),
	Z_1_AXIS(3,"SENSOR_1_Z_AXIS"),
	X_2_AXIS(4,"SENSOR_2_X_AXIS"),
	Y_2_AXIS(5,"SENSOR_2_Y_AXIS"),
	Z_2_AXIS(6,"SENSOR_2_Z_AXIS"),
	MAGNITUDE(7,"MAGNITUDE"),
	FREQUENCY(8,"FREQUENCY");
	private int index;
	private String tittle;
	
	TitleEnums(int index, String tittle) {
		this.index = index;
		this.tittle = tittle;
	}

	public int getIndex() {
		return index;
	}

	public String getTittle() {
		return tittle;
	}

	public static TitleEnums valueOf(int index) {
		for (TitleEnums  enums : TitleEnums.values()) {
			if(index == enums.getIndex()) {
				return enums;
			}
		}
		return PROGRAM;
	}
}
