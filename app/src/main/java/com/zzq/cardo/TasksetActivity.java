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
 * Created by apple on 2018/1/22.
 */

public class TasksetActivity extends AppCompatActivity {
    private static final String EXTRA_TASK_ID="com.zzq.android.task_id";
    private static final String EXTRA_CARD_ID="com.zzq.android.card_id";
    private static final String EXTRA_TASKSLIST_ID="com.zzq.android.taskslist_id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
    }
    public static Intent newIntent(Context packageContext,UUID taskuuid,UUID carduuid,UUID tasklistid){
        Intent intent = new Intent(packageContext,TasksetActivity.class);
        intent.putExtra(EXTRA_TASK_ID,taskuuid);
        intent.putExtra(EXTRA_CARD_ID,carduuid);
        intent.putExtra(EXTRA_TASKSLIST_ID,tasklistid);
        return intent;
    }

    protected Fragment createFragment(){
        UUID taskid = (UUID)getIntent().getSerializableExtra(EXTRA_TASK_ID);
        UUID cardid = (UUID)getIntent().getSerializableExtra(EXTRA_CARD_ID);
        UUID taskslistid = (UUID)getIntent().getSerializableExtra(EXTRA_TASKSLIST_ID);
        return TasksetFragment.newInstance(taskid,cardid,taskslistid);
    }
}