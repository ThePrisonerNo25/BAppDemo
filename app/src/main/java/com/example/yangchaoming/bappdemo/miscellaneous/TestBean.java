package com.example.yangchaoming.bappdemo.miscellaneous;

public class TestBean {
    public String name;
    public int age;
    public String lastName;

    public TestBean(String name, int age, String lastName) {
        this.name = name;
        this.age = age;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
