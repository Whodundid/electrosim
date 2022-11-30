package simBase;

import visuals.VisualGUI;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//Last edited: 3-15-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

public class Start {
	
	public static final String version = "ElectroSim B0.18";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { }
		new VisualGUI("rawr.ecs");
		
	}
}