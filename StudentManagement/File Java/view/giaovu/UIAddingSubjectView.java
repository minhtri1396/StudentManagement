package view.giaovu;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import util.Image;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import util.JTextFieldLimit;
import model.define.ColorDefine;
import model.Rectangle;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import view.UIView;
import util.DateFormatter;

public class UIAddingSubjectView extends UIView {

    public UIAddingSubjectView() {
        super();
        super.setPreferredSize(SizeDefine.ADDING_SUBJECT_FRAME);
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private void createComponents() {
        addTitleView();
        addButtons();
        addSubjectCodeInputField();
        addSubjectNameInputField();
        addClassroomInputField();
        addCalendarView();
        addFooterView();
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        setTitleConstraints();
        setButtonsConstraints();
        setSubjectCodeInputFieldConstraints();
        setSubjectNameInputFieldConstraints();
        setClassroomConstraints();
        setCalendarConstraints();
        setFooterConstraints();
    }
    
    // Title
    private JLabel titleLabel;
    private Rectangle titleBkg;
    private void addTitleView() {
        titleLabel = new JLabel(StringDefine.ADDING_SUBJECT_TITLE);
        titleLabel.setFont(FontDefine.TITLE);
        titleLabel.setForeground(ColorDefine.TITLE);
        titleLabel.setBackground(ColorDefine.TITLE_BKG);
        titleLabel.setOpaque(true);
        this.add(titleLabel);
        
        titleBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.TITLE_BKG);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // titleLabel
        layout.putConstraint(
                SpringLayout.NORTH, titleLabel,
                10,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        // titleBkg
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleBkg,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(
                SpringLayout.NORTH, titleBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, titleBkg,
                10,
                SpringLayout.SOUTH, titleLabel);
        layout.putConstraint(
                SpringLayout.WEST, titleBkg,
                25,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, titleBkg,
                -25,
                SpringLayout.EAST, this);
    }
    
    // Buttons
    private JButton addingButton;
    private JButton cancelButton;
    private void addButtons() {
        addingButton = new JButton(StringDefine.ADDING_BUTTON);
        addingButton.setPreferredSize(SizeDefine.ADDING_BUTTON);
        addingButton.setFont(FontDefine.BUTTON);
        addingButton.setForeground(ColorDefine.ADDING_BTN_TEXT);
        addingButton.setBackground(ColorDefine.ADDING_BTN_BKG);
        components.put("AddingButton", addingButton);
        this.add(addingButton);
        
        cancelButton = new JButton(StringDefine.CANCEL_SUBJECT_BUTTON);
        cancelButton.setPreferredSize(SizeDefine.ADDING_BUTTON);
        cancelButton.setFont(FontDefine.BUTTON);
        cancelButton.setForeground(ColorDefine.CANCEL_BTN_TEXT);
        cancelButton.setBackground(ColorDefine.CANCEL_BTN_BKG);
        components.put("CancelButton", cancelButton);
        this.add(cancelButton);
    }
    private void setButtonsConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // addingButton
        layout.putConstraint(
                SpringLayout.NORTH, addingButton,
                35,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.EAST, addingButton,
                -10,
                SpringLayout.EAST, this);
        // cancelButton
        layout.putConstraint(
                SpringLayout.NORTH, cancelButton,
                35,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.EAST, cancelButton,
                -10,
                SpringLayout.WEST, addingButton);
    }
    
    // Subject code
    private JLabel subjCodeImage;
    private JTextField subjCodeTextField;
    private JLabel hintSubjCodeLabel;
    private void addSubjectCodeInputField() {
        subjCodeImage = Image.BUILDER.getImageFrom("subject_code.png", 60, 60);
        this.add(subjCodeImage);
        
        subjCodeTextField = new JTextField();
        subjCodeTextField.setDocument(new JTextFieldLimit(7));
        subjCodeTextField.setFont(FontDefine.TEXT_FIELD);
        components.put("SubjectCodeTextField", subjCodeTextField);
        this.add(subjCodeTextField);
        
        hintSubjCodeLabel = new JLabel(StringDefine.HINT_SUBJECT_CODE);
        hintSubjCodeLabel.setFont(FontDefine.HINT);
        hintSubjCodeLabel.setForeground(ColorDefine.HINT);
        hintSubjCodeLabel.setBackground(ColorDefine.HINT_BKG);
        hintSubjCodeLabel.setOpaque(true);
        this.add(hintSubjCodeLabel);
    }
    private void setSubjectCodeInputFieldConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // subjCodeImage
        layout.putConstraint(
                SpringLayout.NORTH, subjCodeImage,
                25,
                SpringLayout.SOUTH, cancelButton);
        layout.putConstraint(
                SpringLayout.WEST, subjCodeImage,
                20,
                SpringLayout.WEST, this);
        // subjCodeTextField
        layout.putConstraint(
                SpringLayout.NORTH, subjCodeTextField,
                5,
                SpringLayout.NORTH, subjCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, subjCodeTextField,
                20,
                SpringLayout.EAST, subjCodeImage);
        layout.putConstraint(
                SpringLayout.EAST, subjCodeTextField,
                -180,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, subjCodeTextField,
                -7,
                SpringLayout.NORTH, hintSubjCodeLabel);
        // hintSubjCodeLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintSubjCodeLabel,
                2,
                SpringLayout.SOUTH, subjCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, hintSubjCodeLabel,
                20,
                SpringLayout.EAST, subjCodeImage);
    }
    
    // Subject name
    private JLabel subjNameImage;
    private JTextField subjNameTextField;
    private JLabel hintSubjNameLabel;
    private void addSubjectNameInputField() {
        subjNameImage = Image.BUILDER.getImageFrom("subject_name.png", 65, 70);
        this.add(subjNameImage);
        
        subjNameTextField = new JTextField();
        subjNameTextField.setFont(FontDefine.TEXT_FIELD);
        subjNameTextField.setDocument(new JTextFieldLimit(50));
        components.put("SubjectNameTextField", subjNameTextField);
        this.add(subjNameTextField);
        
        hintSubjNameLabel = new JLabel(StringDefine.HINT_SUBJECT_NAME);
        hintSubjNameLabel.setFont(FontDefine.HINT);
        hintSubjNameLabel.setForeground(ColorDefine.HINT);
        hintSubjNameLabel.setBackground(ColorDefine.HINT_BKG);
        hintSubjNameLabel.setOpaque(true);
        this.add(hintSubjNameLabel);
    }
    private void setSubjectNameInputFieldConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // subjNameImage
        layout.putConstraint(
                SpringLayout.NORTH, subjNameImage,
                40,
                SpringLayout.SOUTH, subjCodeImage);
        layout.putConstraint(
                SpringLayout.WEST, subjNameImage,
                16,
                SpringLayout.WEST, this);
        // subjNameTextField
        layout.putConstraint(
                SpringLayout.NORTH, subjNameTextField,
                10,
                SpringLayout.NORTH, subjNameImage);
        layout.putConstraint(
                SpringLayout.WEST, subjNameTextField,
                20,
                SpringLayout.EAST, subjNameImage);
        layout.putConstraint(
                SpringLayout.EAST, subjNameTextField,
                -20,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, subjNameTextField,
                -7,
                SpringLayout.NORTH, hintSubjNameLabel);
        // hintSubjNameLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintSubjNameLabel,
                -5,
                SpringLayout.SOUTH, subjNameImage);
        layout.putConstraint(
                SpringLayout.WEST, hintSubjNameLabel,
                20,
                SpringLayout.EAST, subjNameImage);
    }
    
    // Classroom
    private JLabel classroomImage;
    private JTextField classroomTextField;
    private JLabel hintClassroomLabel;
    private void addClassroomInputField() {
        classroomImage = Image.BUILDER.getImageFrom("classroom.png", 55, 62);
        this.add(classroomImage);
        
        classroomTextField = new JTextField();
        classroomTextField.setDocument(new JTextFieldLimit(5));
        classroomTextField.setFont(FontDefine.TEXT_FIELD);
        components.put("ClassroomTextField", classroomTextField);
        this.add(classroomTextField);
        
        hintClassroomLabel = new JLabel(StringDefine.HINT_CLASSROOM);
        hintClassroomLabel.setFont(FontDefine.HINT);
        hintClassroomLabel.setForeground(ColorDefine.HINT);
        hintClassroomLabel.setBackground(ColorDefine.HINT_BKG);
        this.add(hintClassroomLabel);
    }
    private void setClassroomConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // classroomImage
        layout.putConstraint(
                SpringLayout.NORTH, classroomImage,
                45,
                SpringLayout.SOUTH, subjNameImage);
        layout.putConstraint(
                SpringLayout.WEST, classroomImage,
                22,
                SpringLayout.WEST, this);
        // classroomTextField
        layout.putConstraint(
                SpringLayout.NORTH, classroomTextField,
                5,
                SpringLayout.NORTH, classroomImage);
        layout.putConstraint(
                SpringLayout.WEST, classroomTextField,
                20,
                SpringLayout.EAST, classroomImage);
        layout.putConstraint(
                SpringLayout.EAST, classroomTextField,
                -280,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, classroomTextField,
                -9,
                SpringLayout.NORTH, hintClassroomLabel);
        // hintClassroomLabel
        layout.putConstraint(
                SpringLayout.SOUTH, hintClassroomLabel,
                0,
                SpringLayout.SOUTH, classroomImage);
        layout.putConstraint(
                SpringLayout.WEST, hintClassroomLabel,
                20,
                SpringLayout.EAST, classroomImage);
    }
    
    // Calendar
    private JLabel calendarImage;
    private JLabel dateLabel;
    private JDatePickerImpl startDatePiker;
    private JLabel toLabel;
    private JTextField endDateTextField;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private void addCalendarView() {
        calendarImage = Image.BUILDER.getImageFrom("calendar.png", 60, 75);
        this.add(calendarImage);
        
        dateLabel = new JLabel();
        dateLabel.setFont(FontDefine.DATE);
        dateLabel.setForeground(ColorDefine.DATE);
        dateLabel.setBackground(ColorDefine.DATE_BKG);
        dateLabel.setOpaque(true);
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setToolTipText(StringDefine.HINT_SUBJECT_DATE_OF_WEEK);
        components.put("DateLabel", dateLabel);
        this.add(dateLabel);
        
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        DateFormatter dateFormatter = new DateFormatter(null);
        components.put("DateFormatter", dateFormatter);
        UtilDateModel startDateModel = new UtilDateModel();
        startDateModel.setSelected(true);
        startDatePiker = new JDatePickerImpl(
                new JDatePanelImpl(startDateModel, p),
                dateFormatter);
        startDatePiker.setPreferredSize(SizeDefine.DATE_PICKER);
        components.put("StartDatePicker", startDatePiker);
        this.add(startDatePiker);
        
        endDateTextField = new JTextField();
        endDateTextField.setEditable(false);
        endDateTextField.setHorizontalAlignment(JTextField.CENTER);
        endDateTextField.setToolTipText(StringDefine.HINT_SUBJECT_END_DATE);
        components.put("EndDateTextField", endDateTextField);
        this.add(endDateTextField);
        
        toLabel = Image.BUILDER.getImageFrom("right_arrow.png", 25, 25);
        this.add(toLabel);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        javax.swing.text.DateFormatter formatter = 
                (javax.swing.text.DateFormatter)startTimeEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setValue(calendar.getTime());
        startTimeSpinner.setPreferredSize(SizeDefine.TIME_PICKER);
        startTimeSpinner.setToolTipText(StringDefine.HINT_SUBJECT_START_TIME);
        components.put("StartTimeSpinner", startTimeSpinner);
        this.add(startTimeSpinner);
        
        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        formatter = (javax.swing.text.DateFormatter)endTimeEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setValue(calendar.getTime());
        endTimeSpinner.setPreferredSize(SizeDefine.TIME_PICKER);
        endTimeSpinner.setToolTipText(StringDefine.HINT_SUBJECT_END_TIME);
        components.put("EndTimeSpinner", endTimeSpinner);
        this.add(endTimeSpinner);
    }
    private void setCalendarConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // calendarImage
        layout.putConstraint(
                SpringLayout.NORTH, calendarImage,
                50,
                SpringLayout.SOUTH, classroomImage);
        layout.putConstraint(
                SpringLayout.WEST, calendarImage,
                20,
                SpringLayout.WEST, this);
        // dateLabel
        layout.putConstraint(
                SpringLayout.NORTH, dateLabel,
                5,
                SpringLayout.SOUTH, calendarImage);
        layout.putConstraint(
                SpringLayout.WIDTH, dateLabel,
                0,
                SpringLayout.WIDTH, calendarImage);
        layout.putConstraint(
                SpringLayout.WEST, dateLabel,
                20,
                SpringLayout.WEST, this);
        // startDatePiker
        layout.putConstraint(
                SpringLayout.NORTH, startDatePiker,
                10,
                SpringLayout.NORTH, calendarImage);
        layout.putConstraint(
                SpringLayout.WEST, startDatePiker,
                20,
                SpringLayout.EAST, calendarImage);
        // toLabel
        layout.putConstraint(
                SpringLayout.NORTH, toLabel,
                -8,
                SpringLayout.SOUTH, startDatePiker);
        layout.putConstraint(
                SpringLayout.WEST, toLabel,
                10,
                SpringLayout.EAST, startDatePiker);
        // endDateTextField
        layout.putConstraint(
                SpringLayout.NORTH, endDateTextField,
                0,
                SpringLayout.NORTH, startDatePiker);
        layout.putConstraint(
                SpringLayout.SOUTH, endDateTextField,
                0,
                SpringLayout.SOUTH, startDatePiker);
        layout.putConstraint(
                SpringLayout.WEST, endDateTextField,
                10,
                SpringLayout.EAST, toLabel);
        layout.putConstraint(
                SpringLayout.EAST, endDateTextField,
                -20,
                SpringLayout.EAST, this);
        // startTimeSpinner
        layout.putConstraint(
                SpringLayout.WIDTH, startTimeSpinner,
                0,
                SpringLayout.WIDTH, startDatePiker);
        layout.putConstraint(
                SpringLayout.WEST, startTimeSpinner,
                0,
                SpringLayout.WEST, startDatePiker);
        layout.putConstraint(
                SpringLayout.SOUTH, startTimeSpinner,
                0,
                SpringLayout.SOUTH, calendarImage);
        // endTimeSpinner
        layout.putConstraint(
                SpringLayout.WIDTH, endTimeSpinner,
                0,
                SpringLayout.WIDTH, endDateTextField);
        layout.putConstraint(
                SpringLayout.WEST, endTimeSpinner,
                0,
                SpringLayout.WEST, endDateTextField);
        layout.putConstraint(
                SpringLayout.SOUTH, endTimeSpinner,
                0,
                SpringLayout.SOUTH, calendarImage);
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
