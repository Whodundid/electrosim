package visualMenus;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import visuals.VisualGUI;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class EditMenu extends JMenu {
	
	VisualGUI refGUI;
	JMenuItem undoMenuBtn = new JMenuItem("Undo");
	JMenuItem redoMenuBtn = new JMenuItem("Redo");
	JMenuItem copyMenuBtn = new JMenuItem("Copy");
	JMenuItem pasteMenuBtn = new JMenuItem("Paste");
	JMenuItem cutMenuBtn = new JMenuItem("Cut");
	
	public EditMenu(VisualGUI gui) {
		this.refGUI = gui;
		this.createAssets();
		this.setText("Edit");
		this.add(undoMenuBtn);
		this.add(redoMenuBtn);
		this.add(copyMenuBtn);
		this.add(pasteMenuBtn);
		this.add(cutMenuBtn);
	}

	private void createAssets() {
		undoMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
		});
		
		redoMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
		});
		
		copyMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
		});
		
		pasteMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
		});
		
		cutMenuBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
			}
		});
	}
}
