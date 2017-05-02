package controller.giaovu;

import controller.giaovu.subwindow.UIAddingStudentController;
import controller.giaovu.subwindow.UIStudentsListController;
import controller.giaovu.subwindow.UIImportingFileController;
import app.Application;
import controller.UIController;
import controller.UIFilterListController;
import controller.UISubWindowController;
import controller.dao.SubjectStudentDAO;
import controller.giaovu.subwindow.UIInputTypeController;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import model.AttendanceTable;
import model.pojo.Student;
import model.pojo.Subject;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import util.Image;
import util.Result.ResponseReceiver;
import view.UIFilterListView;

public class UICheckingListController extends UIController {
    public enum ResultType {
        BACK
    }
    
    private JLabel backImage;
    private JButton addingStudentButton;
    
    private UIFilterListController uiFilterListController;
    
    public UICheckingListController(UIFilterListView uiFilterListView) {
        super(uiFilterListView);
        init();
    }
    
    public UICheckingListController() {
        super(new UIFilterListView());
        init();
    }
    
    private void init() {
        responseReceiver = null;
        
        uiFilterListController = new UIFilterListController((UIFilterListView)uiView);
        
        setBackButton();
        setAddingStudentButton();
        setFooter();
        
        addListenerForViews();
    }
    
    private void setBackButton() {
        backImage = Image.BUILDER.getImageFrom("back.png", 50, 25);
        backImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        uiFilterListController.addLeftPanel(backImage);
    }
    
    private void setAddingStudentButton() {
        addingStudentButton = new JButton(StringDefine.ADDING_STUDENT_BUTTON);
        addingStudentButton.setPreferredSize(SizeDefine.ADDING_STUDENT_BUTTON);
        addingStudentButton.setFont(FontDefine.BUTTON);
        addingStudentButton.setForeground(ColorDefine.ADDING_STUDENT_BTN_TEXT);
        addingStudentButton.setBackground(ColorDefine.ADDING_STUDENT_BTN_BKG);
        
        uiFilterListController.addRightPanel(addingStudentButton);
    }
    
    private void setFooter() {
        JLabel noteLabel;
        
        noteLabel = new JLabel("Đã điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel checkedImage = new JPanel(new GridLayout(1, 1));
        checkedImage.setPreferredSize(new Dimension(180, 50));
        checkedImage.setBackground(ColorDefine.CHECKED_BOX_BKG);
        checkedImage.add(noteLabel);
        
        noteLabel = new JLabel("Không điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel expiredImage = new JPanel(new GridLayout(1, 1));
        expiredImage.setPreferredSize(new Dimension(180, 50));
        expiredImage.setBackground(ColorDefine.EXPIRED_BOX_BKG);
        expiredImage.add(noteLabel);
        
        noteLabel = new JLabel("Chưa điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel uncheckedImage = new JPanel(new GridLayout(1, 1));
        uncheckedImage.setPreferredSize(new Dimension(180, 50));
        uncheckedImage.setBackground(ColorDefine.UNCHECKED_BOX_BKG);
        uncheckedImage.add(noteLabel);
        
        JPanel footerPanel = new JPanel();
        footerPanel.add(checkedImage);
        footerPanel.add(expiredImage);
        footerPanel.add(uncheckedImage);
        
        uiFilterListController.setFooter(footerPanel);
        
        SpringLayout footerLayout = new SpringLayout();
        footerPanel.setLayout(footerLayout);
        
        // checkedImage
        footerLayout.putConstraint(
                SpringLayout.NORTH, checkedImage,
                0,
                SpringLayout.NORTH, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.WEST, checkedImage,
                25,
                SpringLayout.WEST, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.SOUTH, checkedImage,
                0,
                SpringLayout.SOUTH, footerPanel);
        // expiredImage
        footerLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, expiredImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.NORTH, expiredImage,
                0,
                SpringLayout.NORTH, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.SOUTH, expiredImage,
                0,
                SpringLayout.SOUTH, footerPanel);
        // uncheckedImage
        footerLayout.putConstraint(
                SpringLayout.NORTH, uncheckedImage,
                0,
                SpringLayout.NORTH, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.EAST, uncheckedImage,
                -25,
                SpringLayout.EAST, footerPanel);
        footerLayout.putConstraint(
                SpringLayout.SOUTH, uncheckedImage,
                0,
                SpringLayout.SOUTH, footerPanel);
    }

    private void addListenerForViews() {
        backImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (responseReceiver != null) {
                    responseReceiver.receiveResult(ResultType.BACK);
                }
            }
        });
        
        addingStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                Application.getInstance().setEnabled(false);
                openSubWindow(
                        StringDefine.ADDING_STUDENT_CHOICES,
                        new UIInputTypeController()
                );
            }
        });
    }
    
    private Subject subject;
    
    public void setSubject(Subject subject) {
        this.subject = subject;
        uiFilterListController.setTitle("THÔNG TIN ĐIỂM DANH CỦA MÔN HỌC");
        
//        double percent = subject.getPercentAttendance();
//        if ((percent - (int)(percent)) * 100 == 0) {
//            uiFilterListController.setTitle(String.format("%.0f%% sinh viên đi học", percent));
//        } else {
//            uiFilterListController.setTitle(String.format("%.2f%% sinh viên đi học", percent));
//        }
//        
//        if (percent < Subject.PERCENT_MINIMUM) {
//            uiFilterListController.setTitleBackground(ColorDefine.INVALID_BKG);
//            uiFilterListController.setTitleBackground(ColorDefine.INVALID_BKG);
//        } else {
//            uiFilterListController.setTitleBackground(ColorDefine.VALID_BKG);
//            uiFilterListController.setTitleBackground(ColorDefine.VALID_BKG);
//        }
    }
    
    public void depose() {
        subject = null;
        isNeedStopLoading = true;
        while (isLoading) { }
        clear();
    }
    
    private UIInputTypeController.ResultType prevResultType;
    private void receiveResultFromSubWindow(Object object) {
        this.focus();
        if (object instanceof UIInputTypeController.ResultType) {
            UIInputTypeController.ResultType result = (UIInputTypeController.ResultType)object;
            switch (result) {
                case INPUT_BY_ADDING:
                    openSubWindow(StringDefine.ADDING_NEW_TITLE, new UIAddingStudentController());
                    break;
                case INPUT_FROM_LIST:
                    prevResultType = result;
                    openSubWindow(StringDefine.ADDING_FROM_LIST_TITLE, new UIStudentsListController());
                    break;
                case INPUT_FROM_FILE:
                    prevResultType = result;
                    openSubWindow(StringDefine.ADDING_FROM_FILE_TITLE, new UIImportingFileController());
                    break;
                case NONE:
                    Application.getInstance().setEnabled(true);
                    break;
            }
        } else {
            if (object instanceof Student) { // result from INPUT_BY_ADDING
                if (responseReceiver != null) {
                    Student student = (Student)object;
                    this.addStudent(student);
                    responseReceiver.receiveResult(new Object[]{subject, 1});
                }
            } else if (object instanceof Student[]) { // result from INPUT_FROM_LIST or INPUT_FROM_FILE
                Student[] students = (Student[])object;
                if (prevResultType == UIInputTypeController.ResultType.INPUT_FROM_FILE) {
                    prevResultType = UIInputTypeController.ResultType.INPUT_FROM_LIST;
                    students = filterStudentNotStudy(students);
                    if (students != null && students.length > 0) {
                        UIStudentsListController uiStudentsListController = new UIStudentsListController();
                        uiStudentsListController.setStudents(students);
                        openSubWindow(StringDefine.ADDING_FROM_LIST_TITLE, uiStudentsListController);
                    }
                } else {
                    this.addStudents(students);
                    if (responseReceiver != null) {
                        responseReceiver.receiveResult(new Object[]{subject, students.length});
                    }
                }
            }
        }
    }
    
    private Student[] filterStudentNotStudy(Student[] students) {
        Student[] filterStudents = SubjectStudentDAO.BUILDER.filterStudentNotStudy(
                subject.getCode(),
                students
        );
        
        String message = "%s\n  + Đã lấy được %d sinh viên\n  + Đã bỏ qua %d sinh viên";
        
        int nIgnoreStudents = students.length - filterStudents.length;
        if (nIgnoreStudents == students.length ) {
            message = String.format(message,
                    "Có vẻ như toàn bộ sinh viên bạn cần thêm đã có tên trong môn học này!",
                    0,
                    nIgnoreStudents
            );
        } else {
            message = String.format(message,
                    "Đã lấy được một số (hoặc  tất cả sinh viên).\n"
                            + "Một số thông tin không lấy được (nếu có) là vì sinh viên đó đã có tên trong môn học này hoặc không được điền đầy đủ thông tin",
                    filterStudents.length,
                    nIgnoreStudents);
        }
        
        JOptionPane.showMessageDialog(
                null,
                message,
                "Thông báo kết quả import",
                JOptionPane.INFORMATION_MESSAGE
        );
        
        return filterStudents;
    }
    
    private void openSubWindow(String title,
            UISubWindowController uiSubWindowController) {
        uiSubWindowController.setTitle(title);
        uiSubWindowController.setNote(subject.getCode());
        uiSubWindowController.show();
        uiSubWindowController.setResponseReceiver((Object obj) -> {
            receiveResultFromSubWindow(obj);
        });
    }
    
    private void focus() {
        Application.getInstance().setVisible(true);
        Application.getInstance().requestFocusInWindow();
    }
    
    private volatile boolean isLoading;
    private volatile boolean isNeedStopLoading;
    public void addStudents(Student[] students) {
        isLoading = true;
        isNeedStopLoading = false;
        
        if (students.length > 0) {
            uiFilterListController.setLoadingMode(true);
            for (Student student : students) {
                addStudent(student);
                if (isNeedStopLoading) {
                    break;
                }
            }
            uiFilterListController.setLoadingMode(false);
        }
        
        isLoading = false;
    }
    
    private void addStudent(Student student) {
        String subjectCode = subject.getCode();
        String studentCode = student.getCode();
        AttendanceTable attendanceTable = AttendanceTable.getFromDAO(
                subjectCode,
                subject.getNameId(),
                studentCode
        );
        if (attendanceTable == null) { // the student doesn't study at the subject
            attendanceTable = AttendanceTable.createNew(
                    student,
                    subjectCode,
                    subject.getNameId()
            );
        }
        
        addAttendanceTableToList(studentCode, attendanceTable);
        uiFilterListController.validate();
    }
    
    private void addAttendanceTableToList(String name, AttendanceTable attendanceTable) {
        UICheckingItemController uiCheckingItemController = new UICheckingItemController();
        uiCheckingItemController.setAttendanceTable(name, subject, attendanceTable);
        uiFilterListController.addItem(uiCheckingItemController.getContentView());
        
    }
    
    private ResponseReceiver responseReceiver;
    
    public void setResponseReceiver(ResponseReceiver responseReceiver) {
        this.responseReceiver = responseReceiver;
    }
    
    public void removeAt(int row) {
        uiFilterListController.removeAt(row);
    }
    
    public void clear() {
        if (getItemCount() > 0) {
            uiFilterListController.clear();
        }
    }
    
    public int getItemCount() {
        return uiFilterListController.getItemCount();
    }
    
}
