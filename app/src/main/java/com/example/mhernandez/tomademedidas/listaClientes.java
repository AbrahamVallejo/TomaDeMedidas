package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by mhernandez on 27/09/2017.
 */

public class listaClientes extends AppCompatActivity {

    public static DBProvider oDB;
    public listaClientes() { oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final String idCliente = oExt.getString("idCliente");
        final String idDisp = oExt.getString("idDisp");
        String nom = oExt.getString("nombre");
        String tel = oExt.getString("telefono");
        String dir = oExt.getString("direccion");
        final EditText nombre = (EditText) this.findViewById(R.id.txtNombre);
        final EditText telefono = (EditText) this.findViewById(R.id.txtTelefono);
        final EditText direccion = (EditText) this.findViewById(R.id.txtDireccion);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        nombre.setText(nom);
        telefono.setText(tel);
        direccion.setText(dir);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        oDB.updateCliente(idCliente,idDisp,nombre.getText().toString(),telefono.getText().toString(),direccion.getText().toString());
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
