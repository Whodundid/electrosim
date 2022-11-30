package visualUtil;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import simUtil.DesignSaver;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class YesNoDialog extends JDialog {
	
	boolean result;
	String type;
	JDialog thisComp;
	DesignSaver saver;

	public YesNoDialog(String type) {
		this.type = type;
		this.thisComp = this;
		this.setBounds(100, 100, 450, 135);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		
		JPanel buttonPane = new JPanel();
		JButton yesButton = new JButton("Yes");
		yesButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel textDisplay = new JLabel("");
		
		if (type.equals("saver")) {
			this.setTitle("Saving file..");
			textDisplay.setFont(new Font("Tahoma", Font.PLAIN, 16));
			textDisplay.setText("Do you wish to overwrite existing file?");
		}
		
		buttonPane.setBounds(0, 50, 450, 50);
		yesButton.setBounds(64, 0, 110, 32);
		cancelButton.setBounds(259, 0, 110, 32);
		textDisplay.setBounds(88, 11, 312, 32);
		
		buttonPane.setLayout(null);
		
		yesButton.setActionCommand("yes");
		cancelButton.setActionCommand("cancel");
		cancelButton.requestFocus();
		
		buttonPane.add(cancelButton);
		buttonPane.add(yesButton);
		getContentPane().add(buttonPane);
		getContentPane().add(textDisplay);
		
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("yes")) {
					result = true;
					saver.save();
					thisComp.dispose();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("cancel")) {
					result = false;
					thisComp.dispose();
				}
			}
		});
	}
	
	public boolean getResult() {
		return this.result;
	}
	
	public void setDesignSaver(DesignSaver saver) {
		this.saver = saver;
	}
}
