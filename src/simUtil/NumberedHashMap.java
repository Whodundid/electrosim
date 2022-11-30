package simUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

public class NumberedHashMap<type1, type2> {
	
	ArrayList<Map<Object, Object>> createdList = new ArrayList<Map<Object, Object>>();
	
	public int size() {
		return this.createdList.size();
	}
	
	public void put(type1 obj, type2 value) {
		Map<Object, Object> ObjValList = new HashMap<Object, Object>();
		ObjValList.put(obj, value);
		createdList.add(ObjValList);
	}
	
	public void remove(int pointNumber) {
		this.createdList.remove(pointNumber);
	}
	
	public void removeMapsContainingObj(Object obj) {
		for (int i = 0; i < this.size(); i++) {
			Map<Object, Object> tempMap = createdList.get(i);
			Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
			if (entry.getKey() == obj)
				this.remove(i);
		}
	}
	
	public void removeMapWithSaidValues(Object obj, Object point) {
		for (int i = 0; i < this.size(); i++) {
			Map<Object, Object> tempMap = createdList.get(i);
			Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
			if (entry.getKey() == obj && entry.getValue() == point)
				this.remove(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	public type2 getValue(int pointNumber) {
		Map<Object, Object> tempMap = createdList.get(pointNumber);
		Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
		return (type2) entry.getValue();
	}
	
	@SuppressWarnings("unchecked")
	public type1 getObject(int pointNumber) {
		Map<Object, Object> tempMap = createdList.get(pointNumber);
		Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
		return (type1) entry.getKey();
	}
	
	public boolean containsObject(Object obj) {
		Map<Object, Object> tempMap;
		for (int i = 0; i < this.size(); i++) {
			tempMap = createdList.get(i);
			if (tempMap.containsKey(obj))
				return true;
		}
		return false;
	}
	
	public boolean containsObjValue(Object obj, Object value) {
		Map<Object, Object> tempMap;
		for (int i = 0; i < this.size(); i++) {
			tempMap = createdList.get(i);
			if (tempMap.containsKey(obj))
				if (tempMap.containsValue(value))
				return true;
		}
		return false;
	}
	
	public boolean isEmpty() {
		if (this.createdList.isEmpty())
			return true;
		return false;
	}
	
	public void clear() {
		this.createdList.clear();
	}
	
	public void printOutContents() {
		for (int i = 0; i < createdList.size(); i++) {
			System.out.println("[" + this.getObject(i) + " " + this.getValue(i) + "]");
		}
	}
}