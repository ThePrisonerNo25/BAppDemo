package com.example.yangchaoming.bappdemo.miscellaneous;

public class ItemBean  {
    String tag;
    boolean isSelected;

    public ItemBean(String tag, boolean isSelected) {
        this.tag = tag;
        this.isSelected = isSelected;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
