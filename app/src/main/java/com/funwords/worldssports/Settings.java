package com.funwords.worldssports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Settings extends AppCompatActivity {

    ImageView american, russian;
    Button menuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        american = findViewById(R.id.american);
        russian = findViewById(R.id.russian);
        menuBtn = findViewById(R.id.menuBtn);

        if(GameActivity.language==0){
            american.setBackgroundResource(R.color.shadowColor);
            russian.setBackgroundResource(0);
            menuBtn.setText("Main Menu");
        }else{
            russian.setBackgroundResource(R.color.shadowColor);
            american.setBackgroundResource(0);
            menuBtn.setText("ГЛАВНОЕ МЕНЮ");
        }



        menuBtn.setText(GameActivity.obj.getMain_menu());

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, GameActivity.class));
                finish();
            }
        });


    }

    public void changeLanguage(View view) {
        if(view.getId() == R.id.american){
            american.setBackgroundResource(R.color.shadowColor);
            russian.setBackgroundResource(0);
            GameActivity.language=0;
            menuBtn.setText("Main Menu");

        }else if(view.getId() == R.id.russian){
            russian.setBackgroundResource(R.color.shadowColor);
            american.setBackgroundResource(0);
            GameActivity.language=1;
            menuBtn.setText("ГЛАВНОЕ МЕНЮ");
        }


    }
}