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

public class cama extends AppCompatActivity{

    public static DBProvider oDB;
    public cama() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText NHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final EditText A = (EditText) this.findViewById(R.id.txt_A);
        final EditText B = (EditText) this.findViewById(R.id.txt_B);
        final EditText C = (EditText) this.findViewById(R.id.txt_C);
        final EditText D = (EditText) this.findViewById(R.id.txt_D);
        final EditText E = (EditText) this.findViewById(R.id.txt_E);
        final EditText F = (EditText) this.findViewById(R.id.txt_F);
        final EditText G = (EditText) this.findViewById(R.id.txt_G);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txt_observaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String numeroHabitaciones = NHabitaciones.getText().toString();
                        Double txtA = Double.parseDouble(A.getText().toString());
                        Double txtB = Double.parseDouble(B.getText().toString());
                        Double txtC = Double.parseDouble(C.getText().toString());
                        Double txtD = Double.parseDouble(D.getText().toString());
                        Double txtE = Double.parseDouble(E.getText().toString());
                        Double txtF = Double.parseDouble(F.getText().toString());
                        Double txtG = Double.parseDouble(G.getText().toString());
                        String OBS = Observaciones.getText().toString();
                        oDB.insertProyecto(2, 3, 4, 5, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1);
                        oDB.insertProyectoCama(2 , 3, 4, 5, numeroHabitaciones, txtA, txtB, txtC,
                                txtD, txtE, txtF, txtG, FechaAlta, nombreProyecto, idFormato, OBS, 0, 1, 1, 1);
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

