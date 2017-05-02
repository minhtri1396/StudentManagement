package controller.tool;

import controller.UIController;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import view.tool.UIQuantityFilterView;

public class UIQuantityFilterController extends UIController {
    
    public interface ResponseReceiver {
        void receiveNextObjects(List<Object> nextObjects);
    }
    
    private ResponseReceiver receiver;
    
    public void setResponseReceiver(ResponseReceiver receiver) {
        this.receiver = receiver;
    }
    
    private ArrayList<Object> objects;
    
    private int nItemPerPage;
    
    private int currentPage;
    
    private JComboBox pageComboBox;
    private JLabel noteLabel;
    
    public UIQuantityFilterController(UIQuantityFilterView uiFilterQuantityView) {
        super(uiFilterQuantityView);
        init();
    }
    
    public UIQuantityFilterController() {
        super(new UIQuantityFilterView());
        init();
    }

    private int selectedPageIndex;
    private void init() {
        currentPage = 0;
        objects = new ArrayList<>();
        
        pageComboBox = (JComboBox)uiView.findViewById("PageComboBox");
        pageComboBox.addItemListener((ItemEvent evt) -> {
            if (selectedPageIndex != pageComboBox.getSelectedIndex()) {
                selectedPageIndex = pageComboBox.getSelectedIndex();
                if (receiver != null) {
                    List<Object> nextObjects =
                            get(Integer.parseInt(
                                    ((String)pageComboBox.getSelectedItem()).split(" ")[1]) - 1);
                    receiver.receiveNextObjects(nextObjects);
                }
            }
        });
        noteLabel = (JLabel)uiView.findViewById("NoteLabel");
    }
    
    public void setFilterInfo(int nItemPerPage) {
        this.nItemPerPage = nItemPerPage;
    }
    
    public int getNumberPages() {
        return (int)Math.ceil(objects.size() * 1.0 / nItemPerPage);
    }
    
//    public void setList(Object[] objects) {
//        this.objects = new ArrayList<>(Arrays.asList(objects));
//        
//        pageComboBox.removeAllItems();
//        selectedPageIndex = 0;
//        for (int i = 0; i < objects.length; ++i) {
//            pageComboBox.addItem(String.format("Trang %d", i + 1));
//        }
//        
//        displayNumberItemOnCurrentPage();
//    }
    
    public List<Object> get(int page) {
        if (page < 0 || page >= getNumberPages()) {
            return null;
        }
        
        currentPage = page;
        
        displayNumberItemOnCurrentPage();
        
        int fromIndex = page * nItemPerPage;
        int toIndex = (page + 1) * nItemPerPage;
        if (toIndex > objects.size()) {
            return objects.subList(fromIndex, objects.size());
        } else {
            return objects.subList(fromIndex, toIndex);
        }
    }
    
    public void add(Object object) {
        objects.add(object);
        int nPages = getNumberPages();
        if (pageComboBox.getItemCount() != nPages) {
            for (int i = pageComboBox.getItemCount(); i < nPages; ++i) {
                pageComboBox.addItem(String.format("Trang %d", i + 1));
            }
        }
        displayNumberItemOnCurrentPage();
    }
    
    private void displayNumberItemOnCurrentPage() {
        noteLabel.setText(String.format("(%d/%d)", getNumberItemAt(currentPage), objects.size()));
    }
    
    public boolean isCurrentPageFull() {
        return ((currentPage * nItemPerPage) + nItemPerPage) < objects.size();
    }
    
    public int getNumberItemAt(int page) {
        if (page < 0 || page >= getNumberPages()) {
            return 0;
        }
        
        int toIndex = (page + 1) * nItemPerPage;
        if (toIndex > objects.size()) {
            int fromIndex = page * nItemPerPage;
            return objects.size() - fromIndex;
        } else {
            return nItemPerPage;
        }
    }
    
    public void remove(Object object) {
        objects.remove(object);
    }
    
    public void removeAt(int row) {
        objects.remove(row);
    }
    
    public void clear() {
        pageComboBox.setSelectedIndex(0);
        objects.clear();
    }
    
}
