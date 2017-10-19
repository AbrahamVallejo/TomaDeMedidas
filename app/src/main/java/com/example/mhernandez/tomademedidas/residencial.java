package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class residencial extends AppCompatActivity {

    public static DBProvider oDB;
    public residencial() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText Ubicacion = (EditText) this.findViewById(R.id.txtUbicacion);
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
        final EditText Control = (EditText) this.findViewById(R.id.txtControl);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final EditText Fijacion = (EditText) this.findViewById(R.id.txtFijacion);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText Corredera = (EditText) this.findViewById(R.id.txtCorredera);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtUbicacion = Ubicacion.getText().toString();
                        String txtA = A.getText().toString();
                        String txtB = B.getText().toString();
                        String txtC = C.getText().toString();
                        String txtD = D.getText().toString();
                        String txtE = E.getText().toString();
                        String txtF = F.getText().toString();
                        String txtG = G.getText().toString();
                        String txtH = H.getText().toString();
                        String txtProfMarco = ProfMarco.getText().toString();
                        String txtProfJaladera = ProfJaladera.getText().toString();
                        String txtControl = Control.getText().toString();
                        String txtMedidaSugeria = MedidaSugerida.getText().toString();
                        String txtObservaciones = Observaciones.getText().toString();
                        String txtFijacion = Fijacion.getText().toString();
                        String txtPiso = Piso.getText().toString();
                        String txtCorredera = Corredera.getText().toString();
                        oDB.insertProyecto(1, 2, 3, 4, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1);
                        oDB.insertProyectoResidencial(txtUbicacion, txtA, txtB, txtC, txtD, txtE, txtF, txtG, txtH, txtProfMarco, txtProfJaladera, txtControl, txtMedidaSugeria, "IMAGEN", txtObservaciones, txtFijacion, txtPiso, txtCorredera);
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
}
