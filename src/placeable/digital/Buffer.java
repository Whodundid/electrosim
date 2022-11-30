package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 3-1-17
//Author: Hunter Troy Bragg

public class Buffer extends Gate{
	
	//the and constructor. This creates a new and gate based off of the given number of inputs and outputs with the given ID.
	public Buffer(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.BUFFER);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	public boolean logic(boolean isTest) {
		if (inputs.isEmpty())
			return false;
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i) == 1) {
				if (!isTest)
					this.setOutputValue(1);
				return true;
			}
		}
		if (!isTest)
			this.setOutputValue(0);
		return false;
	}
}