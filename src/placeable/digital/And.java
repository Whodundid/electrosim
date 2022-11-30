package placeable.digital;

import placeable.GateTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 3-10-17
//Author: Hunter Troy Bragg

public class And extends Gate{
	
	private int point;

	//the and constructor. This creates a new and gate based off of the given number of inputs and outputs with the given ID.
	public And(String ID, int inputs, int outputs) {
		setID(ID);
		setObjType(ObjectTypes.lOGICGATE);
		setGateType(GateTypes.AND);
		setNumInputs(inputs);
		setNumOutputs(outputs);
		setObjState(ObjectStates.IDLE);
	}
	
	public boolean checkConnections() {
		for (int i = 0; i < this.getOutputsMap().size(); i++) {
			if (this.getOutputsMap().getValue(i) >= this.getInputs().size()) {
				if (!this.getOutputsMap().isEmpty()) {
					if (this.getOutputsMap().getObject(i) != null) {
						if (this.getOutputsMap().getObject(i).getOutputValueBoolean()) {
							this.point = this.getOutputsMap().getValue(i);
							//System.out.println(point);
							return true;
						}
					}
				}
			}
		} return false;
	}
	
	private boolean localLogic() {
		int x = 0;
		for(int i=0; i<this.inputs.size(); i++) {
			x += this.inputs.get(i);			
		}
		if (x == this.inputs.size())
			return true;
		else
			return false;
	}
	
	//This is the logic specifically used for and gates. First, this checks if this object's inputs will
	//trigger an output. If local logic is false, a check is then made to see if any of this object's output 
	//points are connected to objects which are currently on. If so, the connected object's output value is
	//placed on this object's corresponding output point.
	public boolean logic(boolean isTest) {
		if (this.inputs.isEmpty())
			return false;
		else if (localLogic()) {
			if (!isTest)
				this.setOutputValue(1);
			return true;
		}
		/*else if (checkConnections()) {
			if (!isTest)
				this.setOutputValue(0);
			this.outputs.put(point - this.getInputs().size(), 1);
			return true;
		}*/
		else {
			if (!isTest)
				this.setOutputValue(0);
			return false;
		}
	}
}