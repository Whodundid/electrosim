package placeable;

public enum ObjectStates {
	DEAD(0),
	RUNNING(1),
	IDLE(2),
	WAITINGTOBEUPDATED(3);
	
	private int state;
	
	ObjectStates(int state) {
		this.state = state;
	}
	
	public int getState() {
		return this.state;
	}
}