package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

        setContentView(R.layout.log_in);

        String[][] aRef = log_in.oDB.buscarUser(10);
        if (Integer.parseInt(aRef[0][0]) != 10 ) {
        log_in.oDB.insertUser(10, "admin", "admin", "email", 1, "nombre", "apellido", 1 );
        }

    }

    public void onSaveClickIniciar(View view){
        EditText npass = (EditText) this.findViewById(R.id.txt_password);
        EditText nUser = (EditText) this.findViewById(R.id.txt_usuario);

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
            String[][] aRef = entrada_inicio.oDB.iniciarUser(nUser.getText().toString(),npass.getText().toString());
            Log.v("[obtener]", "Traigo un user: "+ aRef.length);
            if(aRef[0][0].toString() == "0"){
                Toast.makeText(this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
            }else {
            finish();
            Intent intent = new Intent(log_in.this, MainActivity.class);
            startActivity(intent);}
        }else {
            Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
    }

}
