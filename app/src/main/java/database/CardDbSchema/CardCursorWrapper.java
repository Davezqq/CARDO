package database.CardDbSchema;

import android.database.Cursor;
import android.database.CursorWrapper;
import database.CardDbSchema.CardDbSchema.CardTable;


import com.zzq.cardo.Card;

import java.util.Date;
import java.util.UUID;

/**
 * Created by apple on 2018/1/25.
 */

public class CardCursorWrapper extends CursorWrapper {
    public  CardCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Card getCard(){
        String uuidString = getString(getColumnIndex(CardTable.Cols.UUID));
        String date = getString(getColumnIndex(CardTable.Cols.DATE));
        int total_task = getInt(getColumnIndex(CardTable.Cols.TOTAL_TASK));
        int color = getInt(getColumnIndex(CardTable.Cols.COLOR));
        int rank = getInt(getColumnIndex(CardTable.Cols.RANK));
        String tasklistid = getString(getColumnIndex(CardTable.Cols.TASKLISTuuid));
        int remaintask = getInt(getColumnIndex(CardTable.Cols.REMAIN_TASK));
        Card card=new Card();
        card.setMcardId(UUID.fromString(uuidString));
        card.setMcard_data_text(new Date(date));
        card.setMtotal_task(total_task);
        card.setColor_index(color);
        card.setTasksListid(UUID.fromString(tasklistid));
        card.setMremain_task(remaintask);
        return  card;
    }
}
