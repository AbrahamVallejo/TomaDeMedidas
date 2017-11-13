package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class medidaHoteleria extends AppCompatActivity {

    private Spinner spAreaH, spFijacionH, spControlH, spCorrederaH;
    public static DBProvider oDB;
    public medidaHoteleria() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_hoteleria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        spinnerArea(); spinnerControl(); spinnerFijacion(); spinnerCorredera();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final String Nombre = oExt.getString("Nombre");
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
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                String[][] aRefH = MainActivity.oDB.lastHoteleria();
                int idHoteleria = Integer.parseInt(aRefH[(0)][0]) + 1;
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                oDB.insertProyectoHoteleria(idHoteleria, idDisp, idProyecto, idDisp, txtHabitacion,
                        txtArea, txtAncho, txtAlto, txtHojas, OBS, Nombre, "Nombre Proyecto", FechaAlta, 2,
                        txtPiso, txtEdificio, txtControl, txtFijacion, FechaAlta, 1, txtMedidaSugerida, 1, 1, 1, txtCorredera);                finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerArea(){
        String[][] aRes= medidaHoteleria.oDB.ObtenerUbicacion("0",1);
        spAreaH= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAreaH.setAdapter(adapter);
    }

    public void spinnerControl(){
        String[][] aRes= medidaHoteleria.oDB.ObtenerControl("0",1);
        spControlH= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlH.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= medidaHoteleria.oDB.ObtenerFijacion("0",1);
        spFijacionH= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionH.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        String[][] aRes= medidaHoteleria.oDB.ObtenerCorredera("0",1);
        spCorrederaH= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione uno...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);   //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaH.setAdapter(adapter);
    }
}
