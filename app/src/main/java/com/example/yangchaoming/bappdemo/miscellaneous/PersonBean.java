package com.example.yangchaoming.bappdemo.miscellaneous;

public class PersonBean {
    public PersonBean(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String name;
    public String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
