package ru.bkmz.PersidinGecson.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import ru.bkmz.PersidinGecson.data.Data;
import ru.bkmz.PersidinGecson.data.NameList;
import ru.bkmz.PersidinGecson.util.Notification;
import ru.bkmz.PersidinGecson.util.Table;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ControllerMain {

    public DatePicker date;
    public TextField addText;
    public Button addButton;
    public Button delete;
    public TableView table;
    public TextField timeText;
    public Button timeButton;

    ObservableList<CheckBox> listOLL = FXCollections.observableArrayList();
    ArrayList<String> current_dateValuetS = Data.CURRENT_DATE.getValuetAL();
    ArrayList<String> dateArrayList = Data.DATE.getValuetAL();
    ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    ObservableList<Table> list = FXCollections.observableArrayList();

    public void initialize() {

        date.setPromptText(NameList.DATE_FORMAT.getName());
        addText.setPromptText(NameList.NAME_ADD_TXT.getName());
        addButton.setText(NameList.NAME_ADD_BUTTON.getName());
        delete.setText(NameList.NAME_DELETE_BUTTON.getName());
        timeText.setPromptText(NameList.TIME_TEXT.getName());
        timeText.setText(String.valueOf(Data.TIME.getValuetI()));
        timeButton.setText(NameList.TIME_BUTTON.getName());


        column(table, NameList.COLUMN_ID.getName(), "checkBox");
        column(table, NameList.COLUMN_NAME.getName(), "name");
        column(table, NameList.COLUMN_DATA.getName(), "data");

       
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(NameList.DATA_FORMAT_DATE.getName());

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

        };

        this.date.setConverter(converter);
        int size = current_dateValuetS.size();

        for (int i = 0; i < size; i++) {
            String[] dateS = dateArrayList.get(i).split( NameList.DATE_SPLIT.getName());
            bulderElemetList(current_dateValuetS.get(i), dateS, true);
            table.setItems(list);
        }

        System.out.println(Data.DATE.getValuetAL().size());
 /*       for (int i = 0; i < Data.DATE.getValuetAL().size(); i++) {
            String[] dateS = dateArrayList.get(i).split( NameList.DATE_SPLIT.getName());
            bulderElemetList(current_dateValuetS.get(i), dateS,true);
        }*/
        upList();
    }


    public void author(ActionEvent actionEvent) {

    }

    public static void column(TableView<Table> table, String nameColumn, String nameId) {
        TableColumn<Table, String> name = new TableColumn<>(nameColumn);
        name.setCellValueFactory(new PropertyValueFactory<>(nameId));
        table.getColumns().addAll(name);
    }

    public void add(ActionEvent actionEvent) {
        bulderElemetList(addText.getText(), this.date.getEditor().getText().split( NameList.DATE_SPLIT.getName()), false);
        upList();

    }

    public void delete(ActionEvent actionEvent) {
        deleteBoll();
        upList();
    }

    private void deleteBoll() {
        for (int i = checkBoxArrayList.size() - 1; i >= 0; i--) {
            if (checkBoxArrayList.get(i).isSelected()) {
                listOLL.remove(i);
                current_dateValuetS.remove(i);
                dateArrayList.remove(i);
                checkBoxArrayList.remove(i);
                list.remove(i);

            }
        }
        Data.CURRENT_DATE.setValuetAL(current_dateValuetS);
        Data.DATE.setValuetAL(dateArrayList);
    }

    void upList() {
        System.out.println(Data.CURRENT_DATE.getValuetAL());
        Data.CURRENT_DATE.getValuetAL().clear();
        System.out.println(Data.CURRENT_DATE.getValuetAL());
        Data.CURRENT_DATE.setValuetAL(current_dateValuetS);
        Data.DATE.setValuetAL(dateArrayList);
        Data.save();
        table.setItems(list);

    }

    void bulderElemetList(String name, String[] dateS, boolean load) {
        boolean tow = false;
        for (String s :
                current_dateValuetS) {
            if (s.equals(name) & !load) {
                new Notification(NameList.WARNING.getName(), NameList.THERE_IS_A_RECORD.getName(), Alert.AlertType.WARNING);
                tow = true;
            }
        }
        if (!tow) {
            if (dateS[0].equals("") | name.equals("")) {
                new Notification(NameList.WARNING.getName(), NameList.FILL_IN_ALL_THE_FIELDS.getName(), Alert.AlertType.WARNING);
            } else {
                int y, m, d;
                d = Integer.parseInt(dateS[0]);
                m = Integer.parseInt(dateS[1]) - 1;
                y = Integer.parseInt(dateS[2]) - 1900;
                Date date = new Date(y, m, d);

                SimpleDateFormat formatForDateNow = new SimpleDateFormat(NameList.DATA_FORMAT_DATE.getName());
                System.out.println(name + "  \t  " + formatForDateNow.format(date));
                CheckBox checkBox = new CheckBox();
                Table table = new Table(checkBox, name, formatForDateNow.format(date));
                list.add(table);

                if (!load) {
                    current_dateValuetS.add(name);
                    dateArrayList.add(formatForDateNow.format(date));
                    checkBoxArrayList.add(checkBox);
                    listOLL.add(checkBox);


                } else {
                    checkBoxArrayList.add(checkBox);
                    listOLL.add(checkBox);
                }


            }

        }
    }

    public void timeButton(ActionEvent actionEvent) {
        Data.TIME.setValue(timeText.getText());
        Data.save();
    }
}
