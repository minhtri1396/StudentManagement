package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import model.AttendanceTable.MarkType;
import util.Image;
import view.UICheckBoxView;

public class UICheckBoxController extends UIController {
    public interface MouseClickListener {
        boolean mouseClicked(Message note);
    }
    
    public class Message {
        public MarkType getType() {
            return type;
        }
        public String getMessage() {
            return noteLabel.getText();
        }
    }
    
    private MouseClickListener mouseClickListener;
    
    public void setMouseClickListener(MouseClickListener mcl) {
        this.mouseClickListener = mcl;
    }
    
    private MarkType type;
    private JLabel noteLabel;
    private JLabel squareBoxLabel;
    
    private boolean isCanChangeAfterEditing;
    
    public UICheckBoxController(UICheckBoxView uiCheckBoxView) {
        super(uiCheckBoxView);
        init();
    }
    
    public UICheckBoxController() {
        super(new UICheckBoxView());
        init();
    }
    
    private void init() {
        isCanChangeAfterEditing = false;
        type = MarkType.EXPIRED;
        squareBoxLabel = (JLabel)uiView.findViewById("SquareBoxLabel");
        noteLabel = (JLabel)uiView.findViewById("NoteLabel");
        addListenerForView();
    }
    
    private void addListenerForView() {
        noteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkBoxMouseClicked(e);
            }
        });
        squareBoxLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkBoxMouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (type == MarkType.NORMAL) {
                    squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_trans.png", 32, 32));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (type == MarkType.NORMAL) {
                    squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick.png", 32, 32));
                }
            }
        });
    }
    
    private void checkBoxMouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (type == MarkType.NORMAL) {
                type = MarkType.CHECKED;
                squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_blue.png", 32, 32));
                sendMessage();
            } else if (isCanChangeAfterEditing) {
                if (type == MarkType.EXPIRED) {
                    type = MarkType.CHECKED;
                    if (sendMessage()) {
                        squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_blue.png", 32, 32));
                    }
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (isCanChangeAfterEditing) {
                if (type == MarkType.CHECKED) {
                    type = MarkType.EXPIRED;
                    if (sendMessage()) {
                        squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_red.png", 32, 32));
                    }
                }
            }
        }
    }
    
    private boolean sendMessage() {
        if (mouseClickListener != null) {
            return mouseClickListener.mouseClicked(new Message());
        }
        
        return true;
    }
    
    public void setCanChangeAfterEditing(boolean canChange) {
        isCanChangeAfterEditing = canChange;
    }
    
    public void setType(MarkType type) {
        this.type = type;
        switch (type) {
            case CHECKED:
                squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_blue.png", 32, 32));
                break;
            case EXPIRED:
                squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_red.png", 32, 32));
                break;
            case NOPE:
                squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick_gray.png", 32, 32));
                break;
            default:
                squareBoxLabel.setIcon(Image.BUILDER.getImageSrcFrom("stick.png", 32, 32));
                break;
        }
    }
    
    public void setNote(String note) {
        noteLabel.setText(note);
    }
    
    public void setToolTipText(String toolTipText) {
        noteLabel.setToolTipText(toolTipText);
        squareBoxLabel.setToolTipText(toolTipText);
    }
    
}
