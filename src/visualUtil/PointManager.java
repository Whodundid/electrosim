package visualUtil;

import java.awt.Component;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import placeable.SimObject;
import simUtil.NumberedHashMap;
import visuals.DesignPanel;

//Last edited: 3-9-17
//Author: Hunter Troy Bragg

public class PointManager {
	
	SimObjectVisual base;
	DesignPanel panel;
	SimObject obj;
	int numInputs;
	int numOutputs;
	int pointValue;
	int totalPoints;
	NumberedHashMap<ObjectPoint, Integer> inputPoints = new NumberedHashMap<ObjectPoint, Integer> ();
	NumberedHashMap<ObjectPoint, Integer>  outputPoints = new NumberedHashMap<ObjectPoint, Integer> ();
	boolean isEvenInputs = false;
	boolean isEvenOutputs = false;
	boolean isSelected = false;
	ImageIcon img;
	int startingPosIn, currentPosIn, startingPosOut, currentPosOut;
	
	public PointManager(SimObjectVisual Base, DesignPanel Panel) {
		this.base = Base;
		this.panel = Panel;
		this.obj = panel.getObjectManger().getStorrageHandler().retrieve((Base.getName()));
	}
	
	public void createPoints() {
		this.numInputs = obj.getInputs().size();
		this.numOutputs = obj.getOutputs().size();
		this.totalPoints = 0;
		if (this.inputPoints.isEmpty() && this.outputPoints.isEmpty()) {
			for (int i = 0; i < numInputs; i++) {
				ObjectPoint inputPoint = new ObjectPoint("objPoints/single_null_Point.png", obj);
				inputPoint.pointNumberIO = i;
				inputPoint.pointNumber = totalPoints;
				if (obj.getPointValue(i) == 1)
					inputPoint.setOn();
				if (isSelected)
					inputPoint.select();
				inputPoints.put(inputPoint, 0);
				totalPoints++;
			}
			for (int i = 0; i < numOutputs; i++) {
				ObjectPoint outputPoint = new ObjectPoint("objPoints/single_null_Point.png", obj);
				outputPoint.pointNumberIO = numInputs + i;
				outputPoint.pointNumber = totalPoints;
				if (obj.getOutputValueBoolean())
					outputPoint.setOn();
				if (isSelected)
					outputPoint.select();
				outputPoints.put(outputPoint, 0);
				totalPoints++;
			}
		}
		else {
			this.inputPoints.clear();
			this.outputPoints.clear();
			createPoints();
		}
	}
	
	public void selectPoints() {
		this.isSelected = true;
		for (int i = 0; i < inputPoints.size(); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			point.select();
			point.updateImage();
		}
		for (int i = 0; i < outputPoints.size(); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			point.select();
			point.updateImage();
		}
		panel.repaint();
	}
	
	public void deselectPoints() {
		this.isSelected = false;
		for (int i = 0; i < inputPoints.size(); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			point.deselect();
			point.updateImage();
		}
		for (int i = 0; i < outputPoints.size(); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			point.deselect();
			point.updateImage();
		}
		panel.repaint();
	}
	
	public void drawPoints() {
		isEvenInputs = false;
		isEvenOutputs = false;
		if (numInputs % 2 == 0)
			isEvenInputs = true;
		if (numOutputs % 2 == 0)
			isEvenOutputs = true;
		if (numInputs == 1)
			startingPosIn = (int) ((base.getLocation().getY() + base.getHeight()/2 - 3));
		if (numOutputs == 1)
			startingPosOut = (int) ((base.getLocation().getY() + base.getHeight()/2 - 3));
		if (numInputs == 2)
			startingPosIn = (int) ((base.getLocation().getY() + 3 + numInputs * 7));
		if (numOutputs == 2) 
			startingPosOut = (int) ((base.getLocation().getY() + 3 + numOutputs * 7));
		if (numInputs == 3)
			startingPosIn = (int) ((base.getLocation().getY() + 7 + (numInputs * 7)/2));
		if (numOutputs == 3)
			startingPosOut = (int) ((base.getLocation().getY() + 7 + (numOutputs * 7)/2));
		if (numInputs > 3) {
			startingPosIn = (int) ((base.getLocation().getY() + 3 + (numInputs * 7)/2));
			if (isEvenInputs)
				startingPosIn += 7;
			else
				startingPosIn += 4;
		} if (numOutputs > 3) {
			startingPosOut = (int) ((base.getLocation().getY() + 3 + (numOutputs * 7)/2));
			if (isEvenOutputs)
				startingPosOut += 7;
			else
				startingPosOut += 4;
		}
		
		currentPosIn = startingPosIn;
		currentPosOut = startingPosOut;
		
		drawInputs();
		drawOutputs();
		updatePoints();
		panel.repaint();
	}
	
	public void drawInputs() {
		currentPosIn = startingPosIn;
		for (int i = 0; i < inputPoints.size(); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			if (isEvenInputs) {
				if (i == numInputs/2 && inputPoints.size() > 3) {
					currentPosIn -= 7;
					point.setLoc(base.getLocation().x - (int)point.imgX + 2, currentPosIn);
				}
			}
			if (inputPoints.size() == 1) {
				point.setLoc(base.getLocation().x - (int)point.imgX + 2, base.getLocation().y + 10);
			} else if (inputPoints.size() == 2) {
				point.setLoc(base.getLocation().x - (int)point.imgX + 2, currentPosIn);
				currentPosIn -= 14;
			} else if (inputPoints.size() == 3) {
				point.setLoc(base.getLocation().x - (int)point.imgX + 2, currentPosIn);
				currentPosIn -= 7;
			} else {
				point.setLoc(base.getLocation().x - (int)point.imgX + 2, currentPosIn);
				currentPosIn -= 7;
			}
			point.setRotation(180);
			panel.add(point);
		}
	}
	
	public void drawOutputs() {
		currentPosOut = startingPosOut;
		for (int i = 0; i < outputPoints.size(); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			if (isEvenOutputs) {
				if (i == numOutputs/2 && outputPoints.size() > 3) {
					currentPosOut -= 7;
					point.setLoc(base.getLocation().x + base.getIcon().getIconWidth() - 2, currentPosOut);
				}
			}
			if (outputPoints.size() == 1) {
				point.setLoc(base.getLocation().x + base.getIcon().getIconWidth() - 2, base.getLocation().y + 10);
			} else if (outputPoints.size() == 2) {
				point.setLoc(base.getLocation().x + base.getIcon().getIconWidth() - 2, currentPosOut);
				currentPosOut -= 14;
			} else if (outputPoints.size() == 3) {
				point.setLoc(base.getLocation().x + base.getIcon().getIconWidth() - 2, currentPosOut);
				currentPosOut -= 7;
			}
			else {
				point.setLoc(base.getLocation().x + base.getIcon().getIconWidth() - 2, currentPosOut);
				currentPosOut -= 7;
			}
			point.setRotation(0);
			panel.add(point);
		}
	}
	
	public void updateLocation(int x, int y) {
		for (int i = 0; i < (numInputs); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			int newX = point.getX() + x;
			int newY = point.getY() + y;
			point.setLoc(newX, newY);
		}
		for (int i = 0; i < (numOutputs); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			int newX = point.getX() + x;
			int newY = point.getY() + y;
			point.setLocation(newX, newY);
		}
	}
	
	public void updatePoints() {
		for (int i = 0; i < (numInputs); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			if (obj.getPointValue(i) == 1)
				point.setOn();
			else
				point.setOff();
			point.updateImage();
		}
		for (int i = 0; i < (numOutputs); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			if (obj.getPointValue(i + numInputs) == 1)
				point.setOn();
			else
				point.setOff();
			point.updateImage();
		}
	}
	
	public void alignAllPoints(Point oldPoint, Point newPoint) {
		int x, y;
		for (int i = 0; i < (numInputs); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			x = (int) (newPoint.getX() - oldPoint.getX());
			y = (int) (newPoint.getY() - oldPoint.getY());
			point.setLocation(point.getX() + x, point.getY() + y);
			panel.add(point);
		}
		for (int i = 0; i < (numOutputs); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			x = (int) (newPoint.getX() - oldPoint.getX());
			y = (int) (newPoint.getY() - oldPoint.getY());
			point.setLocation(point.getX() + x, point.getY() + y);
			panel.add(point);
		}
		panel.repaint();
	}
	
	public boolean isPoint(JLabel obj) {
		if (inputPoints.containsObject(obj) || outputPoints.containsObject(obj))
			return true;
		return false;
	}
	
	public boolean isPointOutput(JLabel obj) {
		if (outputPoints.containsObject(obj)) {
			return true;
		}
		return false;
	}
	
	public Component getBaseObject(JLabel obj) {
		return this.base;
	}
	
	public void destroy() {
		clear();
		this.base = null;
		this.img = null;
		this.obj = null;
		this.panel = null;
	}
	
	public void clear() {
		for (int i = 0; i < (numInputs); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			panel.remove(point);
		}
		for (int i = 0; i < (numOutputs); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			panel.remove(point);
		}
		this.inputPoints.clear();
		this.outputPoints.clear();
		panel.repaint();
	}
	
	public void removeInputPoints() {
		for (int i = 0; i < (numInputs); i++) {
			ObjectPoint point = inputPoints.getObject(i);
			panel.remove(point);
		}
		this.inputPoints.clear();
		panel.repaint();
	}
	
	public void removeOutputPoints() {
		for (int i = 0; i < (numOutputs); i++) {
			ObjectPoint point = outputPoints.getObject(i);
			panel.remove(point);
		}
		this.inputPoints.clear();
		panel.repaint();
	}
	
	public void updateLever() {
		for (int i = 0; i < numOutputs; i++) {
			if (!obj.getOutputValueBoolean()) {
				if (base.isSelected)
					img = new ImageIcon("objResources/null_Lever_Sel.png");
				else
					img = new ImageIcon("objResources/null_Lever.png");
				base.setIcon(img);
				base.setBounds(base.getLocation().x, base.getLocation().y, img.getIconWidth(), img.getIconHeight());
			} else if (obj.getOutputValueBoolean()) {
				if (base.isSelected)
					img = new ImageIcon("objResources/on_Lever_Sel.png");
				else
					img = new ImageIcon("objResources/on_Lever.png");				
				base.setIcon(img);
				base.setBounds(base.getLocation().x, base.getLocation().y, img.getIconWidth(), img.getIconHeight());
			}
		}
	}
	
	public void updateDOut() {
		for (int i = 0; i < numInputs; i++) {
			boolean pointValue = obj.getOutputValueBoolean();
			if (!pointValue) {
				if (base.isSelected)
					img = new ImageIcon("objResources/null_DOut_Sel.png");
				else
					img = new ImageIcon("objResources/null_DOut.png");
				base.setIcon(img);
				base.setBounds(base.getLocation().x, base.getLocation().y, img.getIconWidth(), img.getIconHeight());
				panel.add(base);
			} else if (pointValue) {
				if (base.isSelected)
					img = new ImageIcon("objResources/on_DOut_Sel.png");
				else
					img = new ImageIcon("objResources/on_DOut.png");
				base.setIcon(img);
				base.setBounds(base.getLocation().x, base.getLocation().y, img.getIconWidth(), img.getIconHeight());
				panel.add(base);
			}
		}
	}
}