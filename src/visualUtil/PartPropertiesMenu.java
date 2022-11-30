package visualUtil;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import objectUtil.ObjectManager;
import placeable.SimObject;
import visuals.DesignPanel;
import placeable.digital.DigitalClock;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;

//Last edited: 3-15-17
//Last Build: ElectroSim B0.18
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class PartPropertiesMenu extends JFrame{
	
	int numInputs;
	int numOutputs;
	long numFrequency;
	int inputSliderPreVal;
	int outputSliderPreVal;
	int freqPreVal;
	String partNamePreVal;
	DesignPanel refPan;
	ObjectManager refMan;
	SimObjectVisual refPart;
	SimObject refObj;	
	JSlider inputSlider;
	JSlider outputSlider;
	JTextField partNameVisual;
	JTextField inputNumVisual;
	JTextField outputNumVisual;
	JTextField frequencyNumVisual;

	public PartPropertiesMenu(SimObject obj, SimObjectVisual part) {
		refPart = part;
		refPan = obj.getDesignPanel();
		refMan = obj.getObjectManager();
		refObj = obj;
		partNamePreVal = refObj.getID();
		numInputs = refObj.getInputs().size();
		numOutputs = refObj.getOutputs().size();
		inputSliderPreVal = numInputs;
		outputSliderPreVal = numOutputs;
		if (refObj.checkObjType().equals("dClock")) {
			numFrequency = ((DigitalClock)refObj).getInterval();
			freqPreVal = (int) numFrequency;
		}
		createAssets();
	}
	
	public void display() {
		if (!this.isVisible())
			this.setVisible(true);
	}
	
	public void createAssets() {
		JTabbedPane propertyTabs = new JTabbedPane(JTabbedPane.TOP);
		JPanel panel = new JPanel();
		JLabel IDLabel = new JLabel("Part ID:");
		JLabel inputLabel = new JLabel("Inputs:");
		JLabel outputLabel = new JLabel("Outputs:");
		JLabel frequencyLabel = new JLabel("Frequency:");
		inputSlider = new JSlider();
		outputSlider = new JSlider();
		partNameVisual = new JTextField();		
		inputNumVisual = new JTextField();
		outputNumVisual = new JTextField();
		frequencyNumVisual = new JTextField();
		
		this.setAlwaysOnTop(true);
		this.getContentPane().setBackground(new Color(240, 248, 255));
		this.setTitle("Properties");
		this.setResizable(false);
		this.setBounds(100, 100, 229, 350);
		this.getContentPane().add(propertyTabs, BorderLayout.CENTER);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(refPan);
		
		propertyTabs.setBackground(new Color(176, 224, 230));		
		propertyTabs.addTab("General", null, panel, null);
		
		panel.setBackground(new Color(240, 255, 255));		
		panel.setLayout(null);		
		
		IDLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		IDLabel.setBounds(10, 11, 59, 19);		
		inputLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		inputLabel.setBounds(10, 55, 51, 20);
		outputLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		outputLabel.setBounds(10, 134, 59, 20);
		frequencyLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frequencyLabel.setBounds(10, 209, 78, 20);
		
		inputSlider.setSnapToTicks(true);
		inputSlider.setPaintLabels(true);
		inputSlider.setPaintTicks(true);
		inputSlider.setMinimum(0);
		inputSlider.setMaximum(10);		
		inputSlider.setValue(numInputs);
		inputSlider.setBorder(new LineBorder(new Color(211, 211, 211)));
		inputSlider.setBackground(new Color(245, 245, 245));
		inputSlider.setBounds(10, 90, 200, 33);				
		
		outputSlider.setValue(0);
		outputSlider.setSnapToTicks(true);
		outputSlider.setPaintTicks(true);
		outputSlider.setPaintLabels(true);
		outputSlider.setMinimum(0);
		outputSlider.setMaximum(10);		
		outputSlider.setValue(numOutputs);
		outputSlider.setBorder(new LineBorder(new Color(211, 211, 211)));
		outputSlider.setBackground(new Color(245, 245, 245));
		outputSlider.setBounds(10, 165, 200, 33);
		
		partNameVisual.setBounds(74, 10, 134, 20);
		partNameVisual.setColumns(10);
		partNameVisual.setText(refObj.getID());
		inputNumVisual.setBounds(63, 57, 43, 20);	
		inputNumVisual.setColumns(10);		
		inputNumVisual.setText(Integer.toString(numInputs));
		outputNumVisual.setColumns(10);
		outputNumVisual.setBounds(75, 136, 43, 20);
		outputNumVisual.setText(Integer.toString(numOutputs));
		frequencyNumVisual.setText("0");
		frequencyNumVisual.setColumns(10);
		frequencyNumVisual.setBounds(94, 211, 43, 20);
		frequencyNumVisual.setText(Integer.toString(freqPreVal));
		updateVisuals();
		
		inputSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (inputSliderPreVal != inputSlider.getValue()) {
					PointManager pointMan = refObj.getPointManager();
					refObj.getInputs().clear();
					refObj.setNumInputs(inputSlider.getValue());
					refMan.removeObjRef(refObj.getID());
					pointMan.clear();
					pointMan.createPoints();
					pointMan.drawPoints();
					refPan.repaint();
					inputNumVisual.setText(Integer.toString(inputSlider.getValue()));
					refMan.updateObj(refObj.getID(), true, true);
					refObj.getInputsMap().clear();
					refObj.getOutputsMap().clear();
					refObj.getConnections().clear();
					inputSliderPreVal = inputSlider.getValue();
				}
			}
		});
		
		outputSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (outputSliderPreVal != outputSlider.getValue()) {
					PointManager pointMan = refObj.getPointManager();
					refObj.getOutputs().clear();
					refObj.setNumOutputs(outputSlider.getValue());
					refMan.removeObjRef(refObj.getID());
					pointMan.clear();
					pointMan.createPoints();
					pointMan.drawPoints();
					refPan.repaint();
					outputNumVisual.setText(Integer.toString(outputSlider.getValue()));
					refMan.updateObj(refObj.getID(), true, true);
					refObj.getInputsMap().clear();
					refObj.getOutputsMap().clear();
					refObj.getConnections().clear();
					outputSliderPreVal = outputSlider.getValue();
				}
			}
		});
		
		inputNumVisual.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10 && !inputNumVisual.getText().isEmpty()) {
					if (Integer.valueOf(inputNumVisual.getText()) >= 0 && Integer.valueOf(inputNumVisual.getText()) <= inputSlider.getMaximum())
						try {
							inputSlider.setValue(Integer.valueOf(inputNumVisual.getText()));
						} catch (InputMismatchException r) {}
					else {
						refMan.displayConsole();
						refMan.getConsole().writeOnConsole("Size must be greater than 0 and less than 10.", Color.RED);
						inputNumVisual.setText(Integer.toString(inputSliderPreVal));
					}
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}			
		});
		
		outputNumVisual.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10 && !outputNumVisual.getText().isEmpty()) {
					if (Integer.valueOf(outputNumVisual.getText()) >= 0 && Integer.valueOf(outputNumVisual.getText()) <= outputSlider.getMaximum())
						try {
							outputSlider.setValue(Integer.valueOf(outputNumVisual.getText()));
						} catch (InputMismatchException r) {}
					else {
						refMan.displayConsole();
						refMan.getConsole().writeOnConsole("Size must be greater than 0 and less than 10.", Color.RED);
						outputNumVisual.setText(Integer.toString(outputSliderPreVal));
					}
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}			
		});
		
		frequencyNumVisual.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10 && !frequencyNumVisual.getText().isEmpty()) {
					if (Integer.valueOf(frequencyNumVisual.getText()) >= 0 && Integer.valueOf(frequencyNumVisual.getText()) <= Integer.MAX_VALUE) {
						((DigitalClock)refObj).setInterval(Integer.valueOf(frequencyNumVisual.getText()));
						//((DigitalClock)refObj).notify();
					} else {
						refMan.displayConsole();
						refMan.getConsole().writeOnConsole("Value must be greater than or equal to 10", Color.RED);
						frequencyNumVisual.setText(Integer.toString(freqPreVal));
					}
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}			
		});
		
		partNameVisual.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				partNameVisual.selectAll();
			}
		});
		
		partNameVisual.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String origName = refObj.getID();
					if (!partNameVisual.getText().isEmpty() && partNameVisual.getText() != partNamePreVal) {
						if (!refMan.getStorrageHandler().contains(partNameVisual.getText())) {
							refObj.setID(partNameVisual.getText());
							refMan.getStorrageHandler().replace(origName, refObj);
						}
						SimObjectVisual refPartPre = refPart;
						refPart.getObjectMover().setSimObject(refObj);
						refPart.setID(partNameVisual.getText());
						refPan.replaceObjInList(refPartPre, refPart);
						partNamePreVal = partNameVisual.getText();
					} else
						partNameVisual.setText(origName);
					partNameVisual.transferFocusBackward();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}			
		});
		
		panel.add(IDLabel);
		panel.add(inputLabel);
		panel.add(outputLabel);
		panel.add(frequencyLabel);
		panel.add(inputSlider);
		panel.add(outputSlider);	
		panel.add(partNameVisual);
		panel.add(inputNumVisual);
		panel.add(outputNumVisual);
		panel.add(frequencyNumVisual);
	}
	
	public void updateVisuals() {
		if (refObj.checkObjType().equals("digitalOutput")) {
			outputSlider.setVisible(false);
			outputNumVisual.setEditable(false);
			frequencyNumVisual.setEditable(false);
		} else if (refObj.checkObjType().equals("digitalInput")) {
			inputSlider.setVisible(false);
			inputNumVisual.setEditable(false);
			frequencyNumVisual.setEditable(false);
		} else if (refObj.checkObjType().equals("dClock")) {
			inputSlider.setVisible(false);
			inputNumVisual.setEditable(false);
			outputSlider.setVisible(false);
			outputNumVisual.setEditable(false);
		} else if (refObj.checkObjType().equals("logicGate")) {
			frequencyNumVisual.setEditable(false);
		} else if (refObj.checkObjType().equals("latch")) {
			inputSlider.setVisible(false);
			inputNumVisual.setEditable(false);
			frequencyNumVisual.setEditable(false);
		} else if (refObj.checkObjType().equals("ff")) {
			inputSlider.setVisible(false);
			inputNumVisual.setEditable(false);
			frequencyNumVisual.setEditable(false);
		}
	}	
}
