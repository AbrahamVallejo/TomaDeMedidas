package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 03/11/2017.
 */


public class medidaEspecial extends AppCompatActivity {

    public static DBProvider oDB;
    public medidaEspecial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final String Nombre = oExt.getString("Nombre");
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Grosor = (EditText) this.findViewById(R.id.txtGrosor);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String FechaAlta = currentTime.toString();
                Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                Double txtAlto = Double.parseDouble(Alto.getText().toString());
                Double txtGrosor = Double.parseDouble(Grosor.getText().toString());
                String OBS = Observaciones.getText().toString();
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                String[][] aRefE = MainActivity.oDB.lastEspecial();
                int idEspecial = Integer.parseInt(aRefE[(0)][0]) + 1;
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                oDB.insertProyectoEspecial(idEspecial, idDisp, idProyecto, idProyectoDisp, Nombre, txtAlto, txtAncho, txtGrosor,
                        OBS, "IMAGEN", FechaAlta, 5, FechaAlta, 1, 1, 1, 1);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_referencia_medida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }

        if (id == R.id.imagenReferencia){
            customDialog = new Dialog(medidaEspecial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
