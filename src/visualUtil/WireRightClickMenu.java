package visualUtil;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import objectUtil.ObjectManager;
import visuals.DesignPanel;

//Last edited: 3-4-17
//Author: Hunter Troy Bragg

public class WireRightClickMenu extends JPopupMenu {
	private static final long serialVersionUID = 3L;
	public WireRightClickMenu(Component part, ObjectManager man) {
		JMenuItem remove = new JMenuItem("Delete");
		remove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				DesignPanel frame = (DesignPanel) part.getParent();
				man.delete(part.getName());
				frame.selMulti.clear();
				frame.remove(part);
				frame.repaint();
			}
		});
		
		add(remove);
	}
}