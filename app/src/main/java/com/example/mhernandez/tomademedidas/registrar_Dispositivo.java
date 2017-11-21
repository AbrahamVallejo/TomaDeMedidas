package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tloaiza on 13/10/2017.
 */

public class registrar_Dispositivo extends AppCompatActivity {

    public static DBProvider oDB;
    public int internet = 0;
    boolean aux1 =false;
    public registrar_Dispositivo() { oDB = new DBProvider(this);}


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Quitar Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.crear_dispositivo);

        String model = Build.MODEL;
        EditText nDisp = (EditText) this.findViewById(R.id.nDispositivo);
        nDisp.setEnabled(false);
        nDisp.setText(model);

        Bundle oExt = this.getIntent().getExtras();
        internet = oExt.getInt("internet");
        if(internet == 1) {
            Toast.makeText(getApplicationContext(), "No hay internet!", Toast.LENGTH_LONG).show();
        }else {
            aux1=true;
        }
    }

    public void onSaveClickDispositivo(View view){
        Log.v("[obtener]", "Internet: " +internet +" Conexion="+aux1);
        if (aux1==true){
            Thread timerThreadDos = new Thread(){
                public void run(){
                    try {
                        sleep(1000);
                        getclienteLista();      getformatoLista();      getusuarioLista();
                        getproyeccionLista();   getubicacionLista();    getcontrolLista();
                        getcorrederaLista();    getfijacionLista();     getcopeteLista();
                    }catch (InterruptedException e){
                        e.printStackTrace();       }
                }
            };
            timerThreadDos.start();

            EditText nDisp = (EditText) this.findViewById(R.id.nDispositivo);
            EditText nUser = (EditText) this.findViewById(R.id.nUsuario);

            String part1 = nDisp.getText().toString().replace(" ","");
            String part2 = nUser.getText().toString().replace(" ","");
            int aux=0;
            if(part1.length()==0){
                aux++; nDisp.setText(""); nDisp.setHint("Campo Vacío");
            }
            if(part2.length()==0){
                aux++; nUser.setText(""); nUser.setHint("Campo Vacío");
            }
            if(aux==0){
                    adddispositivos(nDisp.getText().toString(),nUser.getText().toString());
                    finish();
                    Intent intent = new Intent(registrar_Dispositivo.this, log_in.class);
                    startActivity(intent);

            }else {
                Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
        }else {
            Toast.makeText(getApplicationContext(), "Requiere Internet!", Toast.LENGTH_LONG).show();
            aux1= isOnlineNet();
        }
    }

    public void adddispositivos(String dispositivo, String usuario){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES DISPOSITIVOS!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("adddispositivos", dispositivo, usuario);
    }

    public void getclienteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CLIENTE!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getclienteLista", "2");
    }

    public void getusuarioLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES USUARIO!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getusuarioLista", "2");
    }

    public void getfijacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES FIJACIÓN!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getfijacionLista", "2");
    }

    public void getproyeccionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PROYECCIÓN!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyeccionLista", "2");
    }

    public void getcopeteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES COPETE!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getcopeteLista", "2");
    }

    public void getcontrolLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CONTROL!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getcontrolLista", "2");
    }

    public void getcorrederaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CORREDERA!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getcorrederaLista", "2");
    }

    public void getformatoLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES FORMATO!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getformatoLista", "2");
    }

    public void getubicacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES UBICACIÓN!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getubicacionLista", "2");
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0); //Log.v("[obtener]",String.valueOf(reachable) );
            return true;
        } catch (Exception e) {
            /* TODO Auto-generated catch block* */
            e.printStackTrace();
        }
        return false;
    }

}
