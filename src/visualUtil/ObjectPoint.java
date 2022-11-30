package visualUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import objectUtil.ObjectManager;
import placeable.SimObject;
import simUtil.DecimalRounder;
import simUtil.DoublePoint;
import visuals.DesignPanel;

//Last edited: 3-9-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class ObjectPoint extends JLabel{
	
	public boolean isSelected;
	public boolean isOn;
	public boolean isMouseOver;
	private DoublePoint loc;
	public double rotation = 0;
	String strRotation = "north";
	DoublePoint center;
	double radius;
	double imgX, imgY;
	DesignPanel panel;
	SimObject corresObj;
	public int pointNumberIO, pointNumber;
	public boolean wasClicked = false;
	ObjectPoint thisObject;
	ObjectManager manager;
	
	public ObjectPoint() {
		this("objPoints/single_null_Point.png", null);
	}
	
	public ObjectPoint(String pic, SimObject obj) {
		this.corresObj = obj;
		this.panel = corresObj.getDesignPanel();
		this.thisObject = this;
		this.manager = obj.getObjectManager();
		RotatableImage image = new RotatableImage(pic, "north");
		this.setIcon(image);
		this.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		this.loc = new DoublePoint(0, 0);
		imgX = image.getIconWidth();
		imgY = image.getIconHeight();
		this.center = new DoublePoint(imgX/2, imgY/2);
		radius = Math.sqrt(Math.pow(imgX/2, 2) + Math.pow(imgY/2, 2));
		addAdapter();
	}
	
	public boolean isSelected() { return isSelected; }
	
	public void select() { this.isSelected = true; }
	
	public void deselect() { this.isSelected = false; }
	
	public void setOn() { this.isOn = true; }
	
	public void setOff() { this.isOn = false; }
	
	public DoublePoint getLoc() { return this.loc; }
	
	public void setLoc(int x, int y) { 
		this.loc.move(x, y);
		this.setLocation(x, y);
		center.x = Math.abs(((loc.getX() + imgX) - loc.getX())/2 + loc.getX());
		center.y = Math.abs(((loc.getY() + imgY) - loc.getY())/2 + loc.getY());
	}
	
	public double getRotation() { return rotation; }
	
	public void setRotation(double rotate) {
		rotation = (rotate % 360);
		this.rotation = rotate;
		if (rotation < 0)
			rotation = 360 + rotation;
		updateBounds(rotation);
		setRotationDirection(rotation);
		updateImage();
	}
	
	public void addRotation(double rotate) {
		double preRotate = rotation;
		rotation = (rotate % 360);
		rotation = ((rotation + preRotate) % 360) + 0;
		if (rotation < 0)
			rotation = 360 + rotation;
		updateBounds(rotation);
		setRotationDirection(rotation);
		updateImage();
	}
	
	public void setRotationDirection(double rotation) {
		if (rotation >= 45 && rotation <= 135)
			this.strRotation = "south";
		else if ((rotation < 45 && rotation >= 0) || rotation > 315)
			this.strRotation = "west";
		else if (rotation > 135 && rotation < 225)
			this.strRotation = "east";
		else
			this.strRotation = "north";
		//System.out.println(strRotation);
	}
	
	public void addAdapter() {
		MouseAdapter mouse = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!wasClicked) {
					isMouseOver = true;
					updateImage();
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				int btn = e.getButton();
				if (btn == 1) {
					if (panel.srcPoint.isEmpty()) {
						panel.setSrcPoint(corresObj, pointNumberIO);
						panel.point = thisObject;
						wasClicked = true;
					} else {
						SimObject srcObj = panel.getSrcPoint().getObject(0);
						if (corresObj.getPointManager().isPointOutput(thisObject)) {
							if (srcObj == corresObj) {
								if (pointNumber != panel.getSrcPoint().getValue(0))
									manager.linkObjPoint(corresObj.getID(), pointNumberIO, srcObj.getID(), panel.getSrcPoint().getValue(0));
							} else
								manager.linkObjPoint(corresObj.getID(), pointNumberIO, srcObj.getID(), panel.getSrcPoint().getValue(0));
						} else {
							if (srcObj == corresObj) {
								if (pointNumber != panel.getSrcPoint().getValue(0))
									manager.linkObjPoint(srcObj.getID(), panel.getSrcPoint().getValue(0), corresObj.getID(), pointNumberIO);
							} else
								manager.linkObjPoint(srcObj.getID(), panel.getSrcPoint().getValue(0), corresObj.getID(), pointNumberIO);
						}
						panel.clearSrcPoint();
						wasClicked = false;
					}
				} else if (btn == 3)
					System.out.println(pointNumberIO);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (!wasClicked) {
					isMouseOver = false;
					updateImage();
				}
			}
		};
		this.addMouseListener(mouse);
	}	
	
	public void updateBounds(double angle) {
		if (angle == 0 || angle == 180) {
			DoublePoint points1_1 = rotatePoint(this.loc.getX(), this.loc.getY(), angle);
			DoublePoint points1_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY() + imgY, angle);
			DoublePoint newLoc = DoublePoint.getSmaller(points1_1, points1_2);
			this.setBounds(newLoc.ceilX(), newLoc.ceilY(), (int)imgX, (int)imgY);
		} else if (angle == 90 || angle == 270) {
			DoublePoint points2_1 = rotatePoint(this.loc.getX(), this.loc.getY() + imgY, angle);
			DoublePoint points2_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY(), angle);
			DoublePoint newLoc = DoublePoint.getSmaller(points2_1, points2_2);
			this.setBounds(newLoc.ceilX(), newLoc.ceilY(), (int)imgY, (int)imgX);
		} else if ((angle > 0 && angle < 90) || (angle > 180 && angle < 270)) {
			DoublePoint points1_1 = rotatePoint(this.loc.getX(), this.loc.getY(), angle);
			DoublePoint points1_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY() + imgY, angle);
			DoublePoint points2_1 = rotatePoint(this.loc.getX(), this.loc.getY() + imgY, angle);
			DoublePoint points2_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY(), angle);
			DoublePoint smallerX = DoublePoint.getSmallerX(points1_2, points1_1);
			DoublePoint biggerX = DoublePoint.getBiggerX(points1_1, points1_2);
			DoublePoint smallerY = DoublePoint.getSmallerY(points2_1, points2_2);
			DoublePoint biggerY = DoublePoint.getBiggerY(points2_1, points2_2);
			/*System.out.println(points1_1.getX() + " " + points1_1.getY());
			System.out.println(points1_2.getX() + " " + points1_2.getY());
			System.out.println(points2_1.getX() + " " + points2_1.getY());
			System.out.println(points2_2.getX() + " " + points2_2.getY());
			System.out.println("==================");
			System.out.println(biggerX.getX());
			System.out.println(smallerX.getX());
			System.out.println(biggerY.getY());
			System.out.println(smallerY.getY());*/
			this.setBounds(smallerX.floorX(), smallerY.ceilY(), Math.abs(biggerX.ceilX() - smallerX.floorX()), Math.abs(smallerY.floorY() - biggerY.ceilY()));
		} else {
			DoublePoint points1_1 = rotatePoint(this.loc.getX(), this.loc.getY(), angle);
			DoublePoint points1_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY() + imgY, angle);
			DoublePoint points2_1 = rotatePoint(this.loc.getX(), this.loc.getY() + imgY, angle);
			DoublePoint points2_2 = rotatePoint(this.loc.getX() + imgX, this.loc.getY(), angle);
			DoublePoint smallerX = DoublePoint.getSmallerX(points2_1, points2_2);
			DoublePoint biggerX = DoublePoint.getBiggerX(points2_1, points2_2);
			DoublePoint smallerY = DoublePoint.getSmallerY(points1_1, points1_2);
			DoublePoint biggerY = DoublePoint.getBiggerY(points1_1, points1_2);
			/*System.out.println(points1_1.getX() + " " + points1_1.getY());
			System.out.println(points1_2.getX() + " " + points1_2.getY());
			System.out.println(points2_1.getX() + " " + points2_1.getY());
			System.out.println(points2_2.getX() + " " + points2_2.getY());
			System.out.println("==================");
			System.out.println(biggerX.getX());
			System.out.println(smallerX.getX());
			System.out.println(biggerY.getY());
			System.out.println(smallerY.getY());*/
			this.setBounds(smallerX.floorX(), smallerY.ceilY(), Math.abs(biggerX.ceilX() - smallerX.floorX()), Math.abs(smallerY.floorY() - biggerY.ceilY()));                                          
		}
	}
	
	public DoublePoint rotatePoint(double x, double y, double newAngle) {
		double rad = Math.toRadians(-newAngle);
		double c = Math.cos(rad);
		double s = Math.sin(rad);
		x -= center.getX();
		y -= center.getY();
		double newX = (x * c - y * s) + center.getX();
		double newY = (x * s + y * c) + center.getY();
		newX = DecimalRounder.roundUp(newX, 2);
		newY = DecimalRounder.roundUp(newY, 2);
		return new DoublePoint(newX, newY);
	}
	
	public void updateImage() {
		RotatableImage img;
		if (isSelected) {
			if (isMouseOver) {
				if (isOn)
					img = new RotatableImage("objPoints/single_selOn_Point_Sel.png", this.strRotation);
				else
					img = new RotatableImage("objPoints/single_sel_Point_Sel.png", this.strRotation);
			} else {
				if (isOn)
					img = new RotatableImage("objPoints/single_on_Point_Sel.png", this.strRotation);
				else {
					img = new RotatableImage("objPoints/single_null_Point_Sel.png", this.strRotation);
				}
			}			
		} else {
			if (isMouseOver) {
				if (isOn)
					img = new RotatableImage("objPoints/single_selOn_Point.png", this.strRotation);
				else
					img = new RotatableImage("objPoints/single_sel_Point.png", this.strRotation);
			} else {
				if (isOn)
					img = new RotatableImage("objPoints/single_on_Point.png", this.strRotation);
				else
					img = new RotatableImage("objPoints/single_null_Point.png", this.strRotation);
			}
		}
		this.setIcon(img);
	}
}