package com.example.zhi.object;

import java.util.List;

/**
 * 任务列表实体类
 * <p/>
 * Author: Eron
 * Date: 2016/3/28 0028
 * Time: 11:37
 */
public class TaskList {

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

        public class Info {
            private String id;// 每条任务的id
            private String edate;
            private String name;
            private String content;
            private String date;
            private String list;//全部接收人
            private String jieshou;//已接收人id
            private String over;// 已完成人id
            private String ip;
            private String addtime;
            private String adduser;
            private String state;
            private String rwTag;
            private String dj;
            private String overtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEdate() {
                return edate;
            }

            public void setEdate(String edate) {
                this.edate = edate;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getList() {
                return list;
            }

            public void setList(String list) {
                this.list = list;
            }

            public String getJieshou() {
                return jieshou;
            }

            public void setJieshou(String jieshou) {
                this.jieshou = jieshou;
            }

            public String getOver() {
                return over;
            }

            public void setOver(String over) {
                this.over = over;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getAdduser() {
                return adduser;
            }

            public void setAdduser(String adduser) {
                this.adduser = adduser;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getRwTag() {
                return rwTag;
            }

            public void setRwTag(String rwTag) {
                this.rwTag = rwTag;
            }

            public String getDj() {
                return dj;
            }

            public void setDj(String dj) {
                this.dj = dj;
            }

            public String getOvertime() {
                return overtime;
            }

            public void setOvertime(String overtime) {
                this.overtime = overtime;
            }
        }

    }

}
