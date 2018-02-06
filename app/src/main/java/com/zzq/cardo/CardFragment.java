package com.zzq.cardo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by zzq on 2017/12/22.
 */

public class CardFragment extends Fragment {
    private Card mCard;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCard = new Card();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_card,container,false);//加载fragment_card xml视图
       return  v;
    }

    @Override
    public void onPause() {
        super.onPause();
        CardLab.get(getActivity()).updateCard(mCard);
    }
}
