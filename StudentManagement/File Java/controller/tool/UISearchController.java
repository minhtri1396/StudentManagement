package controller.tool;

import controller.UIController;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import util.Image;
import view.tool.UISearchView;

public class UISearchController extends UIController implements MouseListener {
    
    private JLabel searchImageBtn;
    private JLabel searchImage;
    private JTextField searchTextField;

    public UISearchController(UISearchView uiSearchView) {
        super(uiSearchView);
        init();
    }
    
    public UISearchController() {
        super(new UISearchView());
        init();
    }
    
    private void init() {
        this.searchImageBtn = (JLabel)uiView.findViewById("SearchImageButton");
        this.searchImage = (JLabel)uiView.findViewById("SearchImage");
        this.searchTextField = (JTextField)uiView.findViewById("SearchTextField");
        
        addListenerForViews();
    }
    
    private void addListenerForViews() {
        this.searchImageBtn.addMouseListener(this);
        this.searchImage.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        boolean isVisble = this.searchTextField.isVisible();
        this.searchImageBtn.setVisible(isVisble);
        
        isVisble = !isVisble;
        this.searchImage.setVisible(isVisble);
        this.searchTextField.setVisible(isVisble);
        
        if (isVisble) {
            this.searchTextField.requestFocusInWindow();
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        if (this.searchImageBtn.isVisible()) {
            this.searchImageBtn.setIcon(Image.BUILDER.getImageSrcFrom("search_hover.png", 25, 25));
        } else {
            this.searchImage.setIcon(Image.BUILDER.getImageSrcFrom("search_hover.png", 25, 25));
        }
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        if (this.searchImageBtn.isVisible()) {
            this.searchImageBtn.setIcon(Image.BUILDER.getImageSrcFrom("search.png", 25, 25));
        } else {
            this.searchImage.setIcon(Image.BUILDER.getImageSrcFrom("search.png", 25, 25));
        }
    }
    
}
