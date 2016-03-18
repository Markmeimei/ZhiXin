package com.example.zhi.object;

/**
 * Author: Eron
 * Date: 2016/3/2 0002
 * Time: 10:11
 */
public class Login {
    private int code;
    private String message;
    private User User;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }

    public class User {
        private int id;
        private String name;
        private String group_type;
        private String m_phone;
        private String bumen_id;
        private String sex;
        private String birthday;
        private String qq;
        private String email;
        private String state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
