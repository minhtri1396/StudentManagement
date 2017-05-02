package app;

public class Main {
    
    public static void main(String[] args) {
        Application app = Application.getInstance();
        controller.login.UILoginController uiLoginController = 
                new controller.login.UILoginController();
        app.show(uiLoginController.getContentView(), "login");
    }
    
}
