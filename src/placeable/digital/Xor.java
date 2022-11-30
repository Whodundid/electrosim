package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class Xor extends Gate{
	
	//the xor constructor. This creates a new xor gate based off of the given number of inputs and outputs with the given ID.
	public Xor(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.XOR);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	//this is the logic specifically used for xor gates. This logic more or less works like cascading not gates.
	//the output will only be true if the number of high input values is odd.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty()) {
			return false;
		}
		int x = 0;
		for(int i=0; i<this.inputs.size(); i++) {
			x += this.inputs.get(i);			
		}
		if ((x % this.inputs.size()) == 1) {
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