package visualMenus;

import javax.swing.JMenuBar;

import visuals.VisualGUI;

@SuppressWarnings("serial")
public class SimulatorMenuBar extends JMenuBar {
	
	public SimulatorMenuBar(VisualGUI gui) {
		FileMenu file = new FileMenu(gui);
		EditMenu edit = new EditMenu(gui);
		SimMenu sim = new SimMenu(gui);
		UtilitiesMenu util = new UtilitiesMenu(gui);
		
		this.add(file);
		this.add(edit);
		this.add(sim);
		this.add(util);
	}
	
}
