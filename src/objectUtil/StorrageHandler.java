package objectUtil;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import placeable.SimObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Last edited: 3-4-17
//Author: Hunter Troy Bragg

public class StorrageHandler {
	
	ConcurrentHashMap<String, SimObject> storrage = new ConcurrentHashMap<String, SimObject>();
	
	public void store(String ID, SimObject obj) {
		if (storrage.containsKey(ID))
			System.out.println("Error: cannot contain duplicate objects.");
		else
			this.storrage.put(ID, obj);
	}
	
	public SimObject retrieve(String ID) {
		if (storrage.containsKey(ID))
			return storrage.get(ID);
		else
			return null;
	}
	
	public void replace(String original, SimObject newObj) {
		if (this.storrage.containsKey(original)) {
			this.storrage.remove(original).getID();
			this.storrage.put(newObj.getID(), newObj);
		}
	}
	
	public Map<String, SimObject> getStorrage() {
		return this.storrage;
	}
	
	public List<SimObject> retrieveAll() {
		List<SimObject> createdList = new ArrayList<SimObject>();
		for (SimObject tempObj : storrage.values()) {
			createdList.add(tempObj);
		}
		return createdList;
	}
	
	public String getNextAvailibleEntry(String name) {
		String originalName = name;
		int count = 0;
		String tempName = name += count;
		StringBuilder changer = new StringBuilder(tempName);
		for (int i = 0; i < storrage.size(); i++) {
			if (storrage.containsKey(tempName)) {
				for (int e = 0; e < changer.length(); e++) {
					if (Character.isDigit(changer.charAt(e))) {
						changer.deleteCharAt(e);
					}
				}
				count += 1;
			}
			tempName = changer.toString();
			tempName += count;
		}
		String nextName = originalName += count;
		return nextName;
	}
	
	public void remove(String ID) {
		if (storrage.containsKey(ID)) {
			storrage.remove(ID);
		}
		else
			System.out.println("Error: that object does not exist");
	}
	
	public void removeObjectReference(String ID) {
		SimObject refObj = this.retrieve(ID);
		for (Entry<String, SimObject> e : storrage.entrySet()) {
			if (e.getValue().getOutputsMap().containsObject(refObj))
				e.getValue().getOutputsMap().removeMapsContainingObj(refObj);
			if (e.getValue().getInputsMap().containsObject(refObj))
				e.getValue().getInputsMap().removeMapsContainingObj(refObj);
		}
	}
	
	public boolean contains(String ID) {
		if (storrage.containsKey(ID))
			return true;
		else
			return false;
	}
	
	public int size() {
		return storrage.size();
	}
	
	public boolean isEmpty() {
		return storrage.isEmpty();
	}
	
	public void removeAll() {
		storrage.clear();
	}
}