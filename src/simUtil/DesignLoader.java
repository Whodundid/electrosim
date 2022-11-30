package simUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import objectUtil.ObjectCreator;
import objectUtil.ObjectManager;
import placeable.digital.DigitalClock;
import placeable.digital.Lever;
import placeable.SimObject;
import visuals.DesignPanel;
import visuals.VisualGUI;

//Last edited: 3-15-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

public class DesignLoader {
	VisualGUI refGUI;
	JFrame mainFrame;
	JTabbedPane pane;
	ObjectManager manager;
	ObjectCreator objCreator;
	String ID, objType, identityType, fileName, obj;
	SimObject tempObj;
	int objPoint = 0;
	int inputs = 0, outputs = 0, posX = 0, posY = 0, input = 0;
	int inputVal = 0, output = 0, outputVal = 0;
	int intervalVal;
	boolean connections;
	boolean isEnd;
	
	public DesignLoader(String file, VisualGUI gui) {
		this.refGUI = gui;
		this.mainFrame = gui.getFrame();
		pane = gui.getTabPane();
		fileName = file;
		manager = new ObjectManager();
		objCreator = manager.getObjCreator();
	}
	
	public void load() {
		try {
			File file = new File(fileName);
			DesignPanel panel = new DesignPanel(manager, mainFrame);
			panel.setFilePath(fileName);
			objCreator.setDesignPanel(panel);
			try (Scanner fileReader = new Scanner(file)) {
				String line = fileReader.nextLine();
				Scanner startReader = new Scanner(line);
				String command = startReader.next();
				if (command.equals("ST")) {
					command = fileReader.nextLine();
					startReader.close();
					String commandLine;
					while (!isEnd) {
						try {
							commandLine = fileReader.nextLine();
							System.out.println(commandLine);
							Scanner commandReader = new Scanner(commandLine);
							command = commandReader.next();
							switch (command) {
							case "OD":
								objType = commandReader.next();
								switch (objType) {
								case "logicGate":
								case "latch":
								case "flipFlop":
									identityType = commandReader.next(); break;
								case "dClock":
									intervalVal = Integer.valueOf(commandReader.next()); break;
								}

								ID = commandReader.next();
								inputs = Integer.valueOf(commandReader.next());
								outputs = Integer.valueOf(commandReader.next());
								posX = Integer.valueOf(commandReader.next());
								posY = Integer.valueOf(commandReader.next());
								
								objCreator.setCreatorPosArgs(posX, posY);
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorValueArugments(intervalVal);
								
								if (identityType != null) {
									switch (identityType) {
										case ("or"): objCreator.createObj(ID, "or"); break;
										case ("nor"): objCreator.createObj(ID, "nor"); break;
										case ("and"): objCreator.createObj(ID, "and"); break;
										case ("nand"): objCreator.createObj(ID, "nand"); break;
										case ("xor"): objCreator.createObj(ID, "xor"); break;
										case ("xnor"): objCreator.createObj(ID, "xnor"); break;
										case ("buffer"): objCreator.createObj(ID, "buffer"); break;
										case ("not"): objCreator.createObj(ID, "not"); break;
										case ("dl"): objCreator.setCreatorDataTypeArguments('d'); objCreator.createObj(ID, "latch"); break;
										case ("tl"): //objCreator.setCreatorDataTypeArguments('t'); objCreator.createObj(ID, "latch"); break;
										case ("jkl"): //objCreator.setCreatorDataTypeArguments('j'); objCreator.createObj(ID, "latch"); break;
										case ("srl"): //objCreator.setCreatorDataTypeArguments('s'); objCreator.createObj(ID, "latch"); break;
										case ("dff"): //objCreator.setCreatorDataTypeArguments('d'); objCreator.createObj(ID, "ff"); break;
										case ("tff"): //objCreator.setCreatorDataTypeArguments('t'); objCreator.createObj(ID, "ff"); break;
										case ("jkff"): //objCreator.setCreatorDataTypeArguments('j'); objCreator.createObj(ID, "ff"); break;
									}
								} else {
									switch (objType) {
										case ("digitalInput"): objCreator.createObj(ID, "lever"); break;
										case ("digitalOutput"): objCreator.createObj(ID, "dout"); break;
										case ("diode"): break;
										case ("capacitor"): break;
										case ("resistor"): break;
										case ("transistor"): break;
										case ("transformer"): break;
										case ("analogInput"): break;
										case ("analogOutput"): break;
										case ("wire"): objCreator.createObj(ID, "wire"); break;
										case ("dClock"): objCreator.createObj(ID, "dClock"); new Thread((DigitalClock)manager.getCurrentObj()).start(); break;
									}
								} identityType = null; break;
							case "IV":
								tempObj = manager.getCurrentObj();
								for (int i = 0; i < inputs; i++) {
									input = Integer.valueOf(commandReader.next());
									inputVal = Integer.valueOf(commandReader.next());
									tempObj.setPointValue(input, inputVal);
								} break;
							case "OV":
								tempObj = manager.getCurrentObj();
								for (int i = 0; i < outputs; i++) {
									output = Integer.valueOf(commandReader.next());
									outputVal = Integer.valueOf(commandReader.next());
									tempObj.setPointValue(output, outputVal);
									if (tempObj instanceof Lever)
										manager.toggleOutputValueTo(tempObj.getID(), outputVal);
								} break;
							case "IC":
								tempObj = manager.getCurrentObj();
								while (commandReader.hasNext()) {
									obj = commandReader.next();
									objPoint = Integer.valueOf(commandReader.next());
									tempObj.getLoadedInputs().put(obj, objPoint);
								} connections = true; break;
							case "OC":
								tempObj = manager.getCurrentObj();
								while (commandReader.hasNext()) {
									obj = commandReader.next();
									objPoint = Integer.valueOf(commandReader.next());
									tempObj.getLoadedOutputs().put(obj, objPoint);
								} connections = true; break;
							case "CM":
								tempObj = manager.getCurrentObj();
								while (commandReader.hasNext()) {
									obj = commandReader.next();
									objPoint = Integer.valueOf(commandReader.next());
									tempObj.getLoadedConnections().put(obj, objPoint);
								} connections = true; break;
							case "EN":
								this.isEnd = true; System.out.println("END REACHED"); break;
							}							
							commandReader.close();
						} catch (Exception e) { e.printStackTrace(); }
					}
				} else
					System.out.println("Error: selected file is either corrupted or is of wrong file type 4");
				fileReader.close();
				startReader.close();
			} catch (IOException e) {}
			
			if (connections) {
				SimObject conObj;
				List<SimObject> objects = manager.getStorrageHandler().retrieveAll();
				for (int i = 0; i < objects.size(); i++) {
					SimObject obj = objects.get(i);
					for (int q = 0; q < obj.getLoadedInputs().size(); q++) {
						String conObjID = obj.getLoadedInputs().getObject(q);
						int conPoint = obj.getLoadedInputs().getValue(q);
						conObj = manager.getStorrageHandler().retrieve(conObjID);
						obj.getInputsMap().put(conObj, conPoint);
					}
					for (int q = 0; q < obj.getLoadedOutputs().size(); q++) {
						String conObjID = obj.getLoadedOutputs().getObject(q);
						int conPoint = obj.getLoadedOutputs().getValue(q);
						conObj = manager.getStorrageHandler().retrieve(conObjID);
						obj.getOutputsMap().put(conObj, conPoint);
					}
					for (int q = 0; q < obj.getLoadedConnections().size(); q++) {
						String conObjID = obj.getLoadedConnections().getObject(q);
						int conPoint = obj.getLoadedConnections().getValue(q);
						conObj = manager.getStorrageHandler().retrieve(conObjID);
						obj.getConnections().put(conObj, conPoint);
					}
					manager.updateObj(obj.getID(), true, true);
				}
			}
			this.correctFileName();
			refGUI.createNewDesignTab(panel, fileName);
		} catch (java.lang.OutOfMemoryError e) { System.out.println("Not enough memory to load file."); }
	}
	
	public void correctFileName() {
		int count = 0;
		for (int i = 0; i < fileName.length(); i++) {
			if (fileName.charAt(i) != 46)
				count++;
			else
				fileName = fileName.substring(0, count);
		}
	}
}