package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class medidaResidencial extends AppCompatActivity {

    public static DBProvider oDB;
    public medidaResidencial() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = getIntent().getExtras();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
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
//                oDB.insertProyectoResidencial();
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
