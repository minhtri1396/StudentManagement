package controller;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import view.UIListView;
import view.UIView;

public class UIListController extends UIController {
    
    private Dimension DIM_OF_ITEM;
    
    private JPanel listPanel;
    private JLabel noticeLabel;
    
    private JPanel prevItemView;
    private JPanel lastItemView;
    
    public UIListController(UIListView uiListView) {
        super(uiListView);
        init();
    }
    
    public UIListController() {
        super(new UIListView());
        init();
    }
    
    private void init() {
        prevItemView = null;
        lastItemView = null;
        
        this.DIM_OF_ITEM = new Dimension(0, 0);
        
        listPanel = (JPanel)uiView.findViewById("ListPanel");
        listPanel.setLayout(new SpringLayout());
        
        noticeLabel = (JLabel)uiView.findViewById("NoticeLabel");
        
    }
    
    public void addItem(UIView view) {
        if (view != null) {
            prevItemView = lastItemView;
            lastItemView = view;
            
            if (noticeLabel.isVisible()) {
                noticeLabel.setVisible(false);
            }
            listPanel.add(lastItemView);
            setItemConstraints();
            listPanel.revalidate();
            listPanel.repaint();
        }
    }
    
    private void setItemConstraints() {
        SpringLayout layout = (SpringLayout) listPanel.getLayout();
        
        if (prevItemView == null) {
            layout.putConstraint(
                    SpringLayout.NORTH, lastItemView,
                    0,
                    SpringLayout.NORTH, listPanel);
            // Change size to can scroll (if need)
            DIM_OF_ITEM.setSize(DIM_OF_ITEM.width,
                    lastItemView.getHeight() * getItemCount());
            listPanel.setPreferredSize(DIM_OF_ITEM);
        } else {
            layout.putConstraint(
                    SpringLayout.NORTH, lastItemView,
                    20,
                    SpringLayout.SOUTH, prevItemView);
            // Change size to can scroll (if need)
            DIM_OF_ITEM.setSize(DIM_OF_ITEM.width,
                    (lastItemView.getHeight() + 20) * getItemCount());
            listPanel.setPreferredSize(DIM_OF_ITEM);
        }
        
        layout.putConstraint(
                SpringLayout.WEST, lastItemView,
                0,
                SpringLayout.WEST, listPanel);
        layout.putConstraint(
                SpringLayout.EAST, lastItemView,
                0,
                SpringLayout.EAST, listPanel);
    }
    
    public void remove(Component component) {
        listPanel.remove(component);
        Component[] components = listPanel.getComponents();
        if (components.length > 0) {
            this.clear();
            for (Component c : components) {
                addItem((UIView)c);
            }
        } else {
            prevItemView = null;
            lastItemView = null;
            DIM_OF_ITEM.setSize(0, 0);
            listPanel.setPreferredSize(DIM_OF_ITEM);
            noticeLabel.setVisible(true);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
    
    public void removeAt(int row) {
        listPanel.remove(row);
        
        if (getItemCount() == 0) {
            prevItemView = null;
            lastItemView = null;
            DIM_OF_ITEM.setSize(0, 0);
            listPanel.setPreferredSize(DIM_OF_ITEM);
            noticeLabel.setVisible(true);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
    
    public void clear() {
        if (getItemCount() > 0) {
            prevItemView = null;
            lastItemView = null;
            listPanel.removeAll();
            DIM_OF_ITEM.setSize(0, 0);
            listPanel.setPreferredSize(DIM_OF_ITEM);
            noticeLabel.setVisible(true);
            listPanel.revalidate();
            listPanel.repaint();
        }
    }
    
    public int getItemCount() {
        return listPanel.getComponentCount();
    }
    
}
