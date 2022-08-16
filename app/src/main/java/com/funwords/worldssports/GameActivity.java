package com.funwords.worldssports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    Intent intent;
    public static int language = 0;
    public static ModelString obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().hide();

        obj = new ModelString();


        if(language==0){

            obj.setSetting("Setting");
            obj.setSecond("Second");
            obj.setSeconds("Seconds");
            obj.setMain_menu("Main Menu");
            obj.setCompleted_in("Complete In ");
            obj.setComplete("Complete");
            obj.setCompleteRemain("SECONDS! WOULD LIKE TO PLAY AGAIN?");
            obj.setDont_find("YOU DIDN’T FIND A WORD. WOULD YOU LIKE TO TRY AGAIN?");
            obj.setPlay("Play");
        }else if(language==1){

            obj.setSetting("НАСТРОЙКИ");
            obj.setSecond("СЕКУНДА");
            obj.setSeconds("СЕКУНД");
            obj.setMain_menu("ГЛАВНОЕ МЕНЮ");
            obj.setCompleted_in("ЗАВЕРШЕНО ЗА ");
            obj.setComplete("ГОТОВО");
            obj.setCompleteRemain("СЕКУНД! ХОТИТЕ СЫГРАТЬ ЕЩЕ?");
            obj.setDont_find("ВЫ НЕ НАШЛИ НУЖНОЕ СЛОВО. ХОТИТЕ СЫГРАТЬ ЕЩЕ РАЗ?");
            obj.setPlay("ИГРАТЬ");
        }


        ImageView playImage = findViewById(R.id.playImage);

        playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(GameActivity.this, PlayGameScreen.class);
                startActivity(intent);

            }
        });

        Button settingBtn = findViewById(R.id.settingBtn);
        settingBtn.setText(obj.getSetting());

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameActivity.this, Settings.class);
                startActivity(intent);
                finish();
            }
        });
    }
}