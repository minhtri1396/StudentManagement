package controller.giaovu;

import controller.UIController;
import controller.UIController;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import model.pojo.Subject;
import util.DateFormatter;
import util.DateProcess;
import view.giaovu.UISubjectInfoView;

public class UISubjectInfoController extends UIController {
    
    private JTextPane subjNameTextPane;
    private JLabel dateLabel;
    private JLabel subjCodeLabel;
    private JLabel classroomLabel;
    private JLabel numberOfStudent;
    private JLabel studyingTimeLabel;
    
    public UISubjectInfoController(UISubjectInfoView uiSubjectInfoView) {
        super(uiSubjectInfoView);
        init();
    }
    
    public UISubjectInfoController() {
        super(new UISubjectInfoView());
        init();
    }
    
    private void init() {
        subjNameTextPane = (JTextPane)uiView.findViewById("SubjectNameTextPane");
        dateLabel = (JLabel)uiView.findViewById("DateLabel");
        subjCodeLabel = (JLabel)uiView.findViewById("SubjectCodeLabel");
        classroomLabel = (JLabel)uiView.findViewById("ClassroomLabel");
        numberOfStudent = (JLabel)uiView.findViewById("NumberOfStudent");
        studyingTimeLabel = (JLabel)uiView.findViewById("StudyingTimeLabel");
    }
    
    private Subject subject;
    
    public void depose() {
        subject = null;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
        
        subjNameTextPane.setText(subject.getName());
        subjCodeLabel.setText(subject.getCode());
        classroomLabel.setText(subject.getClassroom());
        
        setNumberOfStudent(subject.getNumberStudents());
        
        dateLabel.setText(String.format("(%s đến %s)",
                DateFormatter.stringOf(subject.getStartTime()),
                DateFormatter.stringOf(subject.getEndTime())));
        
        studyingTimeLabel.setText(String.format("%s, %s đến %s",
                DateProcess.getDayOfWeek(subject.getStartTime()),
                DateFormatter.timeAsStringOf(subject.getStartTime()),
                DateFormatter.timeAsStringOf(subject.getEndTime())));
    }
    
    public void setNumberOfStudent(int numberStudents) {
        numberOfStudent.setText(
                String.format("%d sinh viên", numberStudents));
    }
    
    public int getNumberOfStudent() {
        return Integer.parseInt(numberOfStudent.getText().split(" ")[0]);
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public String getSubjectCode() {
        return subjCodeLabel.getText();
    }
    
}
