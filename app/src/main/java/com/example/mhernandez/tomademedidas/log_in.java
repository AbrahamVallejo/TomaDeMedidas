package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

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
        if(aux==0){
            String text = npass.getText().toString();
            Boolean val = false;

            String[][] aRef = entrada_inicio.oDB.buscarUser(nUser.getText().toString());
            Log.v("[ob"," "+aRef[0][0]);
            if (Integer.parseInt(aRef[0][0]) == 0){
                Toast.makeText(this, "Usuario Incorrecto", Toast.LENGTH_LONG).show();
            }else{
                for (int x=0; x< aRef.length; x++){
                    String var = aRef[x][2].toString();
                    val = BCrypt.checkpw(text,var);
                    if (val ==true){
                        break;
                    }
                }

                if (val == false){
                    Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                }else {
                    if (nRecordar.isChecked() == true){
                        entrada_inicio.oDB.recordarUser(Integer.parseInt(aRef[0][0]),1, 1); //id, verificacion(saber quien es), estatus(recordarlo)
                    }else {
                        entrada_inicio.oDB.recordarUser(Integer.parseInt(aRef[0][0]),1, 0); //id, verificacion, estatus
                    }
                    finish();
                    Intent intent = new Intent(log_in.this, MainActivity.class);
                    startActivity(intent);}
            }
        }else {
            Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
    }

}
