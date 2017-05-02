package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.StringDefine;

public class UIListView extends UIView {
    
    public UIListView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JScrollPane scrollPane;
    private JLabel noticeLabel;
    
    private void createComponents() {
        noticeLabel = new JLabel(StringDefine.NOTICE_EMPTY_SUBJECTS_LIST);
        noticeLabel.setFont(FontDefine.NOTICE);
        noticeLabel.setForeground(ColorDefine.NOTICE);
        noticeLabel.setBackground(ColorDefine.NOTICE_BKG);
        components.put("NoticeLabel", noticeLabel);
        this.add(noticeLabel);
        
        JPanel listPanel = new JPanel(new SpringLayout());
        listPanel.setBackground(ColorDefine.BACKGROUND);
        components.put("ListPanel", listPanel);
        this.add(listPanel);
        
        scrollPane = new JScrollPane(
                listPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        
        this.add(scrollPane);
    }
    
    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // noticeLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, noticeLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, noticeLabel,
                -80,
                SpringLayout.VERTICAL_CENTER, this);
        // scrollPane (listPanel)
        layout.putConstraint(
                SpringLayout.NORTH, scrollPane,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.EAST, scrollPane,
                -10,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, scrollPane,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, scrollPane,
                10,
                SpringLayout.WEST, this);
    }
    
}
