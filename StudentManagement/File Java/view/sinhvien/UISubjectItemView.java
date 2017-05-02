package view.sinhvien;

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

public class UISubjectItemView extends UIView {
    
    public UISubjectItemView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JLabel subjCodeLabel;
    private JLabel classroomLabel;
    private JPanel subjCodeBkgPanel;
    private JTextPane subjNameTextPane;
    private void createComponents() {
        this.setPreferredSize(SizeDefine.NOTIFICATION_ITEM_PANEL);
        this.setSize(SizeDefine.NOTIFICATION_ITEM_PANEL);
        this.setBorder(DropShadowBorder.getSharedInstance());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // subjCodeLabel
        subjCodeLabel = new JLabel();
        subjCodeLabel.setFont(FontDefine.SUBJECT_ITEM_TAG);
        subjCodeLabel.setForeground(ColorDefine.SUBJECT_ITEM_TAG);
        subjCodeLabel.setBackground(ColorDefine.SUBJECT_ITEM_BKG_TAG);
        subjCodeLabel.setOpaque(true);
        components.put("SubjectCodeLabel", subjCodeLabel);
        this.add(subjCodeLabel);
        // classroomLabel
        classroomLabel = new JLabel();
        classroomLabel.setFont(FontDefine.SUBJECT_ITEM_TAG);
        classroomLabel.setForeground(ColorDefine.SUBJECT_ITEM_TAG);
        classroomLabel.setBackground(ColorDefine.SUBJECT_ITEM_BKG_TAG);
        classroomLabel.setOpaque(true);
        components.put("ClassroomLabel", classroomLabel);
        this.add(classroomLabel);
        // subjCodeBkgPanel
        subjCodeBkgPanel = new JPanel();
        subjCodeBkgPanel.setBackground(ColorDefine.SUBJECT_ITEM_BKG_TAG);
        subjCodeBkgPanel.setPreferredSize(SizeDefine.SUBJECT_ITEM_TAG);
        components.put("SubjectCodeBkgPanel", subjCodeBkgPanel);
        this.add(subjCodeBkgPanel);
        // subjNameTextPane
        subjNameTextPane = new JTextPane();
        subjNameTextPane.setFont(FontDefine.SUBJECT_ITEM);
        subjNameTextPane.setForeground(ColorDefine.SUBJECT_ITEM);
        subjNameTextPane.setBackground(ColorDefine.SUBJECT_ITEM_BKG);
        subjNameTextPane.setEditorKit(new WrapEditorKit());
        subjNameTextPane.setOpaque(false);
        subjNameTextPane.setEditable(false);
        subjNameTextPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Center text
        StyledDocument doc = subjNameTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        components.put("SubjectNameTextPane", subjNameTextPane);
        this.add(subjNameTextPane);
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // subjCodeLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, subjCodeLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, subjCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, subjCodeLabel,
                -8,
                SpringLayout.VERTICAL_CENTER, subjCodeBkgPanel);
        // classroomLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, classroomLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, subjCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.NORTH, classroomLabel,
                0,
                SpringLayout.SOUTH, subjCodeLabel);
        // subjCodeBkgPanel
        layout.putConstraint(
                SpringLayout.NORTH, subjCodeBkgPanel,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, subjCodeBkgPanel,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, subjCodeBkgPanel,
                0,
                SpringLayout.WEST, this);
        // subjNameTextPane
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, subjNameTextPane,
                0,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(
                SpringLayout.WEST, subjNameTextPane,
                5,
                SpringLayout.EAST, subjCodeBkgPanel);
        layout.putConstraint(
                SpringLayout.EAST, subjNameTextPane,
                -5,
                SpringLayout.EAST, this);
    }
    
}
