package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class Nand extends Gate{
	
	//the nand constructor. This creates a new nand gate based off of the given number of inputs and outputs with the given ID.
	public Nand(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.NAND);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	//this is the logic specifically used for nand gates. This logic checks that all input values are a 1. 
	//If all of the inputs are a 1, set the output value to false.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty()) {
			return false;
		}
		int x = 0;
		for(int i=0; i<this.inputs.size(); i++) {
			x += this.inputs.get(i);			
		}
		if (x == this.inputs.size()) {
			if (isTest == false)
				this.setOutputValue(0);
			return false;
		}
		else {
			if (isTest == false)
				this.setOutputValue(1);
			return true;
		}		
	}
}