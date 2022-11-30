package visualPartButtons;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import objectUtil.ObjectCreator;
import objectUtil.ObjectManager;
import visuals.DesignPanel;
import visuals.VisualGUI;

@SuppressWarnings("serial")
public class Parts extends JToolBar{

	VisualGUI refGUI;
	JButton andMenuBtn = new JButton("And");
	JButton orMenuBtn = new JButton("Or");
	JButton norMenuBtn = new JButton("Nor");
	JButton nandMenuBtn = new JButton("Nand");
	JButton xorMenuBtn = new JButton("Xor");
	JButton xnorMenuBtn = new JButton("Xnor");
	JButton bufferMenuBtn = new JButton("Buffer");
	JButton notMenuBtn = new JButton("Not");
	JButton leverMenuBtn = new JButton("Lever");
	JButton DOutMenuBtn = new JButton("Digital Light");
	JButton dLatchMenuBtn = new JButton("D Latch");
	
	public Parts(VisualGUI gui) {
		this.refGUI = gui;
		this.createAssets();
		this.setBackground(SystemColor.inactiveCaptionBorder);
		this.setBorder(new LineBorder(new Color(227, 227, 227), 1, true));
		this.add(orMenuBtn);
		this.add(norMenuBtn);
		this.add(andMenuBtn);
		this.add(nandMenuBtn);
		this.add(xorMenuBtn);
		this.add(xnorMenuBtn);
		this.add(bufferMenuBtn);
		this.add(notMenuBtn);
		this.add(leverMenuBtn);
		this.add(DOutMenuBtn);
		this.add(dLatchMenuBtn);
	}
	
	private void createAssets() {
		andMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		orMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);	
		norMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		nandMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		xorMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		xnorMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		bufferMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);		
		notMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);		
		leverMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);		
		DOutMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);		
		dLatchMenuBtn.setBackground(SystemColor.inactiveCaptionBorder);
		
		orMenuBtn.setIcon(new ImageIcon("simResources/or_SimIcon.png"));
		norMenuBtn.setIcon(new ImageIcon("simResources/nor_SimIcon.png"));
		andMenuBtn.setIcon(new ImageIcon("simResources/and_SimIcon.png"));
		nandMenuBtn.setIcon(new ImageIcon("simResources/nand_SimIcon.png"));
		xorMenuBtn.setIcon(new ImageIcon("simResources/xor_SimIcon.png"));
		xnorMenuBtn.setIcon(new ImageIcon("simResources/xnor_SimIcon.png"));
		
		orMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		norMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		andMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		nandMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		xorMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		xnorMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		bufferMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		notMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		leverMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		DOutMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);		
		dLatchMenuBtn.setHorizontalAlignment(SwingConstants.TRAILING);
		
		orMenuBtn.setToolTipText("Place an OR gate");
		norMenuBtn.setToolTipText("Place an NOR gate");
		andMenuBtn.setToolTipText("Place an AND gate");
		nandMenuBtn.setToolTipText("Place an NAND gate");
		xorMenuBtn.setToolTipText("Place an XOR gate");
		xnorMenuBtn.setToolTipText("Place an XNOR gate");
		bufferMenuBtn.setToolTipText("Place a Buffer gate");
		notMenuBtn.setToolTipText("Place a NOT gate");
		leverMenuBtn.setToolTipText("Place a Lever");
		DOutMenuBtn.setToolTipText("Place a digital light");
		dLatchMenuBtn.setToolTipText("Place a D Latch");
		
		orMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("or", "or");
				}
			}
		});

		norMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("nor", "nor");
				}
			}
		});

		andMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("and", "and");
				}
			}
		});

		nandMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("nand", "nand");
				}
			}
		});

		xorMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("xor", "xor");
				}
			}
		});

		xnorMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(2, 1);
					objCreator.createObj("xnor", "xnor");
				}
			}
		});

		bufferMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(1, 1);
					objCreator.createObj("buffer", "buffer");
				}
			}
		});

		notMenuBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(1, 1);
					objCreator.createObj("not", "not");
				}
			}
		});

		leverMenuBtn.addMouseListener (new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(0, 1);
					objCreator.createObj("lever", "lever");
				}
			}
		});

		DOutMenuBtn.addMouseListener (new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorIOArguments(1, 0);
					objCreator.createObj("dout", "dout");
				}
			}
		});

		dLatchMenuBtn.addMouseListener (new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTabbedPane tabPane = (JTabbedPane) refGUI.getTabPane();
				if (tabPane.getComponentCount() > 1) {
					DesignPanel designPane = (DesignPanel) tabPane.getSelectedComponent();
					ObjectManager referenceMan = designPane.getObjectManger();
					ObjectCreator objCreator = referenceMan.getObjCreator();
					objCreator.setCreatorDefaultArguments(true, true, false);
					objCreator.setCreatorDataTypeArguments('d');
					objCreator.createObj("dlatch", "latch");
				}
			}
		});
	}
}
