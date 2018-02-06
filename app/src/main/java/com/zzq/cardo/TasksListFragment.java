package com.zzq.cardo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by apple on 2017/12/24.
 */

public class TasksListFragment extends Fragment {
    private RecyclerView taskrecyclerview;
    private static final String ARG_CARD_ID="card_id";
    private static final String ARG_TASKLIST_ID="tasklist_id";
    private Card mCard;
    private UUID cardID;
    private UUID tasklistID;
    private TaskAdapter mTaskAdapter;
    private TasksList mTasksList;
    List<Card> cards;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_list,container,false);
        cardID=(UUID)getArguments().getSerializable(ARG_CARD_ID);
        tasklistID=(UUID)getArguments().getSerializable(ARG_TASKLIST_ID);
        CardLab cardLab = CardLab.get(getActivity());
        mTasksList = new TasksList(getActivity());
        mTasksList.setTasklistid(tasklistID);
        mCard=cardLab.getCard(cardID);
        taskrecyclerview = view.findViewById(R.id.task_cycler_view);
        taskrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(taskrecyclerview);

        updatetaskUI();
        return view;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        UUID uuid;
        uuid = mTaskAdapter.getTaskid();
        mCard.setMtotal_task(mCard.getMtotal_task()-1);
        CardLab.get(getActivity()).updateCard(mCard);
        switch (item.getItemId()){
            case 1:
                mTasksList.DeleteTask(uuid);
                updatetaskUI();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updatetaskUI();
    }

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Task mTask;
        private UUID cardid;
        private TextView task_clock_text_view;
        private TextView task_content_text_view;
        private TextView task_particular_text_view;
        private CheckBox task_solved_box_view;


        public TaskHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_task, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0,1,0,"删除任务");
                }
            });
            task_clock_text_view = itemView.findViewById(R.id.task_clock_text);
            task_content_text_view = itemView.findViewById(R.id.task_content_text);
            task_particular_text_view = itemView.findViewById(R.id.task_particular_text);
            task_solved_box_view=itemView.findViewById(R.id.task_solved_box);

        }
        public void bind(Task task,UUID cid){
            mTask = task;
            cardid = cid;
            task_clock_text_view.setText(mTask.getHours()+":"+mTask.getMinute());
            task_content_text_view.setText(mTask.getContent());
            task_particular_text_view.setText(mTask.getParticular());
            task_solved_box_view.setChecked(mTask.isSolved());
            task_solved_box_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        }
        @Override
        public void onClick(View v) {
           // Toast.makeText(getActivity(),"clicked!",Toast.LENGTH_SHORT).show();
              Intent intent = TasksetActivity.newIntent(getActivity(),mTask.getTaskid(),cardid,tasklistID);
              startActivity(intent);
              updatetaskUI();
        }
    }
        private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
            private List<Task> mTasks;
            private UUID taskid;
            public TaskAdapter(List<Task> tasks){
                mTasks = tasks;
            }
            @Override
            public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
                return new TaskHolder(layoutInflater,parent);
            }

            @Override
            public void onBindViewHolder(TaskHolder holder, int position) {
                final Task task=mTasks.get(position);
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        setTaskid(task.getTaskid());
                        return false;
                    }
                });
                holder.bind(task,cardID);
            }

            @Override
            public int getItemCount() {
                return mTasks.size();
            }
            public UUID getTaskid(){
                return taskid;
            }
            public void setTaskid(UUID uuid){
                taskid = uuid;
            }
            public void setTasks(List<Task> tasks){mTasks=tasks;}
        }
    private void updatetaskUI(){
        List<Task> tasks = mTasksList.getTasks();
        if(mTaskAdapter==null) {
            mTaskAdapter = new TaskAdapter(tasks);
            taskrecyclerview.setAdapter(mTaskAdapter);
            mTaskAdapter.setTasks(tasks);
        }else {
            mTaskAdapter.notifyDataSetChanged();
            mTaskAdapter.setTasks(tasks);
        }
    }
    public static TasksListFragment newInstance(UUID crimeId,UUID tasklistId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD_ID,crimeId);
        args.putSerializable(ARG_TASKLIST_ID,tasklistId);
        TasksListFragment fragment=new TasksListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu_task,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_task:
                mCard.setMtotal_task(mCard.getMtotal_task()+1);
                CardLab.get(getActivity()).updateCard(mCard);
                Task task = new Task();
                task.setTasklistid(tasklistID);
                task.setTaskid(UUID.randomUUID());
                task.setHours(0);
                task.setMinute(0);
                mTasksList.addTask(task);
                Intent intent = TasksetActivity.newIntent(getActivity(),task.getTaskid(),mCard.getMcardId(),tasklistID);
                startActivity(intent);
                updatetaskUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
