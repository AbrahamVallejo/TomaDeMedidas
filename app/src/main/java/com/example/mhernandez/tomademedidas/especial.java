package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class especial extends AppCompatActivity {

    public static DBProvider oDB;
    public especial() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idCliente = oExt.getInt("id_cliente");
        final int idclienteDisp = oExt.getInt("id_cliente_disp");
        final String Agente = oExt.getString("Agente");

        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Grosor = (EditText) this.findViewById(R.id.txtGrosor);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                        Double txtAlto = Double.parseDouble(Alto.getText().toString());
                        Double txtGrosor = Double.parseDouble(Grosor.getText().toString());
                        String OBS = Observaciones.getText().toString();
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefE = MainActivity.oDB.lastEspecial();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idEspecial = Integer.parseInt(aRefE[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        oDB.insertProyecto(idProyecto, idDisp, idCliente, idclienteDisp, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                           0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1, Agente, 1);
                        oDB.insertProyectoEspecial(idEspecial, idDisp, idProyecto, idDisp, nombreProyecto, txtAlto, txtAncho, txtGrosor,
                        OBS, "IMAGEN", FechaAlta, idFormato, FechaAlta, 1, 1, 1, 1);
                        Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(rIntent);
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
