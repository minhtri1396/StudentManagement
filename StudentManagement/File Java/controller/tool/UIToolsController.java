package controller.tool;

import controller.UIController;
import view.tool.UIQuantityFilterView;
import view.tool.UISearchView;
import view.tool.UIToolsView;

public class UIToolsController extends UIController {
    
    private UIQuantityFilterController uiQuantityFilterController;
    private UISearchController uiSearchController;

    public UIToolsController(UIToolsView uiToolView) {
        super(uiToolView);
        init();
    }
    
    public UIToolsController() {
        super(new UIToolsView());
        init();
    }
    
    private void init() {
        this.uiSearchController = new UISearchController(
                (UISearchView) uiView.findViewById("UISearchView"));
        
        // Maybe we will use this search tool in future, but not on this version
        uiSearchController.setVisible(false);
        
        this.uiQuantityFilterController = new UIQuantityFilterController(
                (UIQuantityFilterView) uiView.findViewById("UIQuantityFilterView"));
    }
    
    public UIQuantityFilterController getQuantituFilterController() {
        return uiQuantityFilterController;
    }
    
    public UISearchController getSearchController() {
        return uiSearchController;
    } 
    
}
