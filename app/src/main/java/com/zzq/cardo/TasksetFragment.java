package com.zzq.cardo;

import android.app.Activity;
import android.app.Application;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.UUID;

import static android.app.TimePickerDialog.*;

/**
 * Created by apple on 2018/1/22.
 */

public class TasksetFragment extends Fragment{
    private static final String ARG_TASK_ID = "task_id";
    private static final String ARG_CARD_ID = "card_id";
    private static final String ARG_TASKLIST_ID = "tasklist_id";
    private Task mTask;
    private Card mCard;
    private TasksList mTasksList;
    private EditText mtitleField;
    private EditText mdetailField;
    private CheckBox mSolvedCheckbox;
    private Button mButton;
    private Button setButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskid = (UUID)getArguments().getSerializable(ARG_TASK_ID);
        UUID cardid = (UUID)getArguments().getSerializable(ARG_CARD_ID);
        UUID tasklistid = (UUID)getArguments().getSerializable(ARG_TASKLIST_ID);
        CardLab cardLab = CardLab.get(getActivity());
        mCard=cardLab.getCard(cardid);
        mTasksList = new TasksList(getActivity());
        mTasksList.setTasklistid(tasklistid);
        mTask=mTasksList.getTask(taskid);
    }
    public static TasksetFragment newInstance(UUID taskuuid,UUID carduuid,UUID tasklistid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID,taskuuid);
        args.putSerializable(ARG_CARD_ID,carduuid);
        args.putSerializable(ARG_TASKLIST_ID,tasklistid);
        TasksetFragment fragment = new TasksetFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_task, container, false);
        if(mTask!=null){
        mtitleField = (EditText) v.findViewById(R.id.task_title);
        mtitleField.setText(mTask.getContent());
        mtitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              mTask.setContent(s.toString());
              mTasksList.updateTask(mTask);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mdetailField=(EditText)v.findViewById(R.id.task_detail);
        mdetailField.setText(mTask.getParticular());
        mdetailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setParticular(s.toString());
                mTasksList.updateTask(mTask);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSolvedCheckbox=(CheckBox)v.findViewById(R.id.solved_check_box);
        mSolvedCheckbox.setChecked(mTask.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mCard.setMremain_task(mCard.getMremain_task()+1);
                    CardLab.get(getActivity()).updateCard(mCard);
                }
                else{
                    mCard.setMremain_task(mCard.getMremain_task()-1);
                    CardLab.get(getActivity()).updateCard(mCard);
                }
                mTask.setSolved(isChecked);
                mTasksList.updateTask(mTask);
            }
        });
        mButton = (Button)v.findViewById(R.id.show_time_picker);
        mButton.setText("点击设置时刻");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mTask.setHours(hourOfDay);
                        mTask.setMinute(minute);
                        mTasksList.updateTask(mTask);
                    }
                },18,25,true);
                timePickerDialog.show();
            }
        });
        setButton = (Button)v.findViewById(R.id.setover);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        }
        return v;
    }
}
