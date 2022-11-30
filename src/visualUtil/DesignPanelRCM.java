package visualUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import objectUtil.ObjectManager;

//Last edited: 2-25-17
//Author: Hunter Troy Bragg

public class DesignPanelRCM extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	public DesignPanelRCM(ObjectManager man) {
		JMenuItem console = new JMenuItem("Open console window");
		console.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				man.displayConsole();
			}
		});		
		
		JMenuItem mntmProperties = new JMenuItem("Properties");
		mntmProperties.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		add(console);
		add(mntmProperties);
	}
}