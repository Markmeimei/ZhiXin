package com.example.zhi.object;

/**
 * Author: Eron
 * Date: 2016/3/17 0017
 * Time: 17:40
 */
public class ReceiverObject {
    private String id;
    private String name;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReceiverObject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
