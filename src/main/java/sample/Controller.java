package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.User;
import repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Controller {
    public Button btnLogin;
    public TextField txtFieldUsername;
    public TextField txtFieldPassword;
    public Button btnShow;
    public PasswordField pwdFieldPassword;
    public Label lblUsername;
    public Label lblInfo;
    public MenuItem mnutemLogin;
    public TabPane tabPane;
    public Tab tabLogin;
    public Tab tabMain;
    public TextField txtFieldUsernameRegister;
    public Button btnRegister;
    public PasswordField pwdFieldRegister;
    public PasswordField pwdFieldReEnterPasswordRegister;
    public MenuItem mnuItemRegister;
    public Tab tabRegister;
    private UserRepository userRepository;

    public void initialize() {
//        tabPane.getTabs().remove(tabLogin);
//        tabPane.getTabs().remove(tabMain);
        tabPane.getTabs().clear();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TODOFx");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        userRepository = new UserRepository(entityManager);


    }

    public void showMessageInConsole(ActionEvent actionEvent) {
        System.out.println("button clicked!");
    }

    public void closeAction(ActionEvent actionEvent) {
        System.out.println("close menu pressed");
    }

    public void login(ActionEvent actionEvent) {
        if (txtFieldUsername.getText().length() < 1) {
            lblUsername.setTextFill(Color.RED);
            lblInfo.setVisible(true);
            lblInfo.setText("Please fill usernae");
        } else {
            User user = userRepository.findById(txtFieldUsername.getText()).get();
            if(user.getPassword().equals(pwdFieldPassword.getText())) {
                System.out.println(txtFieldUsername.getText());
                System.out.println(txtFieldPassword.getText());
                tabPane.getTabs().remove(tabLogin);
                tabPane.getTabs().add(tabMain);
            }
            else {
                System.out.println("wrong password");
            }

        }
    }

    public void showPassword(ActionEvent actionEvent) {
        if (!txtFieldPassword.isVisible()) {
            btnShow.setText("Hide");
            txtFieldPassword.setText(pwdFieldPassword.getText());
            txtFieldPassword.setEditable(false);
            txtFieldPassword.setVisible(true);
            pwdFieldPassword.setVisible(false);
        } else {
            btnShow.setText("Show");
            txtFieldPassword.setVisible(false);
            pwdFieldPassword.setVisible(true);
        }
    }

    public void showLoginPane(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabLogin);
    }

    public void registerUser(ActionEvent actionEvent) {
        if(!userRepository.findById(txtFieldUsernameRegister.getText()).isPresent()) {
            if (pwdFieldRegister.getText().equals(pwdFieldReEnterPasswordRegister.getText())) {
                User user = new User();
                user.setUsername(txtFieldUsernameRegister.getText());
                user.setPassword(pwdFieldRegister.getText());

                userRepository.save(user);
                tabPane.getTabs().add(tabLogin);
                tabPane.getTabs().remove(tabRegister);
            }
        }
        else {
            lblInfo.setVisible(true);
            lblInfo.setText("User already exists");
        }
    }

    public void openRegisterTab(ActionEvent actionEvent) {
        tabPane.getTabs().add(tabRegister);
    }
}