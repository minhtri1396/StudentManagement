package view.tool;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import model.define.ColorDefine;
import model.define.FontDefine;
import view.UIView;

public class UIQuantityFilterView extends UIView {
    
    public UIQuantityFilterView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JComboBox pageComboBox;
    private JLabel noteLabel;
    private void createComponents() {
        pageComboBox = new JComboBox();
        pageComboBox.setBackground(ColorDefine.BACKGROUND);
        pageComboBox.setEditable(false);
        ((JLabel)pageComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        components.put("PageComboBox", pageComboBox);
        this.add(pageComboBox);
        
        noteLabel = new JLabel();
        noteLabel.setFont(FontDefine.HINT);
        noteLabel.setForeground(ColorDefine.HINT);
        noteLabel.setBackground(ColorDefine.HINT_BKG);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        components.put("NoteLabel", noteLabel);
        this.add(noteLabel);
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // pageComboBox
        layout.putConstraint(
                SpringLayout.WEST, pageComboBox,
                10,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, pageComboBox,
                -170,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, pageComboBox,
                0,
                SpringLayout.VERTICAL_CENTER, this);
        // noteLabel
        layout.putConstraint(
                SpringLayout.WEST, noteLabel,
                10,
                SpringLayout.EAST, pageComboBox);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, noteLabel,
                -2,
                SpringLayout.VERTICAL_CENTER, pageComboBox);
    }
    
}
