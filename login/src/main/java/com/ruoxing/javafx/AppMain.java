package com.ruoxing.javafx;

/**
 * 项目:javafxtest
 * 日期:2017/11/27 22:47 星期一
 * 作者:Administrator
 * 描述:JavaFX的Main类
*/

import com.ruoxing.javafx.controller.ClientErrorController;
import com.ruoxing.javafx.controller.LoginController;
import com.ruoxing.javafx.service.ClientService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ResourceBundle;

public class AppMain extends Application {
    /**
     * 描述:日志
     */
    private static final Logger logger = Logger.getLogger(AppMain.class);

    private final ClientHandler handler = new ClientHandler();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void init() throws Exception {
        super.init();

    }

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage)  throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("登录");
        //设置舞台关闭事件
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        final Scene loginScene = handler.loadClient("/fxml/LoginController.fxml", "i18n.login");
        primaryStage.setScene(loginScene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/image/icon/login-icon-64px.png")));
        primaryStage.show();
    }


    /**
     * 描述:处理客户端的内部类
     */
    private class ClientHandler {
        private static final String loginFxml = "LoginController.fxml";
        private LoginController loginController;
        private ClientService clientService = ClientService.getClientService();

        /**
         * 描述:检查客户端是否合法
         * @throws IOException
         */
        private boolean checkClient() {
            return clientService.isValid();
        }

        /**
         * 描述:加载客户端场景
         * @param resources fxml
         * @param bundle    i18文件
         * @return  Scene
         * @throws IOException
         */
        private Scene loadClient(String resources, String bundle) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(resources));
            //设置国际化包资源
            if (bundle != null) {
                fxmlLoader.setResources(ResourceBundle.getBundle(bundle));
            }
            Pane root = fxmlLoader.load();
            loginController = fxmlLoader.getController();
            return new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        }


        private void initStage() {
            //获取屏幕宽度与高度
            final Rectangle2D screen = Screen.getPrimary().getBounds();
            final double width = screen.getWidth();
            final double height = screen.getHeight();
            //logger.info("屏幕属性[width=" + width + ",height=" + height + "]");
            primaryStage.setWidth(width / 7 * 2);
            primaryStage.setHeight(height / 7 * 3);
            //primaryStage.setTitle(primaryStage.getTitle() + "  " + errorController.dateTime.format(DateTimeUtils.DATE_FORMAT));
        }

    }

}
