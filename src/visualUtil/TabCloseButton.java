package visualUtil;

import javax.swing.JButton;
import visuals.DesignPanel;

@SuppressWarnings("serial")
public class TabCloseButton extends JButton{
	
	DesignPanel refPanel;
	
	public void setDesignPanel(DesignPanel pan) {
		this.refPanel = pan;
	}
	
	public DesignPanel getDesignPanel() {
		return this.refPanel;
	}
}
