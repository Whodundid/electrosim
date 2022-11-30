package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class Xnor extends Gate{
	
	//the xnor constructor. This creates a new xnor gate based off of the given number of inputs and outputs with the given ID.
	public Xnor(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.XNOR);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	//this is the logic specifically used for xnor gates. This logic checks that all input values are equal to each other. 
	//so, all of the inputs either need to be a 1 or a 0 to become true.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty()) {
			return false;
		}
		int x = 0;
		for(int i=0; i<this.inputs.size(); i++) {
			x += this.inputs.get(i);			
		}
		if ((x % this.inputs.size()) == 0) {
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