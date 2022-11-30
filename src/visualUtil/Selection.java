package visualUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//Last edited: 3-9-17
//Author: Hunter Troy Bragg

@SuppressWarnings("serial")
public class Selection extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        this.setBackground(new Color(0,0,0,0));
        float dashLine[] = {2.0f};
        BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashLine, 0.0f);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(dashed);
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.dispose();
    }
}