package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;



/**
 * Created by mhernandez on 11/09/2017.
 */

public class log_in extends AppCompatActivity {

    public static DBProvider oDB;
    public log_in() { oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread timerThreadDos = new Thread(){
            public void run(){
                try {
                    sleep(1000);
                    String[][] aRef = log_in.oDB.ObtenerUser("0",2); Log.v("[hola"," "+aRef.length);
                    if (aRef.length ==1){
                        Intent intent = new Intent(log_in.this, MainActivity.class);
                        startActivity(intent);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();       }
            }
        };
        //timerThreadDos.start();
        setContentView(R.layout.log_in);

    }

    public void onSaveClickIniciar(View view){
        EditText npass = (EditText) this.findViewById(R.id.txt_password);
        EditText nUser = (EditText) this.findViewById(R.id.txt_usuario); //
        CheckBox nRecordar = (CheckBox) this.findViewById(R.id.checkBox);

        String part1 = npass.getText().toString().replace(" ","");
        String part2 = nUser.getText().toString().replace(" ","");
        int aux=0;
        if(part1.length()==0){
            aux++; npass.setText(""); npass.setHint("Campo Vacío");
        }
        if(part2.length()==0){
            aux++; nUser.setText(""); nUser.setHint("Campo Vacío");
        }
        if(aux==0){//if (!nombre.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty()){
            String password = npass.getText().toString();

            String[][] aRef = entrada_inicio.oDB.iniciarUser(nUser.getText().toString(),npass.getText().toString());
            Log.v("[obtener]", "Traigo un user: "+ aRef.length);
            if(aRef[0][0].toString() == "0"){
                Toast.makeText(this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
            }else {
                if (nRecordar.isChecked() == true){
                    entrada_inicio.oDB.recordarUser(Integer.parseInt(aRef[0][0]),1, 1); //id, verificacion, estatus
                }else {
                    entrada_inicio.oDB.recordarUser(Integer.parseInt(aRef[0][0]),1, 0); //id, verificacion, estatus
                }
            finish();
            Intent intent = new Intent(log_in.this, MainActivity.class);
            startActivity(intent);}
        }else {
            Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
    }

}
