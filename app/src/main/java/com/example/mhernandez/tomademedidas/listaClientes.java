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
        final String nom = oExt.getString("nombre");
        final String tel = oExt.getString("telefono");
        final String dir = oExt.getString("direccion");
        final EditText nombre = (EditText) this.findViewById(R.id.txtNombre);
        final EditText telefono = (EditText) this.findViewById(R.id.txtTelefono);
        final EditText direccion = (EditText) this.findViewById(R.id.txtDireccion);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        nombre.setText(nom.trim());
        telefono.setText(tel.trim());
        direccion.setText(dir.trim());
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
        String part1 =nombre.getText().toString().replace(" ",""); //String part1[] =
        String part2 = telefono.getText().toString().replace(" ",""); //String part2[] =
        String part3 =direccion.getText().toString().replace(" ",""); //String part3[] =
        Log.v("[obtener]",part1); Log.v("[obtener]",part2);Log.v("[obtener]",part3);
                        int aux=0;
                        if(part1.length()==0){ //nombre.getText().length()==0
                            aux=1; nombre.setText("");
                            nombre.setHint("Campo Vacío");Log.v("[obtener]",part1);
                        }
                        if(part2.length()==0){
                            aux=1; telefono.setText("");
                            telefono.setHint("Campo Vacío");Log.v("[obtener]",part2);
                        }
                        if(part3.length()==0){
                            aux=1; direccion.setText("");
                            direccion.setHint("Campo Vacío");Log.v("[obtener]",part3);
                        }
                        if(aux==0){
                            String[][] aRef = MainActivity.oDB.buscarCliente(Integer.parseInt(idCliente), Integer.parseInt(idDisp) );
                            if (Integer.parseInt(aRef[0][5]) == 1) {
                                oDB.updateCliente(idCliente, idDisp, nombre.getText().toString(), telefono.getText().toString(), direccion.getText().toString(), 1);
                            }
                            if (Integer.parseInt(aRef[0][5]) != 1) {
                                oDB.updateCliente(idCliente, idDisp, nombre.getText().toString(), telefono.getText().toString(), direccion.getText().toString(), 2);
                            }
                            Toast.makeText(getApplicationContext(), "SE HAN GUARDADO LOS CAMBIOS", Toast.LENGTH_SHORT).show();
                            finish();
                        }
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