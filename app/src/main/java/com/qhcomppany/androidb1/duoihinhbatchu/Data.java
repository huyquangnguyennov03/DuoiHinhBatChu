package com.qhcomppany.androidb1.duoihinhbatchu;

import com.qhcomppany.androidb1.duoihinhbatchu.object.cauDo;

import java.util.ArrayList;

public class Data {
    private static Data data;
    static {
        data = new Data();
    }
    public static Data getData(){
        return data;
    }
    public ArrayList<cauDo> arrCauDo = new ArrayList<>();
}
