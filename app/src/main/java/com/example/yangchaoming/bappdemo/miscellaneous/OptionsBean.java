package com.example.yangchaoming.bappdemo.miscellaneous;

public class OptionsBean {
    public String value;
    public boolean selected;

    public OptionsBean(String value, boolean selected) {
        this.value = value;
        this.selected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "OptionsBean{" +
                "value='" + value + '\'' +
                ", selected=" + selected +
                '}';
    }
}
