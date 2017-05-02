package controller;

import controller.tool.UIQuantityFilterController;
import controller.tool.UIToolsController;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import view.UIListView;
import view.UIView;
import view.UIFilterListView;
import view.tool.UIToolsView;

public class UIFilterListController extends UIController {
    
    public static final int MAX_ITEM_ON_PAGE = 100;
    
    private JLabel titleLabel;
    private JLabel subTitleLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel titleBkg;
    private JLabel noticeLabel;
    private UIToolsController uiToolsController;
    private UIListController uiListController;
    private JPanel footerPanel;
    
    private boolean isLoading;
    private boolean isHadFooter;
    private Semaphore mutexForIsLoading;
    
    private UIQuantityFilterController uiQuantityFilterController;
    
    public UIFilterListController(UIFilterListView uiFilterListView) {
        super(uiFilterListView);
        init();
    }
    
    public UIFilterListController() {
        super(new UIFilterListView());
        init();
    }
    
    private void init() {
        isLoading = false;
        isHadFooter = false;
        mutexForIsLoading = new Semaphore(1);
        
        this.titleLabel = (JLabel)uiView.findViewById("TitleLabel");
        this.subTitleLabel = (JLabel)uiView.findViewById("SubTitleLabel");
        this.leftPanel = (JPanel)uiView.findViewById("LeftPanel");
        this.rightPanel = (JPanel)uiView.findViewById("RightPanel");
        this.titleBkg = (JPanel)uiView.findViewById("TitleBkg");
        this.noticeLabel = (JLabel)uiView.findViewById("NoticeLabel");
        
        this.uiToolsController = new UIToolsController(
                (UIToolsView) uiView.findViewById("UIToolsView"));
        this.uiListController = new UIListController(
                (UIListView) uiView.findViewById("UIListView"));
        
        this.footerPanel = (JPanel)uiView.findViewById("FooterPanel");
        
        this.uiQuantityFilterController = this.uiToolsController
                .getQuantituFilterController();
        
        this.setVisibleViews(false);
        this.addListenerForViews();
    }
    
    private void setVisibleViews(boolean visible) {
        if (uiToolsController.isVisible() != visible) {
            uiToolsController.setVisible(visible);
        }
        if (isHadFooter) {
            if (footerPanel.isVisible() != visible) {
                footerPanel.setVisible(visible);
            }
        }
    }
    
    private void addListenerForViews() {
        uiQuantityFilterController.setFilterInfo(MAX_ITEM_ON_PAGE);
        uiQuantityFilterController.setResponseReceiver((nextObjects) ->{
            List<Object> views = nextObjects;
            uiListController.clear();
            views.forEach((view) -> {
                uiListController.addItem((UIView)view);
            });
            if (uiQuantityFilterController.isCurrentPageFull()) {
                if (isLoading) {
                    setInvisibleForLoadingState(false);
                }
            } else {
                try {
                    mutexForIsLoading.acquire();
                } catch (InterruptedException ex) {
                }
                if (isLoading) {
                    setInvisibleForLoadingState(true);
                }
                mutexForIsLoading.release();
            }
        });
    }
    
    public void addItem(UIView view) {
        if (view != null) {
            if (isLoading){
            } else {
                setVisibleViews(true);
            }
            uiQuantityFilterController.add(view);
            
            if (uiQuantityFilterController.isCurrentPageFull()) {
                if (isLoading) {
                    setInvisibleForLoadingState(false);
                }
            } else {
                try {
                    mutexForIsLoading.acquire();
                } catch (InterruptedException ex) {
                }
                if (isLoading) {
                    setInvisibleForLoadingState(true);
                }
                mutexForIsLoading.release();
                uiListController.addItem(view);
            }
        }
    }
    
    public void remove(Component component) {
        uiQuantityFilterController.remove(component);
        uiListController.remove(component);
        
        if (getItemCount() == 0) {
            setVisibleViews(false);
        }
    }
    
    public void removeAt(int row) {
        uiQuantityFilterController.removeAt(row);
        uiListController.removeAt(row);
        
        if (getItemCount() == 0) {
            setVisibleViews(false);
        }
    }
    
    public void clear() {
        if (getItemCount() > 0) {
            setVisibleViews(false);
            uiQuantityFilterController.clear();
            uiListController.clear();
        }
    }
    
    public int getItemCount() {
        return uiListController.getItemCount();
    }
    
    public void setTitleBackground(Color color) {
        leftPanel.setBackground(color);
        rightPanel.setBackground(color);
        titleLabel.setBackground(color);
        subTitleLabel.setBackground(color);
        titleBkg.setBackground(color);
    }
    
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void setSubTitle(String subTitle) {
        subTitleLabel.setText(subTitle);
    }
    
    public void addLeftPanel(Component component) {
        leftPanel.add(component);
    }
    
    public void addRightPanel(Component component) {
        rightPanel.add(component);
    }
    
    public void setLoadingMode(boolean isLoading) {
        try {
            mutexForIsLoading.acquire();
        } catch (InterruptedException ex) {
        }
        this.isLoading = isLoading;
        mutexForIsLoading.release();
        setInvisibleForLoadingState(isLoading);
    }
    
    private synchronized void setInvisibleForLoadingState(boolean isLoading) {
        noticeLabel.setVisible(isLoading);
        
        isLoading = !isLoading;
        leftPanel.setVisible(isLoading);
        rightPanel.setVisible(isLoading);
        
        if (isLoading) {
            isLoading = uiListController.getItemCount() > 0;
        }
        uiToolsController.setVisible(isLoading);
        
        if (footerPanel.isVisible() != isHadFooter) {
            footerPanel.setVisible(isHadFooter);
        }
    }
    
    public void setFooter(JPanel panel) {
        isHadFooter = true;
        panel.setBackground(footerPanel.getBackground());
        footerPanel.add(panel);
        
        SpringLayout layout = new SpringLayout();
        footerPanel.setLayout(layout);
        
        // footerPanel
        layout.putConstraint(
                SpringLayout.NORTH, panel,
                0,
                SpringLayout.NORTH, footerPanel);
        layout.putConstraint(
                SpringLayout.WEST, panel,
                0,
                SpringLayout.WEST, footerPanel);
        layout.putConstraint(
                SpringLayout.SOUTH, panel,
                0,
                SpringLayout.SOUTH, footerPanel);
        layout.putConstraint(
                SpringLayout.EAST, panel,
                0,
                SpringLayout.EAST, footerPanel);
    }
    
    public void validate() {
        footerPanel.repaint();
        footerPanel.validate();
        uiListController.getContentView().validate();
    }
    
}
