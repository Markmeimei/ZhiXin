package com.example.zhi.object;

/**
 * 邮件详情实体类
 * <p/>
 * Author: Eron
 * Date: 2016/4/2 0002
 * Time: 10:55
 */
public class MailsDetail {

    private String id;
    private String state;
    private String shou_id;
    private String fa_id;
    private String title;
    private String content;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShou_id() {
        return shou_id;
    }

    public void setShou_id(String shou_id) {
        this.shou_id = shou_id;
    }

    public String getFa_id() {
        return fa_id;
    }

    public void setFa_id(String fa_id) {
        this.fa_id = fa_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
