package view;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import util.Image;

public class UICheckBoxView extends UIView {
    
    public UICheckBoxView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JLabel noteLabel;
    private JLabel squareBoxLabel;
    private void createComponents() {
        noteLabel = new JLabel();
        noteLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        components.put("NoteLabel", noteLabel);
        this.add(noteLabel);
        
        squareBoxLabel = Image.BUILDER.getImageFrom("stick.png", 25, 25);
        components.put("SquareBoxLabel", squareBoxLabel);
        this.add(squareBoxLabel);
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        // noteLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, noteLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, squareBoxLabel);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, noteLabel,
                0,
                SpringLayout.VERTICAL_CENTER, squareBoxLabel);
        // squareBoxLabel
        layout.putConstraint(
                SpringLayout.NORTH, this,
                0,
                SpringLayout.NORTH, squareBoxLabel);
        layout.putConstraint(
                SpringLayout.EAST, this,
                0,
                SpringLayout.EAST, squareBoxLabel);
        layout.putConstraint(
                SpringLayout.SOUTH, this,
                0,
                SpringLayout.SOUTH, squareBoxLabel);
        layout.putConstraint(
                SpringLayout.WEST, this,
                0,
                SpringLayout.WEST, squareBoxLabel);
    }
    
}
