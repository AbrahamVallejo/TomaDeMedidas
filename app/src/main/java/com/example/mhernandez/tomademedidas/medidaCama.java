package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 03/11/2017.
 */


public class medidaCama extends AppCompatActivity {

    public static DBProvider oDB;
    public medidaCama() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_cama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final String Nombre = oExt.getString("Nombre");
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
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String FechaAlta = currentTime.toString();
                String numeroHabitacion = NHabitaciones.getText().toString();
                Double txtA = Double.parseDouble(A.getText().toString());
                Double txtB = Double.parseDouble(B.getText().toString());
                Double txtC = Double.parseDouble(C.getText().toString());
                Double txtD = Double.parseDouble(D.getText().toString());
                Double txtE = Double.parseDouble(E.getText().toString());
                Double txtF = Double.parseDouble(F.getText().toString());
                Double txtG = Double.parseDouble(G.getText().toString());
                String OBS = Observaciones.getText().toString();
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                String[][] aRefC = MainActivity.oDB.lastCama();
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                int idCama = Integer.parseInt(aRefC[(0)][0]) + 1;
                oDB.insertProyectoCama(idCama, idDisp, idProyecto, idProyectoDisp, numeroHabitacion, txtA, txtB, txtC, txtD, txtE,
                        txtF, txtG, FechaAlta, Nombre, 4, OBS, 0, 1, 1, 1);
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
}
