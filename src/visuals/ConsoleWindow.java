package visuals;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import objectUtil.ObjectCreator;
import objectUtil.ObjectManager;
import placeable.digital.DigitalClock;
import placeable.SimObject;
import simUtil.DesignSaver;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

//Last edited: 3-11-17
//Author: Hunter Troy Bragg

public class ConsoleWindow {

	JFrame frmConsole;
	JTextField textField;
	JTextPane textArea;
	String name;
	ObjectManager manager1;
	ObjectCreator objCreator;
	DesignPanel panel;
	Scanner lineReader;
	Style style;
	List<String> entryHistory = new ArrayList<String>();
	int historyLine = 0;
	int lastUsed = 2;
	DesignSaver saver;
	public boolean isSaving;

	public ConsoleWindow(String name, DesignPanel panel, ObjectManager man) {
		this.name = name;
		this.panel = panel;
		this.manager1 = man;
		initialize();
	}
	
	private void initialize() {
		frmConsole = new JFrame();
		frmConsole.setResizable(false);
		frmConsole.setName(name);
		frmConsole.setTitle(name);
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
	
	public void setSaver(DesignSaver saver) {
		this.saver = saver;
	}
		
	public List<String> getHistory() {
		return this.entryHistory;
	}
	
	public JFrame getWindow() {
		return this.frmConsole;
	}
	
	public JTextField getCommandLine() {
		return this.textField;
	}
	
	public Scanner getLineReader() {
		return this.lineReader;
	}
	
	public void consoleCommand(String text) {
		lineReader = new Scanner(text);
		String instruction = lineReader.next();
		if (isSaving) {
			switch (instruction) {
			case "y":
			case "Y":
				if (isSaving) {
					writeOnConsole("Overwriting..", Color.YELLOW);
					saver.save();					
				} break;
			case "N":
			case "n":
				writeOnConsole("Canceling save operation.", Color.YELLOW); break;
			default:
				writeOnConsole("Invalid input. No action taken.", Color.RED);
			}
			isSaving = false;
		}
		else {
			switch (instruction) {
			case "new":
			case "cr":
			case "create":
				try {
					objCreator = manager1.getObjCreator();
					String creationType = lineReader.next();
					String ID;
					int value = 0;
					int inputs = 0;
					int outputs = 0;
					switch (creationType) {
					case "tt":
					case "truthtable":
						try {
							ID = lineReader.next();
							try {
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "tt");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "or":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "or");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "nor":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "nor");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "and":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "and");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "nand":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "nand");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "xor":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "xor");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "xnor":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "xnor");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "buffer":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "buffer");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "not":
					case "inverter":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "not");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "le":
					case "lever":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "lever");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "light":
					case "dout":
						try {
							ID =  lineReader.next();
							inputs = Integer.valueOf(lineReader.next());
							outputs = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorIOArguments(inputs, outputs);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "dout");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "dflipflop":
					case "dff":
						try {
							ID =  lineReader.next();
							try {
								objCreator.setCreatorDataTypeArguments('d');
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "latch");
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); } 
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					case "ck":
					case "clk":
					case "clock":
					case "digitalclock":
						try {
							ID =  lineReader.next();
							value = Integer.valueOf(lineReader.next());
							try {
								objCreator.setCreatorValueArugments(value);
								objCreator.setCreatorDefaultArguments(true, false, true);
								objCreator.createObj(ID, "dClock");
								DigitalClock clock = (DigitalClock) manager1.getCurrentObj();
								new Thread(clock).start();
							} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
						break;
					default:
						writeOnConsole("Unknown type.", Color.RED);
						break;
					}
				} catch (NoSuchElementException e) { writeOnConsole("No arguments defined. ie: 'create and testAnd 2 1'", Color.RED); }
				break;
			case "delete":
				try {
					String obj = lineReader.next();
					try {
						List<String> killed = new ArrayList<String>();
						killed.add(obj);
						manager1.unlinkObjsFromToBeDeletedObjs(killed);
						manager1.delete(obj);
						panel.selMulti.clear();
						panel.repaint();
						writeOnConsole("Deleted Object.", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException|NullPointerException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "deleteall":
			case "deleteAll":
				try {
					manager1.removeAll();
					panel.selMulti.clear();
					panel.repaint();
					writeOnConsole("Object Manager Cleared.", Color.GREEN);
				} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				break;
			case "set":
			case "force":
				try {
					String obj = lineReader.next();
					int objPoint = Integer.valueOf(lineReader.next());
					int value = Integer.valueOf(lineReader.next());
					try {
						SimObject tempObj = manager1.getStorrageHandler().retrieve(obj);
						tempObj.setPointValue(objPoint, value);
						manager1.updateObj(obj, true, true);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid entry.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "link":
				try {
					String srcObj = lineReader.next();
					int srcPoint = Integer.valueOf(lineReader.next());
					String destObj = lineReader.next();
					int destPoint = Integer.valueOf(lineReader.next());
					try {
						manager1.linkObjPoint(srcObj, srcPoint, destObj, destPoint);
						writeOnConsole("Link created.", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "unlink":
				try {
					String srcObj = lineReader.next();
					int srcPoint = Integer.valueOf(lineReader.next());
					String destObj = lineReader.next();
					int destPoint = Integer.valueOf(lineReader.next());
					try {
						manager1.unlinkFromPoint(srcObj, srcPoint, destObj, destPoint);
						writeOnConsole("Link removed.", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "stats":
				try {
					String obj = lineReader.next();
					if (manager1.getStorrageHandler().contains(obj)) {
						manager1.showCurrentStats(obj);
						manager1.printPointmap(obj);
					} else
						writeOnConsole("Object does not exist.", Color.RED);
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "t":
			case "tog":
			case "toggle":
				try {
					String obj = lineReader.next();
					try {
						manager1.toggleOutputValue(obj);
						writeOnConsole("Toggled object's output value.", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid object.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "ttin":
			case "ttaddin":
				try {
					String ID = lineReader.next();
					String obj = lineReader.next();
					try {
						manager1.addTruthTableInput(ID, obj);
						writeOnConsole("Added input to truthTable: " + obj + ".", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "ttout":
			case "ttaddout":
				try {
					String ID = lineReader.next();
					String obj = lineReader.next();
					try {
						manager1.addTruthTableOutput(ID, obj);
						writeOnConsole("Added output to truthTable: " + obj + ".", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "ttshow":
				try {
					String ID = lineReader.next();
					try {
						manager1.generateTruthTable(ID);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "ttremove":
				try {
					String ID = lineReader.next();
					String obj = lineReader.next();
					try {
						manager1.removeTruthTableValue(ID, obj);
						writeOnConsole("Removed object from truthTable: " + obj + ".", Color.GREEN);
					} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
				} catch (NoSuchElementException e) { writeOnConsole("Expected arguments..", Color.RED); }
				break;
			case "list":
				try {
					String arg = lineReader.next();
					switch (arg) {
					case "obj":
					case "objs":
					case "objects":
						try {
							manager1.listObjects();
						} catch (InputMismatchException|NullPointerException e) { writeOnConsole("Invalid input.", Color.RED); }
						break;
					case "con":
					case "cons":
					case "connections":
						try {
							manager1.listAllConnections();
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
					default:
						writeOnConsole("Unrecognized command.", Color.RED);
					}
				}
				break;
			case "save":
				try {
					String path = lineReader.next();
					saver = new DesignSaver(manager1.getDesignPanel(), path, true);
					saver.setFileName(name);
				} catch (NoSuchElementException e) { writeOnConsole("File name and directory must be specified.", Color.RED); }
				break;
			case "help":
				writeOnConsole("Command list:", Color.YELLOW);
				writeOnConsole("create", Color.YELLOW);
				writeOnConsole("├── or", Color.YELLOW);
				writeOnConsole("├── nor", Color.YELLOW);
				writeOnConsole("├── and", Color.YELLOW);
				writeOnConsole("├── nand", Color.YELLOW);
				writeOnConsole("├── xor", Color.YELLOW);
				writeOnConsole("├── xnor", Color.YELLOW);
				writeOnConsole("├── not", Color.YELLOW);
				writeOnConsole("├── buffer", Color.YELLOW);
				writeOnConsole("├── dout", Color.YELLOW);
				writeOnConsole("├── lever", Color.YELLOW);
				writeOnConsole("├── truthtable", Color.YELLOW);
				writeOnConsole("└── dff", Color.YELLOW);
				writeOnConsole("delete", Color.YELLOW);
				writeOnConsole("deleteall", Color.YELLOW);
				writeOnConsole("set", Color.YELLOW);
				writeOnConsole("link", Color.YELLOW);
				writeOnConsole("unlink", Color.YELLOW);
				writeOnConsole("toggle", Color.YELLOW);
				writeOnConsole("stats", Color.YELLOW);
				writeOnConsole("ttin", Color.YELLOW);
				writeOnConsole("ttout", Color.YELLOW);
				writeOnConsole("ttshow", Color.YELLOW);
				writeOnConsole("ttremove", Color.YELLOW);
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
	}
	
	public void writeOnConsole(String text, Color color) {
		StyleConstants.setForeground(style, color);
		StyledDocument area = textArea.getStyledDocument();
		try {
			area.insertString(area.getLength(), text + "\n", style);
			((JScrollPane)textArea.getParent().getParent()).getVerticalScrollBar().setValue(((JScrollPane)textArea.getParent().getParent()).getVerticalScrollBar().getMaximum() + 1);
		} catch (BadLocationException e1) { e1.printStackTrace(); }
	}
	public void writeOnConsoleN(String text, Color color) {
		StyleConstants.setForeground(style, color);
		StyledDocument area = textArea.getStyledDocument();
		try {
			area.insertString(area.getLength(), text, style);
			((JScrollPane)textArea.getParent().getParent()).getVerticalScrollBar().setValue(((JScrollPane)textArea.getParent().getParent()).getVerticalScrollBar().getMaximum() + 1);
		} catch (BadLocationException e1) { e1.printStackTrace(); }
	}
}