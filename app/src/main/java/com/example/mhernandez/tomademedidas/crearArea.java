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


public class crearArea extends AppCompatActivity {

    public static DBProvider oDB;
    public crearArea() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_area);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = getIntent().getExtras();
        final EditText Area = (EditText) this.findViewById(R.id.txtArea);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtArea = Area.getText().toString();
                String[][] aRefD = MainActivity.oDB.lastDispositivo();
                int idDisp = Integer.parseInt(aRefD[(0)][0]);
                Integer idArea = 0;
                oDB.insertUbicacion(idArea, idDisp, txtArea);
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
