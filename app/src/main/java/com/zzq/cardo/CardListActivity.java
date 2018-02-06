package com.zzq.cardo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zzq on 2017/12/23.
 */

public class CardListActivity extends AppCompatActivity {
    private ColorList mColorList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ColorList colorList=new ColorList();
        FragmentManager fm =getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment = new CardListFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }

    }
}
