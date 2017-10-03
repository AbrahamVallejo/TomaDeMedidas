package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by mhernandez on 27/09/2017.
 */

public class listaClientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        String nom = oExt.getString("nombre");
        String tel = oExt.getString("telefono");
        String dir = oExt.getString("direccion");
        EditText nombre = (EditText) this.findViewById(R.id.txtNombre);
        EditText telefono = (EditText) this.findViewById(R.id.txtTelefono);
        EditText direccion = (EditText) this.findViewById(R.id.txtDireccion);
        nombre.setText(nom);
        telefono.setText(tel);
        direccion.setText(dir);
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
