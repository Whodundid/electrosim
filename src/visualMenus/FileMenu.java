package visualMenus;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import objectUtil.ObjectManager;
import simUtil.DesignLoader;
import simUtil.DesignSaver;
import visuals.DesignPanel;
import visuals.VisualGUI;

//Last edited: 3-16-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class FileMenu extends JMenu {
	
	JMenu newMenu = new JMenu("New");
	JMenuItem openMenuBtn = new JMenuItem("Open");
	JMenuItem saveMenuBtn = new JMenuItem("Save");
	JMenuItem saveAsMenuBtn = new JMenuItem("Save as...");
	JMenuItem closeMenuBtn = new JMenuItem("Close");
	JMenuItem circuitMenuBtn = new JMenuItem("Circuit");
	JMenuItem subCircuitMenuBtn = new JMenuItem("Sub Circuit");
	JMenuItem exitMenuBtn = new JMenuItem("Exit");
	VisualGUI refGUI;
	
	public FileMenu(VisualGUI gui) {
		this.refGUI = gui;
		this.createAssets();
		this.setText("File");
		this.add(newMenu);
		this.add(openMenuBtn);
		this.add(saveMenuBtn);
		this.add(saveAsMenuBtn);
		this.add(closeMenuBtn);
		this.add(exitMenuBtn);
	}
	
	private void createAssets() {
		circuitMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				refGUI.createNewDesignTab(null, null);
			}
		});
		subCircuitMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//create sub circuit;
			}
		});
		newMenu.add(circuitMenuBtn);
		newMenu.add(subCircuitMenuBtn);
		
		openMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFileChooser fc = new JFileChooser();
				if (refGUI.getCurrentDirectory() != null)
					fc.setCurrentDirectory(refGUI.getCurrentDirectory());
				FileNameExtensionFilter filter = new FileNameExtensionFilter("ecs","ecs");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(refGUI.getFrame());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					refGUI.setCurrentDirectory(fc.getCurrentDirectory());
					DesignLoader loader = new DesignLoader(fc.getSelectedFile().getName(), refGUI);
					loader.load();
				}
			}
		});
		
		saveMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JTabbedPane tabPane = refGUI.getTabPane();
				DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
				if (designPane.hasFilePath())
					saverNoWindow(designPane.getFilePath());
				else
					saverWindow();
			}			
		});
		
		saveAsMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				saverWindow();
			}
		});
		
		closeMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent a) {
				int btn = a.getButton();
				if (btn == 1) {
					Component tab = refGUI.getTabPane().getSelectedComponent();
					if (tab != null) {
						DesignPanel designPane = (DesignPanel) tab;
						ObjectManager referenceMan = designPane.getObjectManger();
						referenceMan.killManager();
						referenceMan = null;
						refGUI.getTabPane().remove(designPane);
						refGUI.getTabPane().remove(tab);
						tab = null;
					}
				}
			}
		});
		
		exitMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void saverWindow() {
		JTabbedPane tabPane = refGUI.getTabPane();
		DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
		JFileChooser saver = new JFileChooser();
		if (refGUI.getCurrentDirectory() != null)
			saver.setCurrentDirectory(refGUI.getCurrentDirectory());
		saver.showSaveDialog(saver);
		try {
			DesignSaver fileSaver = new DesignSaver(designPane, saver.getSelectedFile().getPath(), false);
			designPane.getObjectManger().getConsole().setSaver(fileSaver);
			String fileName = saver.getSelectedFile().getName();
			int count = 0;
			for (int i = 0; i < fileName.length(); i++) {
				if (fileName.charAt(i) != 46)
					count++;
				else
					fileName = fileName.substring(0, count);
			}
			count = 0;
			fileSaver.setFileName(fileName);
			fileSaver.tryToSave();
			designPane.getTitleArea().setText(fileName);
			designPane.setName(fileName);
			designPane.setFilePath(saver.getSelectedFile().getPath());
		} catch (NullPointerException e) {}
		refGUI.setCurrentDirectory(saver.getCurrentDirectory());
	}
	
	public void saverNoWindow(String path) {
		JTabbedPane tabPane = refGUI.getTabPane();
		DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
		DesignSaver fileSaver = new DesignSaver(designPane, path, false);
		designPane.getObjectManger().getConsole().setSaver(fileSaver);
		String fileName = path;
		int count = 0;
		for (int i = 0; i < fileName.length(); i++) {
			if (fileName.charAt(i) != 46)
				count++;
			else
				fileName = fileName.substring(0, count);
		}
		count = 0;
		fileSaver.setFileName(fileName);
		fileSaver.tryToSave();
		designPane.setFilePath(path);
	}
}
