package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 07/11/2017.
 */


public class modificarEspecial extends AppCompatActivity {

    public static DBProvider oDB;
    public modificarEspecial() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idEspecial = Integer.parseInt(oExt.getString("idEspecial"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        int idProyecto = Integer.parseInt(oExt.getString("idProyecto"));
        int idProyectoDisp = Integer.parseInt(oExt.getString("idProyectoDisp"));
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
