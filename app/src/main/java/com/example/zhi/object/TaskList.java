package com.example.zhi.object;

/**
 * 任务列表实体类
 *
 * Author: Eron
 * Date: 2016/3/26
 * Time: 23:36
 */
public class TaskList {
    private String title;
    private String time;
    private String adduser;
    private String endtime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
