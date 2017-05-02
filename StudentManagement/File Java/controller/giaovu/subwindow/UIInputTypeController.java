package controller.giaovu.subwindow;

import controller.UISubWindowController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import view.giaovu.subwindow.UIInputTypeView;

public class UIInputTypeController extends UISubWindowController {
    
    public enum ResultType {
        INPUT_FROM_LIST,
        INPUT_BY_ADDING,
        INPUT_FROM_FILE,
        NONE
    }
    
    private final JLabel choiceAddingImage;
    private final JLabel choiceFromListImage;
    private final JLabel choiceImportImage;
    
    public UIInputTypeController() {
        super(new UIInputTypeView());
        super.centerWindow();
        
        choiceAddingImage = (JLabel)uiView.findViewById("ChoiceAddingImage");
        choiceFromListImage = (JLabel)uiView.findViewById("ChoiceFromListImage");
        choiceImportImage = (JLabel)uiView.findViewById("ChoiceImportImage");
        
        addListenersForViews();
    }
    
    private void addListenersForViews() {
        choiceAddingImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close(ResultType.INPUT_BY_ADDING);
            }
        });
        choiceFromListImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close(ResultType.INPUT_FROM_LIST);
            }
        });
        choiceImportImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close(ResultType.INPUT_FROM_FILE);
            }
        });
    }
    
    @Override
    public void close() {
        close(ResultType.NONE);
    }
    
    private void close(ResultType resultType) {
        super.close();
        receiver.receiveResult(resultType);
    }
    
}
