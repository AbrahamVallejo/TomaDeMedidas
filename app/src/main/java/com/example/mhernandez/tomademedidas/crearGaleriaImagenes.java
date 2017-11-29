package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

/**
 * Created by mhernandez on 28/11/2017.
 */


public class crearGaleriaImagenes extends AppCompatActivity {

    public static DBProvider oDB;
    public crearGaleriaImagenes() {oDB = new DBProvider(this);}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_galeria_fotos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
