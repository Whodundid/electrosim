package visualUtil;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Last edited: 3-11-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class TabTitleArea extends JPanel{

	JLabel titlePanel;
	
	public TabTitleArea(GridBagLayout gridBagLayout) {
		this.setLayout(gridBagLayout);
	}

	public JLabel getTitle() {
		return this.titlePanel;
	}
	
	public void setTitle(JLabel lblTitle) {
		this.titlePanel = lblTitle;
	}
}
