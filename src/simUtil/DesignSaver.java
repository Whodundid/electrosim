package simUtil;

import objectUtil.ObjectManager;
import objectUtil.StorrageHandler;
import placeable.digital.Gate;
import placeable.digital.Latch;
import placeable.digital.FlipFlop;
import placeable.digital.DigitalClock;
import placeable.SimObject;
import placeable.Wire;
import simBase.Start;
import visualUtil.YesNoDialog;
import visuals.ConsoleWindow;
import visuals.DesignPanel;
import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

//Last edited: 3-15-17
//Author: Hunter Troy Bragg

public class DesignSaver {
	ObjectManager manager;
	ConsoleWindow con;
	String fileName;
	StorrageHandler storrage;
	String objType;
	String val;
	String typeIdentity;
	boolean needsTypeIdentity = false;
	boolean goodToSave;
	
	public DesignSaver(DesignPanel panel, String path, boolean fromConsole) {
		manager = panel.getObjectManger();
		con = manager.getConsole();
		File newName = new File(path + ".ecs");
		File newNameNoPath = new File(path);
		if (newName.exists() || newNameNoPath.exists()) {
			if (fromConsole) {
				manager.displayConsole();
				con.writeOnConsole("File already exists under that name.", Color.YELLOW);
				con.writeOnConsole("Do you wish to overwrite already existing file? Y/N", Color.YELLOW);
				con.isSaving = true;
			} else {
				YesNoDialog dialog = new YesNoDialog("saver");
				dialog.setLocationRelativeTo(panel.mainFrame);
				dialog.setDesignSaver(this);
			}
		} else
			goodToSave = true;
		newName = null;
		newNameNoPath = null;
	}
	
	public void setFileName(String name) {
		this.fileName = name;
	}
	
	public void tryToSave() {
		if (goodToSave)
			save();
		goodToSave = false;
	}
	
	public void save() {
		try {
			storrage = manager.getStorrageHandler();
			List<SimObject> objList = storrage.retrieveAll();
			PrintWriter writer = new PrintWriter(fileName + ".ecs", "UTF-8");
		    writer.println("ST");
		    writer.println(Start.version);
		    for (int i = 0; i < objList.size(); i++) {
		    	needsTypeIdentity = false;
		    	typeIdentity = null;
		    	SimObject tempObj = objList.get(i);
		    	Double xLoc = tempObj.getLocation2D().getX(), yLoc = tempObj.getLocation2D().getY();
		    	switch (tempObj.checkObjType()) {
		    		case "logicGate": typeIdentity = ((Gate)tempObj).checkGateType(); needsTypeIdentity = true; break;
		    		case "latch": typeIdentity = ((Latch)tempObj).checkLatchType(); needsTypeIdentity = true; break;
		    		case "flipFlop": typeIdentity = ((FlipFlop)tempObj).checkFFType(); needsTypeIdentity = true; break;
		    		case "dClock": typeIdentity = ((Long)((DigitalClock)tempObj).getInterval()).toString(); needsTypeIdentity = true; break;
		    	}
		    	
	    		writer.println("OD " + tempObj.checkObjType() + " " 
	    				 			   + (val = (needsTypeIdentity) ? typeIdentity + " " : "")
	    				 			   + tempObj.getID() + " "  
	    				 			   + tempObj.getInputs().size() + " " 
	    				 			   + tempObj.getOutputs().size() + " " 
	    				 			   + xLoc.intValue() + " " 
	    				 			   + yLoc.intValue()
	    				 	  );
	    		
	    		if (!tempObj.getInputs().isEmpty() && !(tempObj instanceof Wire)) {
	    			writer.print("IV ");
	    			for (int e = 0; e < tempObj.getInputs().size(); e++) {
			    		if (e == tempObj.getInputs().size() - 1)
			    			writer.print(e + " " + tempObj.getInputs().get(e));
			    		else
			    			writer.print(e + " " + tempObj.getInputs().get(e) + " ");
			    	}
	    			writer.println();
	    		}
	    		if (!tempObj.getOutputs().isEmpty() && !(tempObj instanceof Wire)) {
	    			writer.print("OV ");
			    	for (int e = 0; e < tempObj.getOutputs().size(); e++) {
			    		if (e == tempObj.getOutputs().size() - 1)
			    			writer.print((e + tempObj.getInputs().size()) + " " + tempObj.getOutputs().get(e));
			    		else
			    			writer.print((e + tempObj.getInputs().size()) + " " + tempObj.getOutputs().get(e) + " ");
			    	}
			    	writer.println();
	    		}
		    	if (!tempObj.getInputsMap().isEmpty()) {
		    		writer.print("IC ");
			    	for (int e = 0; e < tempObj.getInputsMap().size(); e++) {
			    		if (e == tempObj.getInputsMap().size() - 1)
			    			writer.print(tempObj.getInputsMap().getObject(e).getID() + " " + tempObj.getInputsMap().getValue(e));
			    		else
			    			writer.print(tempObj.getInputsMap().getObject(e).getID() + " " + tempObj.getInputsMap().getValue(e) + " ");
			    	}
			    	writer.println();
		    	}
		    	if (!tempObj.getOutputsMap().isEmpty()) {
		    		writer.print("OC ");
			    	for (int e = 0; e < tempObj.getOutputsMap().size(); e++) {		    		
			    		if (e == tempObj.getOutputsMap().size() - 1)
			    			writer.print(tempObj.getOutputsMap().getObject(e).getID() + " " + tempObj.getOutputsMap().getValue(e));
			    		else
			    			writer.print(tempObj.getOutputsMap().getObject(e).getID() + " " + tempObj.getOutputsMap().getValue(e) + " ");
			    	}
			    	writer.println();
		    	}
		    	if (!tempObj.getConnections().isEmpty()) {
		    		writer.print("CM ");
		    		for (int e = 0; e < tempObj.getConnections().size(); e++) {
		    			if (e == tempObj.getConnections().size() - 1)
		    				writer.print(tempObj.getConnections().getObject(e).getID() + " " + tempObj.getConnections().getValue(e));
		    			else
		    				writer.print(tempObj.getConnections().getObject(e).getID() + " " + tempObj.getConnections().getValue(e) + " ");
		    		}
		    		writer.println();
		    	}
			}
		    writer.print("EN");
		    writer.close();
		} catch (Exception e) { }
		manager.displayConsole();
		manager.getConsole().writeOnConsole("Design \'" + fileName + "\' Saved", Color.ORANGE);
	}
}