package placeable.digital;

import placeable.ObjectStates;
import placeable.ObjectTypes;
import placeable.SimObject;

//Last edited: 3-3-17
//Author: Hunter Troy Bragg

public class Lever extends SimObject{
	
	//the lever constructor. This sets the object type to a digital input with a default output value of 0.
	public Lever(String ID, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.DIN);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
}