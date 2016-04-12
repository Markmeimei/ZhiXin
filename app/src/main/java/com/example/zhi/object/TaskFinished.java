package com.example.zhi.object;

/**
 * 提交接收、完成任务 返回数据
 *
 * Author:Eron
 * Date: 2016/4/12 0012
 * Time: 14:55
 */
public class TaskFinished {

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
