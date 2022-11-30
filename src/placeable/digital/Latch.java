package placeable.digital;

import placeable.LatchTypes;
import placeable.SimObject;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public abstract class Latch extends SimObject{
	
	private LatchTypes latchType;
	
	protected void setLatchType(LatchTypes type) {
		this.latchType = type;
	}

	public LatchTypes getLatchType() {
		return latchType;
	}

	public String checkLatchType() {
		return latchType.getLatchName();
	}
}