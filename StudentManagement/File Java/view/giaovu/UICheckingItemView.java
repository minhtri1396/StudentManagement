package view.giaovu;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import util.Image;
import view.UIView;

public class UICheckingItemView extends UIView {
    
    public UICheckingItemView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JLabel nameLabel;
    private JLabel nameBkgImage;
    private JPanel checkBoxPanel;
    private void createComponents() {
        this.setPreferredSize(SizeDefine.CHECKING_ITEM_PANEL);
        this.setSize(SizeDefine.CHECKING_ITEM_PANEL);
        
        nameLabel = new JLabel();
        nameLabel.setFont(FontDefine.CHECKING_ITEM);
        nameLabel.setForeground(ColorDefine.CHECKING_ITEM);
        nameLabel.setBackground(ColorDefine.CHECKING_ITEM_BKG);
        nameLabel.setOpaque(true);
        components.put("NameLabel", nameLabel);
        this.add(nameLabel);
        
        nameBkgImage = Image.BUILDER.getImageFrom("tag.png", 130, 50);
        this.add(nameBkgImage);
        
        checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(ColorDefine.BACKGROUND);
        components.put("CheckBoxPanel", checkBoxPanel);
        this.add(checkBoxPanel);
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // nameLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, nameLabel,
                -5,
                SpringLayout.HORIZONTAL_CENTER, nameBkgImage);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, nameLabel,
                0,
                SpringLayout.VERTICAL_CENTER, nameBkgImage);
        // nameBkgImage
        layout.putConstraint(
                SpringLayout.NORTH, nameBkgImage,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.SOUTH, nameBkgImage,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, nameBkgImage,
                0,
                SpringLayout.WEST, this);
        // checkBoxPanel
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, checkBoxPanel,
                0,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(
                SpringLayout.WEST, checkBoxPanel,
                15,
                SpringLayout.EAST, nameBkgImage);
        layout.putConstraint(
                SpringLayout.EAST, checkBoxPanel,
                0,
                SpringLayout.EAST, this);
    }
    
}
