package placeable.digital;

import java.util.ArrayList;
import objectUtil.ObjectManager;
import placeable.ObjectTypes;
import placeable.SimObject;

//Last edited: 3-3-17
//Author: Hunter Troy Bragg

public class TruthTable extends SimObject{
	
	ObjectManager referenceManager;
	
	public TruthTable(String id, ObjectManager manager) {
		this.referenceManager = manager;
		this.setObjType(ObjectTypes.TOBJECT);
		this.setID(id);
	}

	ArrayList<SimObject> TableInputs = new ArrayList<SimObject>();
	ArrayList<SimObject> TableOutputs = new ArrayList<SimObject>();
	int spaces = 14;
	
	public void AddInput(SimObject obj) {
		this.TableInputs.add(obj);
	}
	
	public void AddOutput(SimObject obj) {
		this.TableOutputs.add(obj);
	}
	
	public void removePoint(SimObject obj) {
		if (TableInputs.contains(obj)||TableOutputs.contains(obj)) {
			TableInputs.remove(obj);
			TableOutputs.remove(obj);
		}
		else
			System.out.println("Error: object does not exist.");
	}
	
	public void clearAll() {
		this.TableInputs.clear();
		this.TableOutputs.clear();
	}

	public String generateTruthTable() {
		if (this.TableInputs.size() == 0 || this.TableOutputs.size() == 0)
			return "Truth Table Error: Table Inputs or Outputs not defined!";
		
		String tableOutput = "";
		
		for (int i = 0; i < TableInputs.size(); i++) {
			SimObject tempInputObj = TableInputs.get(i);
			tableOutput += "|" + tempInputObj.getID().toLowerCase();
		}
		tableOutput += "| == ";
		for (int i = 0; i < TableOutputs.size(); i++) {
			SimObject tempOutputObj = TableOutputs.get(i);
			tableOutput += ("|" + tempOutputObj.getID().toLowerCase());
		}
		
		tableOutput += "|\r\n";
		
		int numberOfLines = (int) Math.pow(2, TableInputs.size());
		for (int i = 0; i < numberOfLines; i++) {
			for (int j = 0; j < TableInputs.size(); j++) {
				SimObject tempInputObj = TableInputs.get(j);
				referenceManager.toggleOutputValueTo(tempInputObj.getID(), (i/(int) Math.pow(2, j))%2);
				tableOutput += "| ";
				for (int q = 0; q < tempInputObj.getID().length() + 1; q++) {
					tableOutput += " ";
				}
				tableOutput += tempInputObj.getOutputValueInt();
			}
			tableOutput += "| == ";
			for (int k = 0; k < TableOutputs.size(); k++) {
				SimObject tempOutputObj = TableOutputs.get(k);
				tableOutput += "| ";
				for (int q = 0; q < tempOutputObj.getID().length() + 1; q++) {
					tableOutput += " ";
				}
				tableOutput += tempOutputObj.getOutputValueInt();
			}
			tableOutput += "|\r\n";
		}
		tableOutput += "End TruthTable.";
		for (int i = 0; i < TableInputs.size(); i++) {
			SimObject tempObj = TableInputs.get(i);
			referenceManager.toggleOutputValueTo(tempObj.getID(), 0);
		}
		return tableOutput;		
	}
}