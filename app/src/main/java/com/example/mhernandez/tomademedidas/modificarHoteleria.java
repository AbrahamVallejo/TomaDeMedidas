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


public class modificarHoteleria extends AppCompatActivity {

    public Spinner spAreaH, spFijacionH, spControlH, spCorrederaH;
    public static DBProvider oDB;
    public modificarHoteleria() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_hoteleria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        spinnerArea();
        spinnerControl();
        spinnerFijacion();
        spinnerCorredera();
        final int idHoteleria = Integer.parseInt(oExt.getString("idHoteleria"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        String Edificio = oExt.getString("Edificio");
        String Piso = oExt.getString("Piso");
        String Habitacion = oExt.getString("Habitacion");
        String Ancho = oExt.getString("Ancho");
        String Alto = oExt.getString("Alto");
        String Hojas = oExt.getString("Hojas");
        String MedidaSugerida = oExt.getString("MedidaSugerida");
        String Observaciones = oExt.getString("Observaciones");
        final EditText txtEdificio = (EditText) findViewById(R.id.txtEdificio);
        final EditText txtPiso = (EditText) findViewById(R.id.txtPiso);
        final EditText txtHabitacion = (EditText) findViewById(R.id.txtHabitacion);
        final Spinner txtArea = (Spinner) findViewById(R.id.spinner_area);
        final EditText txtAncho = (EditText) findViewById(R.id.txtAncho);
        final EditText txtAlto = (EditText) findViewById(R.id.txtAlto);
        final EditText txtHojas = (EditText) findViewById(R.id.txtHojas);
        final Spinner txtControl = (Spinner) findViewById(R.id.spinner_control);
        final Spinner txtFijacion = (Spinner) findViewById(R.id.spinner_fijacion);
        final Spinner txtCorredera = (Spinner) findViewById(R.id.spinner_corredera);
        final EditText txtMedidaSugerida = (EditText) findViewById(R.id.txtMedidaSugerida);
        final EditText txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) findViewById(R.id.Guardar);
        txtEdificio.setText(Edificio.trim());
        txtPiso.setText(Piso.trim());
        txtHabitacion.setText(Habitacion.trim());
        txtAncho.setText(Ancho.trim());
        txtAlto.setText(Alto.trim());
        txtHojas.setText(Hojas.trim());
        txtMedidaSugerida.setText(MedidaSugerida.trim());
        txtObservaciones.setText(Observaciones.trim());
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Edificio = txtEdificio.getText().toString();
                String Piso = txtPiso.getText().toString();
                String Habitacion = txtHabitacion.getText().toString();
                String Control = txtControl.getSelectedItem().toString();
                String Area = txtArea.getSelectedItem().toString();
                Double Ancho = Double.parseDouble(txtAncho.getText().toString());
                Double Alto = Double.parseDouble(txtAlto.getText().toString());
                String Hojas = txtHojas.getText().toString();
                String Fijacion = txtFijacion.getSelectedItem().toString();
                String Corredera = txtCorredera.getSelectedItem().toString();
                String MedidaSugerida = txtMedidaSugerida.getText().toString();
                String Observaciones = txtObservaciones.getText().toString();
                oDB.updateProyectoHoteleria(idHoteleria, idDisp, Habitacion, Area, Ancho, Alto, Hojas, "IMAGEN",
                        Observaciones, Piso, Edificio, Control, Fijacion, MedidaSugerida, Corredera);
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
            customDialog = new Dialog(modificarHoteleria.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerArea(){
        Bundle oExt = this.getIntent().getExtras();
        String Area = oExt.getString("Area");
        String[][] aRes= modificarHoteleria.oDB.ObtenerUbicacion("0",1);
        spAreaH= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Area;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAreaH.setAdapter(adapter);
    }

    public void spinnerControl(){
        Bundle oExt = this.getIntent().getExtras();
        String Control = oExt.getString("Control");
        String[][] aRes= modificarHoteleria.oDB.ObtenerControl("0",1);
        spControlH= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Control;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlH.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        Bundle oExt = this.getIntent().getExtras();
        String Fijacion = oExt.getString("Fijacion");
        String[][] aRes= modificarHoteleria.oDB.ObtenerFijacion("0",1);
        spFijacionH= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Fijacion;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionH.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        Bundle oExt = this.getIntent().getExtras();
        String Corredera = oExt.getString("Corredera");
        String[][] aRes= modificarHoteleria.oDB.ObtenerCorredera("0",1);
        spCorrederaH= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Corredera;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);   //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaH.setAdapter(adapter);
    }
}
