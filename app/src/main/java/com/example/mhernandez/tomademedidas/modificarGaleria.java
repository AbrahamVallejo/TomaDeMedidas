package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mhernandez on 08/11/2017.
 */


public class modificarGaleria extends AppCompatActivity {

    public Spinner spArea, spFijacion, spCopete, spProye;
    public static DBProvider oDB;
    public modificarGaleria() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        spinnerArea(); spinnerCopete(); spinnerFijacion(); spinnerProyecciones();
        final int idGaleria = Integer.parseInt(oExt.getString("idGaleria"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        String NHabitaciones = oExt.getString("NHabitaciones");
        String Ancho = oExt.getString("Ancho");
        String Alto = oExt.getString("Alto");
        String Comentarios = oExt.getString("Comentarios");
        final EditText txtNHabitaciones = (EditText) findViewById(R.id.txt_numero_habitaciones);
        final Spinner txtArea = (Spinner) findViewById(R.id.spinner_area);
        final EditText txtAncho = (EditText) findViewById(R.id.txt_ancho);
        final EditText txtAlto = (EditText) findViewById(R.id.txt_alto);
        final Spinner txtCopete = (Spinner) findViewById(R.id.spinner_copete);
        final Spinner txtProyecciones = (Spinner) findViewById(R.id.spinner_proyecciones);
        final Spinner txtFijacion = (Spinner) findViewById(R.id.spinner_fijacion);
        final EditText txtComentarios = (EditText) findViewById(R.id.txt_comentarios);
        Button Guardar = (Button) findViewById(R.id.Guardar);
        txtNHabitaciones.setText(NHabitaciones.trim());
        txtAncho.setText(Ancho.trim());
        txtAlto.setText(Alto.trim());
        txtComentarios.setText(Comentarios.trim());
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NHabitaciones = txtNHabitaciones.getText().toString();
                String Area = txtArea.getSelectedItem().toString();
                Double Ancho = Double.parseDouble(txtAncho.getText().toString());
                Double Alto = Double.parseDouble(txtAlto.getText().toString());
                String Copete = txtCopete.getSelectedItem().toString();
                String Proyecciones = txtProyecciones.getSelectedItem().toString();
                String Fijacion =  txtFijacion.getSelectedItem().toString();
                String Comentarios = txtComentarios.getText().toString();
                oDB.updateProyectoGaleria(idGaleria, idDisp, NHabitaciones, Area, Ancho, Alto, Copete, Proyecciones, Fijacion, "IMAGEN", Comentarios, 2);
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
            customDialog = new Dialog(modificarGaleria.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_galeria);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerArea(){
        Bundle oExt = getIntent().getExtras();
        String Area = oExt.getString("Area");
        String[][] aRes= modificarGaleria.oDB.ObtenerUbicacion("0",1);
        spArea= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Area;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spArea.setAdapter(adapter);
    }

    public void spinnerCopete(){
        Bundle oExt = getIntent().getExtras();
        String Copete = oExt.getString("Copete");
        String[][] aRes= modificarGaleria.oDB.ObtenerCopete("0",1);
        spCopete= (Spinner)( findViewById(R.id.spinner_copete));
        final String[] aData = new String[aRes.length+1];
        aData[0]= Copete;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCopete.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        Bundle oExt = getIntent().getExtras();
        String Fijacion = oExt.getString("Fijacion");
        String[][] aRes= modificarGaleria.oDB.ObtenerFijacion("0",1);
        spFijacion= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Fijacion;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacion.setAdapter(adapter);
    }

    public void spinnerProyecciones(){
        Bundle oExt = getIntent().getExtras();
        String Proyecciones = oExt.getString("Proyecciones");
        String[][] aRes= modificarGaleria.oDB.ObtenerProyeccion("0",1);
        spProye= (Spinner)( findViewById(R.id.spinner_proyecciones));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Proyecciones;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spProye.setAdapter(adapter);
    }
}