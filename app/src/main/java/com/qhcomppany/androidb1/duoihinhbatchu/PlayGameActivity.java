package com.qhcomppany.androidb1.duoihinhbatchu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.qhcomppany.androidb1.duoihinhbatchu.model.playGameModels;
import com.qhcomppany.androidb1.duoihinhbatchu.object.cauDo;

import java.util.ArrayList;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity {
    playGameModels models;
    cauDo cauDo;
    private  String result ="baocao";
    ArrayList<String> arrAnswer;
    GridView gdvAnswer;
    ArrayList<String> arrResult;
    GridView gdvResult;
    int index = 0;
    ImageView imgAnhCauDo;
    TextView txvPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        init();
        anhXa();
        setOnClick();;
        models.loadPosition(this);
        showCauDo();
    }

    private void anhXa(){
        gdvAnswer = findViewById(R.id.gdvAnswer);
        gdvResult = findViewById(R.id.gdvResult);
        imgAnhCauDo = findViewById(R.id.imgAnhCauDo);
        txvPoint = findViewById(R.id.txvPoint);
    }
    private void init() {
        models = new playGameModels(this);
        arrAnswer = new ArrayList<>();
        arrResult = new ArrayList<>();
    }
    private void showCauDo(){
        cauDo =models.layCauDo();
        result=cauDo.result;
        index = models.User.position;
        bamData();
        showAnswer();
        showResult();
        Glide.with(this)
                .load(cauDo.picture)
                .into(imgAnhCauDo);
        models.GetTT();
        txvPoint.setText(models.User.point+"$");
        if (models.isAllQuestionsCompleted()) {
            models.resetQuestionNumber();
        }
    }

    private  void showAnswer(){
       gdvAnswer.setNumColumns(arrAnswer.size());
       gdvAnswer.setAdapter(new answerAdapter(this, 0, arrAnswer));
    }
    private  void showResult(){
        gdvResult.setNumColumns(arrResult.size()/2);
        gdvResult.setAdapter(new answerAdapter(this, 0, arrResult));
    }
    private void setOnClick() {
        gdvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String s = (String) adapterView.getItemAtPosition(position);
                if (s.length() !=0 && index<arrAnswer.size()){

                    for (int i=0; i<arrAnswer.size();i++){
                        if (arrAnswer.get(i).length()==0){
                            index=i;
                            break;
                        }
                    }
                    arrResult.set(position,"");
                    arrAnswer.set(index,s);
                    index++;
                    showAnswer();
                    showResult();
                    checkWin();
                }
            }
        });
        gdvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String s = (String) adapterView.getItemAtPosition(position);
                if (s.length() != 0) {
                    index = position;
                    arrAnswer.set(position, "");
                    boolean added = false;
                    for (int i = 0; i < arrResult.size(); i++) {
                        if (arrResult.get(i).isEmpty()) {
                            arrResult.set(i, s);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        arrResult.add(s);
                    }
                    showAnswer();
                    showResult();
                }
            }
        });

    }
    private void bamData() {
        index = 0;
        arrAnswer.clear();
        arrResult.clear();
        Random r = new Random();
        for (int i = 0; i < result.length(); i++) {
            arrAnswer.add("");
            String s = "" + (char) (r.nextInt(26) + 65);
            arrResult.add(s);
            String s1 = "" + (char) (r.nextInt(26) + 65);
            arrResult.add(s1);  // Use s1 instead of s to ensure different characters
        }
        for (int i = 0; i < result.length(); i++) {
            String s = "" + result.charAt(i);
            arrResult.set(i, s.toUpperCase());
        }
        for (int i = 0; i < arrResult.size(); i++) {
            String s = arrResult.get(i);
            int vt = r.nextInt(arrResult.size());
            arrResult.set(i, arrResult.get(vt));
            arrResult.set(vt, s);
        }
    }

    private void checkWin(){
        String s="";
        for (String s1: arrAnswer){
            s = s + s1;
        }
        s = s.toUpperCase();
        if (s.equals(result.toUpperCase())){
            Toast.makeText(this, "Bạn đã trả lời đúng", Toast.LENGTH_LONG).show();
            models.GetTT();
            models.User.point = models.User.point + 10;
            models.SaveTT();
            showCauDo();
        }
    }

    public void moGoiY(View view) {
        models.GetTT();
        if (models.User.point < 0) {
            Toast.makeText(this, "Bạn đã hết tiền, không thể dùng gợi ý", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = -1;
        for (int i = 0; i < arrAnswer.size(); i++) {
            if (arrAnswer.get(i).isEmpty()) {
                id = i;
                break;
            }
        }

        if (id == -1) {
            for (int i = 0; i < arrAnswer.size(); i++) {
                String s = result.toUpperCase().charAt(i) + "";
                if (!arrAnswer.get(i).toUpperCase().equals(s)) {
                    id = i;
                    break;
                }
            }
        }

        if (id == -1) {
            Toast.makeText(this, "Không có ký tự nào cần gợi ý", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentAnswer = arrAnswer.get(id).toUpperCase();
        String correctCharacter = String.valueOf(result.charAt(id)).toUpperCase();

        // Nếu ký tự hiện tại đã sai vị trí, đẩy ký tự đúng cũ xuống arrResult
        if (!currentAnswer.isEmpty() && !currentAnswer.equals(correctCharacter)) {
            for (int j = 0; j < arrResult.size(); j++) {
                if (arrResult.get(j).isEmpty()) {
                    arrResult.set(j, correctCharacter);
                    break;
                }
            }
        }

        // Tìm ký tự đúng trong arrResult và lấy nếu có
        for (int i = 0; i < arrResult.size(); i++) {
            if (correctCharacter.equals(arrResult.get(i))) {
                arrResult.set(i, currentAnswer);
                break;
            }
        }

        arrAnswer.set(id, correctCharacter);

        showAnswer();
        showResult();
        models.GetTT();
        models.User.point = models.User.point - 0; // Số điểm trừ khi dùng gợi ý (ở đây là 0)
        models.SaveTT();
        txvPoint.setText(models.User.point + "$");

        // Kiểm tra chiến thắng sau khi gợi ý ô cuối cùng
        checkWin();
    }



    public void changeQuestions(View view) {
        if (models.User.point < 0){
            Toast.makeText(this, "Bạn đã hết tiền, không thể dùng gợi ý", Toast.LENGTH_SHORT).show();
            return;
        }
        models.User.point =models.User.point-0;
        models.SaveTT();
        txvPoint.setText(models.User.point+"$");
        showCauDo();
    }
    protected void onDestroy() {
        super.onDestroy();

        // Lưu vị trí của người chơi
        models.User.position = index;
        models.savePosition(this);
    }
}