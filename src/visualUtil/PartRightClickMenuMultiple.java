package visualUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import objectUtil.ObjectCreator;
import objectUtil.ObjectManager;
import placeable.digital.DOut;
import placeable.digital.Lever;
import placeable.SimObject;
import visuals.DesignPanel;

//Last edited: 3-4-17
//Author: Hunter Troy Bragg

public class PartRightClickMenuMultiple extends JPopupMenu {
	private static final long serialVersionUID = 2L;
	public PartRightClickMenuMultiple(List<SimObjectVisual> parts, ObjectManager man) {
		JMenuItem delete = new JMenuItem("Delete");
		delete.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				DesignPanel frame = (DesignPanel) parts.get(0).getParent();
				List<String> killed = new ArrayList<String>();
				for (int i = 0; i < parts.size(); i++) {
					killed.add(parts.get(i).getName());
				}
				man.unlinkObjsFromToBeDeletedObjs(killed);
				for (int q = 0; q < parts.size(); q++) {
					man.delete(parts.get(q).getName());
					frame.remove(parts.get(q));
				}
				frame.selMulti.clear();
				frame.repaint();
			}
		});
		
		JMenuItem truthTable = new JMenuItem("Generate Truth Table from Selection");
		truthTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int btn = e.getButton();
				if (btn == 1) {
					List<String> inputs = new ArrayList<String>();
					List<String> outputs = new ArrayList<String>();
					DesignPanel frame = (DesignPanel) parts.get(0).getParent();
					ObjectManager man = frame.getObjectManger();
					ObjectCreator objCreator = man.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.createObj("tt", "tt");
					SimObject tempObj = man.getCurrentObj();
					if (!frame.selMulti.isEmpty()) {
						for (int i = 0; i < frame.getSelectedComponents().size(); i++) {
							Component tempComp = frame.getSelectedComponents().get(i);
							SimObject obj = man.getStorrageHandler().retrieve(tempComp.getName());
							if (obj instanceof Lever) {
								man.addTruthTableInput(tempObj.getID(), obj.getID());
								inputs.add(obj.getID());
							} else if (obj instanceof DOut) {
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
		this.add(truthTable);
		this.add(delete);
	}
}