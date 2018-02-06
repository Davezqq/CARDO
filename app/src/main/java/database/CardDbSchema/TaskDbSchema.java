package database.CardDbSchema;

/**
 * Created by apple on 2018/2/1.
 */

public class TaskDbSchema {
    public static final class TaskTable{
        public static final String NAME = "tasks";
        public static final class Cols{
            public static final String tasklistid = "task_list_id";
            public static final String taskclock = "task_clock";
            public static final String taskcontent = "task_content";
            public static final String taskdetail = "task_detail";
            public static final String tasksloved = "task_solved";
            public static final String taskid = "task_id";
            public static final String taskhour = "task_hour";
            public static final String taskminute = "task_minute";
        }
    }
}
