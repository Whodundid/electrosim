package placeable.digital;

import objectUtil.ObjectManager;
import placeable.ObjectStates;
import placeable.ObjectTypes;
import placeable.SimObject;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class DigitalClock extends SimObject implements Runnable{
	
	private long intervalTime = 0;
	private ObjectManager refferenceMan;
	
	public DigitalClock(String ID, int intervalTime, ObjectManager man) {
		this.setID(ID);
		this.setObjState(ObjectStates.RUNNING);
		this.setNumInputs(1);
		this.setNumOutputs(1);
		this.setObjType(ObjectTypes.DCLOCK);
		this.intervalTime = intervalTime;
		this.refferenceMan = man;
	}
	
	public long getInterval() {
		return this.intervalTime;
	}
	
	public void setInterval(long interval) {
		this.intervalTime = interval;
	}
	
	protected void toggleOutput() {
		if (this.getOutputValueInt() == 0) {
			this.setOutputValue(1);
			for (int i = 0; i < this.getOutputs().size(); i++) {
				this.getOutputs().put(i, 1);
			}
		} else {
			this.setOutputValue(0);
			for (int i = 0; i < this.getOutputs().size(); i++) {
				this.getOutputs().put(i, 0);
			}
		}
		refferenceMan.updateObj(this.getID(), true, false);
		this.getPointManager().drawPoints();
	}
	
	public void run() {
		while (this.checkDead() == false) {
			if (this.getInputs().get(0) == 1) {
				this.toggleOutput();
				//System.out.println(java.lang.Runtime.getRuntime().freeMemory());
				try {
					Thread.sleep(intervalTime);
					continue;
				} catch (InterruptedException e) { }
			} else {
				if (this.getOutputValueBoolean()) {
					this.setOutputValue(0);
					for (int i = 0; i < this.getOutputs().size(); i++) {
						this.getOutputs().put(i, 0);
					}
					refferenceMan.updateObj(this.getID(), true, false);
					this.getPointManager().drawPoints();
				}
			}
			try {
				Thread.sleep(intervalTime);
			} catch (InterruptedException e) { }
		}
		Thread currentThread = Thread.currentThread();
		try {
			currentThread.join();
		} catch (InterruptedException e) { e.printStackTrace(); }
	}
}