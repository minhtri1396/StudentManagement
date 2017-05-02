package view.sinhvien.subwindow;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import model.Rectangle;
import model.define.ColorDefine;
import model.define.FontDefine;
import util.WrapEditorKit;
import view.UIView;

public class UIDetailAttendanceView extends UIView {
    
    public UIDetailAttendanceView() {
        super();
        
        createComponents();
        createLayoutConstraints();
    }

    private void createComponents() {
        this.setBackground(ColorDefine.BACKGROUND);
        this.setSize(new Dimension(700, 700));
        
        addTitleView();
        addContentViews();
        addNotes();
        addFooterView();
    }

    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setTitleConstraints();
        setContentViewsConstraints();
        setNoteConstraints();
        setFooterConstraints();
    }
    
    // Title
    private JTextPane titleTextPane;
    private JLabel dateLabel;
    private JPanel titleBkg;
    private void addTitleView() {
        titleTextPane = new JTextPane();
        titleTextPane.setFont(FontDefine.TITLE);
        titleTextPane.setForeground(ColorDefine.TITLE);
        titleTextPane.setBackground(ColorDefine.TITLE_BKG);
        titleTextPane.setEditorKit(new WrapEditorKit());
        titleTextPane.setOpaque(true);
        titleTextPane.setEditable(false);
        // Center text
        StyledDocument doc = titleTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        components.put("TitleTextPane", titleTextPane);
        // dateLabel
        dateLabel = new JLabel();
        dateLabel.setFont(FontDefine.SUB_TITLE);
        dateLabel.setForeground(ColorDefine.TITLE);
        dateLabel.setBackground(ColorDefine.TITLE_BKG);
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        components.put("DateLabel", dateLabel);
        // titleBkg
        titleBkg = new JPanel();
        titleBkg.setBackground(ColorDefine.TITLE_BKG);
        titleBkg.add(titleTextPane);
        titleBkg.add(dateLabel);
        this.add(titleBkg);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        SpringLayout titleBkgLayout = new SpringLayout();
        titleBkg.setLayout(titleBkgLayout);
        
        // titleTextPane
        titleBkgLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleTextPane,
                10,
                SpringLayout.VERTICAL_CENTER, titleBkg);
        titleBkgLayout.putConstraint(
                SpringLayout.WEST, titleTextPane,
                20,
                SpringLayout.WEST, titleBkg);
        titleBkgLayout.putConstraint(
                SpringLayout.EAST, titleTextPane,
                -20,
                SpringLayout.EAST, titleBkg);
        // dateLabel
        titleBkgLayout.putConstraint(
                SpringLayout.NORTH, dateLabel,
                5,
                SpringLayout.NORTH, titleBkg);
        titleBkgLayout.putConstraint(
                SpringLayout.WEST, dateLabel,
                5,
                SpringLayout.WEST, titleBkg);
        // titleBkg
        layout.putConstraint(
                SpringLayout.NORTH, titleBkg,
                0,
                SpringLayout.NORTH, this);
        layout.putConstraint(
                SpringLayout.WEST, titleBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, titleBkg,
                0,
                SpringLayout.EAST, this);
        layout.putConstraint(
                SpringLayout.SOUTH, titleBkg,
                100,
                SpringLayout.NORTH, this);
    }
    
    // Content
    private JLabel weekLabel;
    private JPanel line;
    private JLabel titleAttendanceTable;
    private JPanel checkBoxPanel;
    private void addContentViews() {
        weekLabel = new JLabel("Tuần 4: 25/12/2017");
        weekLabel.setFont(FontDefine.SUBJECT_INFO);
        weekLabel.setForeground(ColorDefine.SUBJECT_INFO);
        weekLabel.setBackground(ColorDefine.SUBJECT_NAME_BKG);
        weekLabel.setOpaque(true);
        components.put("WeekLabel", weekLabel);
        this.add(weekLabel);
        
        line = new JPanel();
        line.setBackground(ColorDefine.TITLE_BKG);
        this.add(line);
        
        titleAttendanceTable = new JLabel("Bảng thông tin điểm danh");
        titleAttendanceTable.setFont(FontDefine.SUBJECT_INFO);
        titleAttendanceTable.setForeground(ColorDefine.SUBJECT_INFO);
        titleAttendanceTable.setBackground(ColorDefine.SUBJECT_NAME_BKG);
        titleAttendanceTable.setOpaque(true);
        this.add(titleAttendanceTable);
        
        checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(ColorDefine.BACKGROUND);
        components.put("CheckBoxPanel", checkBoxPanel);
        this.add(checkBoxPanel);
    }
    private void setContentViewsConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // weekLabel
        layout.putConstraint(
                SpringLayout.NORTH, weekLabel,
                20,
                SpringLayout.SOUTH, titleBkg);
        layout.putConstraint(
                SpringLayout.WEST, weekLabel,
                20,
                SpringLayout.WEST, this);
        // line
        layout.putConstraint(
                SpringLayout.NORTH, line,
                25,
                SpringLayout.SOUTH, weekLabel);
        layout.putConstraint(
                SpringLayout.WEST, line,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, line,
                0,
                SpringLayout.EAST, this);
        // titleAttendanceTable
        layout.putConstraint(
                SpringLayout.NORTH, titleAttendanceTable,
                25,
                SpringLayout.SOUTH, line);
        layout.putConstraint(
                SpringLayout.WEST, titleAttendanceTable,
                20,
                SpringLayout.WEST, this);
        // checkBoxPanel
        layout.putConstraint(
                SpringLayout.NORTH, checkBoxPanel,
                25,
                SpringLayout.SOUTH, titleAttendanceTable);
        layout.putConstraint(
                SpringLayout.WEST, checkBoxPanel,
                10,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, checkBoxPanel,
                0,
                SpringLayout.EAST, this);
    }
    
    // Note
    private JPanel notePanel;
    private JPanel checkedImage;
    private JPanel expiredImage;
    private JPanel uncheckedImage;
    private void addNotes() {
        JLabel noteLabel;
        
        noteLabel = new JLabel("Đã điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        checkedImage = new JPanel(new GridLayout(1, 1));
        checkedImage.setPreferredSize(new Dimension(180, 50));
        checkedImage.setBackground(ColorDefine.CHECKED_BOX_BKG);
        checkedImage.add(noteLabel);
        
        noteLabel = new JLabel("Không điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        expiredImage = new JPanel(new GridLayout(1, 1));
        expiredImage.setPreferredSize(new Dimension(180, 50));
        expiredImage.setBackground(ColorDefine.EXPIRED_BOX_BKG);
        expiredImage.add(noteLabel);
        
        noteLabel = new JLabel("Chưa điểm danh");
        noteLabel.setFont(FontDefine.CHECKING_ITEM);
        noteLabel.setForeground(ColorDefine.CHECKING_ITEM);
        noteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        uncheckedImage = new JPanel(new GridLayout(1, 1));
        uncheckedImage.setPreferredSize(new Dimension(180, 50));
        uncheckedImage.setBackground(ColorDefine.UNCHECKED_BOX_BKG);
        uncheckedImage.add(noteLabel);
        
        notePanel = new JPanel();
        notePanel.add(checkedImage);
        notePanel.add(expiredImage);
        notePanel.add(uncheckedImage);
        notePanel.setBackground(ColorDefine.BACKGROUND);
        notePanel.setPreferredSize(new Dimension(0, 25));
        this.add(notePanel);
    }
    private void setNoteConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        SpringLayout noteLayout = new SpringLayout();
        notePanel.setLayout(noteLayout);
        
        // checkedImage
        noteLayout.putConstraint(
                SpringLayout.NORTH, checkedImage,
                0,
                SpringLayout.NORTH, notePanel);
        noteLayout.putConstraint(
                SpringLayout.WEST, checkedImage,
                25,
                SpringLayout.WEST, notePanel);
        noteLayout.putConstraint(
                SpringLayout.SOUTH, checkedImage,
                0,
                SpringLayout.SOUTH, notePanel);
        // expiredImage
        noteLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, expiredImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, notePanel);
        noteLayout.putConstraint(
                SpringLayout.NORTH, expiredImage,
                0,
                SpringLayout.NORTH, notePanel);
        noteLayout.putConstraint(
                SpringLayout.SOUTH, expiredImage,
                0,
                SpringLayout.SOUTH, notePanel);
        // uncheckedImage
        noteLayout.putConstraint(
                SpringLayout.NORTH, uncheckedImage,
                0,
                SpringLayout.NORTH, notePanel);
        noteLayout.putConstraint(
                SpringLayout.EAST, uncheckedImage,
                -25,
                SpringLayout.EAST, notePanel);
        noteLayout.putConstraint(
                SpringLayout.SOUTH, uncheckedImage,
                0,
                SpringLayout.SOUTH, notePanel);
        // notePanel
        layout.putConstraint(
                SpringLayout.SOUTH, notePanel,
                -20,
                SpringLayout.NORTH, footerBkg);
        layout.putConstraint(
                SpringLayout.WEST, notePanel,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, notePanel,
                0,
                SpringLayout.EAST, this);
        
    }
    
    // Footer
    private JLabel studyingTime;
    private Rectangle footerBkg;
    private void addFooterView() {
        studyingTime = new JLabel();
        studyingTime.setFont(FontDefine.SUBJECT_INFO);
        studyingTime.setForeground(ColorDefine.FOOTER);
        studyingTime.setBackground(ColorDefine.FOOTER_BKG);
        studyingTime.setOpaque(true);
        components.put("StudyingTime", studyingTime);
        this.add(studyingTime);
        
        footerBkg = new Rectangle(0, 0, 10000, 10000, ColorDefine.FOOTER_BKG);
        this.add(footerBkg);
    }
    private void setFooterConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // studyingTime
        layout.putConstraint(
                SpringLayout.SOUTH, studyingTime,
                -10,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, studyingTime,
                0,
                SpringLayout.HORIZONTAL_CENTER, this);
        // footerBkg
        layout.putConstraint(
                SpringLayout.NORTH, footerBkg,
                -10,
                SpringLayout.NORTH, studyingTime);
        layout.putConstraint(
                SpringLayout.SOUTH, footerBkg,
                0,
                SpringLayout.SOUTH, this);
        layout.putConstraint(
                SpringLayout.WEST, footerBkg,
                0,
                SpringLayout.WEST, this);
        layout.putConstraint(
                SpringLayout.EAST, footerBkg,
                0,
                SpringLayout.EAST, this);
    }
    
}
