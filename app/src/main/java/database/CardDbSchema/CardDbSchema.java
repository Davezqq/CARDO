package database.CardDbSchema;

/**
 * Created by apple on 2018/1/24.
 */

public class CardDbSchema {
    public static final class CardTable{
        public static  final String NAME = "cards";
        public static final class  Cols{
            public static final String UUID = "uuid";
            public static final String DATE = "date";
            public static final String TOTAL_TASK="total_task";
            public static final String REMAIN_TASK = "remain_task";
            public static final String COLOR="color";
            public static final String RANK ="rank";
            public static final String TASKLISTuuid="tasklist";
        }
    }
}
