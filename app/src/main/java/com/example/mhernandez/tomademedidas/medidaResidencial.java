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


public class medidaResidencial extends AppCompatActivity {

    private Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public medidaResidencial() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerUbicacion(); spinnerControl(); spinnerFijacion(); spinnerCorredera(); spinnerAgpto();
        final Bundle oExt = getIntent().getExtras();
        final int idProyecto = Integer.parseInt(oExt.getString("idProyecto"));
        final int idProyectoDisp = Integer.parseInt(oExt.getString("idProyectoDisp"));
        final String Nombre = oExt.getString("Nombre");
        final Spinner Ubicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText A = (EditText) this.findViewById(R.id.txtA);
        final EditText B = (EditText) this.findViewById(R.id.txtB);
        final EditText C = (EditText) this.findViewById(R.id.txtC);
        final EditText D = (EditText) this.findViewById(R.id.txtD);
        final EditText E = (EditText) this.findViewById(R.id.txtE);
        final EditText F = (EditText) this.findViewById(R.id.txtF);
        final EditText G = (EditText) this.findViewById(R.id.txtG);
        final EditText H = (EditText) this.findViewById(R.id.txtH);
        final EditText ProfMarco = (EditText) this.findViewById(R.id.txtProfMarco);
        final EditText ProfJaladera = (EditText) this.findViewById(R.id.txtProfJaladera);
        final Spinner Control = (Spinner) this.findViewById(R.id.spinner_control);
        final Spinner agpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner Corredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final Button crearUbicacion = (Button) this.findViewById(R.id.crearUbicacion);
        crearUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(medidaResidencial.this, crearUbicacion.class);
                startActivity(rIntent);
            }
        });
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String FechaAlta = currentTime.toString();
                String txtUbicacion = Ubicacion.getSelectedItem().toString();
                String txtPiso = Piso.getText().toString();
                Double txtA = Double.parseDouble(A.getText().toString());
                Double txtB = Double.parseDouble(B.getText().toString());
                Double txtC = Double.parseDouble(C.getText().toString());
                Double txtD = Double.parseDouble(D.getText().toString());
                Double txtE = Double.parseDouble(E.getText().toString());
                Double txtF = Double.parseDouble(F.getText().toString());
                Double txtG = Double.parseDouble(G.getText().toString());
                Double txtH = Double.parseDouble(H.getText().toString());
                Double txtProfMarco = Double.parseDouble(ProfMarco.getText().toString());
                Double txtProfJaladera = Double.parseDouble(ProfJaladera.getText().toString());
                String txtControl = Control.getSelectedItem().toString();
                String txtAgpto = agpto.getSelectedItem().toString();
                String txtFijacion = Fijacion.getSelectedItem().toString();
                String txtCorredera = Corredera.getSelectedItem().toString();
                String txtMedidasSugerida = MedidaSugerida.getText().toString();
                String txtObservaciones = Observaciones.getText().toString();
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                String[][] aRefR = MainActivity.oDB.lastResidencial();
                int idResidencial = Integer.parseInt(aRefR[(0)][0]) + 1;
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                oDB.insertProyectoResidencial(idResidencial, idDisp, idProyecto, idProyectoDisp, txtUbicacion, txtA, txtB, txtC, txtD,
                        txtE, txtF, txtG, txtH, txtProfMarco, txtProfJaladera, txtControl, txtAgpto, txtMedidasSugerida, txtObservaciones,
                        "IMAGEN", Nombre, FechaAlta, 1, FechaAlta, 1, txtFijacion, txtPiso, 1,
                        1, 1, txtCorredera);
                finish();
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

    public void spinnerUbicacion(){
        String[][] aRes= medidaResidencial.oDB.ObtenerUbicacion("0",1);
        spUbicacionR= (Spinner)( findViewById(R.id.spinner_ubicacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spUbicacionR.setAdapter(adapter);
    }

    public void spinnerControl(){
        String[][] aRes= medidaResidencial.oDB.ObtenerControl("0",1);
        spControlR= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlR.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= medidaResidencial.oDB.ObtenerFijacion("0",1);
        spFijacionR= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionR.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        String[][] aRes= medidaResidencial.oDB.ObtenerCorredera("0",1);
        spCorrederaR= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione uno...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);  //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaR.setAdapter(adapter);
    }

    public void spinnerAgpto(){
        String[][] aRes= medidaResidencial.oDB.ObtenerControl("0",1);
        spAgptoR= (Spinner)( findViewById(R.id.spinner_agpto));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAgptoR.setAdapter(adapter);
    }

}
