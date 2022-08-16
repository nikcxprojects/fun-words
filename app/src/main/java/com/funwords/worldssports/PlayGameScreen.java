package com.funwords.worldssports;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayGameScreen extends AppCompatActivity {

    String word="";
    String guessLetter="";
    Button btn;
    int secs = 0;
    private int seconds = 0;
    Button TimeBtn;
    Handler handler;
    Button result;

    Button[][] gridBtns = new Button[5][5];
    int[][] ids ={{R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5},
            {R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10},
            {R.id.button11,R.id.button12,R.id.button13,R.id.button14,R.id.button15},
            {R.id.button16,R.id.button17,R.id.button18,R.id.button19,R.id.button20},
            {R.id.button21,R.id.button22,R.id.button23,R.id.button24,R.id.button25}};

    TextView guessWord;
    int index = 0;
    private boolean running;
    String[] guessWords = new String[]{
            "badminton","champion","paintball","snowboard","wrestling","coach","gymnastics","lacrosse",
            "medal","referee","running","boomerang","ball","playoff","swimming","uniform","olympics",
            "infielder","exercise","jockey","bicycling","dartboard","winner","somersault","pitcher","croquet"
            ,"decathlon","curling","karate","parkour","goal","result","player"};
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game_screen);

        getSupportActionBar().hide();
        guessWord = findViewById(R.id.guessWord);
        TimeBtn = findViewById(R.id.TimeBtn);
        result = findViewById(R.id.result);

        TimeBtn.setOnClickListener(null);
        running =true;


        for (int i=0;i<5;i++) {
            for(int j=0;j<5;j++){
                gridBtns[i][j] = findViewById(ids[i][j]);
            }
        }

        Random random = new Random();
        int i = random.nextInt(5);
        int j = random.nextInt(5);
        int wordIndex = random.nextInt(25);

        System.out.println(i+" "+j+" "+wordIndex);

        ArrayList<String> list =  new ArrayList<>();

        FindAdjacents findAdjacents = new FindAdjacents();

        guessWord.setText(guessWords[wordIndex]);
        word = guessWords[wordIndex];
        int count  = 0;
        //for(int k=0;k<count;k++)
        while(true){

            list =  findAdjacents.adjacentElements(i,j);

            if(count < guessWords[wordIndex].length()){
                if(gridBtns[i][j].getText().toString().equals("0")){
                    gridBtns[i][j].setText(String.valueOf(guessWords[wordIndex].charAt(count)));
                    count++;
                }
            }else{
                break;
            }

            int randIndex = random.nextInt(list.size());
            String nextIndex = list.get(randIndex);

            i = Integer.parseInt(String.valueOf(nextIndex.charAt(0)));
            j = Integer.parseInt(String.valueOf(nextIndex.charAt(1)));

        }

        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        for(int p = 0; p<5;p++){
            for(int q = 0;q<5;q++){
                int in = random.nextInt(26);

                if(gridBtns[p][q].getText().toString().equals("0")){
                    gridBtns[p][q].setText(String.valueOf(alphabet[in]));
                }
            }

        }
        runTimer();



    }

    public void clickItem(View view) {

        btn = (Button) view;
        guessLetter += btn.getText().toString();
        if(btn.getText().toString().charAt(0)==(word.charAt(index))){
            btn.setBackgroundColor(getResources().getColor(R.color.green));
            index++;
        }else{
            btn.setBackgroundColor(getResources().getColor(R.color.grey));
           // index--;
        }

        if(guessLetter.equals(word) && index == word.length() && secs <= 30 ){

            TimeBtn.setText(GameActivity.obj.getComplete());
            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
               }
           },3000);
            handler.removeCallbacks(new Runnable() {
                @Override
                public void run() {
                }
            });
            running = false;
            result.setVisibility(View.VISIBLE);
            result.setText( GameActivity.obj.getCompleted_in()+" " +secs+ " "+GameActivity.obj.getCompleteRemain());

        }

    }

    private void runTimer()
    {

        // Creates a new Handler
        handler = new Handler();
        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                // Set the text view text.
                TimeBtn.setText(secs +" "+GameActivity.obj.getSecond());
                if(secs == 30){
                    TimeBtn.setText(GameActivity.obj.getPlay()+"!");
                    running = false;
                    handler.removeCallbacks(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                    result.setVisibility(View.VISIBLE);
                    result.setText(GameActivity.obj.getDont_find());

                    TimeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(PlayGameScreen.this, PlayGameScreen.class));
                            finish();
                        }
                    });
                  }else if(guessLetter.equals(word) && !running){
                    TimeBtn.setText(GameActivity.obj.getPlay());
                    running = false;
                    handler.removeCallbacks(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    //handler.postDelayed(this, 1000);
                    TimeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(PlayGameScreen.this, PlayGameScreen.class));
                            finish();
                        }
                    });
                }


                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }
                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

}