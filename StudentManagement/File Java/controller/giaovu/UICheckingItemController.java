package controller.giaovu;

import controller.UICheckBoxController;
import controller.UICheckBoxController.Message;
import controller.UIController;
import controller.dao.AttendanceDAO;
import controller.dao.SubjectStudentDAO;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.AttendanceTable;
import model.pojo.Subject;
import util.DateFormatter;
import util.DateProcess;
import view.giaovu.UICheckingItemView;

public class UICheckingItemController extends UIController {
    
    private JLabel nameLabel;
    private JPanel checkBoxPanel;
    
    public UICheckingItemController(UICheckingItemView uiCheckingItemView) {
        super(uiCheckingItemView);
        init();
    }
    
    public UICheckingItemController() {
        super(new UICheckingItemView());
        init();
    }
    
    private void init() {
        nameLabel = (JLabel)uiView.findViewById("NameLabel");
        checkBoxPanel = (JPanel)uiView.findViewById("CheckBoxPanel");
    }
    
    public void setAttendanceTable(String name, Subject subject, AttendanceTable attendanceTable) {
        nameLabel.setText(name); // studentID
        UICheckBoxController uiCheckBoxController;
        checkBoxPanel.setLayout(new GridLayout(1, -1, 0, 20));
        Date date;
        String toolTipText;
        Date startDate = subject.getStartTime();
        int size = DateProcess.distanceOf(startDate, subject.getEndTime());
        for (int i = 0; i < size; ++i) {
            date = DateProcess.addDay(startDate, i * 7);
            uiCheckBoxController = new UICheckBoxController();
            uiCheckBoxController.setType(attendanceTable.getMarkTypeAt(date));
            uiCheckBoxController.setNote(String.valueOf(i + 1));
            toolTipText = String.format("%s (tuần %d)", DateFormatter.stringOf(date), i + 1);
            uiCheckBoxController.setToolTipText(toolTipText);
            uiCheckBoxController.setCanChangeAfterEditing(true);
            uiCheckBoxController.setMouseClickListener((Message message) -> {
                return editAttendance(message, subject, attendanceTable);
            });
            checkBoxPanel.add(uiCheckBoxController.getContentView());
        }
        
        checkBoxPanel.validate();
    }
    
    private boolean editAttendance(Message message, Subject subject, AttendanceTable attendanceTable) {
        if (subject.isExpired()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Môn học đã kết thúc, bạn không thể thây đổi thông tin điểm danh được",
                    "Không thể thây đổi thông tin điểm danh",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        } else {
            String studentID = nameLabel.getText();
            int attendanceID = SubjectStudentDAO.BUILDER.getAttendanceID(
                    subject.getCode(),
                    subject.getNameId(),
                    studentID
            );

            int week = Integer.parseInt(message.getMessage()) - 1;
            Date attendanceDate = DateProcess.addDay(subject.getStartTime(), 7 * week);

            if (message.getType() == AttendanceTable.MarkType.CHECKED) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(attendanceDate);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                attendanceTable.sureMarkAt(calendar.getTime(), subject, studentID, attendanceID);
            } else { // EXPIRED
                AttendanceDAO.BUILDER.delete(attendanceID, attendanceDate);
    //            int currentWeek = (int)((attendanceDate.getTime() - subject.getStartTime().getTime()) /
    //                (1000 * 60 * 60 * 24 * 7)) + 1;
    //            SubjectStudentDAO.BUILDER.updateAttendancePercent(
    //                    currentWeek,
    //                    subject.getCode(),
    //                    subject.getNameId()
    //            );
            }
            
            return true;
        }
    }
    
}
