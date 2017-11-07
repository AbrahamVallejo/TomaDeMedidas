package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class medidaHoteleria extends AppCompatActivity {

    public static DBProvider oDB;
    public medidaHoteleria() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_hoteleria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final EditText Edificio = (EditText) this.findViewById(R.id.txtEdificio);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText Habitacion = (EditText) this.findViewById(R.id.txtHabitacion);
        final Spinner Area = (Spinner) this.findViewById(R.id.spinner_area);
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Hojas = (EditText) this.findViewById(R.id.txtHojas);
        final Spinner Control = (Spinner) this.findViewById(R.id.spinner_control);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner Corredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final Button crearArea = (Button) this.findViewById(R.id.crearArea);
        crearArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(medidaHoteleria.this, crearArea.class);
                startActivity(rIntent);
            }
        });
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String FechaAlta = currentTime.toString();
                String txtEdificio = Edificio.getText().toString();
                Integer txtPiso = Integer.parseInt(Piso.getText().toString());
                String txtHabitacion = Habitacion.getText().toString();
                String txtArea = Area.getSelectedItem().toString();
                Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                Double txtAlto = Double.parseDouble(Alto.getText().toString());
                Double txtHojas = Double.parseDouble(Hojas.getText().toString());
                String txtControl = Control.getSelectedItem().toString();
                String txtFijacion = Fijacion.getSelectedItem().toString();
                String txtCorredera = Corredera.getSelectedItem().toString();
                String txtMedidaSugerida = MedidaSugerida.getText().toString();
                String OBS = Observaciones.getText().toString();
//                oDB.insertProyectoHoteleria();
                finish();
            }
        });
    }
}
