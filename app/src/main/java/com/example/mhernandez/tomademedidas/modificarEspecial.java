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

/**
 * Created by mhernandez on 07/11/2017.
 */


public class modificarEspecial extends AppCompatActivity {

    public static DBProvider oDB;
    public modificarEspecial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idEspecial = Integer.parseInt(oExt.getString("idEspecial"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        String Alto = oExt.getString("Alto");
        String Ancho = oExt.getString("Ancho");
        String Grosor = oExt.getString("Grosor");
        String Observaciones = oExt.getString("Observaciones");
        final EditText txtAlto = (EditText) findViewById(R.id.txtAlto);
        final EditText txtAncho = (EditText) findViewById(R.id.txtAncho);
        final EditText txtGrosor = (EditText) findViewById(R.id.txtGrosor);
        final EditText txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);
        txtAlto.setText(Alto.trim());
        txtAncho.setText(Ancho.trim());
        txtGrosor.setText(Grosor.trim());
        txtObservaciones.setText(Observaciones.trim());
        Button Guardar = (Button) findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Alto = Double.parseDouble(txtAlto.getText().toString());
                Double Ancho = Double.parseDouble(txtAncho.getText().toString());
                Double Grosor = Double.parseDouble(txtGrosor.getText().toString());
                String Observaciones = txtObservaciones.getText().toString();
                oDB.updateProyectoEspecial(idEspecial, idDisp, Ancho, Alto, Grosor, "IMAGEN", Observaciones);
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
            customDialog = new Dialog(modificarEspecial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
