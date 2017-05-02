package controller.login;

import app.Application;
import controller.UIController;
import controller.dao.LoginDAO;
import controller.dao.LoginDAO.LoginType;
import controller.login.subwindow.UIChangePasswordController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import model.define.StringDefine;
import view.login.UILoginView;

public class UILoginController extends UIController {
    
    private final JRadioButton studentLoginRadio;
    private final JRadioButton giaovuLoginRadio;
    private final JTextField userNameTextField;
    private final JPasswordField passTextField;
    private final JButton loginBtn;
    
    private boolean isLoading;
    
    public UILoginController() {
        super(new UILoginView());
        
        studentLoginRadio = (JRadioButton)uiView.findViewById("StudentLoginRadio");
        giaovuLoginRadio = (JRadioButton)uiView.findViewById("GiaoVuLoginRadio");
        userNameTextField = (JTextField)uiView.findViewById("UserNameTextField");
        passTextField = (JPasswordField)uiView.findViewById("PassTextField");
        loginBtn = (JButton)uiView.findViewById("LoginButton");
        
        isLoading = false;
        
        addListenerForViews();
    }

    private void addListenerForViews() {
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isLoading && isCompleted()) {
                    LoginType loginType = studentLoginRadio.isSelected() ?
                            LoginType.STUDENT : LoginType.GIAO_VU;
                    String userID = userNameTextField.getText();
                    if (LoginDAO.BUILDER.checkMapping(
                            userID,
                            new String(passTextField.getPassword()),
                            loginType))
                    {
                        if (userID.equals(new String(passTextField.getPassword()))) {
                            UIChangePasswordController uiChangePasswordController =
                                    new UIChangePasswordController();
                            uiChangePasswordController.setInformation(userID, LoginType.STUDENT);
                            uiChangePasswordController.setResponseReceiver((Object obj) -> {
                                Application.getInstance().setVisible(true);
                                Application.getInstance().requestFocusInWindow();
                                Application.getInstance().setEnabled(true);
                                boolean isSuccessed = (boolean)obj;
                                if (isSuccessed) {
                                    login();
                                }
                            });
                            Application.getInstance().setEnabled(false);
                            uiChangePasswordController.show();
                        } else {
                            login();
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Tên đăng nhập không tồn tại hoặc mật khẩu không chính xác",
                                "Không thể đăng nhập",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });
    }
    
    private boolean isCompleted() {
        String messageTitle = null;
        String message = null;
        if (userNameTextField.getText().trim().length() == 0) {
            messageTitle = "Chưa điền tên đăng nhập";
            message = "Bạn cần điền tên đăng nhập";
        } else if (passTextField.getPassword().length == 0) {
            messageTitle = "Chưa điền mật khẩu";
            message = "Bạn cần điền mật khẩu để đăng nhập";
        }

        if (message == null) {
            return true;
        }

        JOptionPane.showMessageDialog(
                null,
                message,
                messageTitle,
                JOptionPane.INFORMATION_MESSAGE
        );

        return false;
    }
    
    private void login() {
        if (isLoading) {
        } else {
            isLoading = true;
            loginBtn.setText("Loading...");
            setEnableViews(false);
            String userID = userNameTextField.getText();

            new Thread(() -> {
                UIController uiController;
                if (studentLoginRadio.isSelected()) { // student login
                    uiController = new controller.sinhvien.UIMainController(userID);
                } else { // giaovu login
                    uiController = new controller.giaovu.UIMainController(userID);
                }
                Application.getInstance().show(uiController.getContentView(), userID);
                passTextField.setText("");
                isLoading = false;
                loginBtn.setText(StringDefine.LOGIN_BTN);
                setEnableViews(true);
            }).start();
        }
    }
    
    private void setEnableViews(boolean enable) {
        loginBtn.setEnabled(enable);
        giaovuLoginRadio.setEnabled(enable);
        studentLoginRadio.setEnabled(enable);
        userNameTextField.setEnabled(enable);
        passTextField.setEnabled(enable);
    }
    
}
