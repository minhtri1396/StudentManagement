package view.giaovu.subwindow;

import java.awt.Dimension;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import util.DateFormatter;
import util.Image;
import util.JTextFieldLimit;
import view.UIView;

public class UIAddingStudentView extends UIView {
    
    public UIAddingStudentView() {
        super();
        
        createComponents();
        createLayoutConstraints();
    }

    private void createComponents() {
        this.setBackground(ColorDefine.BACKGROUND);
        this.setSize(new Dimension(500, 700));
        
        addTitleView();
        addButtons();
        addStudentCodeInputField();
        addStudentNameInputField();
        addCalendarView();
        addSchoolYearInputField();
    }

    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setTitleConstraints();
        setButtonsConstraints();
        setStudentCodeInputFieldConstraints();
        setStudentNameInputFieldConstraints();
        setCalendarConstraints();
        setSchoolYearConstraints();
    }
    
    // Title
    private JLabel titleLabel;
    private JPanel titleBkg;
    private void addTitleView() {
        titleLabel = new JLabel(StringDefine.ADDING_STUDENT_TITLE);
        titleLabel.setFont(FontDefine.TITLE);
        titleLabel.setForeground(ColorDefine.TITLE);
        titleLabel.setBackground(ColorDefine.TITLE_BKG);
        titleLabel.setOpaque(true);
        
        titleBkg = new JPanel();
        titleBkg.setBackground(ColorDefine.TITLE_BKG);
        titleBkg.add(titleLabel);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // titleLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titleBkg);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleLabel,
                0,
                SpringLayout.VERTICAL_CENTER, titleBkg);
        // titleBkg
        layout.putConstraint(
                SpringLayout.NORTH, titleBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, titleBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, titleBkg,
                0,
                SpringLayout.EAST, this);
    }
    
    // Buttons
    private JButton addingButton;
    private void addButtons() {
        addingButton = new JButton(StringDefine.ADDING_BUTTON);
        addingButton.setPreferredSize(SizeDefine.ADDING_BUTTON);
        addingButton.setFont(FontDefine.BUTTON);
        addingButton.setForeground(ColorDefine.ADDING_BTN_TEXT);
        addingButton.setBackground(ColorDefine.ADDING_BTN_BKG);
        components.put("AddingButton", addingButton);
        this.add(addingButton);
    }
    private void setButtonsConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // addingButton
        layout.putConstraint(
                SpringLayout.NORTH, addingButton,
                20,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.EAST, addingButton,
                -10,
                SpringLayout.EAST, this);
    }
    
    // Subject code
    private JLabel studentCodeImage;
    private JTextField studentCodeTextField;
    private JLabel hintStudentCodeLabel;
    private void addStudentCodeInputField() {
        studentCodeImage = Image.BUILDER.getImageFrom("subject_code.png", 60, 60);
        this.add(studentCodeImage);
        
        studentCodeTextField = new JTextField();
        studentCodeTextField.setDocument(new JTextFieldLimit(7));
        studentCodeTextField.setFont(FontDefine.TEXT_FIELD);
        components.put("StudentCodeTextField", studentCodeTextField);
        this.add(studentCodeTextField);
        
        hintStudentCodeLabel = new JLabel(StringDefine.HINT_STUDENT_CODE);
        hintStudentCodeLabel.setFont(FontDefine.HINT);
        hintStudentCodeLabel.setForeground(ColorDefine.HINT);
        hintStudentCodeLabel.setBackground(ColorDefine.HINT_BKG);
        hintStudentCodeLabel.setOpaque(true);
        this.add(hintStudentCodeLabel);
    }
    private void setStudentCodeInputFieldConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // studentCodeImage
        layout.putConstraint(
                SpringLayout.NORTH, studentCodeImage,
                40,
                SpringLayout.SOUTH, addingButton);
        layout.putConstraint(
                SpringLayout.WEST, studentCodeImage,
                20,
                SpringLayout.WEST, this);
        // studentCodeTextField
        layout.putConstraint(
                SpringLayout.NORTH, studentCodeTextField,
                5,
                SpringLayout.NORTH, studentCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, studentCodeTextField,
                20,
                SpringLayout.EAST, studentCodeImage);
        layout.putConstraint(
                SpringLayout.EAST, studentCodeTextField,
                -180,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, studentCodeTextField,
                -7,
                SpringLayout.NORTH, hintStudentCodeLabel);
        // hintStudentCodeLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintStudentCodeLabel,
                2,
                SpringLayout.SOUTH, studentCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, hintStudentCodeLabel,
                20,
                SpringLayout.EAST, studentCodeImage);
    }
    
    // Subject name
    private JLabel studentNameImage;
    private JTextField studentNameTextField;
    private JLabel hintStudentNameLabel;
    private void addStudentNameInputField() {
        studentNameImage = Image.BUILDER.getImageFrom("subject_name.png", 65, 70);
        this.add(studentNameImage);
        
        studentNameTextField = new JTextField();
        studentNameTextField.setFont(FontDefine.TEXT_FIELD);
        studentNameTextField.setDocument(new JTextFieldLimit(50));
        components.put("StudentNameTextField", studentNameTextField);
        this.add(studentNameTextField);
        
        hintStudentNameLabel = new JLabel(StringDefine.HINT_STUDENT_NAME);
        hintStudentNameLabel.setFont(FontDefine.HINT);
        hintStudentNameLabel.setForeground(ColorDefine.HINT);
        hintStudentNameLabel.setBackground(ColorDefine.HINT_BKG);
        hintStudentNameLabel.setOpaque(true);
        this.add(hintStudentNameLabel);
    }
    private void setStudentNameInputFieldConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // subjNameImage
        layout.putConstraint(
                SpringLayout.NORTH, studentNameImage,
                45,
                SpringLayout.SOUTH, studentCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, studentNameImage,
                16,
                SpringLayout.WEST, this);
        // subjNameTextField
        layout.putConstraint(
                SpringLayout.NORTH, studentNameTextField,
                10,
                SpringLayout.NORTH, studentNameImage);
        layout.putConstraint(
                SpringLayout.WEST, studentNameTextField,
                20,
                SpringLayout.EAST, studentNameImage);
        layout.putConstraint(
                SpringLayout.EAST, studentNameTextField,
                -20,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, studentNameTextField,
                -7,
                SpringLayout.NORTH, hintStudentNameLabel);
        // hintSubjNameLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintStudentNameLabel,
                -5,
                SpringLayout.SOUTH, studentNameImage);
        layout.putConstraint(
                SpringLayout.WEST, hintStudentNameLabel,
                20,
                SpringLayout.EAST, studentNameImage);
    }
    
    // SchoolYear
    private JLabel facultyImage;
    private JComboBox schoolYearComboBox;
    private JLabel facultyLabel;
    private void addSchoolYearInputField() {
        facultyImage = Image.BUILDER.getImageFrom("classroom.png", 55, 62);
        this.add(facultyImage);
        
        schoolYearComboBox = new JComboBox();
        schoolYearComboBox.setBackground(ColorDefine.BACKGROUND);
        schoolYearComboBox.setEditable(false);
        schoolYearComboBox.setFont(FontDefine.HINT);
        schoolYearComboBox.setToolTipText(StringDefine.HINT_SCHOOL_YEAR);
        components.put("SchoolYearComboBox", schoolYearComboBox);
        this.add(schoolYearComboBox);
        
        facultyLabel = new JLabel("Khoa CÔNG NGHỆ THÔNG TIN");
        facultyLabel.setFont(FontDefine.FACULTY);
        facultyLabel.setForeground(ColorDefine.HINT);
        facultyLabel.setBackground(ColorDefine.HINT_BKG);
        components.put("FacultyLabel", facultyLabel);
        this.add(facultyLabel);
    }
    private void setSchoolYearConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // facultyImage
        layout.putConstraint(
                SpringLayout.NORTH, facultyImage,
                55,
                SpringLayout.SOUTH, studentNameImage);
        layout.putConstraint(
                SpringLayout.WEST, facultyImage,
                22,
                SpringLayout.WEST, this);
        // schoolYearComboBox
        layout.putConstraint(
                SpringLayout.SOUTH, schoolYearComboBox,
                0,
                SpringLayout.SOUTH, facultyImage);
        layout.putConstraint(
                SpringLayout.WEST, schoolYearComboBox,
                20,
                SpringLayout.EAST, facultyImage);
        layout.putConstraint(
                SpringLayout.EAST, schoolYearComboBox,
                -280,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.NORTH, schoolYearComboBox,
                9,
                SpringLayout.SOUTH, facultyLabel);
        // facultyLabel
        layout.putConstraint(
                SpringLayout.NORTH, facultyLabel,
                5,
                SpringLayout.NORTH, facultyImage);
        layout.putConstraint(
                SpringLayout.WEST, facultyLabel,
                20,
                SpringLayout.EAST, facultyImage);
    }
    
    // Birthday
    private JLabel calendarImage;
    private JDatePickerImpl datePiker;
    private JLabel hintDateLabel;
    private void addCalendarView() {
        calendarImage = Image.BUILDER.getImageFrom("calendar.png", 60, 75);
        this.add(calendarImage);
        
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        DateFormatter dateFormatter = new DateFormatter(null);
        components.put("DateFormatter", dateFormatter);
        UtilDateModel startDateModel = new UtilDateModel();
        startDateModel.setSelected(true);
        datePiker = new JDatePickerImpl(
                new JDatePanelImpl(startDateModel, p),
                dateFormatter);
        datePiker.setPreferredSize(SizeDefine.DATE_PICKER);
        components.put("DatePicker", datePiker);
        this.add(datePiker);
        
        hintDateLabel = new JLabel(StringDefine.HINT_BIRTHDAY);
        hintDateLabel.setFont(FontDefine.HINT);
        hintDateLabel.setForeground(ColorDefine.HINT);
        hintDateLabel.setBackground(ColorDefine.HINT_BKG);
        this.add(hintDateLabel);
    }
    private void setCalendarConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // calendarImage
        layout.putConstraint(
                SpringLayout.NORTH, calendarImage,
                55,
                SpringLayout.SOUTH, facultyImage);
        layout.putConstraint(
                SpringLayout.WEST, calendarImage,
                20,
                SpringLayout.WEST, this);
        // datePiker
        layout.putConstraint(
                SpringLayout.NORTH, datePiker,
                16,
                SpringLayout.NORTH, calendarImage);
        layout.putConstraint(
                SpringLayout.WEST, datePiker,
                20,
                SpringLayout.EAST, calendarImage);
        // hintDateLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintDateLabel,
                -7,
                SpringLayout.SOUTH, calendarImage);
        layout.putConstraint(
                SpringLayout.WEST, hintDateLabel,
                20,
                SpringLayout.EAST, calendarImage);
    }
    
}
