package simUtil;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
//import simUtil.DesignSaver;
import visuals.DesignPanel;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Last edited: 2-28-17
//Author: Hunter Troy Bragg

public class SimConsole {

	JFrame frmConsole;
	JTextField textField;
	JTextPane textArea;
	String name;
	DesignPanel panel;
	Scanner lineReader;
	Style style;
	List<String> entryHistory = new ArrayList<String>();
	int historyLine = 0;
	int lastUsed = 2;

	public SimConsole(String name) {
		this.name = name;
		initialize();
	}
	
	private void initialize() {
		frmConsole = new JFrame();
		frmConsole.setResizable(false);
		frmConsole.setTitle("Main");
		frmConsole.setBounds(100, 100, 637, 362);
		frmConsole.getContentPane().setLayout(null);
		frmConsole.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmConsole.setAlwaysOnTop(true);
		
		textField = new JTextField();
		textField.setBackground(SystemColor.menu);
		textField.setBounds(0, 306, 631, 27);
		frmConsole.getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 611, 285);
		frmConsole.getContentPane().add(scrollPane);
		
		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.windowText);
		style = textArea.addStyle("Console Text", null);
		scrollPane.setViewportView(textArea);
		
		textField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String text = textField.getText();
					textField.setText("");
					if (!text.isEmpty()) {
						entryHistory.add(text);
						historyLine = 0;
						lastUsed = 2;
						writeOnConsole(text, Color.WHITE);
						consoleCommand(text);
					}
				} else if (e.getKeyCode() == 38) {
					if (historyLine < entryHistory.size()) {
						if (lastUsed == 0) {
							historyLine += 1;
							textField.setText(getHistoryLine(historyLine));
							historyLine += 1;
						} else {
							textField.setText(getHistoryLine(historyLine));
							historyLine += 1;
						}
					}
					lastUsed = 1;
				} else if (e.getKeyCode() == 40) {
					if (historyLine <= entryHistory.size() && historyLine > 0) {
						if (historyLine == entryHistory.size() || lastUsed == 1)
							historyLine -= 2;
						else
							historyLine -= 1;
						textField.setText(getHistoryLine(historyLine));
						lastUsed = 0;
					} else if (historyLine == 0) { 
						textField.setText("");
						lastUsed = 2;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}			
		});
	}
	
	public String getHistoryLine(int lineNum) {
		if (entryHistory.isEmpty())
			return null;
		else {
			if (lineNum == 0)
				return entryHistory.get(entryHistory.size() - 1);
			else if (lineNum > 0)
				return entryHistory.get(entryHistory.size() - (lineNum + 1));
			else
				return null;
		}
	}
	
	public List<String> getHistory() {
		return this.entryHistory;
	}
	
	public JFrame getWindow() {
		return this.frmConsole;
	}
	
	public void consoleCommand(String text) {
		lineReader = new Scanner(text);
		String instruction = lineReader.next();
		switch (instruction) {
		case "new":
		case "cr":
		case "create":
		case "list":
			try {
				String arg = lineReader.next();
				switch (arg) {
				case "obj":
				case "objs":
				case "objects":
					try {
						//manager1.listObjects();
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
					break;
				case "con":
				case "cons":
				case "connections":
					try {
						//manager1.listAllConnections();
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
					break;
				default:
					writeOnConsole("Unrecognized command.", Color.RED);
				}
			} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
			break;
		case "clr":
		case "clear":
			if (!lineReader.hasNext()) {
				this.textArea.setText("");
				break;
			} else {
				String arg = lineReader.next();
				switch (arg) {
				case "hst":
				case "hist":
				case "history":
					this.entryHistory.clear();
					this.writeOnConsole("Console history cleared.", Color.GREEN);
					break;
				}
			}
			break;
		case "save":
			//DesignSaver saver = new DesignSaver(manager1.getDesignPanel());
			//saver.save();
			break;
		case "help":
			writeOnConsole("Command list:", Color.YELLOW);
			writeOnConsole("list", Color.YELLOW);
			writeOnConsole("├── objects", Color.YELLOW);
			writeOnConsole("└── connections", Color.YELLOW);
			writeOnConsole("clr", Color.YELLOW);
			writeOnConsole("clr hst", Color.YELLOW);
			writeOnConsole("save", Color.YELLOW);
			writeOnConsole("help", Color.YELLOW);
			break;
		default:
			writeOnConsole("Unrecognized command.", Color.RED);
		}
	}
	
	public void writeOnConsole(String text, Color color) {
		StyleConstants.setForeground(style, color);
		StyledDocument area = textArea.getStyledDocument();
		try {
			area.insertString(area.getLength(), text + "\n", style);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	public void writeOnConsoleN(String text, Color color) {
		StyleConstants.setForeground(style, color);
		StyledDocument area = textArea.getStyledDocument();
		try {
			area.insertString(area.getLength(), text, style);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}