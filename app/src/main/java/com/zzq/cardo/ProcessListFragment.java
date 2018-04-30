package com.zzq.cardo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by apple on 2018/4/24.
 */

public class ProcessListFragment extends Fragment {
    private RecyclerView mProcessRecyclerview;
    private ProcessAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.processcontrol,container,false);
        mProcessRecyclerview = (RecyclerView)view.findViewById(R.id.process_recycler_view);
        mProcessRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        registerForContextMenu(mProcessRecyclerview);
        updateUI();
        return view;
    }

    private class ProcessHolder extends RecyclerView.ViewHolder{
      private MyProcess mProcess;
      private TextView process_name_textview;
      private  TextView process_memory_textview;
      public ProcessHolder(LayoutInflater layoutInflater, ViewGroup parent){
          super(layoutInflater.inflate(R.layout.fragment_process,parent,false));
          itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
              @Override
              public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                  menu.add(0,1,0,"结束进程");
              }
          });
          process_name_textview = (TextView)itemView.findViewById(R.id.process_name_textview);
          process_memory_textview=(TextView)itemView.findViewById(R.id.process_memory);
      }
      public void bind(MyProcess myProcess){
          mProcess = myProcess;
          process_name_textview.setText(mProcess.getName());
          process_memory_textview.setText(mProcess.getMemory());
      }
    }
    private class ProcessAdapter extends RecyclerView.Adapter<ProcessHolder>{
       private List<MyProcess> mMyProcesses;
       public ProcessAdapter(List<MyProcess> processes){
           mMyProcesses = processes;
       }

        @Override
        public ProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProcessHolder(layoutInflater,parent);
        }

        @Override
        public void onViewRecycled(ProcessHolder holder) {
           holder.itemView.setOnLongClickListener(null);
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(ProcessHolder holder, int position) {
            final  MyProcess myProcess = mMyProcesses.get(position);
            holder.bind(myProcess);
        }

        @Override
        public int getItemCount() {
            return mMyProcesses.size();
        }
        public void setMyProcesses(List<MyProcess> processes){
           mMyProcesses=processes;
        }
    }
    private void updateUI(){
        ProcessLab processLab = ProcessLab.get(getActivity());
        List<MyProcess> processes = processLab.getProcesses();
        mAdapter=new ProcessAdapter(processes);
        mProcessRecyclerview.setAdapter(mAdapter);
        mAdapter.setMyProcesses(processes);
    }
}
