package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
        setContentView(R.layout.crear_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final String idFormato = oExt.getString("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Grosor = (EditText) this.findViewById(R.id.txtGrosor);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        String txtAncho = Ancho.getText().toString();
                        String txtAlto = Alto.getText().toString();
                        String txtGrosor = Grosor.getText().toString();
                        String OBS = Observaciones.getText().toString();
/*                        oDB.insertProyecto();
                        oDB.insertProyectoEspecial();*/
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
