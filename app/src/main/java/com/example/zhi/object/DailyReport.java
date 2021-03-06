package com.example.zhi.object;

/**
 * 提交日报返回
 *
 * Author: Eron
 * Date: 2016/3/5 0005
 * Time: 11:15
 */
public class DailyReport {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int state;
        private String text;

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
    }

}
