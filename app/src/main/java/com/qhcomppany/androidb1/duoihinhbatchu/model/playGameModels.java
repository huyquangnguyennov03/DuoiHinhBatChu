package com.qhcomppany.androidb1.duoihinhbatchu.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.qhcomppany.androidb1.duoihinhbatchu.Data;
import com.qhcomppany.androidb1.duoihinhbatchu.PlayGameActivity;
import com.qhcomppany.androidb1.duoihinhbatchu.object.cauDo;
import com.qhcomppany.androidb1.duoihinhbatchu.object.user;

import java.util.ArrayList;

public class playGameModels {
    PlayGameActivity c;
    ArrayList<cauDo> arr;
    int questionNumber=-1;
    public user User;
    private static final String USER_DATA = "user_data";
    public playGameModels(PlayGameActivity c) {
        this.c = c;
        User = new user();
        createData();
    }
    private void createData(){
        arr = new ArrayList<>(Data.getData().arrCauDo);
        //arr.add(new cauDo("","",""));
//        arr.add(new cauDo("màn 1","dongnai","https://i.ytimg.com/vi/dpaz38AFJto/maxresdefault.jpg"));
//        arr.add(new cauDo("màn 2","baocao","https://3.bp.blogspot.com/-pzQILmYu4Jw/U8ePEjoEW2I/AAAAAAAACq8/QN8KosNpR70/s1600/2014-07-17+00.43.58-1.png"));
//        arr.add(new cauDo("màn 3","soctrang","https://i.ytimg.com/vi/mQxjJpr8Gvg/maxresdefault.jpg"));
//        arr.add(new cauDo("màn 4","yeuot","https://i.pinimg.com/originals/bc/78/f2/bc78f2d35cba826e5a24ac8b7d0cb2dd.png"));
//        arr.add(new cauDo("màn 5","cavoi","https://i.ytimg.com/vi/DDU_gtuLhy4/maxresdefault.jpg"));
    }
    public cauDo layCauDo(){
        questionNumber++;
        if (questionNumber>=arr.size()){
            questionNumber=arr.size()-1;
        }
        return arr.get(questionNumber);
    }
    public void GetTT(){
        User.getTT(c);
    }
    public void SaveTT(){

        User.saveTT(c);
    }
    public void savePosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position", questionNumber -1); // Lưu vị trí của câu hỏi
        editor.apply();
    }

    public void loadPosition(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);
        questionNumber = sharedPreferences.getInt("position", 0); // Giá trị mặc định là 0
    }
    public boolean isAllQuestionsCompleted() {
        return questionNumber >= arr.size() - 1;
    }

    public void resetQuestionNumber() {
        questionNumber = -1;
    }

}
