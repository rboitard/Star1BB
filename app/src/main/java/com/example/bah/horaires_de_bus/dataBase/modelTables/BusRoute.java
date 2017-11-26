package com.example.bah.horaires_de_bus.dataBase.modelTables;

/**
 * Created by Bah on 18/11/2017.
 */

public class BusRoute {

    private String shortName;
    private String longName;
    private String description;
    private String type;
    private String color;
    private  String textColor;

    public BusRoute(String shortName, String longName, String description, String type, String color, String textColor) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
        this.type = type;
        this.color = color;
        this.textColor = textColor;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public String toString() {
        return "BusRoute{" +
                "shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", textColor='" + textColor + '\'' +
                '}';
    }
}
