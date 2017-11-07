package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 03/11/2017.
 */


public class medidaGaleria extends AppCompatActivity {

    public static DBProvider oDB;
    public medidaGaleria() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = getIntent().getExtras();
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
//                oDB.insertProyectoGaleria();
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
