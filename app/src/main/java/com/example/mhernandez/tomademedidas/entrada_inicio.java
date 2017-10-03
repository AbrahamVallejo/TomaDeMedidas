package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mhernandez on 11/09/2017.
 */

public class entrada_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada_inicio);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(entrada_inicio.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
