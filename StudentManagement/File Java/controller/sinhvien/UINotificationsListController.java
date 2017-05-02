package controller.sinhvien;

import controller.UIController;
import controller.UIListController;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import model.define.ColorDefine;
import model.pojo.Subject;
import util.Result.ResponseReceiver;
import view.UIListView;
import view.sinhvien.UINotificationsListView;

public class UINotificationsListController extends UIController {
    
    private ResponseReceiver receiver;
    
    public void setResponseReceiver(ResponseReceiver receiver) {
        this.receiver = receiver;
    }
    
    private UIListController uiListController;
    
    private JButton checkAllButton;
    
    private boolean isLoading;
    
    private ArrayList<UISubjectItemController> subjectItemControllers;
    
    public UINotificationsListController(UINotificationsListView uiNotificationsListView) {
        super(uiNotificationsListView);
        init();
    }
        
    public UINotificationsListController() {
        super(new UINotificationsListView());
        init();
    }

    private void init() {
        isLoading = false;
        subjectItemControllers = new ArrayList<>();
        
        uiListController = new UIListController(
                (UIListView)uiView.findViewById("NotiList"));
        
        checkAllButton = (JButton)uiView.findViewById("CheckAllButton");
        checkAllButton.setVisible(false);
        
        addListenerForViews();
    }
    
    private void addListenerForViews() {
        checkAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                subjectItemControllers.stream().map((uiController) -> {
                    if (receiver != null) {
                        receiver.receiveResult(uiController.getSubject());
                    }
                    return uiController;
                }).forEachOrdered((uiController) -> {
                    uiController.depose();
                });
                clear();
            }
        });
    }
    
    public void addSubjects(Subject[] subjects) {
        if (subjects.length > 0) {
            isLoading = true;
            for (Subject subject : subjects) {
                addSubject(subject);
            }
            isLoading = false;
            if (!checkAllButton.isVisible()) {
                checkAllButton.setVisible(true);
            }
        }
    }
    
    public void addSubject(Subject subject) {
        UISubjectItemController uiSubjectItemController = new UISubjectItemController();
        uiSubjectItemController.setTagBackground(ColorDefine.IMPORTANT_SUBJECT_ITEM_BKG);
        uiSubjectItemController.setSubject(subject);
        this.subjectItemControllers.add(uiSubjectItemController);
        uiSubjectItemController.setResponseReceiver((Object obj) -> {
            this.remove(uiSubjectItemController.getContentView());
            uiSubjectItemController.depose();
            if (receiver != null) {
                receiver.receiveResult(obj);
            }
        });
        uiListController.addItem(uiSubjectItemController.getContentView());
        
        if (isLoading) {
        } else if (!checkAllButton.isVisible()) {
            checkAllButton.setVisible(true);
        }
    }
    
    public void remove(Component component) {
        uiListController.remove(component);
        for (UISubjectItemController uiController : subjectItemControllers) {
            if (uiController.getContentView() == component) {
                subjectItemControllers.remove(uiController);
                break;
            }
        }
        
        if (getItemCount() == 0) {
            checkAllButton.setVisible(false);
        }
    }
    
    public void removeAt(int row) {
        uiListController.removeAt(row);
        subjectItemControllers.remove(row);
        
        if (getItemCount() == 0) {
            checkAllButton.setVisible(false);
        }
    }
    
    public void clear() {
        if (getItemCount() > 0) {
            checkAllButton.setVisible(false);
            uiListController.clear();
            subjectItemControllers.clear();
        }
    }
    
    public int getItemCount() {
        return uiListController.getItemCount();
    }
    
}
