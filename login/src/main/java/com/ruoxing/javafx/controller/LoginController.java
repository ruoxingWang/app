package com.ruoxing.javafx.controller;

import com.ruoxing.javafx.service.ClientService;
import com.ruoxing.javafx.service.UserService;
import com.ruoxing.javafx.utils.StringUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

/**
 * 项目:JavaSE
 * 类型:class
 * 日期:2018/7/29  星期日
 * 作者:ruoxing
 * 描述:
 */
public class LoginController implements StringUtils {
    @FXML
    public Label bottomPane;

    @FXML
    private BorderPane rootPane;
    @FXML
    private GridPane loginPane;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnLogin;
    @FXML
    public CheckBox btnRemember;

    private LoginHandler handler = new LoginHandler();
    private ClientService clientService = ClientService.getClientService();
    @FXML
    private void initialize() {

        //宽度、高度的属性绑定
        btnClear.prefWidthProperty().bind(tfUsername.widthProperty().divide(2));
        btnLogin.prefWidthProperty().bind(tfUsername.widthProperty().divide(2));

        //设置获失焦点的属性监听
        tfUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
        });
        //tfPort.setTooltip(new Tooltip("密码" + "长度不能超过12位"));
        tfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            /*if (newValue.length() > 12) {
                tfPassword.setText(oldValue);
                tfPassword.getTooltip().
            }*/
        });


        btnClear.setOnAction(event -> {
            tfUsername.clear();
            tfPassword.clear();
        });
        //监听连接
        btnLogin.setOnAction(event -> {
            final String username = tfUsername.getText().trim();
            final String password = tfPassword.getText().trim();
            if (!handler.checkLogin(username, password)) {
                handler.LOGIN_ERROR.show();
                return;
            }
            final boolean login = handler.userService.login(username, password);
            if (!login) {
                handler.LOGIN_ERROR.show();
            } else {
                handler.saveLogin();
                handler.LOGIN_SUCCESS.show();
            }
        });
        bottomPane.textProperty()
                .bind(new SimpleStringProperty("客户端有效期至:")
                        .concat(clientService.validDateProperty()));
        //加载登录信息
        handler.loadLastLogin();
        handler.randomBackgroundImage();
    }



    private class LoginHandler {
        private String loginProperties = "/properties/login.properties";
        private UserService userService = new UserService();
        private final Alert LOGIN_ERROR = new Alert(Alert.AlertType.ERROR, "输入的账号或密码!!!");
        private final Alert LOGIN_SUCCESS = new Alert(Alert.AlertType.INFORMATION, "登录成功!!!");
        private final Logger logger = Logger.getLogger(LoginController.class);

        /**
         * 描述:检查登录输入
         * @param username  输入的账号
         * @param password  输入的密码
         * @return
         */
        private boolean checkLogin(String username, String password) {
            if (emptyString(username) || emptyString(password)) {
                return false;
            }
            if (username.length() < 3 || password.length() < 3) {
                return false;
            }
            return true;
        }

        /**
         * 描述:加载上次登录信息
         */
        private void loadLastLogin() {
            try (InputStream resource = this.getClass().getResourceAsStream(loginProperties)) {
                final Properties prop= new Properties();
                prop.load(resource);
                final boolean remember = Boolean.parseBoolean(prop.getProperty("login.last.remember"));

                if (remember) {
                    //选择了记住账号密码
                    btnRemember.setSelected(remember);
                    final String username = prop.getProperty("login.last.username");
                    final String password = prop.getProperty("login.last.password");
                    tfUsername.setText(username);
                    tfPassword.setText(password);
                    logger.info("上次登录信息:" + "username=" + username + "," + "password=" + password);
                }
            } catch (IOException e) {
                logger.error("加载上次登录信息失败", e);
            }

        }

        /**
         * 描述:保存本次登录信息
         */
        private void saveLogin() {
            final Properties prop= new Properties();
            final String username = tfUsername.getText().trim();
            final String password = tfPassword.getText().trim();
            if (!btnRemember.isSelected()) {
                prop.put("login.last.remember", "false");
                prop.put("login.last.username", "");
                prop.put("login.last.password", "");
            } else {
                prop.put("login.last.remember", "true");
                prop.put("login.last.username", username);
                prop.put("login.last.password", password);
            }

            try {
                final File file = new File(this.getClass().getResource(loginProperties).toURI());
                final FileWriter fw = new FileWriter(file);
                prop.store(fw, "保存的登录信息");
                fw.close();
            } catch (Exception e) {
                logger.error("保存本次登录信息失败", e);
            }
        }

        private void randomBackgroundImage() {
            final Random RANDOM = new Random();
            final String path = LoginController.class.getResource("/image/png/login").getPath();
            final int maxNo = new File(path).listFiles().length;
            final int imageNo = RANDOM.nextInt(maxNo) + 1;
            rootPane.setStyle("-fx-background-image: url(/image/png/login/background-" + imageNo + ".png)");
        }

    }


}
