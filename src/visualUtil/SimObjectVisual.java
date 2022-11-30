package visualUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import objectUtil.ObjectManager;
import placeable.digital.DOut;
import placeable.digital.Gate;
import placeable.digital.Latch;
import placeable.SimObject;
import placeable.Wire;

//Last edited: 3-11-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class SimObjectVisual extends JLabel{
	
	String ID;
	ObjectMover2D refMover;
	boolean isSelected;
	boolean isOn;
	PartPropertiesMenu partProps;
	SimObject refObj;
	ObjectManager refMan;
	
	public SimObjectVisual(SimObject obj, ImageIcon icon) {
		this.refObj = obj;
		this.refMan = obj.getObjectManager();
		partProps = new PartPropertiesMenu(obj, this);
		this.setIcon(icon);
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		if (!isSelected) {
			this.select();
		}
		this.isSelected = isSelected;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	public void setObjectMover2D(ObjectMover2D mover) {
		this.refMover = mover;
	}
	
	public void setID(String ID) {
		this.ID = ID;
		this.setName(ID);
	}
	
	public String getID() {
		return this.ID;
	}
	
	public ObjectMover2D getObjectMover() {
		return this.refMover;
	}
	
	public void displayProperties() {
		partProps.requestFocus();
		partProps.display();
	}
	
	public void select() {
		this.isSelected = true;
		String objType = refObj.checkObjType();
		if (!(refMan.getObject(this.getName()) instanceof Wire))
			refMan.getObject(this.getName()).getPointManager().selectPoints();
		if (objType.equals("logicGate")) {
			String gateType = ((Gate)refObj).checkGateType();
			switch (gateType) {
				case ("or"): setNewImg("gateResources/null_Or_Sel.png"); break;
				case ("nor"): setNewImg("gateResources/null_Nor_Sel.png"); break;
				case ("and"): setNewImg("gateResources/null_And_Sel.png"); break;
				case ("nand"): setNewImg("gateResources/null_Nand_Sel.png"); break;
				case ("xor"): setNewImg("gateResources/null_Xor_Sel.png"); break;
				case ("xnor"): setNewImg("gateResources/null_Xnor_Sel.png"); break;
				case ("buffer"): setNewImg("gateResources/null_Buffer_Sel.png"); break;
				case ("not"): setNewImg("gateResources/null_Not_Sel.png"); break;
			}
		} else if (objType.equals("digitalInput")) {
			boolean value = refObj.getOutputValueBoolean();
			if (value)
				setNewImg("objResources/on_Lever_Sel.png");
			else
				setNewImg("objResources/null_Lever_Sel.png");
		} else if (objType.equals("digitalOutput")) {
			boolean value = ((DOut)refObj).update();
			if (value)
				setNewImg("objResources/on_DOut_Sel.png");
			else
				setNewImg("objResources/null_DOut_Sel.png");
		} else if (objType.equals("latch")) {
			String latchType = ((Latch) refObj).checkLatchType();
			switch (latchType) {
				case ("dl"): setNewImg("objResources/DLatch_Sel.png"); break;
			}
		}
	}
	
	public void deselect() {
		this.isSelected = false;
		String objType = refObj.checkObjType();
		if (!(refMan.getObject(this.getName()) instanceof Wire))
			refMan.getObject(this.getName()).getPointManager().deselectPoints();
		if (objType.equals("logicGate")) {
			String gateType = ((Gate)refObj).checkGateType();
			switch (gateType) {
				case ("or"): setNewImg("gateResources/null_Or.png"); break;
				case ("nor"): setNewImg("gateResources/null_Nor.png"); break;
				case ("and"): setNewImg("gateResources/null_And.png"); break;
				case ("nand"): setNewImg("gateResources/null_Nand.png"); break;
				case ("xor"): setNewImg("gateResources/null_Xor.png"); break;
				case ("xnor"): setNewImg("gateResources/null_Xnor.png"); break;
				case ("buffer"): setNewImg("gateResources/null_Buffer.png"); break;
				case ("not"): setNewImg("gateResources/null_Not.png"); break;
			}
		} else if (objType.equals("digitalInput")) {
			boolean value = refObj.getOutputValueBoolean();
			if (value)
				setNewImg("objResources/on_Lever.png");
			else
				setNewImg("objResources/null_Lever.png");
		} else if (objType.equals("digitalOutput")) {
			boolean value = ((DOut)refObj).update();
			if (value)
				setNewImg("objResources/on_DOut.png");
			else
				setNewImg("objResources/null_DOut.png");
		} else if (objType.equals("latch")) {
			String latchType = ((Latch) refObj).checkLatchType();
			switch (latchType) {
				case ("dl"): setNewImg("objResources/DLatch.png"); break;
			}
		}
	}
	
	public void setNewImg(String path) {
		ImageIcon selIcon = new ImageIcon(path);
		this.setIcon(selIcon);
	}
}