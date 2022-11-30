package visuals;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import objectUtil.ObjectManager;
import simBase.Start;
import simUtil.DesignLoader;
import visualMenus.DesignExplorer;
import visualMenus.PartList;
import visualMenus.SimulatorMenuBar;
import visualPartButtons.Parts;
import visualUtil.TabCloseButton;
import visualUtil.TabTitleArea;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JLabel;
import java.awt.SystemColor;

//Last edited: 3-15-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class VisualGUI extends JFrame{

	private JFrame frmMainpanel;
	private final JPanel panel = new JPanel();
	private JTabbedPane tabPane;
	public File currentDirectory;
	boolean loadingFile;

	public VisualGUI() {
		init();
	}
	
	public VisualGUI(String file) {
		if (file != null)
			this.loadingFile = true;
		init();
		if (this.loadingFile) {
			DesignLoader loader = new DesignLoader(file, this);
			loader.load();
		}
	}
	
	private void init() {
		frmMainpanel = new JFrame();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		Box verticalBox = Box.createVerticalBox();
		Box horizontalBox = Box.createHorizontalBox();
		SimulatorMenuBar simMenuBar = new SimulatorMenuBar(this);
		Parts partButtons = new Parts(this);
		PartList partList = new PartList();
		DesignExplorer designExp = new DesignExplorer();
		
		frmMainpanel.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmMainpanel.setIconImage(Toolkit.getDefaultToolkit().getImage("simResources/noimg.png"));
		frmMainpanel.setBounds(100, 100, 1444, 697);
		frmMainpanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainpanel.getContentPane().setLayout(null);
		frmMainpanel.getContentPane().add(panel);
		frmMainpanel.setJMenuBar(simMenuBar);
		frmMainpanel.setTitle(Start.version);
		frmMainpanel.setVisible(true);
		
		panel.setBackground(new Color(240, 248, 255));
		panel.setBorder(null);
		panel.setBounds(0, 0, 1428, 632);
		panel.setLayout(null);
		
		partList.setVisible(true);
		designExp.setVisible(true);
		
		horizontalBox.setBounds(4, 5, 1578, 23);		
		verticalBox.setBounds(5, 33, 193, 596);

		tabbedPane.setBounds(203, 30, 1215, 600);
		tabbedPane.setBackground(SystemColor.inactiveCaption);
		tabbedPane.requestFocus();
		
		panel.add(horizontalBox);
		panel.add(verticalBox);
		panel.add(tabbedPane);
		horizontalBox.add(partButtons);
		verticalBox.add(partList);
		verticalBox.add(designExp);		
		
		this.tabPane = tabbedPane;
		if (!loadingFile)
			createNewDesignTab(null, "Untitled Design");
	}
	
	public JFrame getFrame() {
		return frmMainpanel;
	}
	
	public File getCurrentDirectory() {
		return this.currentDirectory;
	}
	
	public void setCurrentDirectory(File dir) {
		this.currentDirectory = dir;
	}
	
	public JTabbedPane getTabPane() {
		return this.tabPane;
	}

	public void createNewDesignTab(DesignPanel panel, String fileName) {
		JTabbedPane tabPane = (JTabbedPane) this.getTabPane();
		ObjectManager newManager = null;
		DesignPanel newPanel = null;
		TabTitleArea panelTab = new TabTitleArea(new GridBagLayout());
		JLabel titleLabel = new JLabel();
		TabCloseButton btnClose = new TabCloseButton();
		String tabName = "";
		
		if (fileName == null)
			tabName = "Untitled Design";
		else
			tabName = fileName;
		
		if (panel == null) {
			newManager = new ObjectManager();
			newPanel = new DesignPanel(newManager, this.getFrame());
			newPanel.setTitleArea(titleLabel);
			newManager.setDesignPanel(newPanel);
			newManager.getObjCreator().setDesignPanel(newPanel);
			newPanel.setName(tabName);
			tabPane.addTab(tabName, newPanel);
			btnClose.setDesignPanel(newPanel);
		} else {
			panel.setTitleArea(titleLabel);
			newManager = panel.getObjectManger();
			panel.setName(tabName);
			tabPane.addTab(tabName, panel);
			btnClose.setDesignPanel(panel);
		}
		newManager.createConsole();
		
		panelTab.setOpaque(false);
		
		btnClose.setPreferredSize(new Dimension(17,17));
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setIcon(new ImageIcon("simResources/closeBtn.png"));
		
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent a) {
				int btn = a.getButton();
				if (btn == 1) {
					Component tab = btnClose.getDesignPanel();
					if (tab != null) {
						DesignPanel designPane = (DesignPanel) btnClose.getDesignPanel();
						ObjectManager referenceMan = designPane.getObjectManger();
						referenceMan.killManager();
						referenceMan = null;
						tabPane.remove(designPane);
						tabPane.remove(tab);
						tab = null;
					}
				}
			}			
			public void mouseEntered(MouseEvent e) {
				ImageIcon pressed = new ImageIcon("simResources/closeBtnPressed.png");
				btnClose.setIcon(pressed);
			}			
			public void mouseExited(MouseEvent e) {
				ImageIcon notPressed = new ImageIcon("simResources/closeBtn.png");
				btnClose.setIcon(notPressed);
			}
		});
		titleLabel.setText(tabName);
		panelTab.add(btnClose, GridBagConstraints.NONE);
		panelTab.add(titleLabel, GridBagConstraints.NONE);
		panelTab.setTitle(titleLabel);
		
		int val = tabPane.getTabCount() - 1;
		tabPane.setTabComponentAt(val, panelTab);
		tabPane.setSelectedIndex(val);
	}
}