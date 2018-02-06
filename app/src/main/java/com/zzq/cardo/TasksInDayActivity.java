package com.zzq.cardo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

/**
 * Created by apple on 2017/12/24.
 */

public class TasksInDayActivity extends AppCompatActivity {
    private static final String EXTRA_CARD_INFO="com.zzq.android.card_id";
    private static final String EXTRA_TASKLIST_INFO="com.zzq.android.tasklist_id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
    public static Intent newIntent(Context packageContext, UUID cardid,UUID tasklistid){
        Intent intent = new Intent(packageContext,TasksInDayActivity.class);
        intent.putExtra(EXTRA_CARD_INFO,cardid);
        intent.putExtra(EXTRA_TASKLIST_INFO,tasklistid);
        return  intent;
    }
    protected Fragment createFragment(){
        UUID cardID=(UUID)getIntent().getSerializableExtra(EXTRA_CARD_INFO);
        UUID tasklistId=(UUID)getIntent().getSerializableExtra(EXTRA_TASKLIST_INFO);
        return TasksListFragment.newInstance(cardID,tasklistId);
    }
}
