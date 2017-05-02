package controller.sinhvien;

import app.Application;
import controller.UIFilterListController;
import controller.UIController;
import controller.dao.LoginDAO.LoginType;
import controller.dao.SubjectStudentDAO;
import controller.dao.SubjectDAO;
import controller.login.subwindow.UIChangePasswordController;
import controller.sinhvien.subwindow.UIDetailAttendanceController;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.AttendanceTable;
import model.AttendanceTable.MarkType;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.pojo.Subject;
import model.define.StringDefine;
import util.DateProcess;
import util.Result;
import view.sinhvien.UIMainView;
import view.UIFilterListView;
import view.sinhvien.UINotificationsListView;

public class UIMainController extends UIController {
    
    private final UINotificationsListController uiNotificationsListController;
    private final UIFilterListController uiSubjectsListController;
    
    private final String studentID;
    
    public UIMainController(String studentID) {
        super(new UIMainView());
        
        this.studentID = studentID;
        
        uiNotificationsListController = new UINotificationsListController(
                (UINotificationsListView)uiView.findViewById("UINotificationsListView"));
        
        uiSubjectsListController = new UIFilterListController(
                (UIFilterListView)uiView.findViewById("UISubjectsListView"));
        uiSubjectsListController.setTitle(StringDefine.SUBJECTS_LIST_TITLE);
        
        addChangePasswordButton();
        addAllSubject();
    }
    
    private void addChangePasswordButton() {
        JLabel changePasswordButton = new JLabel(StringDefine.CHANGING_PASSWORD);
        changePasswordButton.setFont(FontDefine.BUTTON);
        changePasswordButton.setForeground(ColorDefine.CHANGING_PASS_BTN_TEXT);
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        uiSubjectsListController.addRightPanel(changePasswordButton);
        
        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UIChangePasswordController uiChangePasswordController =
                        new UIChangePasswordController();
                uiChangePasswordController.setInformation(studentID, LoginType.STUDENT);
                uiChangePasswordController.setResponseReceiver((Object obj) -> {
                    Application.getInstance().setVisible(true);
                    Application.getInstance().requestFocusInWindow();
                    Application.getInstance().setEnabled(true);
 
                });
                Application.getInstance().setEnabled(false);
                uiChangePasswordController.show();
            }
        });
    }
    
    private void addAllSubject() {
        Date currentDate = new Date();
        Subject[] subjects = SubjectStudentDAO.BUILDER.getSubjectsOf(studentID);
        for (Subject subject : subjects) {
            if (DateProcess.compare(subject.getEndTime(), currentDate) < 0) { // subject is expired
                subject.setExpired(true);
                SubjectDAO.BUILDER.update(subject);
                addToSubjectsList(subject);
            } else {
                AttendanceTable accAttendanceTable = AttendanceTable.getFromDAO(
                        subject.getCode(),
                        subject.getNameId(),
                        studentID
                );
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(subject.getStartTime());
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                calendar.setTime(currentDate);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                MarkType type = accAttendanceTable.getMarkTypeForStudentAt(calendar.getTime());
                if (type == MarkType.NORMAL) {
                    uiNotificationsListController.addSubject(subject);
                } else {
                    addToSubjectsList(subject);
                }
            }
        }
        
        addListenerForAttendancesList();
    }
    
    private void addToSubjectsList(Subject subject) {
        UISubjectItemController uiSubjectItemController = new UISubjectItemController();
        uiSubjectItemController.setSubject(subject);
        
        ((JLabel)uiSubjectItemController.getContentView().findViewById("SubjectCodeLabel"))
                .setFont(FontDefine.SUBJECT_INFO);
        
        
        uiSubjectItemController.setResponseReceiver((Object object) -> {
            if (object instanceof Subject) {
                Subject s = (Subject)object;
                itemClicked(s);
            }
        });

        uiSubjectsListController.addItem(uiSubjectItemController.getContentView());
    }
    
    private void itemClicked(Subject subject) {
        AttendanceTable attendanceTable = AttendanceTable.getFromDAO(
                subject.getCode(),
                subject.getNameId(),
                this.studentID
        );
        UIDetailAttendanceController uiDetailAttendanceController =
                new UIDetailAttendanceController();
        uiDetailAttendanceController.setInformation(subject, studentID, attendanceTable);
        uiDetailAttendanceController.setResponseReceiver((Object obj) -> {
            if (obj instanceof Result.ResponseReceiver) {
                ((Result.ResponseReceiver)obj).receiveResult(
                        this.markAt(subject, studentID, attendanceTable)
                );
            } else {
                Application.getInstance().setVisible(true);
                Application.getInstance().requestFocusInWindow();
                Application.getInstance().setEnabled(true);
            }
        });
        Application.getInstance().setEnabled(false);
        uiDetailAttendanceController.show();
    }
    
    private void addListenerForAttendancesList() {
        uiNotificationsListController.setResponseReceiver((Object object) -> {
            if (object instanceof Subject) {
                Subject subject = (Subject)object;
                this.addToSubjectsList(subject);
                AttendanceTable attendanceTable = AttendanceTable.getFromDAO(
                        subject.getCode(),
                        subject.getNameId(),
                        this.studentID
                );
                this.markAt(subject, studentID, attendanceTable);
            }
        });
    }
    
    private boolean markAt(Subject subject, String studentID, AttendanceTable attendanceTable) {
        boolean markedResult = attendanceTable.markFor(subject, studentID);
        
        if (markedResult) {
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Theo như thời gian trên server thì dường như đây không phải là lúc bạn có thể điểm danh môn này!",
                "Điểm danh không thành công",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        return markedResult;
    }
}
