package view.giaovu.subwindow;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.StringDefine;
import util.Image;
import view.UIView;

public class UIInputTypeView extends UIView {
    
    public UIInputTypeView() {
        super();
        
        createComponents();
        createLayoutConstraints();
    }
    
    private JLabel choiceAddingImage;
    private JLabel titleAddingImage;
    private JLabel choiceFromListImage;
    private JLabel titleListImage;
    private JLabel choiceImportImage;
    private JLabel titleImportImage;
    private void createComponents() {
        this.setBackground(ColorDefine.BACKGROUND);
        this.setSize(new Dimension(500, 200));
        
        choiceAddingImage = Image.BUILDER.getImageFrom("add_new.png", 80, 80);
        choiceAddingImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        components.put("ChoiceAddingImage", choiceAddingImage);
        
        choiceFromListImage = Image.BUILDER.getImageFrom("list.png", 80, 80);
        choiceFromListImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        components.put("ChoiceFromListImage", choiceFromListImage);
        
        choiceImportImage = Image.BUILDER.getImageFrom("folder.png", 80, 80);
        choiceImportImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        components.put("ChoiceImportImage", choiceImportImage);
        
        titleAddingImage = new JLabel(StringDefine.ADDING_NEW_CHOICE);
        titleListImage = new JLabel(StringDefine.ADDING_FROM_LIST_CHOICE);
        titleImportImage = new JLabel(StringDefine.ADDING_FROM_FILE_CHOICE);
        
        this.add(choiceAddingImage);
        this.add(choiceFromListImage);
        this.add(choiceImportImage);
        
        this.add(titleAddingImage);
        this.add(titleListImage);
        this.add(titleImportImage);
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // choiceAddingImage
        layout.putConstraint(
                SpringLayout.NORTH, choiceAddingImage,
                30,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, choiceAddingImage,
                30,
                SpringLayout.WEST, this);
        // titleAddingImage
        layout.putConstraint(
                SpringLayout.NORTH, titleAddingImage,
                10,
                SpringLayout.SOUTH, choiceAddingImage);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleAddingImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, choiceAddingImage);
        // choiceFromListImage
        layout.putConstraint(
                SpringLayout.NORTH, choiceFromListImage,
                30,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, choiceFromListImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        // titleListImage
        layout.putConstraint(
                SpringLayout.NORTH, titleListImage,
                10,
                SpringLayout.SOUTH, choiceFromListImage);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleListImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, choiceFromListImage);
        //choiceImportImage
        layout.putConstraint(
                SpringLayout.NORTH, choiceImportImage,
                30,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, choiceImportImage,
                -30,
                SpringLayout.EAST, this);
        // titleImportImage
        layout.putConstraint(
                SpringLayout.NORTH, titleImportImage,
                10,
                SpringLayout.SOUTH, choiceImportImage);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleImportImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, choiceImportImage);
        
    }
}
