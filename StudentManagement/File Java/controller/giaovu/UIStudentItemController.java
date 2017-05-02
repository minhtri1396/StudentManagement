package controller.giaovu;

import controller.UIController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import model.pojo.Student;
import model.define.ColorDefine;
import util.Result;
import view.giaovu.UIStudentItemView;

public class UIStudentItemController extends UIController implements MouseListener {
    
    private boolean isChecked;
    
    private Result.ResponseReceiver receiver;
    
    public void setResponseReceiver(Result.ResponseReceiver receiver) {
        this.receiver = receiver;
    }
    
    private JLabel studentCodeLabel;
    private JPanel checkedSquare;
    private JTextPane studentNameTextPane;
    private JLabel studentFacultyLabel;
    
    public UIStudentItemController(UIStudentItemView uiStudentItemView) {
        super(uiStudentItemView);
        init();
    }
    
    public UIStudentItemController() {
        super(new UIStudentItemView());
        init();
    }
    
    private void init() {
        isChecked = false;
        
        studentCodeLabel = (JLabel)uiView.findViewById("StudentCodeLabel");
        checkedSquare = (JPanel)uiView.findViewById("CheckedSquare");
        studentNameTextPane = (JTextPane)uiView.findViewById("StudentNameTextPane");
        studentFacultyLabel = (JLabel)uiView.findViewById("StudentFacultyLabel");
        
        addListenerForViews();
    }
    
    private void addListenerForViews() {
        uiView.addMouseListener(this);
        studentNameTextPane.addMouseListener(this);
    }
    
    
    public void setStudent(Student student) {
        studentCodeLabel.setText(student.getCode());
        studentNameTextPane.setText(student.getName());
        studentFacultyLabel.setText(student.getFaculty());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isChecked = !isChecked;
        setCheckedSquareColor();
        if (receiver != null) {
            receiver.receiveResult(new Object[]{id, isChecked});
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (isChecked) {
        } else {
            checkedSquare.setBackground(ColorDefine.STUDENT_ITEM_BKG_HOVER_TAG);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCheckedSquareColor();
    }
    
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        setCheckedSquareColor();
    }
    
    private void setCheckedSquareColor() {
        if (isChecked) {
            checkedSquare.setBackground(ColorDefine.STUDENT_ITEM_BKG_TAG);
        } else {
            checkedSquare.setBackground(ColorDefine.STUDENT_ITEM_BKG_UNCHECKED_TAG);
        }
    }
    
}
