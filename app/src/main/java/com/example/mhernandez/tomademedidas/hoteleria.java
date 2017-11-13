package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mhernandez on 13/10/2017.
 */

public class hoteleria extends AppCompatActivity {

    private Spinner spAreaH, spFijacionH, spControlH, spCorrederaH;
    public static DBProvider oDB;
    public hoteleria() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_hoteleria);
        //Llenar los spinner
        spinnerArea(); spinnerControl(); spinnerFijacion(); spinnerCorredera();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText Habitacion = (EditText) this.findViewById(R.id.txtHabitacion);
        //final EditText Area = (EditText) this.findViewById(R.id.txtArea);
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Hojas = (EditText) this.findViewById(R.id.txtHojas);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText Edificio = (EditText) this.findViewById(R.id.txtEdificio);
        //final EditText Control = (EditText) this.findViewById(R.id.txtControl);
        //final EditText Fijacion = (EditText) this.findViewById(R.id.txtFijacion);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        //final EditText Corredera = (EditText) this.findViewById(R.id.txtCorredera);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtHabitacion = Habitacion.getText().toString();
                        //String txtArea = Area.getText().toString();
                        String txtAncho = Ancho.getText().toString();
                        String txtAlto = Alto.getText().toString();
                        String txtHojas = Hojas.getText().toString();
                        String txtObservaciones = Observaciones.getText().toString();
                        String txtPiso = Piso.getText().toString();
                        String txtEdificio = Edificio.getText().toString();
                        //String txtControl = Control.getText().toString();
                        //String txtFijacion = Fijacion.getText().toString();
                        String txtMedidaSugerida = MedidaSugerida.getText().toString();
                        //String txtCorredera = Corredera.getText().toString();
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefH = MainActivity.oDB.lastHoteleria();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idHoteleria = Integer.parseInt(aRefH[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        oDB.insertProyecto(idProyecto, idDisp, 3, 4, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1,"agente", 1);
                        /*oDB.insertProyectoHoteleria(txtHabitacion, txtArea, txtAncho, txtAlto, txtHojas,
                                "IMAGEN", txtObservaciones, txtPiso, txtEdificio, txtControl, txtFijacion,
                                txtMedidaSugerida, txtCorredera);*/
                        finish();
                    }
                }
        );
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
        String[][] aRes= hoteleria.oDB.ObtenerUbicacion("0",1);
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
        String[][] aRes= hoteleria.oDB.ObtenerControl("0",1);
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
        String[][] aRes= hoteleria.oDB.ObtenerFijacion("0",1);
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
        String[][] aRes= hoteleria.oDB.ObtenerCorredera("0",1);
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
