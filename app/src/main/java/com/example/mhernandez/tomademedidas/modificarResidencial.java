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
 * Created by mhernandez on 10/11/2017.
 */


public class modificarResidencial extends AppCompatActivity {

    private Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public modificarResidencial() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerUbicacion(); spinnerControl(); spinnerFijacion(); spinnerCorredera(); spinnerAgpto();
        Bundle oExt = getIntent().getExtras();
        final int idResidencial = Integer.parseInt(oExt.getString("idResidencial"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        String Ubicacion = oExt.getString("Ubicacion");
        String Piso = oExt.getString("Piso");
        String MedidaSugerida = oExt.getString("MedidaSugerida");
        String ProfMarco = oExt.getString("ProfMarco");
        String ProfJaladera = oExt.getString("ProfJaladera");
        String Control = oExt.getString("Control");
        String Agpto = oExt.getString("Agpto");
        String Corredera = oExt.getString("Corredera");
        String Observaciones = oExt.getString("Observaciones");
        String A = oExt.getString("A");
        String B = oExt.getString("B");
        String C = oExt.getString("C");
        String D = oExt.getString("D");
        String E = oExt.getString("E");
        String F = oExt.getString("F");
        String G = oExt.getString("G");
        String H = oExt.getString("H");
        final Spinner txtUbicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
        final EditText txtPiso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText txtA = (EditText) this.findViewById(R.id.txtA);
        final EditText txtB = (EditText) this.findViewById(R.id.txtB);
        final EditText txtC = (EditText) this.findViewById(R.id.txtC);
        final EditText txtD = (EditText) this.findViewById(R.id.txtD);
        final EditText txtE = (EditText) this.findViewById(R.id.txtE);
        final EditText txtF = (EditText) this.findViewById(R.id.txtF);
        final EditText txtG = (EditText) this.findViewById(R.id.txtG);
        final EditText txtH = (EditText) this.findViewById(R.id.txtH);
        final EditText txtProfMarco = (EditText) this.findViewById(R.id.txtProfMarco);
        final EditText txtProfJaladera = (EditText) this.findViewById(R.id.txtProfJaladera);
        final Spinner txtControl = (Spinner) this.findViewById(R.id.spinner_control);
        final Spinner txtAgpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        final Spinner txtFijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner txtCorredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final EditText txtMedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText txtObservaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        txtPiso.setText(Piso.trim());
        txtA.setText(A.trim());
        txtB.setText(B.trim());
        txtC.setText(C.trim());
        txtD.setText(D.trim());
        txtE.setText(E.trim());
        txtF.setText(F.trim());
        txtG.setText(G.trim());
        txtH.setText(H.trim());
        txtProfMarco.setText(ProfMarco.trim());
        txtProfJaladera.setText(ProfJaladera.trim());
        txtMedidaSugerida.setText(MedidaSugerida.trim());
        txtObservaciones.setText(Observaciones.trim());
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ubicacion = txtUbicacion.getSelectedItem().toString();
                String Piso = txtPiso.getText().toString();
                String MedidaSugerida = txtMedidaSugerida.getText().toString();
                Double ProfMarco = Double.parseDouble(txtProfMarco.getText().toString());
                Double ProfJaladera = Double.parseDouble(txtProfJaladera.getText().toString());
                String Control = txtControl.getSelectedItem().toString();
                String Agpto = txtAgpto.getSelectedItem().toString();
                String Corredera = txtCorredera.getSelectedItem().toString();
                String Observaciones = txtObservaciones.getText().toString();
                String Fijacion = txtFijacion.getSelectedItem().toString();
                Double A = Double.parseDouble(txtA.getText().toString());
                Double B = Double.parseDouble(txtB.getText().toString());
                Double C = Double.parseDouble(txtC.getText().toString());
                Double D = Double.parseDouble(txtD.getText().toString());
                Double E = Double.parseDouble(txtE.getText().toString());
                Double F = Double.parseDouble(txtF.getText().toString());
                Double G = Double.parseDouble(txtG.getText().toString());
                Double H = Double.parseDouble(txtH.getText().toString());
                oDB.updateProyectoResidencial(idResidencial, idDisp, Ubicacion, A, B, C, D , E, F, G, H,
                        ProfMarco, ProfJaladera, Control, Agpto, MedidaSugerida, "IMAGEN", Observaciones, Fijacion, Piso, Corredera);
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

    public void spinnerUbicacion(){
        String[][] aRes= modificarResidencial.oDB.ObtenerUbicacion("0",1);
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
        String[][] aRes= modificarResidencial.oDB.ObtenerControl("0",1);
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
        String[][] aRes= modificarResidencial.oDB.ObtenerFijacion("0",1);
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
        String[][] aRes= modificarResidencial.oDB.ObtenerCorredera("0",1);
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
        String[][] aRes= modificarResidencial.oDB.ObtenerControl("0",1);
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
