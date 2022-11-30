package placeable;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

public class Wire extends SimObject {
	
	SimObject designatedSource;
	
	public Wire(String ID) {
		setID(ID);
		setObjType(ObjectTypes.WIRE);
		setObjState(ObjectStates.IDLE);
	}
	
	public void setDesignatedSource(SimObject src) {
		this.designatedSource = src;
	}
	
	public SimObject getDesignatedSource() {
		if (this.designatedSource != null)
			return this.designatedSource;
		else {
			if (tryForNewDesignatedSource())
				return this.designatedSource;
			else
				return null;
		}
	}
	
	public boolean isThereASource() {
		if (this.getDesignatedSource() != null)
			return true;
		else
			return false;
	}
	
	public boolean revalidateSource() {
		if (this.designatedSource.getOutputValueBoolean())
			return true;
		else {
			if (tryForNewDesignatedSource()) {
				if (this.designatedSource.getOutputValueBoolean())
					return true;
				else
					return false;
			} else
				return false;
		}
	}
	
	public boolean tryForNewDesignatedSource() {
		for (int i = 0; i < this.getConnections().size(); i++) {
			if (!this.getConnections().isEmpty()) {
				if (this.getConnections().getObject(i) != null) {
					if (this.getConnections().getObject(i).getOutputValueBoolean()) {
						int destPoint = this.getConnections().getValue(i);
						if (this.getConnections().getObject(i).isPointOutput(destPoint)) {
							this.designatedSource = this.getConnections().getObject(i);
							return true;
						}
					}
				}				
			}			
		} return false;
	}
	
	public boolean update() {
		if (!this.getConnections().isEmpty()) {
			if (this.designatedSource != null) {
				if (this.designatedSource.getOutputValueBoolean()) {
					this.setOutputValue(1);
					return true;
				} else {
					if (tryForNewDesignatedSource()) {
						if (this.designatedSource.getOutputValueBoolean()) {
							this.setOutputValue(1);
							return true;
						} 
					} else {
						this.setOutputValue(0);
						return false;
					}
				}
			} else if (tryForNewDesignatedSource()) {
				if (this.designatedSource.getOutputValueBoolean()) {
					this.setOutputValue(1);
					return true;
				} else {
					this.setOutputValue(0);
					return false;
				}
			} else {
				this.setOutputValue(0);
				return false;
			}
		} return false;
	} 
}