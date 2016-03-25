package com.example.zhi.object;

/**
 * 主页Item实体类
 *
 * Author: Eron
 * Date: 2016/3/25 0025
 * Time: 9:41
 */
public class MainItemBean {
    private int resourceId;
    private int itemNameResourceID;
    private int index; // Activity 标志

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getItemNameResourceID() {
        return itemNameResourceID;
    }

    public void setItemNameResourceID(int itemNameResourceID) {
        this.itemNameResourceID = itemNameResourceID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
