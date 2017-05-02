package view.sinhvien;

import view.UIFilterListView;
import javax.swing.SpringLayout;
import view.UIView;

public class UIMainView extends UIView{
    
    public UIMainView() {
        addContentPane();
        setContentPaneConstraints();
    }
    
    private UINotificationsListView uiNotificationsListView;
    private UIFilterListView uiSubjectsListView;
    
    private void addContentPane() {
        // uiNotificationsListView
        uiNotificationsListView = new UINotificationsListView();
        components.put("UINotificationsListView", uiNotificationsListView);
        this.add(uiNotificationsListView);
        // uiSubjectsListView
        uiSubjectsListView = new UIFilterListView();
        components.put("UISubjectsListView", uiSubjectsListView);
        this.add(uiSubjectsListView);
        
    }
    
    private void setContentPaneConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // uiNotificationsListView
        layout.putConstraint(
                SpringLayout.NORTH, uiNotificationsListView,
                5,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, uiNotificationsListView,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, uiNotificationsListView,
                0,
                SpringLayout.SOUTH, this);
        // uiSubjectsListView
        layout.putConstraint(
                SpringLayout.NORTH, uiSubjectsListView,
                5,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, uiSubjectsListView,
                10,
                SpringLayout.EAST, uiNotificationsListView);
        layout.putConstraint(
                SpringLayout.EAST, uiSubjectsListView,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, uiSubjectsListView,
                0,
                SpringLayout.SOUTH, this);
    }
    
}
