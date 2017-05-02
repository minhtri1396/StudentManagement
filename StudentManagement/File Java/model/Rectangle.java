package model;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Rectangle extends JPanel {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private Color color;
    
    public Rectangle(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void setColor(Color color) {
        this.color = color;
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // do your superclass's painting routine first, and then paint on top of it.
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
}
