package placeable.digital;

import placeable.FlipFlopTypes;
import placeable.SimObject;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public abstract class FlipFlop extends SimObject{
		
	private FlipFlopTypes flipFlopType;
	
	protected void setFFType(FlipFlopTypes type) {
		this.flipFlopType = type;
	
	}
		
	public FlipFlopTypes getFFType() {
		return flipFlopType;
	}
		
	public String checkFFType() {
		return flipFlopType.getFFName();
	}
}
