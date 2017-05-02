package controller.giaovu.subwindow;

import controller.UISubWindowController;
import controller.dao.SubjectStudentDAO;
import controller.giaovu.subwindow.UIInputTypeController.ResultType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.pojo.Student;
import model.define.StringDefine;
import org.jdatepicker.impl.JDatePickerImpl;
import view.giaovu.subwindow.UIAddingStudentView;

public class UIAddingStudentController extends UISubWindowController {
    
    private final JButton addingButton;
    private final JTextField studentCodeTextField;
    private final JTextField studentNameTextField;
    private final JLabel facultyLabel;
    private final JComboBox schoolYearComboBox;
    private final JDatePickerImpl datePicker;
    
    public UIAddingStudentController() {
        super(new UIAddingStudentView());
        super.centerWindow();
        
        addingButton = (JButton)uiView.findViewById("AddingButton");
        studentCodeTextField = (JTextField)uiView.findViewById("StudentCodeTextField");
        studentNameTextField = (JTextField)uiView.findViewById("StudentNameTextField");
        facultyLabel = (JLabel)uiView.findViewById("FacultyLabel");
        schoolYearComboBox = (JComboBox)uiView.findViewById("SchoolYearComboBox");
        datePicker = (JDatePickerImpl)uiView.findViewById("DatePicker");
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        for (int i = year - 6; i <= year; ++i) {
            schoolYearComboBox.addItem(i);
        }
        
        addListenersForViews();
    }
    
    private void addListenersForViews() {
        addingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isCompleted()) {
                    if (JOptionPane.showConfirmDialog(
                            null,
                            "Bạn chắc chắn muốn thêm sinh viên này?",
                            "Xác nhận thêm sinh viên",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (receiver != null) {
                            String faculty = facultyLabel.getText();
                            faculty = faculty.substring(faculty.indexOf(' ') + 1);
                            receiver.receiveResult(new Student(
                                    studentCodeTextField.getText(),
                                    studentNameTextField.getText(),
                                    (int)schoolYearComboBox.getSelectedItem(),
                                    faculty,
                                    (Date)datePicker.getModel().getValue())
                            );
                        }

                        close();
                    }
                }
            }
        });
    }
    
    private boolean isCompleted() {
        boolean isCompleted = true;
        Date birthday = (Date)datePicker.getModel().getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        int year = calendar.get(Calendar.YEAR);
        calendar.setTime(new Date());
        
        String messageTitle = null;
        String message = null;
        if (studentCodeTextField.getText().trim().length() == 0) {
            messageTitle = StringDefine.ALERT_STUDENT_CODE_EMPTY_TITLE;
            message = StringDefine.ALERT_STUDENT_CODE_EMPTY;
            isCompleted = false;
        } else if (studentNameTextField.getText().trim().length() == 0) {
            messageTitle = StringDefine.ALERT_STUDENT_NAME_EMPTY_TITLE;
            message = StringDefine.ALERT_STUDENT_NAME_EMPTY;
            isCompleted = false;
        } else if (calendar.get(Calendar.YEAR) - year < 18) {
            messageTitle = StringDefine.ALERT_STUDENT_BIRTHDAY_INVALID_TITLE;
            message = StringDefine.ALERT_STUDENT_BIRTHDAY_INVALID;
            isCompleted = false;
        } else if (SubjectStudentDAO.BUILDER.isExisted(
                super.getNote(), //Subject code
                studentCodeTextField.getText().trim())) {
            messageTitle = "Trùng mã số sinh viên";
            message = "Có vẻ như sinh viên bạn cần thêm ĐÃ ĐĂNG KÝ môn học này";
            isCompleted = false;
        }
        
        if (message != null) {
            JOptionPane.showMessageDialog(
                    null,
                    message,
                    messageTitle,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        return isCompleted;
    }
    
    @Override
    public void show() {
        super.show();
        
        studentCodeTextField.setVisible(true);
        studentCodeTextField.requestFocusInWindow();
    }
    
    @Override
    public void close() {
        super.close();
        close(ResultType.NONE);
    }
    
    private void close(ResultType result) {
        receiver.receiveResult(result);
    }
    
}
