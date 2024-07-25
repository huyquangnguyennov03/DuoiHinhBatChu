package com.qhcomppany.androidb1.duoihinhbatchu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.qhcomppany.androidb1.duoihinhbatchu.api.getQuestion;
import com.qhcomppany.androidb1.duoihinhbatchu.model.playGameModels;

public class MainActivity extends AppCompatActivity {
    Button btnPlay, btnRule;
    playGameModels models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnRule = findViewById(R.id.btnRule);
        btnRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), rule.class);
                    startActivity(i);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Data.getData().arrCauDo.size()>0){
                    Intent intent = new Intent(getApplicationContext(), PlayGameActivity.class);
                    startActivity(intent);
                }
            }
        });
     new getQuestion().execute();
    }
    protected void onDestroy() {
        super.onDestroy();
        // Lưu vị trí của câu hỏi
        models.savePosition(this);
    }
}