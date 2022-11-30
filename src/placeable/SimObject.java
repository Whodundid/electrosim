package placeable;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import objectUtil.ObjectManager;
import simUtil.NumberedHashMap;
import visualUtil.PointManager;
import visuals.DesignPanel;

//Last edited: 3-9-17
//Author: Hunter Troy Bragg

//the object class is the heart of all placeable things on the design space.
public abstract class SimObject{
	
	//defines the internal values that this object will be later identified with when actually created.
	private String ID;
	private int objOutputValue = 0;
	protected Map<Integer, Integer> inputs = new HashMap<Integer, Integer>();
	protected Map<Integer, Integer> outputs = new HashMap<Integer, Integer>();
	NumberedHashMap<String, Integer> loadedInputs = new NumberedHashMap<String, Integer>();
	NumberedHashMap<String, Integer> loadedOutputs = new NumberedHashMap<String, Integer>();
	NumberedHashMap<String, Integer> loadedConnections = new NumberedHashMap<String, Integer>();
	NumberedHashMap<SimObject, Integer> connections = new NumberedHashMap<SimObject, Integer>();
	NumberedHashMap<SimObject, Integer> outputPointMap = new NumberedHashMap<SimObject, Integer>();
	NumberedHashMap<SimObject, Integer> inputPointMap = new NumberedHashMap<SimObject, Integer>();
	Point location = new Point();
	PointManager pointMan;
	DesignPanel panel;
	ObjectManager manager;
	JLabel refImg;	
	private ObjectStates objState;
	private ObjectTypes objType;
	
	public void setRefImg(JLabel img) {
		this.refImg = img;
	}
	
	public JLabel getRefImg() {
		return this.refImg;
	}
	
	public DesignPanel getDesignPanel() {
		return this.panel;
	}
	
	public void setDesignPanel(DesignPanel panel) {
		this.panel = panel;
		this.manager = panel.getObjectManger();
	}
	
	public ObjectManager getObjectManager() {
		return this.manager;
	}
	
	public PointManager getPointManager() {
		return this.pointMan;
	}
	
	public void setPointManager(PointManager manager) {
		this.pointMan = manager;
	}
	
	public NumberedHashMap<SimObject, Integer> getOutputsMap() {
		return this.outputPointMap;
	}
	
	public NumberedHashMap<SimObject, Integer> getInputsMap() {
		return this.inputPointMap;
	}
	
	public Map<Integer, Integer> getInputs() {
		return this.inputs;
	}
	
	public Map<Integer, Integer> getOutputs() {
		return this.outputs;
	}
	
	public NumberedHashMap<SimObject, Integer> getConnections() {
		return this.connections;
	}
	
	public NumberedHashMap<String, Integer> getLoadedInputs() {
		return this.loadedInputs;
	}
	
	public NumberedHashMap<String, Integer> getLoadedOutputs() {
		return this.loadedOutputs;
	}
	
	public NumberedHashMap<String, Integer> getLoadedConnections() {
		return this.loadedConnections;
	}
	
	//returns this objects current output value.
	public int getOutputValueInt() {
		return objOutputValue;
	}
	
	public boolean getOutputValueBoolean() {
		if (this.objOutputValue == 1)
			return true;
		else
			return false;
	}
	
	//get this objects id
	public String getID() {
		return this.ID;
	}
	
	//set this objects id
	public void setID(String val) {
		this.ID = val;
	}
	
	//check to see if this object is dead. If it is, signal the object manager to remove this object on the next pass.
	public boolean checkDead() {
		return (objState == ObjectStates.DEAD);
	}
	
	//check to see if this object is curently running. If it is, give this object priority.
	public boolean checkRunning() {
		return (objState == ObjectStates.RUNNING);
	}
	
	//check to see if this object is idle.
	public boolean checkIdle() {
		return (objState == ObjectStates.IDLE);
	}
	
	public void setLocation2D(Point loc) {
		this.location = loc;
	}
	
	public Point getLocation2D() {
		return location;
	}
	
	//internal function used to change this objects current state.
	public void setObjState(ObjectStates state) {
		this.objState = state;
	}
	
	//returns the object's current state.
	public ObjectStates getObjState() {
		return objState;
	}
	
	//internal funtion used to set the type of object this it's going to be, for example: logic gate, digital input, ect.
	protected void setObjType(ObjectTypes type) {
		objType = type;
	}
	
	//returns the objects type.
	public ObjectTypes getObjType() {
		return objType;
	}
	
	//this is used to check what type of object this is against the hard-coded object types and return a word value instead of an integer value.
	public String checkObjType() {
		return objType.getObjectType();
	}
	
	public void addConnection(SimObject object, int point) {
		if (this.connections.containsObject(object)) {
			if (!this.connections.containsObjValue(object, point))
				this.connections.put(object, point);
		} else
			this.connections.put(object, point);
	}
	
	public void removeConnection(SimObject object, int point) {
		if (this.connections.containsObjValue(object, point))
			this.connections.removeMapsContainingObj(object);
	}
	
	//internal function used to define this objects number of inputs. When created, the constructor updates this objects 'inputs' hashmap accordingly.
	public void setNumInputs(int numInputs) {
		for (int i = 0; i < numInputs; i++) {
			inputs.put(i, 0);
		}
	}
	
	//internal function used to define this objects number of outputs. When created, the constructor updates this objects 'outputs' hashmap accordingly.
	public void setNumOutputs(int numOutputs) {
		for (int i = 0; i < numOutputs; i++) {
			outputs.put(i, 0);
		}
	}
	
	public boolean isPointOutput(int point) {
		if (inputs.isEmpty())
			return true;
		else if (point >= inputs.size() && point <= (outputs.size() + inputs.size())) {
			return true;
		} else
			return false;
	}
	
	//deprecated function. Was used to manually force an input's point value high. Can still be used, but may cause problems if connected to other objects.
	//@Deprecated
	public void setPointValue(int input, int val) {
		if (inputs.containsKey(input)) {
			inputs.put(input, val);
			manager.updateObj(this.ID, true, true);
		} else if (outputs.containsKey(input)) {
			outputs.put(input, val);
			manager.updateObj(this.ID, true, true);
		} else
			System.err.println("Invalid input defined.");
	}
	
	//deprecated function. Was used to force all inputs high for this object. Basically only useful for testing at this point.
	@Deprecated
	protected void setAllInVal(int val) {
		for (int i = 0; i < inputs.size(); i++) {
			inputs.put(i, val);
		}
	}
	
	//internal function. This object's base logic function will call this function specifically to update the output value.
	public void setOutputValue(int value) {
		this.objOutputValue = value;
		for (int i = 0; i < outputs.size(); i++) {
			outputs.put(i, value);
		}
	}
	
	public void updatePointValue(int point, int value) {
		if (point < this.inputs.size())
			this.inputs.put(point, value);
	}
	
	//returns the corresponding given point value
	public int getPointValue(int point) {
		if (point < this.inputs.size())
			return this.inputs.get(point);
		else
			return this.outputs.get(point - this.inputs.size());
	}
}