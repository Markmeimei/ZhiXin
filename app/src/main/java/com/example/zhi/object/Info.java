package com.example.zhi.object;

/**
 * 日报记录内容实体类
 *
 * Author: Eron
 * Date: 2016/3/30
 * Time: 22:00
 */
public class Info {

    private String id;
    private String name;
    private String content;
    private String date;
    private String date_nian;
    private String date_yue;
    private String ip;
    private String addtime;
    private String ydr;
    private String yd_time;
    private String userid;

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

    public String getDate_nian() {
        return date_nian;
    }

    public void setDate_nian(String date_nian) {
        this.date_nian = date_nian;
    }

    public String getDate_yue() {
        return date_yue;
    }

    public void setDate_yue(String date_yue) {
        this.date_yue = date_yue;
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

    public String getYdr() {
        return ydr;
    }

    public void setYdr(String ydr) {
        this.ydr = ydr;
    }

    public String getYd_time() {
        return yd_time;
    }

    public void setYd_time(String yd_time) {
        this.yd_time = yd_time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
