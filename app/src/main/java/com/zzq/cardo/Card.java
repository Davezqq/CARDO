package com.zzq.cardo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.CardDbSchema.CardBaseHelper;
import database.CardDbSchema.CardCursorWrapper;
import database.CardDbSchema.TaskDbSchema;

/**
 * Created by zzq on 2017/12/21.
 */

public class Card extends Fragment{
    private UUID mcardId;//card 唯一标识
    private Date mcard_data_text;//card所属日期
    private int mtotal_task;//卡片中包含的任务总数
    private int mremain_task;//已完成数量
    private int color_index;//卡片颜色序号
    private int pesu;
    private UUID mTasksListid;//卡片对应任务列表的唯一标识
    public UUID getMcardId() {
        return mcardId;
    }
    public void setMcardId(UUID uuid) {
        mcardId=uuid;
    }
    public Date getMcard_data_text() {
        return mcard_data_text;
    }

    public void setMcard_data_text(Date mcard_data_text) {
        this.mcard_data_text = mcard_data_text;
    }
    public void setMcard_data_text(int year,int month,int day) {
        this.mcard_data_text = new Date(year-1900,month,day);
    }
    public int getMtotal_task() {
        return mtotal_task;
    }

    public void setMtotal_task(int mtotal_task) {
        this.mtotal_task = mtotal_task;
    }

    public int getMremain_task() {
        return mremain_task;
    }

    public void setMremain_task(int mremain_task) {
        this.mremain_task = mremain_task;
    }
    public int getColor_index() {
        return color_index;
    }
    public void setColor_index(int color_index) {
        this.color_index = color_index;
    }

    public int getPesu() {
        return pesu;
    }
    public void setPesu(int pesu) {
        this.pesu = pesu;
    }

    public UUID getTasksListid() {
        return mTasksListid;
    }

    public void setTasksListid(UUID uuid) {
       mTasksListid=uuid;
    }
    public String getDateformat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(mcard_data_text);
    }
}
