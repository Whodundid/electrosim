package placeable.digital;

import placeable.ObjectStates;
import placeable.ObjectTypes;
import placeable.SimObject;

//Last edited: 3-3-17
//Author: Hunter Troy Bragg

public class DOut extends SimObject {
	
	boolean pass;
	
	//the lever constructor. This sets the object type to a digital input with a default output value of 0.
	public DOut(String ID, int inputs) {
		setID(ID);
		setObjType(ObjectTypes.DOUT);
		setNumInputs(inputs);
		setObjState(ObjectStates.IDLE);
	}
		
	public boolean update() {
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i) == 1) {
				setOutputValue(1);
				return true;
			}
		}
		setOutputValue(0);
		return false;
	}
}