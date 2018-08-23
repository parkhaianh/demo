package entity;

public class Acceleration_info extends DaoValue{
	/**
	 * @author haianh
	 * 
	 */
	private int id;
	private String triggerTime;
	private double index_1_x;
	private double index_2_x;
	private double index_1_y;
	private double index_2_y;
	private double index_1_z;
	private double index_2_z;
	
	
	public Acceleration_info(){
	}
	
	public Acceleration_info(String triggerTime, double index1,double index2,double index3,double index4,double index5, double index6){
		this.triggerTime = triggerTime;
		this.index_1_x = index1;
		this.index_1_y = index3;
		this.index_1_z = index5;
		this.index_2_x = index2;
		this.index_2_y = index4;
		this.index_2_z = index6;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTriggerTime() {
		return triggerTime;
	}
	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}
	public double getIndex_1_x() {
		return index_1_x;
	}
	public void setIndex_1_x(double index_1_x) {
		this.index_1_x = index_1_x;
	}
	public double getIndex_2_x() {
		return index_2_x;
	}
	public void setIndex_2_x(double index_2_x) {
		this.index_2_x = index_2_x;
	}
	public double getIndex_1_y() {
		return index_1_y;
	}
	public void setIndex_1_y(double index_1_y) {
		this.index_1_y = index_1_y;
	}
	public double getIndex_2_y() {
		return index_2_y;
	}
	public void setIndex_2_y(double index_2_y) {
		this.index_2_y = index_2_y;
	}
	public double getIndex_1_z() {
		return index_1_z;
	}
	public void setIndex_1_z(double index_1_z) {
		this.index_1_z = index_1_z;
	}
	public double getIndex_2_z() {
		return index_2_z;
	}
	public void setIndex_2_z(double index_2_z) {
		this.index_2_z = index_2_z;
	}
	
	
	  
}
