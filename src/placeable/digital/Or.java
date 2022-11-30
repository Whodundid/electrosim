package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class Or extends Gate{
	
	//the or constructor. This creates a new or gate based off of the given number of inputs and outputs with the given ID.
	public Or(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.OR);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	//this is the logic specifically used for or gates. This logic checks to see if any input is a 1.
	//if any input is a 1, set the output to true.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty()) {
			return false;
		}
		int x = 0;
		for(int i=0; i<this.inputs.size(); i++) {
			x += this.inputs.get(i);			
		}
		if (x >= 1) {
			if (isTest == false)
				this.setOutputValue(1);
			return true;
		}
		else {
			if (isTest == false)
				this.setOutputValue(0);
			return false;
		}		
	}
}