package com.example.zhi.object;

import java.util.List;

/**
 * 邮件实体类
 * <p/>
 * Author: Eron
 * Date: 2016/4/2 0002
 * Time: 10:51
 */
public class Mails {

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
        private List<Info> info;

        public class Info {
            private String id;
            private String title;
            private String date;
            private String fa_id;
            private String shou_id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getFa_id() {
                return fa_id;
            }

            public void setFa_id(String fa_id) {
                this.fa_id = fa_id;
            }

            public String getShou_id() {
                return shou_id;
            }

            public void setShou_id(String shou_id) {
                this.shou_id = shou_id;
            }
        }

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
