package controller.giaovu;

import controller.UIController;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import model.Rectangle;
import model.pojo.Subject;
import view.giaovu.UISubjectItemView;

public class UISubjectItemController extends UIController {
    public interface MouseClickListener {
        void mouseClicked(String subjectCode, int id);
    }
    
//    private Rectangle leftColumnBkg;
    private JLabel subjCodeLabel;
    private JLabel countStudentLabel;
    private JTextPane subjNameTextPane;
    private Rectangle expiredTagBkg;
    private JLabel expiredLabel;
//    private Rectangle rightColumnBkg;
    private JLabel classroomLabel;
    
    private boolean isValid;
    
    public UISubjectItemController(UISubjectItemView uiSubjectItemView) {
        super(uiSubjectItemView);
        init();
    }
    
    public UISubjectItemController() {
        super(new UISubjectItemView());
        init();
    }
    
    private void init() {
        uiView.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
//        leftColumnBkg = (Rectangle) uiView.findViewById("LeftColumnBkg");
        subjCodeLabel = (JLabel) uiView.findViewById("SubjectCodeLabel");
        countStudentLabel = (JLabel) uiView.findViewById("CountStudentLabel");
        
        subjNameTextPane = (JTextPane) uiView.findViewById("SubjectNameTextPane");
        subjNameTextPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        expiredTagBkg = (Rectangle) uiView.findViewById("ExpiredTagBkg");
        expiredLabel = (JLabel) uiView.findViewById("ExpiredLabel");
        
//        rightColumnBkg = (Rectangle) uiView.findViewById("RightColumnBkg");
        classroomLabel = (JLabel) uiView.findViewById("ClassroomLabel");
        
        isValid = true;
        
        setExpired(false);
    }
    
    public void setSubject(Subject subject) {
        super.setId(subject.getNameId());
        subjCodeLabel.setText(subject.getCode());
        subjNameTextPane.setText(subject.getName());
        setNumberOfStudent(subject.getNumberStudents());
        setExpired(subject.isExpired());
        classroomLabel.setText(subject.getClassroom());
//        setPercentOfAttandance(subject.getPercentAttendance());
    }
    
    public String getSubjectCode() {
        return subjCodeLabel.getText();
    }
    
    public void setExpired(boolean isExpired) {
        expiredTagBkg.setVisible(isExpired);
        expiredLabel.setVisible(isExpired);
    }
    
    public void setNumberOfStudent(int n) {
        countStudentLabel.setText(String.format("%s sv", n));
    }
    
//    public void setPercentOfAttandance(double percent) {
//        if (percent < Subject.PERCENT_MINIMUM) {
//            if (isValid) {
//                isValid = false;
//                subjCodeLabel.setBackground(ColorDefine.INVALID_BKG);
//                countStudentLabel.setBackground(ColorDefine.INVALID_BKG);
//                leftColumnBkg.setColor(ColorDefine.INVALID_BKG);
//                classroomLabel.setBackground(ColorDefine.INVALID_BKG);
//                rightColumnBkg.setColor(ColorDefine.INVALID_BKG);
//            }
//        } else {
//            if (isValid){
//            } else {
//                isValid = true;
//                leftColumnBkg.setColor(ColorDefine.VALID_BKG);
//                rightColumnBkg.setColor(ColorDefine.VALID_BKG);
//            }
//        }
//        
//        if ((percent - (int)(percent)) * 100 == 0) {
//            classroomLabel.setText(String.format("%.0f%%", percent));
//        } else {
//            classroomLabel.setText(String.format("%.2f%%", percent));
//        }
//    }
    
    public void addMouseListener(MouseClickListener mcl) {
        uiView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                mcl.mouseClicked(subjCodeLabel.getText(), getId());
            }
        });
        subjNameTextPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                mcl.mouseClicked(subjCodeLabel.getText(), getId());
            }
        });
    }
    
}
