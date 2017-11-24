package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class cama extends AppCompatActivity{

    public static DBProvider oDB;
    public cama() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_cama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idCliente = oExt.getInt("id_cliente");
        final int idclienteDisp = oExt.getInt("id_cliente_disp");
        final int idUsuarioVenta = oExt.getInt("idUsuarioVenta");
        final String Agente = oExt.getString("Agente");
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        String[][] users = MainActivity.oDB.ObtenerUser("0",3);
        final int usuario = Integer.parseInt(users[0][0]);
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
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefC = MainActivity.oDB.lastCama();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        int idCama = Integer.parseInt(aRefC[(0)][0]) + 1;
                        String OBS = Observaciones.getText().toString();
                        oDB.insertProyecto(idProyecto, idDisp, idCliente, idclienteDisp, idFormato, usuario, nombreProyecto, PedidoSap, FechaAlta,
                                0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, idUsuarioVenta, Agente, 1);
                        oDB.insertProyectoCama(idCama, idDisp, idProyecto, idDisp, numeroHabitaciones, txtA, txtB, txtC,
                                txtD, txtE, txtF, txtG, FechaAlta, nombreProyecto, idFormato, OBS, usuario, 0, 1, 0);
                        Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(rIntent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_referencia_medida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }

        if (id == R.id.imagenReferencia){
            customDialog = new Dialog(cama.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_cama);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}

