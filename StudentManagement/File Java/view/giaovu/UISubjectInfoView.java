package view.giaovu;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.Rectangle;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.StringDefine;
import util.WrapEditorKit;
import view.UIView;

public class UISubjectInfoView extends UIView {
    
    public UISubjectInfoView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private void createComponents() {
        addTitleView();
        addInformationViews();
        addFooterView();
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        setTitleConstraints();
        setInformationViewsContraints();
        setFooterConstraints();
    }
    
    private JTextPane subjNameTextPane;
    private JLabel dateLabel;
    private Rectangle titleBkg;
    private void addTitleView() {
        subjNameTextPane = new JTextPane();
        subjNameTextPane.setFont(FontDefine.SUBJECT_TITLE);
        subjNameTextPane.setForeground(ColorDefine.SUBJECT_TITLE);
        subjNameTextPane.setBackground(ColorDefine.SUBJECT_TITLE_BKG);
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
        
        dateLabel = new JLabel(); 
        dateLabel.setFont(FontDefine.SUBJECT_INFO);
        dateLabel.setForeground(ColorDefine.SUBJECT_TITLE);
        dateLabel.setBackground(ColorDefine.SUBJECT_TITLE_BKG);
        components.put("DateLabel", dateLabel);
        this.add(dateLabel);
        
        titleBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.TITLE_BKG);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // titleLabel
        layout.putConstraint(
                SpringLayout.NORTH, subjNameTextPane,
                20,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, subjNameTextPane,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, subjNameTextPane,
                0,
                SpringLayout.EAST, this);
        // dateLabel
        layout.putConstraint(
                SpringLayout.NORTH, dateLabel,
                7,
                SpringLayout.SOUTH, subjNameTextPane);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, dateLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titleBkg);
        // titleBkg
        layout.putConstraint(
                SpringLayout.NORTH, titleBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, titleBkg,
                20,
                SpringLayout.SOUTH, dateLabel);
        layout.putConstraint(
                SpringLayout.WEST, titleBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, titleBkg,
                0,
                SpringLayout.EAST, this);
    }
    
    private JLabel subjCodeTitle;
    private JLabel subjCodeLabel;
    private JLabel classroomTitle;
    private JLabel classroomLabel;
    private JLabel numberOfStudentTitle;
    private JLabel numberOfStudent;
    private JLabel studyingTimeTitle;
    private JLabel studyingTimeLabel;
    private void addInformationViews() {
        // subjCodeTitle
        subjCodeTitle = new JLabel(StringDefine.SUBJECT_CODE_TITLE);
        subjCodeTitle.setFont(FontDefine.SUBJECT_INFO_TITLE);
        subjCodeTitle.setForeground(ColorDefine.SUBJECT_INFO);
        this.add(subjCodeTitle);
        // subjCodeLabel
        subjCodeLabel = new JLabel();
        subjCodeLabel.setFont(FontDefine.SUBJECT_INFO);
        subjCodeLabel.setForeground(ColorDefine.SUBJECT_INFO);
        components.put("SubjectCodeLabel", subjCodeLabel);
        this.add(subjCodeLabel);
        // classroomTitle
        classroomTitle = new JLabel(StringDefine.CLASSROOM_TITLE);
        classroomTitle.setFont(FontDefine.SUBJECT_INFO_TITLE);
        classroomTitle.setForeground(ColorDefine.SUBJECT_INFO);
        this.add(classroomTitle);
        // classroomLabel
        classroomLabel = new JLabel();
        classroomLabel.setFont(FontDefine.SUBJECT_INFO);
        classroomLabel.setForeground(ColorDefine.SUBJECT_INFO);
        components.put("ClassroomLabel", classroomLabel);
        this.add(classroomLabel);
        // numberOfStudentTitle
        numberOfStudentTitle = new JLabel(StringDefine.NUMBER_OF_STUDENT_TITLE);
        numberOfStudentTitle.setFont(FontDefine.SUBJECT_INFO_TITLE);
        numberOfStudentTitle.setForeground(ColorDefine.SUBJECT_INFO);
        this.add(numberOfStudentTitle);
        // numberOfStudent
        numberOfStudent = new JLabel();
        numberOfStudent.setFont(FontDefine.SUBJECT_INFO);
        numberOfStudent.setForeground(ColorDefine.SUBJECT_INFO);
        components.put("NumberOfStudent", numberOfStudent);
        this.add(numberOfStudent);
        // studyingTimeTitle
        studyingTimeTitle = new JLabel(StringDefine.STUDYING_TIME_TITLE);
        studyingTimeTitle.setFont(FontDefine.SUBJECT_INFO_TITLE);
        studyingTimeTitle.setForeground(ColorDefine.SUBJECT_INFO);
        this.add(studyingTimeTitle);
        // studyingTimeLabel
        studyingTimeLabel = new JLabel();
        studyingTimeLabel.setFont(FontDefine.SUBJECT_INFO);
        studyingTimeLabel.setForeground(ColorDefine.SUBJECT_INFO);
        components.put("StudyingTimeLabel", studyingTimeLabel);
        this.add(studyingTimeLabel);
    }
    private void setInformationViewsContraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // subjCodeTitle
        layout.putConstraint(
                SpringLayout.NORTH, subjCodeTitle,
                60,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.WEST, subjCodeTitle,
                15,
                SpringLayout.WEST, this);
        // subjCodeLabel
        layout.putConstraint(
                SpringLayout.SOUTH, subjCodeLabel,
                0,
                SpringLayout.SOUTH, subjCodeTitle);
        layout.putConstraint(
                SpringLayout.WEST, subjCodeLabel,
                15,
                SpringLayout.EAST, studyingTimeTitle);
        // classroomTitle
        layout.putConstraint(
                SpringLayout.NORTH, classroomTitle,
                40,
                SpringLayout.SOUTH, subjCodeTitle);
        layout.putConstraint(
                SpringLayout.WEST, classroomTitle,
                15,
                SpringLayout.WEST, this);
        // classroomLabel
        layout.putConstraint(
                SpringLayout.SOUTH, classroomLabel,
                0,
                SpringLayout.SOUTH, classroomTitle);
        layout.putConstraint(
                SpringLayout.WEST, classroomLabel,
                15,
                SpringLayout.EAST, studyingTimeTitle);
        // numberOfStudentTitle
        layout.putConstraint(
                SpringLayout.NORTH, numberOfStudentTitle,
                40,
                SpringLayout.SOUTH, classroomTitle);
        layout.putConstraint(
                SpringLayout.WEST, numberOfStudentTitle,
                15,
                SpringLayout.WEST, this);
        // numberOfStudent
        layout.putConstraint(
                SpringLayout.SOUTH, numberOfStudent,
                0,
                SpringLayout.SOUTH, numberOfStudentTitle);
        layout.putConstraint(
                SpringLayout.WEST, numberOfStudent,
                15,
                SpringLayout.EAST, studyingTimeTitle);
        // studyingTimeTitle
        layout.putConstraint(
                SpringLayout.NORTH, studyingTimeTitle,
                40,
                SpringLayout.SOUTH, numberOfStudentTitle);
        layout.putConstraint(
                SpringLayout.WEST, studyingTimeTitle,
                15,
                SpringLayout.WEST, this);
        // studyingTimeLabel
        layout.putConstraint(
                SpringLayout.SOUTH, studyingTimeLabel,
                0,
                SpringLayout.SOUTH, studyingTimeTitle);
        layout.putConstraint(
                SpringLayout.WEST, studyingTimeLabel,
                15,
                SpringLayout.EAST, studyingTimeTitle);
    }
    
    // Footer
    private JLabel schoolLabel;
    private Rectangle footerBkg;
    private void addFooterView() {
        schoolLabel = new JLabel(StringDefine.SCHOOL_NAME);
        schoolLabel.setFont(FontDefine.FOOTER);
        schoolLabel.setForeground(ColorDefine.FOOTER);
        schoolLabel.setBackground(ColorDefine.FOOTER_BKG);
        schoolLabel.setOpaque(true);
        this.add(schoolLabel);
        
        footerBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.FOOTER_BKG);
        this.add(footerBkg);
    }
    private void setFooterConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // schoolLabel
        layout.putConstraint(
                SpringLayout.SOUTH, schoolLabel,
                -10,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, schoolLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        // footerBkg
        layout.putConstraint(
                SpringLayout.NORTH, footerBkg,
                -10,
                SpringLayout.NORTH, schoolLabel);
        layout.putConstraint(
                SpringLayout.SOUTH, footerBkg,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, footerBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, footerBkg,
                0,
                SpringLayout.EAST, this);
    }
    
}
