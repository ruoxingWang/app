package com.ruoxing.javafx.service;

import com.ruoxing.javafx.cipher.Decryption;
import com.ruoxing.javafx.utils.DateTimeUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 项目 :app
 * 类型 :class
 * 日期 :2018-08-02 星期四
 * 作者 :ruoxing
 * 描述 :
 */
public class ClientService implements Decryption, DateTimeUtils {

    private static final ClientService clientService = new ClientService();
    public static ClientService getClientService() {
        return clientService;
    }
    private static final Logger logger = Logger.getLogger(ClientService.class);


    /**
     * 描述:客户端有效期
     */
    final private StringProperty validDate = new SimpleStringProperty();
    final private BooleanProperty valid = new SimpleBooleanProperty(false);
    public ClientService() {
        try {
            final LocalDateTime validDate = decode();
            this.valid.set(validDate.isAfter(beijingDateTime()));
            this.validDate.set(decode().format(DATE_FORMAT));
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("验证客户端有效性失败!!!");
            alert.setContentText("需要进行联网登录验证\n" + "请开启网络,完成登录!");
            final Optional<ButtonType> buttonType = alert.showAndWait();
            System.exit(0);
        }
    }


    public StringProperty validDateProperty() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate.set(validDate);
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }

    public String getValidDate() {
        return validDate.get();
    }

    public boolean isValid() {
        return valid.get();
    }
}
