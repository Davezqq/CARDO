package com.zzq.cardo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import database.CardDbSchema.CardBaseHelper;
import database.CardDbSchema.CardCursorWrapper;
import database.CardDbSchema.CardDbSchema.CardTable;
import database.CardDbSchema.countSchema;

/**
 * Created by zzq on 2017/12/23.
 */

public class CardLab {
    private static CardLab sCardLab;//单例 Card列表
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static CardLab get(Context context){
         if(sCardLab == null) {
             synchronized (CardLab.class) {
                 if (sCardLab == null) {
                     sCardLab = new CardLab(context);
                 }
             }
         }
            return sCardLab;
    }
    private CardLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CardBaseHelper(mContext).getWritableDatabase();
    }
    public List<Card> getCards(){
        List<Card> cards = new ArrayList<>();
        CardCursorWrapper cursor = queryCards(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cards.add(cursor.getCard());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return cards;
    }//从数据库获得card列表
    public Card getCard(UUID id){
        CardCursorWrapper cursor = queryCards(CardTable.Cols.UUID+"=?",new String[]{id.toString()});
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCard();
        }finally {
            cursor.close();
        }
    }//从数据库中获得id对应card
    public void addCard(Card c){
            ContentValues values = getContentValues(c);
            mDatabase.insert(CardTable.NAME,null,values);
            String sql1 = "update "+ countSchema.CountTable.NAME+" set "+ countSchema.CountTable.Cols.Count+" = "+c.getPesu();
            mDatabase.execSQL(sql1);
    }//添加card
    public void DeleteCard(UUID uuid){

      String sql="delete from "+CardTable.NAME+" where uuid='"+uuid.toString()+"'";
      mDatabase.execSQL(sql);
    }

    private  static ContentValues getContentValues(Card card){
        ContentValues values = new ContentValues();
        values.put(CardTable.Cols.UUID,card.getMcardId().toString());
        values.put(CardTable.Cols.DATE,card.getMcard_data_text().toString());
        values.put(CardTable.Cols.COLOR,card.getColor_index());
        values.put(CardTable.Cols.RANK,card.getPesu());
        values.put(CardTable.Cols.TASKLISTuuid,card.getTasksListid().toString());
        values.put(CardTable.Cols.TOTAL_TASK,card.getMtotal_task());
        values.put(CardTable.Cols.REMAIN_TASK,card.getMremain_task());
        return  values;
    }
    public void updateCard(Card card){
        String uuidString = card.getMcardId().toString();
        ContentValues values = getContentValues(card);
        mDatabase.update(CardTable.NAME,values, CardTable.Cols.UUID + "=?",new String[]{uuidString});
    }
    private CardCursorWrapper queryCards(String whereClause,String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CardTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CardCursorWrapper(cursor);
    }
}
