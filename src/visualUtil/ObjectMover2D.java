package visualUtil;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import objectUtil.ObjectManager;
import placeable.digital.Lever;
import placeable.SimObject;
import placeable.Wire;
import visuals.DesignPanel;

//Last edited: 3-11-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

public class ObjectMover2D extends MouseAdapter {
	
	private int btn;	
	SimObjectVisual selObj;
	DesignPanel panel;
	ObjectManager Manager;
	SimObject corresObj;
	String objType, gateType;
	ImageIcon selIcon;
	final int gridScaleX = 7, gridScaleY = 7;
	double totalX, totalY;
	private boolean oneClick;
	
	public ObjectMover2D(SimObjectVisual c, DesignPanel designPanel, ObjectManager manager) {
		selObj = c;
		panel = designPanel;
		Manager = manager;
		corresObj = manager.getObject(c.getName());
	}
	
	public void killMover() {
		this.selObj = null;
		this.panel = null;
		this.Manager = null;
		this.corresObj = null;
		this.selIcon = null;
	}
	
	public void replaceObj(SimObjectVisual obj) {
		this.selObj = obj;
	}
	
	public void setSimObject(SimObject obj) {
		this.corresObj = obj;
	}
	
	public ObjectMover2D getObjectMover2D() {
		return this;
	}
	
	public void updateObjLocation(Component obj) {
		Point oldPoint = obj.getLocation();
		alignToGrid(obj);
		obj.setLocation((int) totalX, (int) totalY);
		if (!(Manager.getObject(obj.getName()) instanceof Wire))
			Manager.getObject(obj.getName()).getPointManager().alignAllPoints(oldPoint, obj.getLocation());
		Manager.setObjLocation(obj.getName(), new Point((int) totalX, (int) totalY));
		panel.add(obj);
		panel.setComponentZOrder(obj, 0);
	}
	
	public void updateObjLocation(Component obj, int x, int y) {
		int newX = obj.getX() + x;
		int newY = obj.getY() + y;
		if (!(Manager.getObject(obj.getName()) instanceof Wire))
			Manager.getObject(obj.getName()).getPointManager().updateLocation(x, y);
		obj.setLocation(newX, newY);
		Manager.setObjLocation(obj.getName(), new Point(x, y));
	}
	
	public PointManager getPointManager(Component obj) {
		if (!(Manager.getObject(obj.getName()) instanceof Wire))
			return Manager.getObject(obj.getName()).getPointManager();
		return null;
	}
	
	public void selectObject() {
		selObj.select();
		panel.repaint();
	}
	
	public void alignToGrid(Component sel) {
		Point preEndPoint = sel.getLocation();
		double resultX = preEndPoint.getX()/gridScaleX;
		double resultY = preEndPoint.getY()/gridScaleY;
		double isoFracX = resultX % 1;
		double isoFracY = resultY % 1;
		double isoIntX = resultX - isoFracX;
		double isoIntY = resultY - isoFracY;
		if (isoFracX >= .5)
			totalX = gridScaleX * (isoIntX + 1) - 3;
		else
			totalX = gridScaleX * isoIntX - 3;
		if (isoFracY >= .5)
			totalY = gridScaleY * (isoIntY + 1);
		else
			totalY = gridScaleY * isoIntY;
		if (totalX < 0)
			totalX = 0;
		if (totalY < 0)
			totalY = 0;
		if (totalX > panel.getWidth()- sel.getWidth())
			totalX = panel.getWidth() - sel.getWidth();
		if (totalY > panel.getHeight() - sel.getHeight())
			totalY = panel.getHeight() - sel.getHeight();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		btn = e.getButton();
		if (selObj instanceof SimObjectVisual && btn == 1) {
			if (Manager.getObject(selObj.getName()) instanceof Lever) {
				PointManager pointMan = Manager.getObject(selObj.getName()).getPointManager();
				Manager.toggleOutputValue(selObj.getName());
				pointMan.updateLever();
				pointMan.updatePoints();
				panel.repaint();
			} else {
				if (oneClick) {
					selObj.displayProperties();
			        oneClick = false;
				} else {
					oneClick = true;
					Timer t = new Timer("doubleclickTimer", false);
					t.schedule(new TimerTask() {
						public void run() {
							oneClick = false;
						}
					}, 500);
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		btn = e.getButton();
		if (selObj instanceof SimObjectVisual) {
			List<SimObjectVisual> panList = panel.selMulti;
			if (btn == 1) {
				if (panList.isEmpty()) {
					panList.add(selObj);
					selectObject();
				} else if (!panList.contains(selObj) && panList.size() == 1) {
					panel.deselectObjects();
					panel.selMulti.clear();
					panel.selMulti.add(selObj);
					selectObject();
					if (!(Manager.getObject(selObj.getName()) instanceof Wire)) {
						PointManager pointMan = getPointManager(selObj);
						pointMan.selectPoints();
					}
				}
			} else if (btn == 3) {
				if (panel.selMulti.isEmpty() || !panel.selMulti.contains(selObj)) {
					PartRightClickMenu menu = new PartRightClickMenu(selObj, Manager);
					menu.show(e.getComponent(), e.getX(), e.getY());
					selObj.getParent().repaint();
				} else if (panel.selMulti.size() == 1 && panel.selMulti.contains(selObj)) {
					PartRightClickMenu menu = new PartRightClickMenu(selObj, Manager);
					menu.show(e.getComponent(), e.getX(), e.getY());
					selObj.getParent().repaint();
				} else if (panel.selMulti.size() > 1) {
					PartRightClickMenuMultiple menu = new PartRightClickMenuMultiple(panel.selMulti, Manager);
					menu.show(e.getComponent(), e.getX(), e.getY());
					selObj.getParent().repaint();
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (selObj instanceof SimObjectVisual && btn == 1) {
			if (panel.selMulti.isEmpty())
				updateObjLocation(selObj);
			else if (panel.selMulti.contains(selObj))
				for (int i = 0; i < panel.selMulti.size(); i++) {
					Component sel = panel.selMulti.get(i);
					updateObjLocation(sel);
				}
			else if (panel.selMulti.size() > 1)
				updateObjLocation(selObj);
		}				
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (selObj instanceof SimObjectVisual && btn == 1) {
			final Point newPoint = selObj.getParent().getMousePosition();
			if (newPoint != null) {
				int x = (newPoint.x - (selObj.getX() + selObj.getWidth()/2));
				int y = (newPoint.y - (selObj.getY() + selObj.getHeight()/2));
				if (panel.selMulti.contains(selObj))
					for (int i = 0; i < panel.selMulti.size(); i++) {
						Component sel = panel.selMulti.get(i);
						updateObjLocation(sel, x, y);
					}
				else
					updateObjLocation(selObj, x, y);
				panel.repaint();
			}
		}
	}
}