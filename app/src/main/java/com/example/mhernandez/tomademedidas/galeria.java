package com.example.mhernandez.tomademedidas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class galeria extends AppCompatActivity {

    private Spinner spArea;
    View vista = null;

    public static DBProvider oDB;
    public galeria() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText NHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final EditText Ancho = (EditText) this.findViewById(R.id.txt_ancho);
        final EditText Alto = (EditText) this.findViewById(R.id.txt_alto);
        final EditText Copete = (EditText) this.findViewById(R.id.txt_copete);
        final EditText Proyecciones = (EditText) this.findViewById(R.id.txt_proyecciones);
        final EditText Fijacion = (EditText) this.findViewById(R.id.txt_fijacion);
        final EditText Comentarios = (EditText) this.findViewById(R.id.txt_comentarios);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Log.v("[a2","Voy al spinner");
        spinnerArea();
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numeroHabitaciones = NHabitaciones.getText().toString();
                        String txtAncho = Ancho.getText().toString();
                        String txtAlto = Alto.getText().toString();
                        String txtCopete = Copete.getText().toString();
                        String txtProyecciones = Proyecciones.getText().toString();
                        String txtFijacion = Fijacion.getText().toString();
                        String txtComentarios = Comentarios.getText().toString();
                        oDB.insertProyecto(1, 2, 3, 4, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1);
                        oDB.insertProyectoGaleria(numeroHabitaciones, "a", txtAncho, txtAlto, txtCopete,
                                txtProyecciones, txtFijacion, "IMAGEN", txtComentarios);
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


    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.crear_proyecto, container, false);
        spinnerArea();
        return vista;
    }*/

    public void spinnerArea(){
        Log.v("[a2","Funcion Spinner");
        String[][] aRes= galeria.oDB.ObtenerClientes("0",1);
        spArea= (Spinner)( findViewById(R.id.spinner_cliente));
        List<String> listaClientesSql; //ArrayAdapter<String> comboAdapterSql;
        listaClientesSql = new ArrayList<>();
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]+"."+aRes[i][1]);
            listaClientesSql.add(inde+"- "+aRes[i][2]);
        }
        Log.v("[a2]",listaClientesSql.toString()); //comboAdapterSql = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientesSql);
        final String[] aData = new String[2];
        aData[0] = "1";
        aData[1] = "2";

        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spArea.setAdapter(adapter);

        //spArea.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaClientesSql));

    }



}
