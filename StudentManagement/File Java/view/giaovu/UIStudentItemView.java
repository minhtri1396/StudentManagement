package view.giaovu;

import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import util.DropShadowBorder;
import util.WrapEditorKit;
import view.UIView;

public class UIStudentItemView extends UIView {
    
    public UIStudentItemView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private JLabel studentCodeLabel;
    private JPanel checkedSquare;
    private JPanel studentCodeBkgPanel;
    private JTextPane studentNameTextPane;
    private JLabel studentFacultyLabel;
    private void createComponents() {
        this.setPreferredSize(SizeDefine.STUDENT_ITEM_PANEL);
        this.setSize(SizeDefine.STUDENT_ITEM_PANEL);
        this.setBorder(DropShadowBorder.getSharedInstance());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // studentCodeLabel
        studentCodeLabel = new JLabel();
        studentCodeLabel.setFont(FontDefine.STUDENT_ITEM_TAG);
        studentCodeLabel.setForeground(ColorDefine.STUDENT_ITEM_TAG);
        studentCodeLabel.setBackground(ColorDefine.STUDENT_ITEM_BKG_TAG);
        studentCodeLabel.setOpaque(true);
        components.put("StudentCodeLabel", studentCodeLabel);
        this.add(studentCodeLabel);
        // checkedSquare
        checkedSquare = new JPanel();
        checkedSquare.setBackground(ColorDefine.STUDENT_ITEM_BKG_UNCHECKED_TAG);
        checkedSquare.setPreferredSize(SizeDefine.STUDENT_ITEM_CHECKED_SQUARE);
        components.put("CheckedSquare", checkedSquare);
        this.add(checkedSquare);
        // studentCodeBkgPanel
        studentCodeBkgPanel = new JPanel();
        studentCodeBkgPanel.setBackground(ColorDefine.STUDENT_ITEM_BKG_TAG);
        studentCodeBkgPanel.setPreferredSize(SizeDefine.STUDENT_ITEM_TAG);
        this.add(studentCodeBkgPanel);
        // studentNameTextPane
        studentNameTextPane = new JTextPane();
        studentNameTextPane.setFont(FontDefine.STUDENT_ITEM);
        studentNameTextPane.setForeground(ColorDefine.STUDENT_ITEM);
        studentNameTextPane.setBackground(ColorDefine.STUDENT_ITEM_BKG);
        studentNameTextPane.setOpaque(true);
        studentNameTextPane.setEditorKit(new WrapEditorKit());
        studentNameTextPane.setOpaque(false);
        studentNameTextPane.setEditable(false);
        studentNameTextPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Center text
        StyledDocument doc = studentNameTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        components.put("StudentNameTextPane", studentNameTextPane);
        this.add(studentNameTextPane);
        // studentFacultyLabel
        studentFacultyLabel = new JLabel();
        studentFacultyLabel.setFont(FontDefine.STUDENT_ITEM);
        studentFacultyLabel.setForeground(ColorDefine.STUDENT_ITEM);
        studentFacultyLabel.setBackground(ColorDefine.STUDENT_ITEM_BKG);
        studentFacultyLabel.setOpaque(true);
        components.put("StudentFacultyLabel", studentFacultyLabel);
        this.add(studentFacultyLabel);
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // studentCodeLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, studentCodeLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, studentCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, studentCodeLabel,
                0,
                SpringLayout.VERTICAL_CENTER, studentCodeBkgPanel);
        // checkedSquare
        layout.putConstraint(
                SpringLayout.NORTH, checkedSquare,
                2,
                SpringLayout.NORTH, studentCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.WEST, checkedSquare,
                2,
                SpringLayout.WEST, studentCodeBkgPanel);
        // studentCodeBkgPanel
        layout.putConstraint(
                SpringLayout.NORTH, studentCodeBkgPanel,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, studentCodeBkgPanel,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, studentCodeBkgPanel,
                0,
                SpringLayout.WEST, this);
        // studentFacultyLabel
        layout.putConstraint(
                SpringLayout.EAST, studentFacultyLabel,
                -20,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, studentFacultyLabel,
                0,
                SpringLayout.VERTICAL_CENTER, studentCodeBkgPanel);
        // studentNameTextPane
        layout.putConstraint(
                SpringLayout.EAST, studentNameTextPane,
                -25,
                SpringLayout.WEST, studentFacultyLabel);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, studentNameTextPane,
                0,
                SpringLayout.VERTICAL_CENTER, studentCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.WEST, studentNameTextPane,
                20,
                SpringLayout.EAST, studentCodeBkgPanel);
    }
    
}
