package com.zzq.cardo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/23.
 */

public class ColorList {

    private static List<String> mcolors;
    public ColorList(){
        mcolors = new ArrayList<>();
        mcolors.add("#F44336");
        mcolors.add("#E91E63");
        mcolors.add("#9C27B0");
        mcolors.add("#673AB7");
        mcolors.add("#3F51B5");
        mcolors.add("#2196F3");
        mcolors.add("#03A9F4");
        mcolors.add("#00BCD4");
        mcolors.add("#009688");
    }
    public static String getcolor(int index){
        return mcolors.get(index);
    }
}
