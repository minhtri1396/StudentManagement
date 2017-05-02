package app;


import java.awt.CardLayout;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;
import model.define.SizeDefine;
import view.UIView;

public class Application extends JFrame {
    
    private Queue<String> screenNamesQueue;
    
    private static final Application THIS = new Application();
    
    private CardLayout card = new CardLayout();
    
    private HashMap<String, UIView> namesMap;
            
    private String showingViewName;
    
    public static Application getInstance() {
        return THIS;
    }
    
    private Application() {
        super.setMinimumSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        super.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                UIView showingView = namesMap.get(showingViewName);
                namesMap.remove(showingViewName);
                showingViewName = screenNamesQueue.poll();
                if (showingViewName == null) {
                    System.exit(0);
                } else {
                    card.removeLayoutComponent(showingView);
                    getContentPane().remove(showingView);
                    setTitle(String.format("Attendance (%s)", showingViewName));
                    card.show(getContentPane(), showingViewName);
                    setExtendedState(JFrame.MAXIMIZED_BOTH); 
                    setVisible(true);
                    revalidate();
                    repaint();
                }
            }
        });
        
        card = new CardLayout();
        super.getContentPane().setLayout(card);
        
        namesMap = new HashMap<>();
        screenNamesQueue = new LinkedList<>();
        showingViewName = null;
    }
    
    public void show(UIView uiView, String viewName) {
        if (showingViewName != null) {
            screenNamesQueue.offer(showingViewName);
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            this.setVisible(true);
        }
        getContentPane().add(uiView, viewName);
        card.show(getContentPane(), viewName);
        
        showingViewName = viewName;
        namesMap.put(viewName, uiView);
        
        super.setTitle(String.format("Attendance (%s)", showingViewName));
    }
    
}
