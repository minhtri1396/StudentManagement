package controller.giaovu.subwindow;

import controller.UISubWindowController;
import controller.giaovu.subwindow.UIInputTypeController.ResultType;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import model.pojo.Student;
import util.file.StudentsListFile;
import util.file.TemplateFile;
import view.giaovu.subwindow.UIImportingFileView;

public class UIImportingFileController extends UISubWindowController {
    
    private final JTextField pathTextField;
    private final JLabel addingPathLabel;
    private final JButton templateBtn;
    private final JButton okBtn;
    
    public UIImportingFileController() {
        super(new UIImportingFileView());
        super.centerWindow();
        
        pathTextField = (JTextField)uiView.findViewById("PathTextField");
        addingPathLabel = (JLabel)uiView.findViewById("AddingPathLabel");
        templateBtn = (JButton)uiView.findViewById("TemplateButton");
        okBtn = (JButton)uiView.findViewById("OKButton");
        
        addListenersForViews();
    }
    
    private void addListenersForViews() {
        addingPathLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = getFileChooser("Chọn tập tin csv cần lấy thông tin");
                String currentPath = pathTextField.getText().trim();
                if (currentPath.length() > 0) {
                    fileChooser.setCurrentDirectory(new File(currentPath));
                }
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return "CSV Files (*.csv)";
                    }

                    @Override
                    public boolean accept(File f) {
                        if (f.isDirectory()) {
                            return true;
                        } else {
                            String filename = f.getName().toLowerCase();
                            return filename.endsWith(".csv") ;
                        }
                    }
                });
                if (fileChooser.showOpenDialog(uiView) == JFileChooser.APPROVE_OPTION) {
                    pathTextField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        
        templateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = getFileChooser("Chọn nơi lưu template");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (fileChooser.showSaveDialog(uiView) == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getPath();
                    if (path.charAt(path.length() - 1) != '\\') {
                        path += '\\';
                    }
                    path += TemplateFile.TEMPLATE_NAME;
                    path = TemplateFile.saveTo(path);
                    try {
                        Desktop.getDesktop().open(new File(path));
                        JOptionPane.showMessageDialog(
                            null,
                            "Tập tin template đã được tạo và đang mở tự động",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    pathTextField.setText(path);
                }
            }
        });
        
        okBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Student[] students = StudentsListFile.getStudentsFrom(
                        pathTextField.getText());
                if (students == null || students.length == 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Có vẻ như chương trình không đọc được nội dung tập tin CSV của bạn. Lỗi này có thể vì:\n"
                                + "  + Tập tin CSV không đúng định dạng. Hãy đảm bảo rằng bạn đã nhập đúng theo Template của chương trình.\n"
                                + "  + Tập tin CSV đã bị thây đổi đường dẫn. Nếu bạn chắc chắn rằng đường dẫn đã chính xác,\n"
                                + "    hãy thử lại lần nữa.\n"
                                + "  + Thông tin các cột trong Template chưa được điền đầy đủ.",
                        "Không đọc được tập tin CSV",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    close();
                    if (receiver != null) {
                        receiver.receiveResult(students);
                    }
                }
            }
        });
    }
    
    private JFileChooser getFileChooser(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);  
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        return fileChooser;
    }
    
    @Override
    public void close() {
        super.close();
        close(ResultType.NONE);
    }
    
    private void close(ResultType result) {
        if (receiver != null) {
            receiver.receiveResult(result);
        }
    }
    
}
