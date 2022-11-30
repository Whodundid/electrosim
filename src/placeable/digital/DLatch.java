package placeable.digital;

import placeable.LatchTypes;
import placeable.ObjectStates;
import placeable.ObjectTypes;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class DLatch extends Latch{
	
	private int storredVal = 0;
	
	public DLatch(String ID) {
		setID(ID);
		setObjType(ObjectTypes.LATCH);
		setLatchType(LatchTypes.D);
		setNumInputs(2);
		setNumOutputs(1);
		setObjState(ObjectStates.IDLE);
	}
	
	public boolean logic(boolean isTest) {
		if ((this.inputs.get(1) == 1)) {
			if (this.inputs.get(0) == 1) {
				if (!isTest) {
					this.storredVal = 1;
					this.setOutputValue(1);
					return true;
				}				
			}
			else {
				this.storredVal = 0;
				this.setOutputValue(0);	
				return false;
			}
		}
		if (this.storredVal == 1)
			this.setOutputValue(1);
		else
			this.setOutputValue(0);			
		return false;
	}
}