package visualMenus;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import objectUtil.ObjectCreator;
import objectUtil.ObjectManager;
import placeable.digital.DOut;
import placeable.digital.Lever;
import placeable.SimObject;
import visuals.DesignPanel;
import visuals.VisualGUI;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class UtilitiesMenu extends JMenu {
	
	VisualGUI refGUI;
	JMenuItem TTableMenuBtn = new JMenuItem("Generate truth table from selection");

	public UtilitiesMenu(VisualGUI gui) {
		this.refGUI = gui;
		this.createAssets();
		this.setText("Utilities");
		this.add(TTableMenuBtn);
	}
	
	private void createAssets() {
		TTableMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int btn = e.getButton();
				if (btn == 1) {
					List<String> inputs = new ArrayList<String>();
					List<String> outputs = new ArrayList<String>();
					JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
					DesignPanel panel = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager man = panel.getObjectManger();
					ObjectCreator objCreator = man.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.createObj("tt", "tt");
					SimObject tempObj = man.getCurrentObj();
					if (!panel.selMulti.isEmpty()) {
						for (int i = 0; i < panel.getSelectedComponents().size(); i++) {
							Component tempComp = panel.getSelectedComponents().get(i);
							SimObject obj = man.getStorrageHandler().retrieve(tempComp.getName());
							if (obj instanceof Lever) {
								man.addTruthTableInput(tempObj.getID(), obj.getID());
								inputs.add(obj.getID());
							}
							else if (obj instanceof DOut) {
								man.addTruthTableOutput(tempObj.getID(), obj.getID());
								outputs.add(obj.getID());
							}
						}
						if (inputs.isEmpty() || outputs.isEmpty()) {
							man.getConsole().writeOnConsole("Error: either no inputs or outputs detected.", Color.RED);
							man.displayConsole();
						} else if (inputs.size() > 8) {
							man.getConsole().writeOnConsole("Error: truth tables can only handle a maximum of 8 inputs.", Color.RED);
							man.displayConsole();
						} else {
							man.generateTruthTable(tempObj.getID());
							man.displayConsole();
						}
					}
					man.delete(tempObj.getID());
				}
			}
		});
	}
}
