/*
 * Copyright (c) 2001-2005, Gaudenz Alder
 * 
 * All rights reserved. 
 * 
 * This file is licensed under the JGraph software license, a copy of which
 * will have been provided to you in the file LICENSE at the root of your
 * installation directory. If you are unable to locate this file please
 * contact JGraph sales for another copy.
 */
package util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.Serializable;

import javax.swing.border.Border;

/**
 * Border with a drop shadow.
 */
public class DropShadowBorder implements Border, Serializable
{
    
    private static final long serialVersionUID = 6854989457150641240L;

    private final Insets insets;

    public static DropShadowBorder sharedInstance = new DropShadowBorder();

    private DropShadowBorder()
    {
        insets = new Insets(2, 2, 2, 2);
    }

    @Override
    public Insets getBorderInsets(Component c)
    {
        return insets;
    }

    @Override
    public boolean isBorderOpaque()
    {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h)
    {
        // choose which colors we want to use
        Color bg = c.getBackground();

        if (c.getParent() != null)
        {
            bg = c.getParent().getBackground();
        }

        if (bg != null)
        {
            Color mid = bg.darker();
            Color edge = average(mid, bg);

            g.setColor(bg);
            g.drawLine(0, h - 2, w, h - 2);
            g.drawLine(0, h - 1, w, h - 1);
            g.drawLine(w - 2, 0, w - 2, h);
            g.drawLine(w - 1, 0, w - 1, h);
            
            // draw the drop-shadow
            g.setColor(mid);
            g.drawLine(0, h - 2, w - 2, h - 2); // horizontal line (bottom)
            g.drawLine(w - 2, 0, w - 2, h - 2); // vertical line (right)
            g.drawLine(0, 0, w, 0); // horizontal line (top)
            g.drawLine(0, 0, 0, h); // vertical line (left)

            g.setColor(edge);
            g.drawLine(1, h - 1, w - 2, h - 1); // horizontal line (bottom)
            g.drawLine(w - 1, 1, w - 1, h - 2); // vertical line (right)
        }
    }

    private static Color average(Color c1, Color c2)
    {
        int red = c1.getRed() + (c2.getRed() - c1.getRed()) / 2;
        int green = c1.getGreen() + (c2.getGreen() - c1.getGreen()) / 2;
        int blue = c1.getBlue() + (c2.getBlue() - c1.getBlue()) / 2;
        return new Color(red, green, blue);
    }

    public static DropShadowBorder getSharedInstance()
    {
        return sharedInstance;
    }
    
}