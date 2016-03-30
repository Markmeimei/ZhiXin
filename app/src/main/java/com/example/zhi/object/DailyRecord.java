package com.example.zhi.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Eron
 * Date: 2016/3/5 0005
 * Time: 15:03
 */
public class DailyRecord {
    public Rb rb;

    public Rb getRb() {
        return rb;
    }

    public void setRb(Rb rb) {
        this.rb = rb;
    }

    public class Rb{
        private int state;
        private String text;
        public List<Info> info = new ArrayList<>();

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Info> getInfo() {
            return info;
        }

        public void setInfo(List<Info> info) {
            this.info = info;
        }

    }

}
