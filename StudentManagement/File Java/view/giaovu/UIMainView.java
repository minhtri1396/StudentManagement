package view.giaovu;

import view.UIFilterListView;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import view.UIView;

public class UIMainView extends UIView{
    
    public UIMainView() {
        addContentPane();
        setContentPaneConstraints();
    }
    
    private JPanel leftPane;
    private JPanel rightPane;
    
    private void addContentPane() {
        // LeftPane
        UIAddingSubjectView uiAddingSubjectView = new UIAddingSubjectView();
        UISubjectInfoView uiSubjectInfoView = new UISubjectInfoView();
        leftPane = new JPanel(new CardLayout());
        leftPane.add(uiAddingSubjectView, "UIAddingSubjectView");
        leftPane.add(uiSubjectInfoView, "UISubjectInfoView");
        components.put("LeftPane", leftPane);
        components.put("UIAddingSubjectView", uiAddingSubjectView);
        components.put("UISubjectInfoView", uiSubjectInfoView);
        this.add(leftPane);
        // RightPane
        UIFilterListView uiSubjectsListView = new UIFilterListView();
        UIFilterListView uiCheckingListView = new UIFilterListView();
        rightPane = new JPanel(new CardLayout());
        rightPane.add(uiSubjectsListView, "UISubjectsListView");
        rightPane.add(uiCheckingListView, "UICheckingListView");
        components.put("RightPane", rightPane);
        components.put("UISubjectsListView", uiSubjectsListView);
        components.put("UICheckingListView", uiCheckingListView);
        this.add(rightPane);
        
    }
    
    private void setContentPaneConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // leftPane
        layout.putConstraint(
                SpringLayout.NORTH, leftPane,
                5,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, leftPane,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, leftPane,
                0,
                SpringLayout.SOUTH, this);
        // rightPane
        layout.putConstraint(
                SpringLayout.NORTH, rightPane,
                5,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, rightPane,
                10,
                SpringLayout.EAST, leftPane);
        layout.putConstraint(
                SpringLayout.EAST, rightPane,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, rightPane,
                0,
                SpringLayout.SOUTH, this);
    }
    
}
