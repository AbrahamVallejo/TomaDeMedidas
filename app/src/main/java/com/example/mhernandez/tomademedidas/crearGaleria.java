package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class crearGaleria extends AppCompatActivity {

    public static DBProvider oDB;
    public crearGaleria() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_galeria_fotos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final Spinner TipoGaleria = (Spinner) this.findViewById(R.id.TipoGaleria);
        EditText Imagen = (EditText) this.findViewById(R.id.Imagen);
        final EditText Descripcion = (EditText) this.findViewById(R.id.Descripcion);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txtTipoGaleria = TipoGaleria.getSelectedItem().toString();
                String txtDescripcion = Descripcion.getText().toString();
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
