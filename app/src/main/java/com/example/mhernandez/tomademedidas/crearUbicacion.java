package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class crearUbicacion extends AppCompatActivity {

    public static DBProvider oDB;
    public crearUbicacion() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_ubicacion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = getIntent().getExtras();
        final EditText Ubicacion = (EditText) this.findViewById(R.id.txtUbicacion);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUbicacion = Ubicacion.getText().toString();
                Integer idArea = 0;
                Integer idDisp = 0;
                oDB.insertUbicacion(idArea, idDisp, txtUbicacion);
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
