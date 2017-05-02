package controller.sinhvien;

import controller.UIController;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import model.pojo.Subject;
import util.Result;
import view.sinhvien.UISubjectItemView;

public class UISubjectItemController extends UIController {
    
    private Result.ResponseReceiver receiver;
    
    public void setResponseReceiver(Result.ResponseReceiver receiver) {
        this.receiver = receiver;
    }
    
    private JLabel subjCodeLabel;
    private JLabel classroomLabel;
    private JPanel subjCodeBkgPanel;
    private JTextPane subjNameTextPane;
    
    public UISubjectItemController(UISubjectItemView uiSubjectItemView) {
        super(uiSubjectItemView);
        init();
    }
    
    public UISubjectItemController() {
        super(new UISubjectItemView());
        init();
    }
    
    private void init() {
        subjCodeLabel = (JLabel)uiView.findViewById("SubjectCodeLabel");
        classroomLabel = (JLabel)uiView.findViewById("ClassroomLabel");
        subjCodeBkgPanel = (JPanel)uiView.findViewById("SubjectCodeBkgPanel");
        subjNameTextPane = (JTextPane)uiView.findViewById("SubjectNameTextPane");
        
        addListenerForViews();
    }
    
    private void addListenerForViews() {
        uiView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (receiver != null) {
                    receiver.receiveResult(subject);
                }
            }
        });
        
        subjNameTextPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (receiver != null) {
                    receiver.receiveResult(subject);
                }
            }
        });
    }
    
    private Subject subject;
    
    public void depose() {
        subject = null;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
        
        subjCodeLabel.setText(subject.getCode());
        classroomLabel.setText(String.format("(%s)", subject.getClassroom()));
        subjNameTextPane.setText(subject.getName());
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setTagBackground(Color color) {
        subjCodeLabel.setBackground(color);
        classroomLabel.setBackground(color);
        subjCodeBkgPanel.setBackground(color);
    }
    
}
