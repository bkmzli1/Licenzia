package ru.bkmz.PersidinGecson;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ru.bkmz.PersidinGecson.data.Data;
import ru.bkmz.PersidinGecson.data.NameList;
import ru.bkmz.PersidinGecson.util.Notification;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import static java.lang.Math.abs;

public class Main extends Application {
    public static final String APPLICATION_NAME = "Лицензии";
    static String[] argsP;
    Thread window = new Thread();

    public static void main(String[] args) throws InterruptedException {
        argsP = args;
        launch();
    }

    @Override
    public void start(Stage hideStage) throws Exception {
        if (!SystemTray.isSupported()) {
            return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("завершить");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayMenu.add(item);
        item = new MenuItem("открыть");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

              openWindow();
            }
        });
        trayMenu.add(item);

        URL inputStream = ClassLoader.class.getResource("/img/icon.png");
        System.out.println(inputStream);
        Image icon = Toolkit.getDefaultToolkit().getImage(inputStream);
        TrayIcon trayIcon = new TrayIcon(icon, APPLICATION_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage(APPLICATION_NAME, "Application started!",
                TrayIcon.MessageType.INFO);


        hideStage.initStyle(StageStyle.UTILITY);
        hideStage.setWidth(0);
        hideStage.setHeight(0);
        hideStage.setOpacity(0);
        hideStage.show();
        System.out.println("1232222");
        openWindow();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Data.load();
                            int min = Data.TIME.getValuetI();
                            if (min == 0) {
                                try {
                                    System.out.println("ожидание = " + 10000);
                                    Thread.sleep(10000 );
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else if (Data.CURRENT_DATE.getValuetAL().size() > 0 & Data.DATE.getValuetAL().size() > 0) {
                                System.out.println("минут = " + min);
                                ArrayList<String> current_dateValuetS = Data.CURRENT_DATE.getValuetAL();
                                ArrayList<String> dateArrayList = Data.DATE.getValuetAL();
                                ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
                                int size = dateArrayList.size();
                                System.out.println(size);
                                for (int i = 0; i < size; i++) {
                                    String[] dateS = dateArrayList.get(i).split("/");
                                    int y, m, d,
                                            yn, mn, dn;
                                    d = Integer.parseInt(dateS[0]);
                                    m = Integer.parseInt(dateS[1]);
                                    y = Integer.parseInt(dateS[2]);
                                    Date dateP = new Date();
                                    SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd/MM/YYYY");
                                    formatForDateNow.setTimeZone(TimeZone.getTimeZone("Asia/Yekaterinburg"));
                                    dateS = formatForDateNow.format(dateP).split("/");
                                    dn = Integer.parseInt(dateS[0]);
                                    mn = Integer.parseInt(dateS[1]);
                                    yn = Integer.parseInt(dateS[2]);
                                    System.out.println(i);
                                    int finalI = i;

                                    System.out.println((y < yn) + ":" + y + " " + yn);
                                    if (y < yn) {
                                        new Notification(NameList.WARNING.getName(), NameList.LiC.getName()+"\"" + current_dateValuetS.get(finalI) + "\"\n" +
                                                "на " + abs(y - yn) + "\\" + abs(m - mn) + "\\" + abs(d - dn) + "\\");
                                    } else if (m < mn) {

                                        new Notification(NameList.WARNING.getName(), NameList.LiC.getName()+"\"" + current_dateValuetS.get(finalI) + "\"\n" +
                                                "на " + abs(y - yn) + "\\" + abs(m - mn) + "\\" + abs(d - dn) + "\\");
                                    } else if (d < dn) {

                                        new Notification(NameList.WARNING.getName(), NameList.LiC.getName()+"\"" + current_dateValuetS.get(finalI) + "\"\n" +
                                                "на " + abs(y - yn) + "\\" + abs(m - mn) + "\\" + abs(d - dn) + "\\");
                                    }
                                }
                            }

                            try {
                                System.out.println("ожидание = " + (1000 * 60) * min);
                                for (int i = 0; i < min; i++) {
                                    System.out.println(i+":min");
                                    for (int j = 0; j < 60; j++) {
                                        System.out.println(j+":sek");
                                        Thread.sleep(1000 );
                                    }

                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();

                            }
                        }
                    },"оповещение");
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void openWindow() {
        if (!window.isAlive()){
            window = new Thread(new Runnable() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            Parent root = loader.getRoot();
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());
                            stage.setScene(scene);
                            stage.setTitle(APPLICATION_NAME);
                            InputStream inputStream = ClassLoader.class.getResourceAsStream("/img/icon.png");
                            try {
                                javafx.scene.image.Image image = new javafx.scene.image.Image(inputStream);
                                stage.getIcons().add(image);
                            } catch (NullPointerException ne) {
                                System.out.println("icon null");
                            }
                            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                public void handle(WindowEvent we) {
                                    window.stop();
                                }
                            });
                            stage.show();
                        }
                    });

                }
            },"windowOpen");
            window.start();
        }else {
            new Notification(NameList.WARNING.getName(), "Окно уже открыто");
        }


    }
}
