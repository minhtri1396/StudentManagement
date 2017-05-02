package view.tool;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import util.Image;
import view.UIView;

public class UISearchView extends UIView {
    
    public UISearchView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private void createComponents() {
        addButtons();
    }
    
    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setButtonsConstraints();
    }
    
    private JLabel searchImage;
    private JLabel searchImageBtn;
    private JTextField searchTextField;
    private void addButtons() {
        searchImageBtn = Image.BUILDER.getImageFrom("search.png", 25, 25);
        components.put("SearchImageButton", searchImageBtn);
        this.add(searchImageBtn);
        
        searchImage = Image.BUILDER.getImageFrom("search.png", 25, 25);
        searchImage.setVisible(false);
        components.put("SearchImage", searchImage);
        this.add(searchImage);
        
        searchTextField = new JTextField();
        searchTextField.setFont(FontDefine.SEARCH_TEXT_FIELD);
        searchTextField.setPreferredSize(SizeDefine.SEARCH_TEXT_FIELD);
        searchTextField.setVisible(false);
        components.put("SearchTextField", searchTextField);
        this.add(searchTextField);
        
        this.setPreferredSize(new Dimension(325, 28));
    }
    private void setButtonsConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // searchImageBtn
        layout.putConstraint(
                SpringLayout.NORTH, searchImageBtn,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, searchImageBtn,
                0,
                SpringLayout.EAST, this);
        // searchImage
        layout.putConstraint(
                SpringLayout.NORTH, searchImage,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, searchImage,
                -10,
                SpringLayout.WEST, searchTextField);
        // searchTextField
        layout.putConstraint(
                SpringLayout.NORTH, searchTextField,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, searchTextField,
                0,
                SpringLayout.EAST, this);
    }
    
}
