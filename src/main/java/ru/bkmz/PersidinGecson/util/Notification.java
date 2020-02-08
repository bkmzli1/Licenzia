package ru.bkmz.PersidinGecson.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Notification {
    public Notification(String name, String info) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
        System.out.println(name + ": " + info);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
            }
        });
    }

    public Notification(String name, String info, Alert.AlertType alert) {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                System.out.println(name + ": " + info);
                Alert nAlert = new Alert(alert);
                nAlert.setTitle(name);
                nAlert.setHeaderText(null);
                nAlert.setContentText(info);
                nAlert.showAndWait();
            }
        });

    }
}
