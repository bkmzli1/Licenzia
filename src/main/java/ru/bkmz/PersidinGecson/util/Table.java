package ru.bkmz.PersidinGecson.util;

import javafx.scene.control.CheckBox;

public class Table {
    CheckBox checkBox = new CheckBox();
    String name,data;

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Table(CheckBox checkBox, String name, String data) {
        this.checkBox = checkBox;
        this.name = name;
        this.data = data;
    }
}
