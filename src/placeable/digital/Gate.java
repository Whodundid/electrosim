package placeable.digital;

import placeable.GateTypes;
import placeable.SimObject;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public abstract class Gate extends SimObject{
	
	private GateTypes gateType;
	
	//this function sets the type of logic gate this object will be
	protected void setGateType(GateTypes type) {
		this.gateType = type;
	}
	
	//returns the type of logic gate this object is
	public GateTypes getGateType() {
		return gateType;
	}
	
	//returns the type of logic gate this object is as a word instead of an integer	
	public String checkGateType() {
		return gateType.getGateName();	
	}
}