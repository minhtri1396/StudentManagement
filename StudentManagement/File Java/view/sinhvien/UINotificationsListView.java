package view.sinhvien;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import view.UIListView;
import view.UIView;

public class UINotificationsListView extends UIView {
    
    public UINotificationsListView() {
        super();
        super.setPreferredSize(SizeDefine.NOTIFICATIONS_LIST_FRAME);
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private void createComponents() {
        addTitleView();
        addButtons();
        addList();
    }

    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setTitleConstraints();
        setButtonsConstraints();
        setListConstraints();
    }

    private JLabel titleLabel;
    private JPanel titleBkg;
    private void addTitleView() {
        titleLabel = new JLabel("CÁC MÔN CẦN ĐIỂM DANH");
        titleLabel.setFont(FontDefine.TITLE);
        titleLabel.setForeground(ColorDefine.TITLE);
        titleLabel.setBackground(ColorDefine.TITLE_BKG);
        titleLabel.setOpaque(true);
        
        titleBkg = new JPanel();
        titleBkg.setBackground(ColorDefine.TITLE_BKG);
        titleBkg.add(titleLabel);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        SpringLayout titleLayout = new SpringLayout();
        titleBkg.setLayout(titleLayout);
        
        // titleLabel
        titleLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titleBkg);
        titleLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleLabel,
                0,
                SpringLayout.VERTICAL_CENTER, titleBkg);
        // titleBkg
        layout.putConstraint(
                SpringLayout.NORTH, titleBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, titleBkg,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, titleBkg,
                70,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, titleBkg,
                0,
                SpringLayout.WEST, this);
    }

    private JButton checkAllButton;
    private void addButtons() {
        checkAllButton = new JButton("Điểm danh tất cả");
        checkAllButton.setFont(FontDefine.BUTTON);
        checkAllButton.setForeground(ColorDefine.NOTIFICATION_ALL_BTN_TEXT);
        checkAllButton.setBackground(ColorDefine.NOTIFICATION_ALL_BTN_BKG);
//        checkAllButton.setPreferredSize(SizeDefine.NOTIFICATION_ALL_BUTTON);
        checkAllButton.setVisible(true);
        components.put("CheckAllButton", checkAllButton);
        this.add(checkAllButton);
    }
    private void setButtonsConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        // checkAllButton
        layout.putConstraint(
                SpringLayout.NORTH, checkAllButton,
                20,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.EAST, checkAllButton,
                -15,
                SpringLayout.EAST, this);
    }
    
    private UIListView notiList;
    private void addList() {
        notiList = new UIListView();
        components.put("NotiList", notiList);
        this.add(notiList);
    }
    private void setListConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        // notiList
        layout.putConstraint(
                SpringLayout.NORTH, notiList,
                20,
                SpringLayout.SOUTH, checkAllButton);
        layout.putConstraint(
                SpringLayout.WEST, notiList,
                10,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, notiList,
                -10,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, notiList,
                -10,
                SpringLayout.SOUTH, this);
    }
    
}
