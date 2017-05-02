package controller.sinhvien.subwindow;

import controller.UICheckBoxController;
import controller.UICheckBoxController.Message;
import controller.UISubWindowController;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import model.AttendanceTable;
import model.AttendanceTable.MarkType;
import model.pojo.Subject;
import util.DateFormatter;
import util.DateProcess;
import util.Result;
import view.sinhvien.subwindow.UIDetailAttendanceView;

public class UIDetailAttendanceController extends UISubWindowController {
    
    private final JTextPane titleTextPane;
    private final JLabel dateLabel;
    private final JLabel weekLabel;
    private final JPanel checkBoxPanel;
    private final JLabel studyingTime;
    
    public UIDetailAttendanceController() {
        super(new UIDetailAttendanceView());
        super.centerWindow();
        super.setTitle("Thông tin điểm danh môn học");
        
        titleTextPane = (JTextPane)uiView.findViewById("TitleTextPane");
        dateLabel = (JLabel)uiView.findViewById("DateLabel");
        weekLabel = (JLabel)uiView.findViewById("WeekLabel");
        checkBoxPanel = (JPanel)uiView.findViewById("CheckBoxPanel");
        studyingTime = (JLabel)uiView.findViewById("StudyingTime");
    }
    
    public void setInformation(Subject subject, String studentCode, AttendanceTable attendanceTable) {
        titleTextPane.setText(subject.getName());
        dateLabel.setText(String.format("%s - %s",
                DateFormatter.stringOf(subject.getStartTime()),
                DateFormatter.stringOf(subject.getEndTime())
        ));
        studyingTime.setText(String.format("%s - %s",
                DateFormatter.timeAsStringOf(subject.getStartTime()),
                DateFormatter.timeAsStringOf(subject.getEndTime())
        ));
        
        setAttendanceTable(subject, attendanceTable);
    }
    
    private ArrayList<UICheckBoxController> uiCheckBoxControllers;
    public void setAttendanceTable(Subject subject, AttendanceTable attendanceTable) {
        uiCheckBoxControllers = new ArrayList<>();
        UICheckBoxController uiCheckBoxController;
        checkBoxPanel.setLayout(new GridLayout(1, -1, 0, 20));
        Date date;
        String toolTipText;
        Date startDate = subject.getStartTime();
        boolean isThisWeek = false;
        MarkType markedType;
        int size = DateProcess.distanceOf(startDate, subject.getEndTime());
        for (int i = 0; i < size; ++i) {
            date = DateProcess.addDay(startDate, i * 7);
            uiCheckBoxController = new UICheckBoxController();
            markedType = attendanceTable.getMarkTypeForStudentAt(date);
            if (markedType == MarkType.NOPE || markedType == MarkType.NORMAL) {
                if (isThisWeek) {
                } else {
                    isThisWeek = true;
                    weekLabel.setText(String.format("Lần điểm danh kế vào tuần %d (%s)",
                            i + 1,
                            DateFormatter.stringOf(date)
                    ));
                }
            }
            uiCheckBoxController.setType(markedType);
            uiCheckBoxController.setNote(String.valueOf(i + 1));
            toolTipText = String.format("%s (tuần %d)", DateFormatter.stringOf(date), i + 1);
            uiCheckBoxController.setToolTipText(toolTipText);
            checkBoxPanel.add(uiCheckBoxController.getContentView());
            uiCheckBoxControllers.add(uiCheckBoxController);
            uiCheckBoxController.setMouseClickListener((Message message) -> {
                if (receiver != null) {
                    receiver.receiveResult((Result.ResponseReceiver) (Object obj) -> {
                        boolean markedResult = (boolean)obj;
                        if (markedResult) {
                        } else {
                            uiCheckBoxControllers.get(Integer.parseInt(message.getMessage()) - 1)
                                    .setType(AttendanceTable.MarkType.EXPIRED);
                        }
                    });
                }
                
                return true;
            });
        }
        
        checkBoxPanel.revalidate();
        checkBoxPanel.repaint();
    }
    
    @Override
    public void close() {
        if (receiver != null) {
            receiver.receiveResult(null);
        }
    }
    
}
