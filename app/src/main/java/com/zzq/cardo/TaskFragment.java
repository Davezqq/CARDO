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
 * Created by apple on 2017/12/24.
 */

public class TaskFragment extends Fragment {
    Task mTask;
    TextView taskcontent;
    TextView taskdetail;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = new Task();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);
        taskcontent = view.findViewById(R.id.task_content_text);
        taskcontent.setText(mTask.getContent());
        taskdetail = view.findViewById(R.id.task_particular_text);
        taskdetail.setText(mTask.getParticular());
        return view;
    }
}
