package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 07/11/2017.
 */


public class modificarCama extends AppCompatActivity {

    public static DBProvider oDB;
    public modificarCama() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_cama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idCama =  Integer.parseInt(oExt.getString("idCama"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        int idProyecto = Integer.parseInt(oExt.getString("idProyecto"));
        int idProyectoDisp = Integer.parseInt(oExt.getString("idProyectoDisp"));
        String NHabitaciones = oExt.getString("NHabitaciones");
        String A = oExt.getString("A");
        String B = oExt.getString("B");
        String C = oExt.getString("C");
        String D = oExt.getString("D");
        String E = oExt.getString("E");
        String F = oExt.getString("F");
        String G = oExt.getString("G");
        String Observaciones = oExt.getString("Observaciones");
        final EditText txtNHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final EditText txtA = (EditText) this.findViewById(R.id.txt_A);
        final EditText txtB = (EditText) this.findViewById(R.id.txt_B);
        final EditText txtC = (EditText) this.findViewById(R.id.txt_C);
        final EditText txtD = (EditText) this.findViewById(R.id.txt_D);
        final EditText txtE = (EditText) this.findViewById(R.id.txt_E);
        final EditText txtF = (EditText) this.findViewById(R.id.txt_F);
        final EditText txtG = (EditText) this.findViewById(R.id.txt_G);
        final EditText txtObservaciones = (EditText) this.findViewById(R.id.txt_observaciones);
        txtNHabitaciones.setText(NHabitaciones.trim());
        txtA.setText(A.trim());
        txtB.setText(B.trim());
        txtC.setText(C.trim());
        txtD.setText(D.trim());
        txtE.setText(E.trim());
        txtF.setText(F.trim());
        txtG.setText(G.trim());
        txtObservaciones.setText(Observaciones.trim());
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NHabitaciones = txtNHabitaciones.getText().toString();
                Double A = Double.parseDouble(txtA.getText().toString());
                Double B = Double.parseDouble(txtB.getText().toString());
                Double C = Double.parseDouble(txtC.getText().toString());
                Double D = Double.parseDouble(txtD.getText().toString());
                Double E = Double.parseDouble(txtE.getText().toString());
                Double F = Double.parseDouble(txtF.getText().toString());
                Double G = Double.parseDouble(txtG.getText().toString());
                String Observaciones = txtObservaciones.getText().toString();
                oDB.updateProyectoCama(idCama, idDisp, NHabitaciones, A, B , C, D , E , F, G, "IMAGEN", Observaciones);
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
}
