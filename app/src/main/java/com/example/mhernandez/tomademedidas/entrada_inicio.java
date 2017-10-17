package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mhernandez on 11/09/2017.
 */

public class entrada_inicio extends AppCompatActivity {

    public static DBProvider oDB;
    public entrada_inicio() { oDB = new DBProvider(this);}

    @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.entrada_inicio);


            Thread timerThread = new Thread(){
                public void run(){
                    String[][] aux1 = entrada_inicio.oDB.ObtenerDispositivos("0",1);
                    try{
                            //String[][] aRef = entrada_inicio.oDB.lastDispositivo();
                            Log.v("[obtener]", "Traigo un: "+ aux1.length);
                            boolean aux = isOnlineNet();
                            if (aux != false){
                                getuserLista();
                                sleep(3000);
                                if (aux1.length == 0){
                                    Intent intent = new Intent(entrada_inicio.this, registrar_Dispositivo.class);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(entrada_inicio.this, log_in.class);
                                    startActivity(intent);
                                }
                            }else{
                                if (aux1.length ==0 ){
                                    Log.v("[obtener", "Sin inter");
                                    sleep(1000);
                                    Intent intent = new Intent(entrada_inicio.this, entrada_inicio.class);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(entrada_inicio.this, log_in.class);
                                    startActivity(intent);}
                            }
                    }catch (InterruptedException e){
                        e.printStackTrace();
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

    public void getuserLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Log.v("WS","TODO PERFECTO EN EL WEB SERVICES!");
                // Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getuserLista");
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0); //Log.v("[obtener]",String.valueOf(reachable) );
            //return reachable;
            return true;
        } catch (Exception e) {
            /* TODO Auto-generated catch block* */
            e.printStackTrace();
        }
        return false;
    }


}


/*

*/