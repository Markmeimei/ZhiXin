package com.example.zhi.object;

/**
 * 登录实体类
 *
 * Author: Eron
 * Date: 2016/3/2 0002
 * Time: 10:11
 */
public class Login {

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
        private String sid;
        private Info info;

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

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public class Info {
            private String id;
            private String name;
            private String pw;
            private String group_type;
            private String m_phone;
            private String bumen_id;
            private String sex;
            private String birthday;
            private String qq;
            private String email;
            private String state;

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

            public String getPw() {
                return pw;
            }

            public void setPw(String pw) {
                this.pw = pw;
            }

            public String getGroup_type() {
                return group_type;
            }

            public void setGroup_type(String group_type) {
                this.group_type = group_type;
            }

            public String getM_phone() {
                return m_phone;
            }

            public void setM_phone(String m_phone) {
                this.m_phone = m_phone;
            }

            public String getBumen_id() {
                return bumen_id;
            }

            public void setBumen_id(String bumen_id) {
                this.bumen_id = bumen_id;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }






}
