package database.CardDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.zzq.cardo.Task;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by apple on 2018/2/1.
 */

public class TaskCursorWrapper extends CursorWrapper {
    public  TaskCursorWrapper(Cursor mCursor){super(mCursor);};
    public Task getTask(){
        String Taskid = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskid));
        String Tasklistid = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.tasklistid));
        String Taskclock =  getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskclock));
        String Taskcontent = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskcontent));
        String Taskdetail = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskdetail));
        String Tasksloved = getString(getColumnIndex(TaskDbSchema.TaskTable.Cols.tasksloved));
        int hours = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskhour));
        int minute = getInt(getColumnIndex(TaskDbSchema.TaskTable.Cols.taskminute));
        Task task = new Task();
        task.setTaskid(UUID.fromString(Taskid));
        task.setTasklistid(UUID.fromString(Tasklistid));
        task.setClock(new GregorianCalendar());
        task.setContent(Taskcontent);
        task.setParticular(Taskdetail);
        task.setSolved(Boolean.valueOf(Tasksloved));
        task.setHours(hours);
        task.setMinute(minute);
        return task;
    }
}
