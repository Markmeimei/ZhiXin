package com.example.zhi.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务接收人实体类
 *
 * Author: Eron
 * Date: 2016/3/18 0018
 * Time: 11:50
 */
public class Receiver {
//    private ArrayList<ReceiverObject> receiverObjects;
//
//    public ArrayList<ReceiverObject> getReceiverObjects() {
//        return receiverObjects;
//    }
//
//    public void setReceiverObjects(ArrayList<ReceiverObject> receiverObjects) {
//        this.receiverObjects = receiverObjects;
//    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {
        private String id;
        private String name;
        private boolean isSelected;

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

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }

}
