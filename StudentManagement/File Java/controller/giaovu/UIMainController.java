
package controller.giaovu;

import app.Application;
import controller.UIFilterListController;
import controller.UIController;
import controller.dao.LoginDAO.LoginType;
import controller.dao.SubjectStudentDAO;
import controller.dao.SubjectDAO;
import controller.login.subwindow.UIChangePasswordController;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.pojo.Student;
import model.pojo.Subject;
import model.define.StringDefine;
import util.DateProcess;
import view.giaovu.UISubjectInfoView;
import view.giaovu.UIAddingSubjectView;
import view.giaovu.UIMainView;
import view.UIFilterListView;

public class UIMainController extends UIController {
    
    private final String giaoVuID;
    
    private final JPanel leftPane;
    private final JPanel rightPane;
    private final UIAddingSubjectController uiAddingSubjectController;
    private final UIFilterListController uiSubjectsListController;
    
    private final UISubjectInfoController uiSubjectInfoController;
    private final UICheckingListController uiCheckingController;
    
    public UIMainController(String giaoVuID) {
        super(new UIMainView());
        
        leftPane = (JPanel) uiView.findViewById("LeftPane");
        rightPane = (JPanel) uiView.findViewById("RightPane");
        
        uiAddingSubjectController = new UIAddingSubjectController(
                (UIAddingSubjectView)uiView.findViewById("UIAddingSubjectView"));
        
        uiSubjectsListController = new UIFilterListController(
                (UIFilterListView)uiView.findViewById("UISubjectsListView"));
        uiSubjectsListController.setTitle(StringDefine.SUBJECTS_LIST_TITLE);
        
        uiSubjectInfoController = new UISubjectInfoController(
                (UISubjectInfoView)uiView.findViewById("UISubjectInfoView"));
        
        uiCheckingController = new UICheckingListController(
                (UIFilterListView)uiView.findViewById("UICheckingListView"));
        
        this.giaoVuID = giaoVuID;
        
        addChangePasswordButton();
        addListenerForViews();
        addAllSubjects();
    }
    
    private JLabel changePasswordButton;
    
    private void addChangePasswordButton() {
        changePasswordButton = new JLabel(StringDefine.CHANGING_PASSWORD);
        changePasswordButton.setFont(FontDefine.BUTTON);
        changePasswordButton.setForeground(ColorDefine.CHANGING_PASS_BTN_TEXT);
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        uiSubjectsListController.addRightPanel(changePasswordButton);
    }
    
    private UISubjectItemController selectedSubjectItem;
    
    private void addListenerForViews() {
        changePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UIChangePasswordController uiChangePasswordController =
                        new UIChangePasswordController();
                uiChangePasswordController.setInformation(giaoVuID, LoginType.GIAO_VU);
                uiChangePasswordController.setResponseReceiver((Object obj) -> {
                    Application.getInstance().setVisible(true);
                    Application.getInstance().requestFocusInWindow();
                    Application.getInstance().setEnabled(true);
 
                });
                Application.getInstance().setEnabled(false);
                uiChangePasswordController.show();
            }
        });
        uiAddingSubjectController.setSubjectReceiver((Object object) -> {
            if (object instanceof Subject) {
                Subject subject = (Subject)object;
                SubjectDAO.BUILDER.add(subject);
                addSubject(subject);
            }
        });
        
        uiCheckingController.setResponseReceiver((Object obj) -> {
            if (obj instanceof UICheckingListController.ResultType) {
                this.backCheckingList();
            } else {
                Object[] objects = (Object[])obj;
                this.increaseNumberOfStudent((Subject)objects[0], (Integer)objects[1]);
            }
        });
    }
    
    private void backCheckingList() {
        CardLayout leftCard = (CardLayout)leftPane.getLayout();
        leftCard.show(leftPane, "UIAddingSubjectView");

        CardLayout rightCard = (CardLayout)rightPane.getLayout();
        rightCard.show(rightPane, "UISubjectsListView");

        selectedSubjectItem.setNumberOfStudent(
                uiSubjectInfoController.getNumberOfStudent());
        
        uiSubjectInfoController.depose();
        uiCheckingController.depose();
    }
    
    private void increaseNumberOfStudent(Subject subject, int nStudents) {
        subject.setNumberStudents(subject.getNumberStudents() + nStudents);
        SubjectDAO.BUILDER.update(subject);
        uiSubjectInfoController.setNumberOfStudent(subject.getNumberStudents());
    }
    
    private void addAllSubjects() {
        Date currentDate = new Date();
        Subject[] subjects = SubjectDAO.BUILDER.getAll();
        for (Subject subject : subjects) {
            if (DateProcess.compare(subject.getEndTime(), currentDate) < 0) {
                subject.setExpired(true);
                SubjectDAO.BUILDER.update(subject);
            }
            addSubject(subject);
        }
    }
    
    private void addSubject(Subject subject) {
        UISubjectItemController uiSubjectItemController = new UISubjectItemController();
        uiSubjectItemController.setSubject(subject);
        
        uiSubjectItemController.addMouseListener((String subjectCode, int nameId) -> {
            itemClicked(subjectCode, nameId, uiSubjectItemController);
        });

        uiSubjectsListController.addItem(uiSubjectItemController.getContentView());
    }
    
    private void itemClicked(String subjectID, int itemID,
            UISubjectItemController uiSubjectItemController) {
        CardLayout leftCard = (CardLayout)leftPane.getLayout();
        leftCard.show(leftPane, "UISubjectInfoView");
        
        CardLayout rightCard = (CardLayout)rightPane.getLayout();
        rightCard.show(rightPane, "UICheckingListView");
        
        new Thread(() -> {
            selectedSubjectItem = uiSubjectItemController;
            Subject subject = SubjectDAO.BUILDER.get(subjectID, itemID);
            uiSubjectInfoController.setSubject(subject);
            
            if (DateProcess.compare(subject.getEndTime(), new Date()) < 0) {
                subject.setExpired(true);
                SubjectDAO.BUILDER.update(subject);
                selectedSubjectItem.setExpired(true);
            }
            
            Student[] students = SubjectStudentDAO.BUILDER.getStudentsStudy(
                    subjectID,
                    subject.getNameId()
            );
            uiCheckingController.setSubject(subject);
            uiCheckingController.addStudents(students);
        }).start();
    }
    
}
