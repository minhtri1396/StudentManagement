package view.giaovu;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.Rectangle;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
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
    
    private void createComponents() {
        this.setBorder(DropShadowBorder.getSharedInstance());
        this.setPreferredSize(SizeDefine.SUBJECT_ITEM_PANEL);
        this.setSize(SizeDefine.SUBJECT_ITEM_PANEL);
        
        addLeftColumnView();
        addMainColumnView();
        addRightColumnView();
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        setLeftColumnConstraints();
        setMainColumnConstraints();
        setRightColumnConstraints();
    }

    private Rectangle leftColumnBkg;
    private JLabel subjCodeLabel;
    private JLabel countStudentLabel;
    private void addLeftColumnView() {
        subjCodeLabel = new JLabel("CTT0003");
        subjCodeLabel.setFont(FontDefine.LEFT_COL_CONTENT);
        subjCodeLabel.setForeground(ColorDefine.TITLE);
        subjCodeLabel.setBackground(ColorDefine.VALID_BKG);
        subjCodeLabel.setOpaque(true);
        components.put("SubjectCodeLabel", subjCodeLabel);
        this.add(subjCodeLabel);
        
        countStudentLabel = new JLabel("120sv");
        countStudentLabel.setFont(FontDefine.SUB_CONTENT);
        countStudentLabel.setForeground(ColorDefine.TITLE);
        countStudentLabel.setBackground(ColorDefine.VALID_BKG);
        countStudentLabel.setOpaque(true);
        components.put("CountStudentLabel", countStudentLabel);
        this.add(countStudentLabel);
        
        leftColumnBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.VALID_BKG);
        leftColumnBkg.setPreferredSize(new DimensionUIResource(160, 0));
        components.put("LeftColumnBkg", leftColumnBkg);
        this.add(leftColumnBkg);
    }
    private void setLeftColumnConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout(); 
        
        // subjCodeLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, subjCodeLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, leftColumnBkg);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, subjCodeLabel,
                -10,
                SpringLayout.VERTICAL_CENTER, leftColumnBkg);
        // countStudentLabel
        layout.putConstraint(
                SpringLayout.EAST, countStudentLabel,
                -5,
                SpringLayout.EAST, leftColumnBkg);
        layout.putConstraint(
                SpringLayout.SOUTH, countStudentLabel,
                0,
                SpringLayout.SOUTH, leftColumnBkg);
        // leftColumnBkg
        layout.putConstraint(
                SpringLayout.NORTH, leftColumnBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, leftColumnBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, leftColumnBkg,
                0,
                SpringLayout.SOUTH, this);
    }
    
    private JTextPane subjNameTextPane;
    private void addMainColumnView() {
        subjNameTextPane = new JTextPane();
        subjNameTextPane.setFont(FontDefine.SUBJECT_NAME);
        subjNameTextPane.setForeground(ColorDefine.SUBJECT_NAME);
        subjNameTextPane.setBackground(ColorDefine.SUBJECT_NAME_BKG);
        subjNameTextPane.setEditorKit(new WrapEditorKit());
        subjNameTextPane.setOpaque(false);
        subjNameTextPane.setEditable(false);
        // Center text
        StyledDocument doc = subjNameTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        components.put("SubjectNameTextPane", subjNameTextPane);
        this.add(subjNameTextPane);
        
        addExpiredTagView();
    }
    private void setMainColumnConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // subjNameLabel
        layout.putConstraint(
                SpringLayout.NORTH, subjNameTextPane,
                10,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, subjNameTextPane,
                10,
                SpringLayout.EAST, leftColumnBkg);
        layout.putConstraint(
                SpringLayout.EAST, subjNameTextPane,
                -10,
                SpringLayout.WEST, rightColumnBkg);
        
        setExpiredTagConstraints();
    }
    
    private Rectangle expiredTagBkg;
    private JLabel expiredLabel;
    private void addExpiredTagView() {
        expiredLabel = new JLabel(StringDefine.EXPIRED_TEXT);
        expiredLabel.setFont(FontDefine.EXPIRED_TEXT);
        expiredLabel.setForeground(ColorDefine.EXPIRED_TEXT);
        expiredLabel.setBackground(ColorDefine.INVALID_BKG);
        components.put("ExpiredLabel", expiredLabel);
        this.add(expiredLabel);
        
        expiredTagBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.INVALID_BKG);
        expiredTagBkg.setPreferredSize(new DimensionUIResource(100, 30));
        components.put("ExpiredTagBkg", expiredTagBkg);
        this.add(expiredTagBkg);
    }
    private void setExpiredTagConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // expiredTagBkg
        layout.putConstraint(
                SpringLayout.EAST, expiredTagBkg,
                -10,
                SpringLayout.WEST, rightColumnBkg);
        layout.putConstraint(
                SpringLayout.SOUTH, expiredTagBkg,
                0,
                SpringLayout.SOUTH, this);
        // expiredLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, expiredLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, expiredTagBkg);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, expiredLabel,
                0,
                SpringLayout.VERTICAL_CENTER, expiredTagBkg);
    }
    
    private Rectangle rightColumnBkg;
    private JLabel classroomLabel;
    private void addRightColumnView() {
        classroomLabel = new JLabel();
        classroomLabel.setFont(FontDefine.LEFT_COL_CONTENT);
        classroomLabel.setForeground(ColorDefine.TITLE);
        classroomLabel.setBackground(ColorDefine.VALID_BKG);
        classroomLabel.setOpaque(true);
        components.put("ClassroomLabel", classroomLabel);
        this.add(classroomLabel);
        
        rightColumnBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.VALID_BKG);
        rightColumnBkg.setPreferredSize(new DimensionUIResource(138, 0));
        components.put("RightColumnBkg", rightColumnBkg);
        this.add(rightColumnBkg);
    }
    private void setRightColumnConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout(); 
        
        // percentLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, classroomLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, rightColumnBkg);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, classroomLabel,
                0,
                SpringLayout.VERTICAL_CENTER, rightColumnBkg);
        // rightColumnBkg
        layout.putConstraint(
                SpringLayout.NORTH, rightColumnBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, rightColumnBkg,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, rightColumnBkg,
                0,
                SpringLayout.SOUTH, this);
    }
    
}
