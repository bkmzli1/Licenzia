package ru.bkmz.PersidinGecson.data;

import javafx.scene.control.Alert;
import ru.bkmz.PersidinGecson.util.Notification;

import java.io.*;
import java.util.*;

import static java.lang.Math.abs;


public enum Data {
    DATE(new ArrayList()),
    CURRENT_DATE(new ArrayList()),
    TIME(60);
    private static String fileAddress = "";
    private static String fileSave = fileAddress + "data.data";
    private Object value;


    Data(int value) {
        this.value = value;
    }

    Data(String values) {
        this.value = values;
    }

    Data(ArrayList<String> list) {
        this.value = list;
    }

    public int getValuetI() {
        System.out.println(value);
        return Integer.valueOf(String.valueOf(value)) ;
    }
    public void setTime(int min){
        if (min >= 0){
            this.value = value;
        }else {
            new Notification("Внимание", "время не может быть < или = 0", Alert.AlertType.ERROR);
        }

    }

    public String getValuetS() {
        return (String) value;
    }

    public ArrayList<String> getValuetAL() {
        List<?> list = new ArrayList<>();
        if (value.getClass().isArray()) {
            list = Arrays.asList((Object[]) value);
        } else if (value instanceof Collection) {
            list = new ArrayList<>((Collection<?>) value);
        }
        return (ArrayList<String>) list;
    }

    public void setValuetAL(ArrayList<String> arrayList) {
        this.value = arrayList;
    }


    public void setValue(Object value) {
        this.value = value;
    }

    public static void load() {
        try {
            read();
        } catch (FileNotFoundException e) {
            try {
                writer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            writer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void read() throws Exception {
        FileInputStream fiStream = new FileInputStream(new File(fileSave));
        ObjectInputStream oiStream = new ObjectInputStream(fiStream);

        for (int i = 0; i < Data.values().length; i++) {
            Object reading = oiStream.readObject();

            Data.values()[i].value = reading;
        }

        oiStream.close();
        fiStream.close();
    }

    private static void writer() throws IOException {
        File fileS = new File(fileAddress);

        if (!fileS.exists()) {
            fileS.mkdir();
        }

        FileOutputStream foStream = new FileOutputStream(new File(fileSave), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < Data.values().length; i++) {
            System.out.println(Data.values()[i].value);
            ooStream.writeObject(Data.values()[i].value);
        }

        ooStream.close();
        foStream.close();
    }


}
