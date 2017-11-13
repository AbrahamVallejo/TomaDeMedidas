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
 * Created by mhernandez on 16/10/2017.
 */

public class residencial extends AppCompatActivity {

    private Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public residencial() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        //Llenar los spinner
        spinnerUbicacion(); spinnerControl(); spinnerFijacion(); spinnerCorredera(); spinnerAgpto();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final Spinner Ubicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
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
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final Spinner Corredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final Spinner Agpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtUbicacion = Ubicacion.getSelectedItem().toString();
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
                        String txtMedidaSugeria = MedidaSugerida.getText().toString();
                        String txtObservaciones = Observaciones.getText().toString();
                        String txtFijacion = Fijacion.getSelectedItem().toString();
                        String txtPiso = Piso.getText().toString();
                        String txtCorredera = Corredera.getSelectedItem().toString();
                        String txtAgpto = Agpto.getSelectedItem().toString();
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefR = MainActivity.oDB.lastResidencial();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idResidencial = Integer.parseInt(aRefR[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        oDB.insertProyecto(idProyecto, idDisp, 3, 4, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                           0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1,"agente", 1);
                        oDB.insertProyectoResidencial(idResidencial, idDisp, idProyecto, idDisp, txtUbicacion, txtA, txtB, txtC, txtD,
                                txtE, txtF, txtG, txtH, txtProfMarco, txtProfJaladera, txtControl, txtAgpto, txtMedidaSugeria, txtObservaciones,
                                "IMAGEN", nombreProyecto, FechaAlta, idFormato, FechaAlta, 1, txtFijacion, txtPiso, 1,
                                1, 1, txtCorredera);
                        finish();
                    }
                }
        );
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

    public void spinnerUbicacion(){
        String[][] aRes= residencial.oDB.ObtenerUbicacion("0",1);
        spUbicacionR= (Spinner)( findViewById(R.id.spinner_ubicacionR));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spUbicacionR.setAdapter(adapter);
    }

    public void spinnerControl(){
        String[][] aRes= residencial.oDB.ObtenerControl("0",1);
        spControlR= (Spinner)( findViewById(R.id.spinner_controlR));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlR.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= residencial.oDB.ObtenerFijacion("0",1);
        spFijacionR= (Spinner)( findViewById(R.id.spinner_fijacionR));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionR.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        String[][] aRes= residencial.oDB.ObtenerCorredera("0",1);
        spCorrederaR= (Spinner)( findViewById(R.id.spinner_correderaR));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione uno...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);  //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaR.setAdapter(adapter);
    }

    public void spinnerAgpto(){
        String[][] aRes= residencial.oDB.ObtenerControl("0",1);
        spAgptoR= (Spinner)( findViewById(R.id.spinner_agptoR));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAgptoR.setAdapter(adapter);
    }

}
