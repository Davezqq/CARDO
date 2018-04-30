package com.zzq.cardo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProcessLab {
    private  static ProcessLab sProcessLab;
    private Context mContext;
    public static ProcessLab get(Context context){
        if(sProcessLab == null){
            synchronized (ProcessLab.class){
                if(sProcessLab == null){
                     sProcessLab = new ProcessLab(context);
                }
            }
        }
        return sProcessLab;

    }
    private ProcessLab(Context context){
        mContext = context;
    }
    public List<MyProcess> getProcesses(){
        ActivityManager myActivityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<MyProcess> processes = new ArrayList<>();
        MyProcess myProcess;
        List<ActivityManager.RunningAppProcessInfo> processInfos = myActivityManager.getRunningAppProcesses();
        for(int i=0;i<processInfos.size();i++){
            int[] pid  = new int[] {processInfos.get(i).pid};
            Debug.MemoryInfo[] memoryInfos = myActivityManager.getProcessMemoryInfo(pid);
            double memorysize = memoryInfos[0].dalvikPrivateDirty/1024.0;
            myProcess = new MyProcess(processInfos.get(i).processName,String.valueOf(memorysize)+"MB");
            processes.add(myProcess);
        }
        return processes;
    }
}
