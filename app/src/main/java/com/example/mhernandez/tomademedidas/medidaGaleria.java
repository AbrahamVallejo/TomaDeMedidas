package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 03/11/2017.
 */


public class medidaGaleria extends AppCompatActivity {

    public Spinner spArea, spFijacion, spCopete, spProye;

    public static DBProvider oDB;
    public medidaGaleria() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = getIntent().getExtras();
        spinnerArea(); spinnerCopete(); spinnerFijacion(); spinnerProyecciones();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final String Nombre = oExt.getString("Nombre");
        final EditText NHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final Spinner Area = (Spinner) this.findViewById(R.id.spinner_area);
        final EditText Ancho = (EditText) this.findViewById(R.id.txt_ancho);
        final EditText Alto = (EditText) this.findViewById(R.id.txt_alto);
        final Spinner Copete = (Spinner) this.findViewById(R.id.spinner_copete);
        final Spinner Proyecciones = (Spinner) this.findViewById(R.id.spinner_proyecciones);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final EditText Comentarios = (EditText) this.findViewById(R.id.txt_comentarios);
        final Button crearArea = (Button) this.findViewById(R.id.crearArea);
        crearArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(medidaGaleria.this, crearArea.class);
                startActivity(rIntent);
            }
        });
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String FechaAlta = currentTime.toString();
                String numeroHabitaciones = NHabitaciones.getText().toString();
                String txtArea  = Area.getSelectedItem().toString();
                Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                Double txtAlto = Double.parseDouble(Alto.getText().toString());
                String txtCopete = Copete.getSelectedItem().toString();
                String txtProyecciones = Proyecciones.getSelectedItem().toString();
                String txtFijacion = Fijacion.getSelectedItem().toString();
                String OBS = Comentarios.getText().toString();
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                String[][] aRefG = MainActivity.oDB.lastGaleria();
                int idGaleria = Integer.parseInt(aRefG[(0)][0]) + 1;
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                oDB.insertProyectoGaleria(idGaleria, idDisp, idProyecto, idProyectoDisp, FechaAlta, numeroHabitaciones, txtArea,
                        txtAncho, txtAlto, txtCopete, txtProyecciones, txtFijacion, OBS, Nombre, "IMAGEN",
                        3, FechaAlta, 1, 1, 1, 1);
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
            customDialog = new Dialog(medidaGaleria.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_galeria);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void spinnerArea(){
        String[][] aRes= medidaGaleria.oDB.ObtenerUbicacion("0",1);
        spArea= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spArea.setAdapter(adapter);
    }

    public void spinnerCopete(){
        String[][] aRes= medidaGaleria.oDB.ObtenerCopete("0",1);
        spCopete= (Spinner)( findViewById(R.id.spinner_copete));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCopete.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= medidaGaleria.oDB.ObtenerFijacion("0",1);
        spFijacion= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacion.setAdapter(adapter);
    }

    public void spinnerProyecciones(){
        String[][] aRes= medidaGaleria.oDB.ObtenerProyeccion("0",1);
        spProye= (Spinner)( findViewById(R.id.spinner_proyecciones));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spProye.setAdapter(adapter);
    }

}
