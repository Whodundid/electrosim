package visuals;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import objectUtil.ObjectManager;
import placeable.SimObject;
import simUtil.NumberedHashMap;
import visualUtil.DesignPanelRCM;
import visualUtil.ObjectPoint;
import visualUtil.PointManager;
import visualUtil.Selection;
import visualUtil.SimObjectVisual;

//Last edited: 3-16-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class DesignPanel extends JPanel {

	Component selObj;
	public JFrame mainFrame;
	public List<SimObjectVisual> selMulti = new ArrayList<SimObjectVisual>();
	Selection selectionBox = new Selection();
	ObjectManager Manager;
	SimObject corresObj;
	String ObjectType, GateType;
	ImageIcon selIcon;
	BufferedImage backgroundGrid;
	PointManager pointMan;
	JLabel titleArea;
	String filePath;
	public NumberedHashMap<SimObject, Integer> srcPoint = new NumberedHashMap<SimObject, Integer>();
	public ObjectPoint point;

	public DesignPanel(ObjectManager manager, JFrame frame) {
		this.mainFrame = frame;
		this.Manager = manager;
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(227, 227, 227), 1, true));
		setLayout(null);
		Manager.setDesignPanel(this);
		try {
			backgroundGrid = ImageIO.read(new File("simResources/grid.png"));
		} catch (IOException e) {}
		
		final MouseAdapter selector = new MouseAdapter() {
			private Point clickPoint;
			private Point endPoint;
			private int btn;
			
			@Override
			public void mousePressed(MouseEvent e) {
				btn = e.getButton();
				selObj = findComponentAt(getMousePosition());
				if (selObj instanceof JPanel && btn == 1) {
					clickPoint = getMousePosition();
					endPoint = null;
					add(selectionBox);
					selectionBox.setBackground(Color.WHITE);
					selectionBox.setSize(0,0);
					clearSrcPoint();
				} else if (selObj instanceof JPanel && btn == 3 && selMulti.size() >= 1) {
					deselectObjects();
					selMulti.clear();
					clearSrcPoint();
					DesignPanelRCM menu = new DesignPanelRCM(Manager);
					menu.show(e.getComponent(), e.getX(), e.getY());
				} else {
					DesignPanelRCM menu = new DesignPanelRCM(Manager);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				btn = e.getButton();
				if (selObj instanceof JPanel && btn == 1) {
					deselectObjects();
					selMulti.clear();
					endPoint = getMousePosition();
					int addedComponents = 0;
					for (int i = 0; i < getComponentCount(); i ++) {
						if (getComponent(i) instanceof SimObjectVisual && endPoint != null && clickPoint != null) {
							if (getComponent(i).getName() != null) {
								Point objPos = getComponent(i).getLocation();
								
								int objPosX1 = objPos.x;
								int objPosY1 = objPos.y;
								int objPosX2 = getComponent(i).getWidth() + objPos.x; 
								int objPosY2 = getComponent(i).getHeight() + objPos.y;
								
								int selX1, selY1, selX2, selY2;
								
								if (endPoint.y < clickPoint.y) { selY2 = clickPoint.y; selY1 = endPoint.y; }
								else { selY2 = endPoint.y; selY1 = clickPoint.y; }
								if (endPoint.x < clickPoint.x) { selX2 = clickPoint.x; selX1 = endPoint.x; }								
								else { selX2 = endPoint.x; selX1 = clickPoint.x; }
								
								if ((objPosX1 >= selX1 && objPosY1 >= selY1 && objPosX2 <= selX2 && objPosY2 <= selY2)) {
									selMulti.add((SimObjectVisual)getComponent(i));
									addedComponents++;
								}
							}
						}
					}
					if (addedComponents > 0)						
						selectComponents();
					endPoint = null;
					clickPoint = null;
					selectionBox.setBounds(0,0,0,0);
					remove(selectionBox);
					repaint();
				}				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (selObj instanceof JPanel && btn == 1) {
					endPoint = e.getPoint();
					if (endPoint == null || clickPoint == null) {
						System.out.println(clickPoint);
						System.out.println(endPoint);
						System.out.println("is somehow null");
					} else {
						int width = endPoint.x - clickPoint.x;
						int height = endPoint.y - clickPoint.y;
						int x = clickPoint.x;
						int y = clickPoint.y;
						if (width < 0) {
							x = endPoint.x;
							width *= -1;
						}
						if (height < 0) {
							y = endPoint.y;
							height *= -1;
						}
						selectionBox.setBounds(x, y, width, height);
						selectionBox.revalidate();
						repaint();
					}
				}
			}
		};
		addMouseListener(selector);
		addMouseMotionListener(selector);
	}
	
	public void selectComponents() {
		for (int i = 0; i < selMulti.size(); i++) {
			selMulti.get(i).select();
		}
		this.repaint();
	}
	
	public void deselectObjects() {
		for (int i = 0; i < selMulti.size(); i++) {
			selMulti.get(i).deselect();
		}
		this.selMulti.clear();
		this.repaint();
	}
	
	public void setTitleArea(JLabel title) {
		this.titleArea = title;
	}
	
	public JLabel getTitleArea() {
		return this.titleArea;
	}
	
	public void setFilePath(String path) {
		this.filePath = path;
	}
	
	public String getFilePath() {
		if (this.filePath != null)
			return this.filePath;
		else
			return null;
	}
	
	public boolean hasFilePath() {
		if (this.filePath == null)
			return false;
		else return true;
	}
	
	public ObjectManager getObjectManger() {
		return this.Manager;
	}
	
	public List<SimObjectVisual> getSelectedComponents() {
		return this.selMulti;
	}
	
	public void setSrcPoint(SimObject obj, int point) {
		if (!this.srcPoint.isEmpty())
			srcPoint.clear();
		this.srcPoint.put(obj, point);
	}
	
	public void clearSrcPoint() {
		if (!srcPoint.isEmpty()) {
			point.wasClicked = false;
			point.isMouseOver = false;
			point.updateImage();
			this.point = null;
			srcPoint.clear();
		}
	}
	
	public NumberedHashMap<SimObject, Integer> getSrcPoint() {
		return this.srcPoint;
	}
	
	public void replaceObjInList(SimObjectVisual origObj, SimObjectVisual obj) {
		if (selMulti.contains(origObj)) {
			for (int i = 0; i < selMulti.size(); i++) {
				selMulti.remove(origObj);
				selMulti.add(obj);
			}
		}
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}
	
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    if (backgroundGrid != null)
	        g.drawImage(backgroundGrid,2,2,this);
	}
}