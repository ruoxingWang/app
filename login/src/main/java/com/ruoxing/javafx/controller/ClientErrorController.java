package com.ruoxing.javafx.controller;

import com.ruoxing.javafx.service.ClientService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

/**
 * 项目:app
 * 类型:class
 * 日期:2018/7/30  星期一
 * 作者:ruoxing
 * 描述:
 */
public class ClientErrorController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label topPane;
    @FXML
    private Button btnClose;
    @FXML
    public Label bottomPane;

    private static final Logger logger = Logger.getLogger(ClientErrorController.class);
    private ClientService clientService = ClientService.getClientService();

    @FXML
    private void initialize() {
        btnClose.setOnAction(event -> System.exit(0));


        bottomPane.textProperty()
                .bind(new SimpleStringProperty("有效期至:")
                        .concat(clientService.validDateProperty()));
        /*topPane.prefWidthProperty().bind(rootPane.prefWidthProperty());
        btnClose.prefWidthProperty().bind(rootPane.prefWidthProperty());
        btnClose.prefHeightProperty().bind(rootPane.prefHeightProperty().subtract(topPane.prefHeightProperty()));*/
    }

}
