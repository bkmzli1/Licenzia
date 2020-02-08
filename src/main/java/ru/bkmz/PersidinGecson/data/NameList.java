package ru.bkmz.PersidinGecson.data;

public enum NameList {
    TIME_TEXT("время уведомления", "время уведомления"),
    TIME_BUTTON("изменить врямя уведомления", "изменить врямя уведомления"),
    WARNING("Внимание", "Внимание"),
    LiC("Просточка лицензии", "Просточка лицензии"),
    COLUMN_ID("выбор", "выбор"),
    COLUMN_NAME("имя", "имя"),
    COLUMN_DATA("дата", "дата"),
    FILL_IN_ALL_THE_FIELDS("Заполните все поля", "Заполните все поля"),
    THERE_IS_A_RECORD("Такая запись есть", "Такая запись есть"),
    DATE_FORMAT("дд/мм/гггг", "дд/мм/гггг"),
    DATE_SPLIT("/", "/"),
    DATA_FORMAT_DATE("dd/MM/yyyy", "dd/MM/yyyy"),
    NAME_ADD_BUTTON("Добавить", "Добавить"),
    NAME_ADD_TXT("напоминани продукта", "напоминание продукта"),
    NAME_DELETE_BUTTON("Удалить выбронное", "Удалить выбронное");
    String value;

    NameList(String name, String value) {
        this.value = value;
    }
    public  String getName(){
        return value;
    }
}
