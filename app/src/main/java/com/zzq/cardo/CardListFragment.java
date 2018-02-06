package com.zzq.cardo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import database.CardDbSchema.CardBaseHelper;
import database.CardDbSchema.countSchema;

import static android.app.DatePickerDialog.*;

/**
 * Created by apple on 2017/12/23.
 */

public class CardListFragment extends Fragment {
    private RecyclerView mCardRecyclerView;
    private CardAdapter mAdapter;
    static private int mcolor_index;
    static private int total;
    private static final String TAG="CardListFragment";
    private int yyear,mmonth,dday;
    private Calendar mCalendar;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list,container,false);
        mCardRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(mCardRecyclerView);
        updatetotal();
        updateUI();
        return view;
    }
    //更新数据库中任务总数total值
    public void updatetotal(){
        SQLiteDatabase mDatabase;
        Context context;
        context = getActivity();
        mDatabase = new CardBaseHelper(context).getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("select "+ countSchema.CountTable.Cols.Count+" from "+ countSchema.CountTable.NAME ,null);
        cursor.moveToFirst();
        total = cursor.getInt(0)+1;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        UUID uuid;
        uuid=mAdapter.getCardid();
        switch (item.getItemId()){
            case  1:
                CardLab.get(getActivity()).DeleteCard(uuid);
                updateUI();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //获取当日日期
    public void getDate(){
        mCalendar= Calendar.getInstance();
        yyear = mCalendar.get(Calendar.YEAR);
        mmonth = mCalendar.get(Calendar.MONTH);
        dday = mCalendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getDate();
    }
    //边栏菜单创建同时执行
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_card,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_card:
                final Card card = new Card();
                OnDateSetListener listener = new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                           card.setMcard_data_text(year,month,dayOfMonth);
                           CardLab.get(getActivity()).updateCard(card);
                          updateUI();
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),0,listener,yyear,mmonth,dday);
                dialog.show();
                card.setMcardId(UUID.randomUUID());
                card.setTasksListid(UUID.randomUUID());
                card.setMtotal_task(0);
                card.setPesu(total);
                card.setMremain_task(0);
                card.setColor_index(total++%9);
                card.setMcard_data_text(new Date());
                CardLab.get(getActivity()).addCard(card);
                updateUI();
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private int mpesu;
        private Card mCard;
        private TextView card_date_text;
        private TextView card_total_text;
        private TextView card_remain_text;
        private ImageView head_color_image;

        public CardHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_card,parent,false));
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0, 1, 0, "删除卡片");
                }
            });
            card_date_text=(TextView)itemView.findViewById(R.id.card_data_view);
            card_total_text=(TextView)itemView.findViewById(R.id.total_tasks);
            card_remain_text=(TextView)itemView.findViewById(R.id.remain_tasks_text);
            head_color_image = (ImageView)itemView.findViewById(R.id.head_image_view);
        }
        public void bind(Card card){
            Log.d(TAG,"bind called:"+card.getColor_index());
            mCard=card;
            mpesu=mCard.getPesu();
            mcolor_index = mCard.getColor_index();
           // SimpleDateFormat formatter;
            //formatter = new SimpleDateFormat("yyyy-MM-dd");
          //  card_date_text.setText(formatter.format(mCard.getMcard_data_text()));
            card_date_text.setText(""+mCard.getDateformat());
            card_total_text.setText("今日共有"+mCard.getMtotal_task()+"项任务");
            card_remain_text.setText("已完成"+mCard.getMremain_task()+"项");
            head_color_image.setBackgroundColor(Color.parseColor(ColorList.getcolor(mcolor_index)));
        }
        @Override
        public void onClick(View v) {
            Intent intent = TasksInDayActivity.newIntent(getActivity(),mCard.getMcardId(),mCard.getTasksListid());
            startActivity(intent);
            updateUI();
        }
    }
    private class CardAdapter extends RecyclerView.Adapter<CardHolder>{
        private List<Card> mCards;
        private int position;
        private UUID cardid;
        public CardAdapter(List<Card> cards){
            mCards =cards;
        }
        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG,"create holder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CardHolder(layoutInflater,parent);
        }

        @Override
        public void onViewRecycled(CardHolder holder) {
            holder.itemView.setOnLongClickListener(null);
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(final CardHolder holder, int position) {
            final Card card = mCards.get(position);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    setPosition(holder.getPosition());
                    setCardid(card.getMcardId());
                    return false;
                }
            });
            holder.bind(card);
        }
        public void setPosition(int position){
           this.position = position;
        }
        public void setCardid(UUID id){
            this.cardid = id;
        }
        public UUID getCardid(){
            return cardid;
        }
        @Override
        public int getItemCount() {
            return mCards.size();
        }
        public List<Card> getCards(int pos){
            return mCards;
        }
        public void setCards(List<Card> cards){
            mCards =cards;
        }
    }
    //更新界面的card列表
    private void updateUI(){
        Log.d(TAG,"updateUI called");
        CardLab cardLab = CardLab.get(getActivity());
        List<Card> cards = cardLab.getCards();
        mAdapter=new CardAdapter(cards);
        mCardRecyclerView.setAdapter(mAdapter);
        mAdapter.setCards(cards);
    }


}
