package com.example.zhi.object;

/**
 * 任务详情实体类
 *
 * Author: Eron
 * Date: 2016/4/1
 * Time: 22:00
 */
public class NoticeDetails {

    private String id;
    private String title;//标题
    private String content;//内容
    private String adduser;
    private String addtime;
    private String ip;
    private String editUser;
    private String editTime;
    private String editIp;
    private String times;
    private String del;
    private String tem_box;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditIp() {
        return editIp;
    }

    public void setEditIp(String editIp) {
        this.editIp = editIp;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getTem_box() {
        return tem_box;
    }

    public void setTem_box(String tem_box) {
        this.tem_box = tem_box;
    }
}
