package objectUtil;

import visualUtil.PointManager;
import visuals.ConsoleWindow;
import visuals.DesignPanel;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import objectUtil.StorrageHandler;
import placeable.digital.*;
import simUtil.NumberedHashMap;
import placeable.analog.*;
import placeable.*;

//Last edited: 3-15-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

public class ObjectManager {

	StorrageHandler StorrageSpace = new StorrageHandler();
	DesignPanel referencePanel;
	ConsoleWindow referenceConsole;
	ObjectCreator objCreator;
	SimObject currentObj;

	public ObjectManager() {
		objCreator = new ObjectCreator(this);
	}

	public DesignPanel getDesignPanel() {
		return this.referencePanel;
	}

	public void setDesignPanel(DesignPanel panel) {
		this.referencePanel = panel;
	}

	public ConsoleWindow getConsole() {
		if (referenceConsole == null)
			createConsole();
		return this.referenceConsole;
	}

	public void createConsole() {
		referenceConsole = new ConsoleWindow(referencePanel.getName(), referencePanel, this);
	}

	public void displayConsole() {
		if (referenceConsole == null)
			createConsole();
		if (!referenceConsole.getWindow().isVisible()) {
			referenceConsole.getWindow().setVisible(true);
			referenceConsole.getCommandLine().grabFocus();
		}
	}

	public void hideConsole() {
		if (referenceConsole == null)
			createConsole();
		if (referenceConsole.getWindow().isVisible())
			referenceConsole.getWindow().setVisible(false);
	}

	public void destoryConsole() {
		if (referenceConsole != null) {
			referenceConsole.getHistory().clear();
			referenceConsole.getWindow().dispose();
			referenceConsole = null;
		}
	}

	public void killManager() {
		this.removeAll();
		this.StorrageSpace = null;
		this.currentObj = null;
		this.referencePanel = null;
		this.destoryConsole();
	}
	
	public SimObject getObject(String ID) {
		if (StorrageSpace.contains(ID))
			return StorrageSpace.retrieve(ID);
		else
			return null;
	}

	public void setObjLocation(String ID, Point loc) {
		SimObject obj = StorrageSpace.retrieve(ID);
		obj.setLocation2D(loc);
	}

	public Point getObjLocation(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		return obj.getLocation2D();
	}

	public ObjectCreator getObjCreator() {
		return this.objCreator;
	}

	public SimObject getCurrentObj() {
		return this.currentObj;
	}

	public void setCurrentObj(SimObject obj) {
		this.currentObj = obj;
	}

	public StorrageHandler getStorrageHandler() {
		return this.StorrageSpace;
	}

	public void addTruthTableInput(String truthTableID, String objID) {
		SimObject gen = StorrageSpace.retrieve(truthTableID);
		SimObject obj = StorrageSpace.retrieve(objID);
		((TruthTable)gen).AddInput(obj);
	}

	public void addTruthTableOutput(String truthTableID, String objID) {
		SimObject gen = StorrageSpace.retrieve(truthTableID);
		SimObject obj = StorrageSpace.retrieve(objID);
		((TruthTable)gen).AddOutput(obj);
	}

	public void removeTruthTableValue(String truthTableID, String objID) {
		SimObject gen = StorrageSpace.retrieve(truthTableID);
		SimObject obj = StorrageSpace.retrieve(objID);
		((TruthTable)gen).removePoint(obj);
	}

	public void generateTruthTable(String truthTableID) {
		SimObject gen = StorrageSpace.retrieve(truthTableID);
		String tblOut = ((TruthTable)gen).generateTruthTable();
		referenceConsole.writeOnConsole("Truth Table Output:", Color.WHITE);
		referenceConsole.writeOnConsole(tblOut, Color.CYAN);
	}

	public void listObjects() {
		if (StorrageSpace.isEmpty()) {
			referenceConsole.writeOnConsole("No objects in ObjectManager.", Color.ORANGE);
			return;
		}
		for (SimObject tempObj : StorrageSpace.retrieveAll()) {
			referenceConsole.writeOnConsoleN(tempObj.getID() + ", ", Color.ORANGE);
		}
		referenceConsole.writeOnConsole("", Color.ORANGE);
	}

	public void listAllConnections() {
		if (StorrageSpace.isEmpty()) {
			referenceConsole.writeOnConsole("No objects in ObjectManager.", Color.ORANGE);
			return;
		}
		referenceConsole.writeOnConsole("------------------------------------------", Color.ORANGE);
		for (SimObject tempObj : StorrageSpace.retrieveAll()) {
			referenceConsole.writeOnConsole("Object ID: " + tempObj.getID(), Color.ORANGE);
			printPointmap(tempObj.getID());
		}
	}

	//internal function. This function is used for digitial inputs and primarily levers to toggle it's output value.
	public void toggleOutputValue(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		if (obj.getOutputValueInt() == 0) {
			obj.setOutputValue(1);
			for (int i = 0; i < obj.getOutputs().size(); i++) {
				obj.getOutputs().put(i, 1);
			}
			updateObj(ID, true, true);
		} else {
			obj.setOutputValue(0);
			for (int i = 0; i < obj.getOutputs().size(); i++) {
				obj.getOutputs().put(i, 0);
			}
			updateObj(ID, true, true);
		}
		if (obj instanceof Lever) {
			obj.getPointManager().updateLever();
			obj.getPointManager().drawPoints();
			referencePanel.repaint();
		}
	}

	public void toggleOutputValueTo(String ID, int value) {
		SimObject obj = StorrageSpace.retrieve(ID);
		obj.setOutputValue(value);
		for (int i = 0; i < obj.getOutputs().size(); i++) {
			obj.getOutputs().put(i, value);
		}
		updateObj(ID, true, true);
		if (obj instanceof Lever) {
			obj.getPointManager().updateLever();
			obj.getPointManager().drawPoints();
			referencePanel.repaint();
		}
	}

	//internal function used to set a different object point's value with the value given. The updateObj method is the only method that can call this.
	protected void setValueFromPoint(String ID, int destPoint, int value) {
		SimObject destObj = StorrageSpace.retrieve(ID);
		if (destPoint < destObj.getInputs().size())
			destObj.getInputs().put(destPoint, value);
		else if (destPoint >= destObj.getInputs().size() && destPoint < destObj.getInputs().size() + destObj.getOutputs().size()) {
			destObj.getOutputs().put(destPoint - destObj.getInputs().size(), value);
		} else
			referenceConsole.writeOnConsole("Invalid point.", Color.RED);
	}

	private void updateOutputPoints(String srcID) {
		SimObject srcObj = StorrageSpace.retrieve(srcID);
		SimObject destObj;
		//System.out.println("ID: " + srcObj.getID());
		//srcObj.getOutputsMap().printOutContents();
		//System.out.println("----------------");
		for (int i = 0; i < srcObj.getOutputsMap().size(); i++) {
			destObj = srcObj.getOutputsMap().getObject(i);
			if (destObj.getObjType().equals(ObjectTypes.WIRE)) {
				boolean wireOutputValue = destObj.getOutputValueBoolean();
				if (!wireOutputValue) {
					if (srcObj.getOutputValueBoolean()) {
						//System.out.println("YER");
						((Wire)destObj).setDesignatedSource(srcObj);
						updateWireConnections((Wire)destObj);
					}
					else if (((Wire)destObj).tryForNewDesignatedSource()) {
						//System.out.println("YERsela");
						updateWireConnections((Wire)destObj);
					}
					updateObj(destObj.getID(), false, false);
				} else {
					//System.out.println("Yango");
					if (((Wire)destObj).revalidateSource()) {
						//System.out.println("Yanas");
						updateWireConnections((Wire)destObj);
					}
					else {
						//System.out.println("Yangoli");
						if (((Wire)destObj).tryForNewDesignatedSource()) {
							//System.out.println("Yingero");
							updateWireConnections((Wire)destObj);
						}
						else {
							((Wire)destObj).setDesignatedSource(null);
							updateObj(destObj.getID(), false, false);
						}
					}
				}
			}			
			for (int q = 0; q < destObj.getConnections().size(); q++) {
				SimObject connectionObj = destObj.getConnections().getObject(q);
				boolean conObjPreVal = connectionObj.getOutputValueBoolean();
				int point = destObj.getConnections().getValue(q);
				if (connectionObj.isPointOutput(point)) {
					/*if (!(connectionObj.getPointValue(point) == srcObj.getOutputValueInt())) {
						this.setValueFromPoint(connectionObj.getID(), point, destObj.getOutputValueInt());
						updateObj(connectionObj.getID(), false, true);
					}*/
					continue;
				} else {
					setValueFromPoint(connectionObj.getID(), point, destObj.getOutputValueInt());
					updateObj(connectionObj.getID(), false, true);
				}
				if (checkIfOutputUpdates(connectionObj.getID(), conObjPreVal))
					updateOutputPoints(connectionObj.getID());
			}
			//setValueFromPoint(destObj.getID(), destObj.getOutputsMap().getValue(i), destObj.getOutputValueInt());
			updateObj(destObj.getID(), false, true);
		}
	}
	
	private void updateWireConnections(Wire wire) {
		SimObject designatedSrc = wire.getDesignatedSource();
		for (int i = 0; i < wire.getConnections().size(); i++) {
			SimObject connectionObj = wire.getConnections().getObject(i);
			if (!(connectionObj.equals(designatedSrc))) {
				int connectionPoint = wire.getConnections().getValue(1);
				//System.out.println(connectionPoint);
				//System.out.println(connectionObj.getID());
				if (connectionObj.getPointValue(connectionPoint) != 1) {
					this.setValueFromPoint(connectionObj.getID(), connectionPoint, wire.getOutputValueInt());
				}
			}
		}
	}

	public void linkObjPoint(String srcID, int srcPoint, String destID, int destPoint) {
		SimObject srcObj = StorrageSpace.retrieve(srcID);
		SimObject destObj = StorrageSpace.retrieve(destID);
		//System.out.println(destPoint);
		if (destObj.getConnections().containsObjValue(srcObj, destPoint)) {
			this.displayConsole();
			referenceConsole.writeOnConsole("Already linked to that obj point.", Color.RED);
		} else if ((destPoint > destObj.getInputs().size() + destObj.getOutputs().size() - 1)||(destPoint < 0)) {
			this.displayConsole();
			referenceConsole.writeOnConsole("Invalid point.", Color.RED);
		}
		else {
			objCreator.setCreatorDefaultArguments(true, true, false);
			objCreator.createObj("wire", "wire");
			this.currentObj.addConnection(srcObj, srcPoint);
			this.currentObj.addConnection(destObj, destPoint);
			srcObj.getConnections().put(destObj, srcPoint);
			destObj.getConnections().put(srcObj, destPoint);
			srcObj.getOutputsMap().put(currentObj, srcPoint);
			if (destObj.isPointOutput(destPoint))
				destObj.getOutputsMap().put(currentObj, destPoint);
			else
				destObj.getInputsMap().put(currentObj, destPoint);
			if (srcObj.isPointOutput(srcPoint))
				destObj.updatePointValue(destPoint, srcObj.getOutputValueInt());
			updateObj(currentObj.getID(), true, true);
			updateObj(destObj.getID(), true, true);
		}

		//need to add a check to see if it is connecting to another wire in which case it should just add that connection to the already existing wire instead of creating a new one
	}

	public void unlinkFromPoint(String srcID, int srcPoint, String destID, int destPoint) {
		SimObject srcObj = StorrageSpace.retrieve(srcID);
		SimObject destObj = StorrageSpace.retrieve(destID);
		if (srcObj.getConnections().containsObjValue(destObj, destPoint)) {
			srcObj.getConnections().removeMapWithSaidValues(destObj, destPoint);
			NumberedHashMap<SimObject, Integer> objConnections = destObj.getInputsMap();
			SimObject conWire = null;
			for (int i = 0; i < objConnections.size(); i++) {
				if (objConnections.getObject(i) instanceof Wire) {
					conWire = objConnections.getObject(i);
					if (conWire.getConnections().containsObject(srcObj) && conWire.getConnections().containsObject(destObj)) {
						srcObj.getOutputsMap().removeMapWithSaidValues(conWire, 0);
						srcObj.getConnections().removeMapsContainingObj(destObj);
						destObj.getInputsMap().removeMapWithSaidValues(conWire, 0);
						destObj.getConnections().removeMapsContainingObj(srcObj);
						destObj.updatePointValue(destPoint, 0);
						updateObj(destObj.getID(), true, true);

						if (conWire.getConnections().size() > 2)
							conWire.removeConnection(destObj, destPoint);
						else
							this.delete(conWire.getID());
					} else
						System.out.println("No link exists.");
				}
			}
		}
	}

	protected boolean checkIfOutputUpdates(String ID, boolean previousValue) {
		if (testLogic(ID) != previousValue)
			return true;
		else
			return false;
	}

	protected boolean testLogic(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		String tempType = obj.checkObjType();
		switch (tempType) {
		case "logicGate":
			String tempGateType = ((Gate)obj).checkGateType();
			switch(tempGateType) {
			case "or":
				return ((Or)obj).logic(true);
			case "nor":
				return ((Nor)obj).logic(true);
			case "and":
				return ((And)obj).logic(true);
			case "nand":
				return ((Nand)obj).logic(true);
			case "xor":
				return ((Xor)obj).logic(true);
			case "xnor":
				return ((Xnor)obj).logic(true);
			case "buffer":
				return ((Buffer)obj).logic(true);
			case "not":
				return ((Not)obj).logic(true);
			}
		case "latch":
			String tempLatchType = ((Latch)obj).checkLatchType();
			switch (tempLatchType) {
			case "d":
				return ((DLatch)obj).logic(true);
			//case "t":
				//return ((TFlipFlop)this).logic(true);
			//case "jk":
				//return ((JKFlipFlop)this).logic(true);
			}
		}
		return false;
	}

	//internal function used to update this object and all objects this is connected to whenever a connection to this object
	//is made or when a previous object is updated.
	public void updateObj(String ID, boolean updatePoints, boolean updateVisual) {
		SimObject obj = StorrageSpace.retrieve(ID);
		PointManager pointMan = obj.getPointManager();
		obj.setObjState(ObjectStates.RUNNING);
		String tempType = obj.checkObjType();
		switch (tempType) {
		case "wire":
			((Wire)obj).update(); break;
		case "logicGate":
			String tempGateType = ((Gate)obj).checkGateType();
			switch(tempGateType) {
			case "or":
				((Or)obj).logic(false); break;
			case "nor":
				((Nor)obj).logic(false); break;
			case "and":
				((And)obj).logic(false); break;
			case "nand":
				((Nand)obj).logic(false); break;
			case "xor":
				((Xor)obj).logic(false); break;
			case "xnor":
				((Xnor)obj).logic(false); break;
			case "buffer":
				((Buffer)obj).logic(false); break;
			case "not":
				((Not)obj).logic(false); break;
			}
			if (updateVisual)
				pointMan.updatePoints();
			break;
		case "object":
			if (updateVisual)
				pointMan.updatePoints(); 
			break;
		case "digitalOutput":
			((DOut)obj).update(); 
			if (updateVisual) {
				pointMan.updatePoints(); 
				pointMan.updateDOut();
			} break;
		case "latch":
			String tempLatchType = ((Latch)obj).checkLatchType();
			switch (tempLatchType) {
			case "dl":
				((DLatch)obj).logic(false);
				if (updateVisual)
					pointMan.updatePoints();
				break;
			//case "tl":
				//((TFlipFlop)this).logic(false); break;
			//case "jkl":
				//((JKFlipFlop)this).logic(false); break;
			}
		}
		if (updatePoints)
			updateOutputPoints(ID);
		obj.setObjState(ObjectStates.IDLE);
	}

	public void removeAll() {
		for (SimObject obj : StorrageSpace.storrage.values()) {
			delete(obj.getID());
		}
	}

	public void delete(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		if (obj instanceof TruthTable) {
			((TruthTable)obj).clearAll();
		}
		obj.getInputsMap().clear();
		obj.getOutputsMap().clear();
		obj.setObjState(ObjectStates.DEAD);
		if (!(obj instanceof TruthTable)) {
			referencePanel.remove(obj.getRefImg());
			referencePanel.repaint();
		}
		StorrageSpace.remove(ID);
		if (!obj.getObjType().equals(ObjectTypes.WIRE) && !(obj instanceof TruthTable))
			obj.getPointManager().destroy();
		obj = null;
	}

	public void unlinkObjsFromToBeDeletedObjs(List<String> killed) {
		for (int i = 0; i < killed.size(); i++) {
			SimObject killedObj = StorrageSpace.retrieve(killed.get(i));
			removeObjRef(killedObj.getID());
			for (int q = 0; q < killedObj.getConnections().size(); q++) {
				SimObject tempObj = killedObj.getConnections().getObject(q);
				int destPoint = killedObj.getConnections().getValue(q);
				tempObj.updatePointValue(destPoint, 0);
				updateObj(tempObj.getID(), true, true);
			}
		}
	}

	public void removeObjRef(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		NumberedHashMap<SimObject, Integer> inputsMap = obj.getInputsMap();
		NumberedHashMap<SimObject, Integer> outputsMap = obj.getOutputsMap();
		NumberedHashMap<SimObject, Integer> connectionMap = obj.getConnections();

		if (obj instanceof Wire) {
			List<SimObject> killConList = new ArrayList<SimObject>();
			for (int i = 0; i < connectionMap.size(); i++) {
				SimObject tempObj = connectionMap.getObject(i);
				killConList.add(tempObj);
				tempObj.getInputsMap().removeMapsContainingObj(obj);
				tempObj.getOutputsMap().removeMapsContainingObj(obj);
				tempObj.getConnections().removeMapsContainingObj(obj);
				int destPoint = connectionMap.getValue(i);
				tempObj.updatePointValue(destPoint, 0);
			}
			for (int i = 0; i < connectionMap.size(); i++) {
				SimObject tempObj = connectionMap.getObject(i);
				NumberedHashMap<SimObject, Integer> connections = tempObj.getConnections();
				for (int q = 0; q < killConList.size(); q++) {
					if (connections.containsObject(killConList.get(q))) {
						connections.removeMapsContainingObj(killConList.get(q));
					}
				}
			}
		} else {
			for (int i = 0; i < inputsMap.size(); i++) {
				if (inputsMap.getObject(i) instanceof Wire) {
					SimObject wire = inputsMap.getObject(i);
					wire.getConnections().removeMapsContainingObj(obj);
					updateObj(wire.getID(), true, false);

				} else {
					SimObject tempObj = inputsMap.getObject(i);
					tempObj.getConnections().removeMapsContainingObj(obj);
					updateObj(tempObj.getID(), true, false);
				}
			}
			for (int i = 0; i < outputsMap.size(); i++) {
				if (outputsMap.getObject(i) instanceof Wire) {
					SimObject wire = outputsMap.getObject(i);
					wire.getConnections().removeMapsContainingObj(obj);
					updateObj(wire.getID(), true, false);
				} else {
					SimObject tempObj = outputsMap.getObject(i);
					tempObj.getConnections().removeMapsContainingObj(obj);
					updateObj(tempObj.getID(), true, false);
				}
			}
			for (int i = 0; i < connectionMap.size(); i++) {
				if (connectionMap.getObject(i) instanceof Wire) {
					SimObject wire = connectionMap.getObject(i);
					wire.getConnections().removeMapsContainingObj(obj);
					updateObj(wire.getID(), true, false);
				} else {
					SimObject tempObj = connectionMap.getObject(i);
					tempObj.getConnections().removeMapsContainingObj(obj);
					updateObj(tempObj.getID(), true, false);
				}
			}
		}
	}

	public void printPointmap(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		NumberedHashMap<SimObject, Integer> outputMap = obj.getOutputsMap();
		NumberedHashMap<SimObject, Integer> inputMap = obj.getInputsMap();
		NumberedHashMap<SimObject, Integer> connectionMap = obj.getConnections();
		if (outputMap.isEmpty() == false) {
			referenceConsole.writeOnConsoleN("Output connections: ", Color.ORANGE);
			for (int i = 0; i < outputMap.size(); i++) {
				SimObject tempObj = outputMap.getObject(i);
				referenceConsole.writeOnConsoleN("[" + i + ", " + tempObj.getID() + " " + outputMap.getValue(i) + "] ", Color.ORANGE);
			}
			referenceConsole.writeOnConsole("", Color.ORANGE);
		} else
			referenceConsole.writeOnConsole("No output connections.", Color.ORANGE);
		if (inputMap.isEmpty() == false) {
			referenceConsole.writeOnConsoleN("Input connections: ", Color.ORANGE);
			for (int i = 0; i < inputMap.size(); i++) {
				SimObject tempObj = inputMap.getObject(i);
				referenceConsole.writeOnConsoleN("[" + i + ", " + tempObj.getID() + " " + inputMap.getValue(i) + "] ", Color.ORANGE);
			}
			referenceConsole.writeOnConsole("", Color.ORANGE);
		} else
			referenceConsole.writeOnConsole("No input connections.", Color.ORANGE);
		if (connectionMap.isEmpty() == false) {
			referenceConsole.writeOnConsoleN("Connection map: ", Color.ORANGE);
			for (int i = 0; i < connectionMap.size(); i++) {
				SimObject tempObj = connectionMap.getObject(i);
				referenceConsole.writeOnConsoleN("[" + i + ", " + tempObj.getID() + " " + connectionMap.getValue(i) + "] ", Color.ORANGE);
			}
			referenceConsole.writeOnConsole("", Color.ORANGE);
		} else
			referenceConsole.writeOnConsole("No connections in connection map.", Color.ORANGE);
		referenceConsole.writeOnConsole("------------------------------------------", Color.ORANGE);
	}

	//this is used to print out all of an object's current values at any given time.
	public void showCurrentStats(String ID) {
		SimObject obj = StorrageSpace.retrieve(ID);
		referenceConsole.writeOnConsole("------------------------------------------", Color.ORANGE);
		referenceConsole.writeOnConsole("Object ID: " + obj.getID(), Color.ORANGE);
		referenceConsole.writeOnConsole("Design Panel: " + referencePanel.getName(), Color.ORANGE);
		referenceConsole.writeOnConsole("Board Location: " + obj.getLocation2D().getX() + " " + obj.getLocation2D().getY(), Color.ORANGE);
		referenceConsole.writeOnConsole("Object type: " + obj.checkObjType(), Color.ORANGE);
		if (obj.checkObjType() == "logic gate")
			referenceConsole.writeOnConsole("Type of Logic gate: " + ((Gate)obj).checkGateType(), Color.ORANGE);
		if (obj.getObjType() != ObjectTypes.DIN && obj.getObjType() != ObjectTypes.AIN)
			referenceConsole.writeOnConsole("Number of input points: " + obj.getInputs().size(), Color.ORANGE);
		if (obj.getObjType() != ObjectTypes.DOUT && obj.getObjType() != ObjectTypes.AOUT)
			referenceConsole.writeOnConsole("Number of output points: " + obj.getOutputs().size(), Color.ORANGE);
		if (obj.getInputs().isEmpty() == false) {
			for (int i = 0; i < obj.getInputs().size(); i++) {
				if (obj.getInputs().get(i) == 1)
					referenceConsole.writeOnConsole("Input point " + i + " value: " + obj.getInputs().get(i), Color.ORANGE);
			}
		}
		if (obj instanceof Wire) {
			@SuppressWarnings("unused")
			String name;
			referenceConsole.writeOnConsole("Designated Source: " + (name = (((Wire)obj).isThereASource()) ? ((Wire)obj).getDesignatedSource().getID() : "none."), Color.ORANGE);
		}
		referenceConsole.writeOnConsole("Object output: " + obj.getOutputValueInt(), Color.ORANGE);
	}
}