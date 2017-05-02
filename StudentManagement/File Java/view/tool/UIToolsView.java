package view.tool;

import java.awt.Dimension;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import view.UIView;

public class UIToolsView extends UIView {
    
    public UIToolsView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private void createComponents() {
        addSearchView();
    }
    
    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setSearchViewConstraints();
    }
    
    private UIView uiQuantityFilterView;
    private UIView uiSearchView;
    private void addSearchView() {
        uiQuantityFilterView = new UIQuantityFilterView();
        components.put("UIQuantityFilterView", uiQuantityFilterView);
        this.add(uiQuantityFilterView);
        
        uiSearchView = new UISearchView();
        components.put("UISearchView", uiSearchView);
        this.add(uiSearchView);
        
        this.setPreferredSize(new Dimension(0, 28));
    }
    private void setSearchViewConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // uiSearchView
        layout.putConstraint(
                SpringLayout.NORTH, uiSearchView,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, uiSearchView,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, uiSearchView,
                0,
                SpringLayout.SOUTH, this);
        
        // uiQuantityFilterView
        layout.putConstraint(
                SpringLayout.NORTH, uiQuantityFilterView,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, uiQuantityFilterView,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, uiQuantityFilterView,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, uiQuantityFilterView,
                -400,
                SpringLayout.EAST, this);
    }
    
}
