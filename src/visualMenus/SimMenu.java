package visualMenus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import objectUtil.ObjectManager;
//import simUtil.SimConsole;
import visuals.DesignPanel;
import visuals.VisualGUI;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class SimMenu extends JMenu {
	
	VisualGUI refGUI;
	JMenuItem propertiesMenuBtn = new JMenuItem("Properties");
	JMenuItem simConsoleMenuBtn = new JMenuItem("Open main simulator console");
	JMenuItem designConsoleMenuBtn = new JMenuItem("Open current work space's console");
	
	public SimMenu(VisualGUI gui) {
		this.refGUI = gui;
		this.createAssets();
		this.setText("Simulator");
		this.add(propertiesMenuBtn);
		this.add(simConsoleMenuBtn);
		this.add(designConsoleMenuBtn);
	}

	private void createAssets() {
		propertiesMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			}
		});
		
		simConsoleMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//SimConsole con = new SimConsole("ElectroSim");
			}
		});
		
		designConsoleMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getTabCount() > 0) {
					DesignPanel panel = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager man = panel.getObjectManger();
					man.displayConsole();
				}
			}
		});
	}
}
