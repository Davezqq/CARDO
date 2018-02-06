package com.zzq.cardo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import database.CardDbSchema.CardBaseHelper;
import database.CardDbSchema.CardCursorWrapper;
import database.CardDbSchema.CardDbSchema;
import database.CardDbSchema.TaskCursorWrapper;
import database.CardDbSchema.TaskDbSchema;

import static database.CardDbSchema.TaskDbSchema.*;

/**
 * Created by apple on 2017/12/24.
 */

public class TasksList{
    private UUID tasklistid;
    private Date mDate;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private String contentdemo="demo content";
    private String particulardemo = "demo particular";
    public void  setTasklistid(UUID uuid){
        tasklistid = uuid;
    }
    public TasksList(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CardBaseHelper(mContext).getWritableDatabase();
    }
    public List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(TaskTable.Cols.tasklistid+"= ?",new String[]{tasklistid.toString()});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return tasks;
    }
    public Task getTask(UUID id){
        TaskCursorWrapper cursor = queryTasks(TaskTable.Cols.taskid+"='"+id.toString()+"'",null);
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTask();
        }finally {
            cursor.close();
        }
    }
    public void addTask(Task task){
        ContentValues values = getContenttaskvalues(task);
        mDatabase.insert(TaskTable.NAME,null,values);
    }
    public void DeleteTask(UUID taskid){
        String sql1 = "delete from "+TaskTable.NAME+" where "+ TaskTable.Cols.taskid+" = '"+taskid.toString()+"'";
        mDatabase.execSQL(sql1);
    }
    public void updateTask(Task task){
        ContentValues values = getContenttaskvalues(task);
        mDatabase.update(TaskTable.NAME,values,TaskTable.Cols.taskid+"=?",new String[]{task.getTaskid().toString()});
    }
    private static ContentValues getContenttaskvalues(Task task){
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.taskid,task.getTaskid().toString());
        values.put(TaskTable.Cols.taskcontent,task.getContent());
        values.put(TaskTable.Cols.taskdetail,task.getParticular());
        values.put(TaskTable.Cols.tasklistid,task.getTasklistid().toString());
        values.put(TaskTable.Cols.tasksloved,Boolean.toString(task.isSolved()));
        values.put(TaskTable.Cols.taskhour,task.getHours());
        values.put(TaskTable.Cols.taskminute,task.getMinute());
        return values;
    }
    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new TaskCursorWrapper(cursor);
    }
}
