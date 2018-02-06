package database.CardDbSchema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import com.zzq.cardo.Card;

import database.CardDbSchema.CardDbSchema.CardTable;

/**
 * Created by apple on 2018/1/24.
 */

public class CardBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME= "cardbase.db";
    public CardBaseHelper(Context context){
        super(context,DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CardTable.NAME + "(" + " _id integer primary key autoincrement, "+CardTable.Cols.UUID+", "+CardTable.Cols.DATE
        +", "+CardTable.Cols.RANK+", "+ CardTable.Cols.COLOR+","+CardTable.Cols.TOTAL_TASK+","+CardTable.Cols.TASKLISTuuid+", "+CardTable.Cols.REMAIN_TASK+")");
        db.execSQL("create table "+ countSchema.CountTable.NAME+"("+" _id integer primary key autoincrement, "+ countSchema.CountTable.Cols.Count+" )");
        db.execSQL("insert into "+ countSchema.CountTable.NAME+"( "+ countSchema.CountTable.Cols.Count+" )"+"values(0)");
        db.execSQL("create table "+ TaskDbSchema.TaskTable.NAME+"("+"_id integer primary key autoincrement, "+ TaskDbSchema.TaskTable.Cols.taskid+", "+
                TaskDbSchema.TaskTable.Cols.taskclock+", "+ TaskDbSchema.TaskTable.Cols.taskcontent+", "+ TaskDbSchema.TaskTable.Cols.taskdetail+", "+
                TaskDbSchema.TaskTable.Cols.tasklistid+", "+ TaskDbSchema.TaskTable.Cols.tasksloved+", "+ TaskDbSchema.TaskTable.Cols.taskhour+", "+ TaskDbSchema.TaskTable.Cols.taskminute+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
