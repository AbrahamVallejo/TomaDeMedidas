package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mhernandez on 06/10/2017.
 */

public class listaProyectos extends AppCompatActivity {

    public static DBProvider oDB;
    public listaProyectos(){oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final String idProyecto = oExt.getString("IdProyecto");
        final String idDisp = oExt.getString("IdDisp");
        String nom = oExt.getString("nombre");
        String fec = oExt.getString("fecha");
        String sap = oExt.getString("pedidoSap");
        String aut = oExt.getString("autorizado");
        String tec = oExt.getString("ATecho");
        String mur = oExt.getString("AMuro");
        String esp = oExt.getString("AEspeciales");
        final EditText nombre = (EditText) this.findViewById(R.id.txtNombreProyecto);
        final EditText fecha = (EditText) this.findViewById(R.id.txtFecha);
        final EditText pedidoSap = (EditText) this.findViewById(R.id.txtPedidoSap);
        final EditText autorizado = (EditText) this.findViewById(R.id.txtAutorizado);
        final EditText ATecho = (EditText) this.findViewById(R.id.txtAccTecho);
        final EditText AMuro = (EditText) this.findViewById(R.id.txtAccMuro);
        final EditText AEspeciales = (EditText) this.findViewById(R.id.txtAccEspeciales);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        nombre.setText(nom);
        fecha.setText(fec);
        pedidoSap.setText(sap);
        autorizado.setText(aut);
        ATecho.setText(tec);
        AMuro.setText(mur);
        AEspeciales.setText(esp);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                       /* oDB.updateProyecto(idProyecto,idDisp, nombre.getText().toString(), fecha.getText().toString(),
                                pedidoSap.getText().toString(), autorizado.getText().toString(), ATecho.getText().toString(), AMuro.getText().toString());*/
                        Toast.makeText(getApplicationContext(), "SE HAN GUARDADO LOS CAMBIOS", Toast.LENGTH_SHORT).show();
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
