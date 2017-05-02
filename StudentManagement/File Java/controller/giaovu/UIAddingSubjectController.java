package controller.giaovu;

import controller.UIController;
import java.awt.event.MouseAdapter;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.pojo.Subject;
import model.define.ColorDefine;
import model.define.StringDefine;
import org.jdatepicker.impl.JDatePickerImpl;
import view.giaovu.UIAddingSubjectView;
import util.DateFormatter;
import util.DateProcess;
import util.Result.ResponseReceiver;

public class UIAddingSubjectController extends UIController {
    
    private JButton addingButton;
    private JButton cancelButton;
    private JTextField subjCodeTextField;
    private JTextField subjNameTextField;
    private JTextField classroomTextField;
    private JLabel dateLabel;
    private JDatePickerImpl startDatePicker;
    private JTextField endDateTextField;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private DateFormatter dateFormatter;
    
    private ResponseReceiver subjectReceiver;
    
    public void setSubjectReceiver(ResponseReceiver subjectReceiver) {
        this.subjectReceiver = subjectReceiver;
    }
    
    public UIAddingSubjectController(UIAddingSubjectView uiAddingSubjectView) {
        super(uiAddingSubjectView);
        init();
    }
    
    public UIAddingSubjectController() {
        super(new UIAddingSubjectView());
        init();
    }
    
    private void init() {
        subjectReceiver = null;
        addingButton = (JButton)uiView.findViewById("AddingButton");
        cancelButton = (JButton)uiView.findViewById("CancelButton");
        subjCodeTextField = (JTextField)uiView.findViewById("SubjectCodeTextField");
        subjNameTextField = (JTextField)uiView.findViewById("SubjectNameTextField");
        classroomTextField = (JTextField)uiView.findViewById("ClassroomTextField");
        dateLabel = (JLabel)uiView.findViewById("DateLabel");
        startDatePicker = (JDatePickerImpl)uiView.findViewById("StartDatePicker");
        endDateTextField = (JTextField)uiView.findViewById("EndDateTextField");
        startTimeSpinner = (JSpinner)uiView.findViewById("StartTimeSpinner");
        endTimeSpinner = (JSpinner)uiView.findViewById("EndTimeSpinner");
        dateFormatter = (DateFormatter)uiView.findViewById("DateFormatter");
        
        addListenerForViews();
        disableCalcelButton();
    }
    
    private void addListenerForViews() {
        subjCodeTextField.getDocument().addDocumentListener(
                new TextFieldChangingListener(cancelButton));
        subjNameTextField.getDocument().addDocumentListener(
                new TextFieldChangingListener(cancelButton));
        classroomTextField.getDocument().addDocumentListener(
                new TextFieldChangingListener(cancelButton));
        startTimeSpinner.addChangeListener((ChangeEvent e) -> {
            enableCancelButtonIfCan();
        });
        endTimeSpinner.addChangeListener((ChangeEvent e) -> {
            enableCancelButtonIfCan();
        });
        
        dateFormatter.setChangedListener((Date date) -> {
            dateLabel.setText(DateProcess.getDayOfWeek(date));
            date = DateProcess.addDay(date, 98); // 98 days = 14 weeks + 1 = 15
            endDateTextField.setText(DateFormatter.stringOf(date));
            enableCancelButtonIfCan();
        });
        
        addingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                addSubject();
            }
        });
        
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Cancel or reset subject input form
                cancelSubject();
            }
        });
    }
    
    private void enableCancelButtonIfCan() {
        if (cancelButton.isEnabled()) {
        } else {
            cancelButton.setEnabled(true);
            cancelButton.setBackground(ColorDefine.CANCEL_BTN_BKG);
        }
    }
    
    private void disableCalcelButton() {
        cancelButton.setEnabled(false);
        cancelButton.setBackground(ColorDefine.BTN_DISABLE_BKG);
    }
    
    private void addSubject() {
        if (isCompletedAllTextFields()) {
            Date date1 = DateProcess.timeToDate((Date) startTimeSpinner.getValue());
            Date date2 = DateProcess.timeToDate((Date) endTimeSpinner.getValue());
            if (DateProcess.compare(date2, date1) > 0) {
                if (JOptionPane.showConfirmDialog(
                        null,
                        StringDefine.ALERT_ADDING_SUBJECT_FORM,
                        StringDefine.ALERT_ADDING_SUBJECT_FORM_TITLE,
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (subjectReceiver != null) {
                        subjectReceiver.receiveResult(new Subject(
                                subjCodeTextField.getText(), 
                                subjNameTextField.getText(), 
                                classroomTextField.getText(), 
                                DateProcess.combineDateAndTime(
                                        (Date)startDatePicker.getModel().getValue(), date1), 
                                DateProcess.combineDateAndTime(
                                        DateFormatter.valueOf(endDateTextField.getText()), date2)));
                    }
                    reset();
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        StringDefine.ALERT_TIME_ADDING_SUBJECT_ERROR,
                        StringDefine.ALERT_TIME_ADDING_SUBJECT_ERROR_TITLE,
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
    
    private void cancelSubject() {
        if (cancelButton.isEnabled()) {
            if (JOptionPane.showConfirmDialog(
                null,
                StringDefine.ALERT_RESET_ADDING_SUBJECT_FORM,
                StringDefine.ALERT_RESET_ADDING_SUBJECT_FORM_TITLE,
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                reset();
            }
        }
    }
    
    private boolean isCompletedAllTextFields() {
        String messageTitle = null;
        String message = null;
        if (subjCodeTextField.getText().trim().isEmpty()) {
            messageTitle = StringDefine.ALERT_SUBJECT_CODE_EMPTY_TITLE;
            message = StringDefine.ALERT_SUBJECT_CODE_EMPTY;
        } else if (subjNameTextField.getText().trim().isEmpty()) {
            messageTitle = StringDefine.ALERT_SUBJECT_NAME_EMPTY_TITLE;
            message = StringDefine.ALERT_SUBJECT_NAME_EMPTY;
        } else if (classroomTextField.getText().trim().isEmpty()) {
            messageTitle = StringDefine.ALERT_CLASSROOM_EMPTY_TITLE;
            message = StringDefine.ALERT_CLASSROOM_EMPTY;
        }
        
        if (message != null) {
            JOptionPane.showMessageDialog(
                    null,
                    message,
                    messageTitle,
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            return false;
        }
        
        return true;
    }
    
    private void reset() {
        subjCodeTextField.setText("");
        subjNameTextField.setText("");
        classroomTextField.setText("");
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startTimeSpinner.setValue(calendar.getTime());
        endTimeSpinner.setValue(calendar.getTime());
        
        calendar.setTime(new Date());
        
        startDatePicker.getModel().setDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        
        disableCalcelButton();
    }
    
}

class TextFieldChangingListener implements DocumentListener {
    
    private final JButton cancelButton;
    
    public TextFieldChangingListener(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (cancelButton.isEnabled()) {
        } else {
            cancelButton.setEnabled(true);
            cancelButton.setBackground(ColorDefine.CANCEL_BTN_BKG);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }
    
}