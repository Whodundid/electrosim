package visualUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import objectUtil.ObjectManager;
import visuals.DesignPanel;

//Last edited: 3-4-17
//Author: Hunter Troy Bragg

public class PartRightClickMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	public PartRightClickMenu(SimObjectVisual part, ObjectManager man) {
		JMenuItem remove = new JMenuItem("Delete");
		remove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				DesignPanel frame = (DesignPanel) part.getParent();
				List<String> killed = new ArrayList<String>();
				killed.add(part.getID());
				man.unlinkObjsFromToBeDeletedObjs(killed);
				man.delete(part.getID());
				frame.selMulti.clear();
				frame.remove(part);
				frame.repaint();
			}
		});
		
		JMenuItem mntmPartStats = new JMenuItem("Stats");
		mntmPartStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				man.displayConsole();
				man.showCurrentStats(part.getID());
				man.printPointmap(part.getID());
			}
		});
		
		JMenuItem mtnmProperties = new JMenuItem("Properties");
		mtnmProperties.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int btn = e.getButton();
				if (btn == 1) {
					part.displayProperties();
				}
			}
		});
		
		add(mntmPartStats);
		add(mtnmProperties);
		add(remove);
	}
}