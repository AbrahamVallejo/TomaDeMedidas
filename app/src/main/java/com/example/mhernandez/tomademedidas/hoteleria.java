package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mhernandez on 13/10/2017.
 */

public class hoteleria extends AppCompatActivity {

    public static DBProvider oDB;
    public hoteleria() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_hoteleria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final String idFormato = oExt.getString("idFormato");
    }
}
