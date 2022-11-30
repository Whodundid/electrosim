package objectUtil;

import java.awt.Color;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import placeable.SimObject;
import placeable.Wire;
import placeable.digital.*;
import placeable.analog.*;
import visualUtil.ObjectMover2D;
import visualUtil.PointManager;
import visualUtil.SimObjectVisual;
import visuals.DesignPanel;

//Last edited: 3-11-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

public class ObjectCreator {
	
	DesignPanel panel;
	ObjectManager manager;
	ImageIcon pic;
	int numInputs;
	int numOutputs;
	int frequency;
	int posX, posY;
	char dataType;
	boolean useDefaultPos;
	boolean useDefaultName;
	boolean printToConsole;
	String newID;
	
	public ObjectCreator(ObjectManager manager) {
		this.manager = manager;
	}
	
	public void setDesignPanel(DesignPanel panel) {
		this.panel = panel;
	}
	
	public void setCreatorIOArguments(int numInputs, int numOutputs) {
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
	}
	
	public void setCreatorValueArugments(int frequency) {
		this.frequency = frequency;
	}
	
	public void setCreatorDataTypeArguments(char type) {
		this.dataType = type;
	}
	
	public void setCreatorPosArgs(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	public void setCreatorDefaultArguments(boolean useDefaultPos, boolean useDefaultName, boolean printToConsole) {
		this.useDefaultPos = useDefaultPos;
		this.useDefaultName = useDefaultName;
		this.printToConsole = printToConsole;
	}
	
	public void clearArgs() {
		this.dataType = Character.MIN_VALUE;
		this.frequency = 0;
		this.numInputs = 0;
		this.numOutputs = 0;
		this.posX = 0;
		this.posY = 0;
		this.printToConsole = false;
		this.useDefaultName = false;
		this.useDefaultPos = false;		
	}
	
	private JLabel createNewObj2D(String name, ImageIcon icon, DesignPanel panel, SimObject obj, int posX, int posY) {
		try {
			SimObjectVisual newObj = new SimObjectVisual(obj, icon);
			newObj.setID(name);
			if (posX < 0 || posY < 0) {
				obj.setLocation2D(new Point (panel.getWidth()/2, panel.getHeight()/2));
				newObj.setBounds(panel.getWidth()/2, panel.getHeight()/2, icon.getIconWidth(), icon.getIconHeight());
			} else {
				obj.setLocation2D(new Point (posX, posY));
				newObj.setBounds(posX, posY, icon.getIconWidth(), icon.getIconHeight());
			}
			if (!(obj instanceof Wire)) {
				PointManager pointMan = new PointManager(newObj, panel);
				obj.setPointManager(pointMan);
				pointMan.createPoints();
				pointMan.drawPoints();
			}
			ObjectMover2D mover = new ObjectMover2D(newObj, panel, manager);
			newObj.addMouseListener(mover);
			newObj.addMouseMotionListener(mover);
			newObj.setObjectMover2D(mover);
			obj.setRefImg(newObj);
			return newObj;
		} catch (NullPointerException | java.lang.OutOfMemoryError e) { e.printStackTrace(); }
		return null;
	}
	
	public void createObj(String ID, String ObjectType) {
		try {
			if (!manager.getStorrageHandler().contains(ID)) {
				if (useDefaultName)
					 newID = manager.getStorrageHandler().getNextAvailibleEntry(ID);
				else 
					newID = ID;
				
				switch (ObjectType) {
				case "wire":
					Wire createdWire = new Wire(newID); pic = new ImageIcon("objResources/null_Wire.png"); 
					addToPanelAndManager(createdWire); break;
				case "lever":
					Lever createdLever = new Lever(newID, numOutputs); pic = new ImageIcon("objResources/null_Lever.png"); 
					addToPanelAndManager(createdLever); break;
				case "dout":
					DOut createdDOut = new DOut(newID, numInputs); pic = new ImageIcon("objResources/null_DOut.png");
					addToPanelAndManager(createdDOut); break;
				case "or":
					Or createdOr = new Or(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Or.png");
					addToPanelAndManager(createdOr); break;
				case "nor":
					Nor createdNor = new Nor(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Nor.png");
					addToPanelAndManager(createdNor); break;
				case "and":
					And createdAnd = new And(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_And.png");
					addToPanelAndManager(createdAnd); break;
				case "nand":
					Nand createdNand = new Nand(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Nand.png");
					addToPanelAndManager(createdNand); break;
				case "xor":
					Xor createdXor = new Xor(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Xor.png");
					addToPanelAndManager(createdXor); break;
				case "xnor":
					Xnor createdXnor = new Xnor(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Xnor.png");
					addToPanelAndManager(createdXnor); break;
				case "not":
					Not createdNot = new Not(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Not.png");
					addToPanelAndManager(createdNot); break;
				case "buffer":
					Buffer createdBuf = new Buffer(newID, numInputs, numOutputs); pic = new ImageIcon("gateResources/null_Buffer.png");
					addToPanelAndManager(createdBuf); break;
				case "dClock":
					DigitalClock createdDClock = new DigitalClock(newID, frequency, manager); pic = new ImageIcon("simResources/noimg.png");
					addToPanelAndManager(createdDClock); break;
				case "latch":
					switch (dataType) {
					case 'd': 
						DLatch createdDLatch = new DLatch(newID); pic = new ImageIcon("objResources/DLatch.png");
						addToPanelAndManager(createdDLatch); break;
					//case 't':
					//case 'J':
					//case 's':
					}
					break;
				/*case "ff":
					switch (dataType) {
					case 'd':
						DFF createdFF = new DFF(newID); pic = new ImageIcon("simResources/noimg.png");
						addToPanelAndManager(createdFF); break;
					}
					break; */
				case "tt":
					TruthTable createdTT = new TruthTable(newID, manager);
					addToPanelAndManager(createdTT); break;
				}
			} else manager.getConsole().writeOnConsole("Object already exists under that name.", Color.RED);
		} catch (NullPointerException | java.lang.OutOfMemoryError e) { e.printStackTrace(); }
	}
	
	private void addToPanelAndManager(SimObject obj) {
		try {
			if (useDefaultPos) {
				posX = -1; posY = -1;
			}
			manager.getStorrageHandler().store(newID, obj);
			obj.setDesignPanel(panel);
			if (!(obj instanceof TruthTable))
				panel.add(createNewObj2D(newID, pic, panel, obj, posX, posY));
			manager.setCurrentObj(obj);
			if (printToConsole)
				manager.getConsole().writeOnConsole("Created Object.", Color.GREEN);
			manager.updateObj(obj.getID(), false, true);
			panel.repaint();
		} catch (NullPointerException | java.lang.OutOfMemoryError e) { e.printStackTrace(); }
		clearArgs();
	}
}