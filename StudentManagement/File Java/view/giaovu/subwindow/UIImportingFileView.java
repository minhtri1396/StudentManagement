package view.giaovu.subwindow;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import util.Image;
import view.UIView;

public class UIImportingFileView extends UIView {
    
    public UIImportingFileView() {
        super();
        
        createComponents();
        createLayoutConstraints();
    }

    private JLabel pathLabel;
    private JTextField pathTextField;
    private JLabel addingPathLabel;
    private JButton templateBtn;
    private JButton okBtn;
    private void createComponents() {
        this.setSize(new Dimension(500, 170));
        
        pathLabel = new JLabel("Chọn đường dẫn tới tập tin CSV: ");
        this.add(pathLabel);
        
        pathTextField = new JTextField();
        pathTextField.setFont(FontDefine.TEXT_FIELD);
        components.put("PathTextField", pathTextField);
        this.add(pathTextField);
        
        addingPathLabel = Image.BUILDER.getImageFrom("folder.png", 25, 25);
        addingPathLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        components.put("AddingPathLabel", addingPathLabel);
        this.add(addingPathLabel);
        
        templateBtn = new JButton("Lấy template");
        templateBtn.setFont(FontDefine.BUTTON);
        templateBtn.setForeground(ColorDefine.GET_BTN_TEXT);
        templateBtn.setBackground(ColorDefine.GET_BTN_BKG);
        templateBtn.setToolTipText("Lấy tập tin csv mẫu để nhập theo");
        components.put("TemplateButton", templateBtn);
        this.add(templateBtn);
        
        okBtn = new JButton("OK");
        okBtn.setFont(FontDefine.BUTTON);
        okBtn.setForeground(ColorDefine.ADDING_BTN_TEXT);
        okBtn.setBackground(ColorDefine.ADDING_BTN_BKG);
        components.put("OKButton", okBtn);
        this.add(okBtn);
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // pathLabel
        layout.putConstraint(
                SpringLayout.NORTH, pathLabel,
                10,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, pathLabel,
                20,
                SpringLayout.WEST, this);
        // pathTextField
        layout.putConstraint(
                SpringLayout.NORTH, pathTextField,
                0,
                SpringLayout.NORTH, addingPathLabel);
        layout.putConstraint(
                SpringLayout.WEST, pathTextField,
                20,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, pathTextField,
                -10,
                SpringLayout.WEST, addingPathLabel);
        layout.putConstraint(
                SpringLayout.SOUTH, pathTextField,
                0,
                SpringLayout.SOUTH, addingPathLabel);
        // addingPathLabel
        layout.putConstraint(
                SpringLayout.NORTH, addingPathLabel,
                25,
                SpringLayout.NORTH, pathLabel);
        layout.putConstraint(
                SpringLayout.EAST, addingPathLabel,
                -20,
                SpringLayout.EAST, this);
        
        // templateBtn
        layout.putConstraint(
                SpringLayout.SOUTH, templateBtn,
                -20,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, templateBtn,
                20,
                SpringLayout.WEST, this);
        // okBtn
        layout.putConstraint(
                SpringLayout.SOUTH, okBtn,
                -20,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.EAST, okBtn,
                -20,
                SpringLayout.EAST, this);
    }
    
}
