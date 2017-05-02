package controller.giaovu.subwindow;

import controller.UIFilterListController;
import controller.UISubWindowController;
import controller.dao.SubjectStudentDAO;
import controller.giaovu.UIStudentItemController;
import controller.giaovu.subwindow.UIInputTypeController.ResultType;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import model.pojo.Student;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import util.BitMap;
import view.UIFilterListView;

public class UIStudentsListController extends UISubWindowController {
    
    public static final int MAX_ITEM_ON_PAGE = 100;
    
    private Student[] students;
    private BitMap checkedIndice;
    private HashMap<Integer, Object[]> checkedMap; // object 1 = students, object 2 = checkedIndice
    
    private UIFilterListController uiFilterListController;
    private JButton addingStudentButton;
    private JComboBox schoolYearComboBox;
    
    private int checkingCount;
    private boolean isCanLoadFromDAO;
    
    public UIStudentsListController() {
        super(new UIFilterListView());
        super.centerWindow();
        
        init();
    }
    
    private void init() {
        this.checkingCount = 0;
        this.isCanLoadFromDAO = true;
        this.students = null;
        this.checkedMap = new HashMap<>();
        
        super.resize(new Dimension(1000, 700));
        this.uiFilterListController = new UIFilterListController((UIFilterListView)uiView);
        this.uiFilterListController.setTitle(StringDefine.STUDENTS_LIST_TITLE);
        this.uiFilterListController.setSubTitle("(Đã chọn 0 sinh viên)");
        
        this.setSchoolYearComboBox();
        this.setConfirmButton();
        
        this.addListenersForViews();
    }
    
    private int selectedSchoolYearIndex;
    private void setSchoolYearComboBox() {
        schoolYearComboBox = new JComboBox();
        schoolYearComboBox.setPreferredSize(SizeDefine.SCHOOLYEAR_COMBOBOX);
        schoolYearComboBox.setBackground(ColorDefine.BACKGROUND);
        schoolYearComboBox.setEditable(false);
        ((JLabel)schoolYearComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        selectedSchoolYearIndex = 0;
        for (int i = year - 6; i <= year; ++i) {
            schoolYearComboBox.addItem(i);
        }
        
        uiFilterListController.addLeftPanel(schoolYearComboBox);
    }
    
    private void setConfirmButton() {
        addingStudentButton = new JButton(StringDefine.CONFIRM_BUTTON);
        addingStudentButton.setPreferredSize(SizeDefine.CONFIRM_BUTTON);
        addingStudentButton.setFont(FontDefine.BUTTON);
        addingStudentButton.setForeground(ColorDefine.CONFIRM_BTN_TEXT);
        addingStudentButton.setBackground(ColorDefine.CONFIRM_BTN_BKG);
        
        uiFilterListController.addRightPanel(addingStudentButton);
    }
    
    private void addListenersForViews() {
        addingStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addingStudentButtonClicked();
            }
        });
        
        schoolYearComboBox.addItemListener((ItemEvent evt) -> {
            if (selectedSchoolYearIndex != schoolYearComboBox.getSelectedIndex()) {
                selectedSchoolYearIndex = schoolYearComboBox.getSelectedIndex();
                restoreCheckedThing();
                displayStudents();
            }
        });
    }
    
    private void addingStudentButtonClicked() {
        if (isCompleted()) {
            if (receiver != null) {
                ArrayList<Student> checkedStudents = new ArrayList<>();
                
                checkedMap.entrySet().forEach((entry) -> {
                    Student[] studentsIterator  = (Student[])(entry.getValue()[0]);
                    BitMap checkedIndiceTterator = (BitMap)(entry.getValue()[1]);
                    for (int i = 0; i < studentsIterator.length; ++i) {
                        if (checkedIndiceTterator.test(i)) {
                            checkedStudents.add(studentsIterator[i]);
                        }
                    }
                });
                
                receiver.receiveResult(checkedStudents.toArray(new Student[0]));
            }
            close();
        }
    }
    
    private boolean isCompleted() {
        if (students == null || students.length == 0 || !isCheckedAnything()) {
            JOptionPane.showMessageDialog(
                null,
                "Bạn cần phải chọn sinh viên trước khi nhấn nút Xác nhận!",
                "Chưa chọn sinh viên",
                JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
        
        return true;
    }
    
    private boolean isCheckedAnything() {
        Student[] studentsIterator;
        BitMap checkedIndiceTterator;
        for (HashMap.Entry<Integer, Object[]> entry : checkedMap.entrySet()) {
            studentsIterator = (Student[])(entry.getValue()[0]);
            checkedIndiceTterator = (BitMap)(entry.getValue()[1]);
            if (checkedIndiceTterator.numClear() != studentsIterator.length) {
                return true;
            } 
        }
        
        return false;
    }
    
    @Override
    public void show() {
        super.show();
        
        if (students == null) {
            restoreCheckedThing();
            displayStudents();
        }
    }
    
    private void restoreCheckedThing() {
        int schoolYear = (int)schoolYearComboBox.getSelectedItem();
        Object[] pair = checkedMap.get(schoolYear);
        if (pair == null) {
            if (isCanLoadFromDAO) {
                students = SubjectStudentDAO.BUILDER.getStudentsNotStudy(
                    super.getNote(),
                    schoolYear
                );
                if (students != null) {
                    checkedIndice = new BitMap(students.length);
                    pair = new Object[] {students, checkedIndice};
                    checkedMap.put(schoolYear, pair);
                }
            } else {
                students = null;
            }
        } else {
            students = (Student[]) pair[0];
            checkedIndice = (BitMap) pair[1];
        }
    }
    
    private void displayStudents() {
        if (students != null && students.length > 0) {
            UIStudentItemController uiStudentItemController;
            uiFilterListController.clear();
            uiFilterListController.setLoadingMode(true);
            int count = 0;
            for (Student student : students) {
                uiStudentItemController = new UIStudentItemController();
                uiStudentItemController.setStudent(student);
                uiStudentItemController.setId(count);
                uiStudentItemController.setResponseReceiver((Object obj) -> {
                    Object[] objects = (Object[])obj;
                    storeCheckedItem((int)objects[0], (boolean)objects[1]);
                });
                if (checkedIndice.test(count)) {
                    uiStudentItemController.setChecked(true);
                }
                uiFilterListController.addItem(uiStudentItemController.getContentView());
                uiFilterListController.validate();
                ++count;
            }
            uiFilterListController.setLoadingMode(false);
        } else if (uiFilterListController.getItemCount() > 0) {
            uiFilterListController.clear();
        }
    }
    
    private void storeCheckedItem(int id, boolean state) {
        if (state) {
            ++checkingCount;
            checkedIndice.mark(id);
        } else {
            --checkingCount;
            checkedIndice.clear(id);
        }
        
        uiFilterListController.setSubTitle(String.format("(Đã chọn %d sinh viên)", checkingCount));
    }
    
    public void setStudents(Student[] students) {
        if (students != null) {
            checkingCount = students.length;
            uiFilterListController.setSubTitle(String.format("(Đã chọn %d sinh viên)", checkingCount));
            
            Object[] objects; // object 1 = students, object 2 = checkedIndice
            for (Student student : students) {
                objects = checkedMap.get(student.getSchoolYear());
                if (objects == null) {
                    objects = new Object[2];
                    objects[0] = new ArrayList<>();
                    checkedMap.put(student.getSchoolYear(), objects);
                }
                ((ArrayList<Student>)objects[0]).add(student);
            }
            
            ArrayList<Student> studentsIterator;
            for (Object[] objs : checkedMap.values()) {
                studentsIterator =((ArrayList<Student>)objs[0]);
                objs[0] = studentsIterator.toArray(new Student[0]);
                objs[1] = new BitMap(studentsIterator.size());
                ((BitMap)objs[1]).markAll();
            }
            
            restoreCheckedThing();
            displayStudents();
        }
    }
    
    @Override
    public void close() {
        super.close();
        close(ResultType.NONE);
    }
    
    private void close(ResultType result) {
        receiver.receiveResult(result);
    }
    
    public void removeAt(int row) {
        uiFilterListController.removeAt(row);
    }
    
    public void clear() {
        uiFilterListController.clear();
    }
    
    public int getItemCount() {
        return uiFilterListController.getItemCount();
    }
    
}
