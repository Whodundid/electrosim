package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 3-3-17
//Author: Hunter Troy Bragg

public class Not extends Gate{
	
	//the not constructor. This creates a new not gate with the given ID.
	public Not(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.NOT);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	//this is the logic specifically used for not gates. This logic just inverts the inputs value and returns the inverted result.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty())
			return true;
		for (int i = 0; i < inputs.size(); i++) {
			if (inputs.get(i) == 1) {
				if (!isTest)
					this.setOutputValue(0);
				return false;
			}
		}
		if (!isTest)
			this.setOutputValue(1);
		return true;
	}
}