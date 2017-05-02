package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.StringDefine;
import view.tool.UIToolsView;

public class UIFilterListView extends UIView {
    
    public UIFilterListView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }
    
    private void createComponents() {
        addTitleView();
        addNotice();
        addButtons();
        addListView();
        addFooter();
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        setTitleConstraints();
        setNoticeConstraints();
        setButtonsConstraints();
        setListViewConstraints();
        setFooterConstraints();
    }
    
    // Title
    private JLabel titleLabel;
    private JLabel subTitleLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel titleBkg;
    private void addTitleView() {
        titleLabel = new JLabel();
        titleLabel.setFont(FontDefine.TITLE);
        titleLabel.setForeground(ColorDefine.TITLE);
        titleLabel.setBackground(ColorDefine.TITLE_BKG);
        titleLabel.setOpaque(true);
        components.put("TitleLabel", titleLabel);
        
        subTitleLabel = new JLabel();
        subTitleLabel.setFont(FontDefine.SUB_TITLE);
        subTitleLabel.setForeground(ColorDefine.TITLE);
        subTitleLabel.setBackground(ColorDefine.TITLE_BKG);
        subTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subTitleLabel.setOpaque(true);
        components.put("SubTitleLabel", subTitleLabel);
        
        leftPanel = new JPanel();
        components.put("LeftPanel", leftPanel);
        rightPanel = new JPanel();
        components.put("RightPanel", rightPanel);
        
        titleBkg = new JPanel();
        leftPanel.setBackground(ColorDefine.TITLE_BKG);
        rightPanel.setBackground(ColorDefine.TITLE_BKG);
        titleBkg.setBackground(ColorDefine.TITLE_BKG);
        titleBkg.add(leftPanel);
        titleBkg.add(titleLabel);
        titleBkg.add(subTitleLabel);
        titleBkg.add(rightPanel);
        components.put("TitleBkg", titleBkg);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        SpringLayout titleLayout = new SpringLayout();
        titleBkg.setLayout(titleLayout);
        
        // leftPanel
        titleLayout.putConstraint(
                SpringLayout.WEST, leftPanel,
                10,
                SpringLayout.WEST, titleBkg);
        titleLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, leftPanel,
                0,
                SpringLayout.VERTICAL_CENTER, titleBkg);
        // titleLabel
        titleLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titleBkg);
        titleLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleLabel,
                0,
                SpringLayout.VERTICAL_CENTER, titleBkg);
        // subTitleLabel
        titleLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, subTitleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titleBkg);
        titleLayout.putConstraint(
                SpringLayout.NORTH, subTitleLabel,
                0,
                SpringLayout.SOUTH, titleLabel);
        // rightPanel
        titleLayout.putConstraint(
                SpringLayout.EAST, rightPanel,
                -10,
                SpringLayout.EAST, titleBkg);
        titleLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, rightPanel,
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
    
    // Buttons
    private UIView uiToolsView;
    private void addButtons() {
        uiToolsView = new UIToolsView();
        components.put("UIToolsView", uiToolsView);
        this.add(uiToolsView);
    }
    private void setButtonsConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // uiToolsView
        layout.putConstraint(
                SpringLayout.NORTH, uiToolsView,
                20,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.WEST, uiToolsView,
                25,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, uiToolsView,
                -25,
                SpringLayout.EAST, this);
    }
    
    // List
    private UIListView uiListView;
    private void addListView() {
        uiListView = new UIListView();
        components.put("UIListView", uiListView);
        this.add(uiListView);
    }
    private void setListViewConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // uiListView
        layout.putConstraint(
                SpringLayout.NORTH, uiListView,
                20,
                SpringLayout.SOUTH, uiToolsView);
        layout.putConstraint(
                SpringLayout.EAST, uiListView,
                -20,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, uiListView,
                -45,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, uiListView,
                20,
                SpringLayout.WEST, this);
    }
    
    private JLabel noticeLabel;
    private void addNotice() {
        noticeLabel = new JLabel(StringDefine.LOADING_LIST);
        noticeLabel.setFont(FontDefine.NOTICE);
        noticeLabel.setForeground(ColorDefine.NOTICE);
        noticeLabel.setBackground(ColorDefine.NOTICE_BKG);
        noticeLabel.setVisible(false);
        components.put("NoticeLabel", noticeLabel);
        this.add(noticeLabel);
    }
    private void setNoticeConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // noticeLabel
        layout.putConstraint(
                SpringLayout.NORTH, noticeLabel,
                20,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, noticeLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
    }
    
    private JPanel footerPanel;
    private void addFooter() {
        footerPanel = new JPanel();
        footerPanel.setBackground(ColorDefine.BACKGROUND);
        footerPanel.setVisible(false);
        components.put("FooterPanel", footerPanel);
        this.add(footerPanel);
    }
    private void setFooterConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        // footerPanel
        layout.putConstraint(
                SpringLayout.NORTH, footerPanel,
                5,
                SpringLayout.SOUTH, uiListView);
        layout.putConstraint(
                SpringLayout.WEST, footerPanel,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, footerPanel,
                -10,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.EAST, footerPanel,
                0,
                SpringLayout.EAST, this);
    }
    
}
