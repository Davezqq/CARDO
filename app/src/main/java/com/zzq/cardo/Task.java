package com.zzq.cardo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by apple on 2017/12/24.
 */

public class Task {
    private GregorianCalendar clock;//task所属时间
    private UUID taskid;//task唯一标识id
    private String content;//task标题
    private String particular;//task细节
    private UUID tasklistid;//task所属tasklist的唯一标识id
    private boolean solved;//是否完成
    private int hours;//task的时刻中的小时
    private int minute;//task时刻中的分钟
     private static final String clockformat = "HH:mm";
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(clockformat);
    public UUID getTaskid(){return taskid;}
    public void setTaskid(UUID uuid){taskid=uuid;}
    public GregorianCalendar getClock() {
        return clock;
    }
    public String getClockformat(){
        return mSimpleDateFormat.format(clock);
    }
    public void setTasklistid(UUID uuid){
        tasklistid = uuid;
    }
    public UUID getTasklistid(){
        return tasklistid;
    }
    public void setClock(GregorianCalendar clock) {
        this.clock = clock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public boolean isSolved() {
        return solved;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
