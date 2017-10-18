package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class galeria extends AppCompatActivity {

    public static DBProvider oDB;
    public galeria() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final String idFormato = oExt.getString("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText NHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final EditText Area = (EditText) this.findViewById(R.id.txt_area);
        final EditText Ancho = (EditText) this.findViewById(R.id.txt_ancho);
        final EditText Alto = (EditText) this.findViewById(R.id.txt_alto);
        final EditText Copete = (EditText) this.findViewById(R.id.txt_copete);
        final EditText Proyecciones = (EditText) this.findViewById(R.id.txt_proyecciones);
        final EditText Fijacion = (EditText) this.findViewById(R.id.txt_fijacion);
        final EditText Comentarios = (EditText) this.findViewById(R.id.txt_comentarios);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numeroHabitacione = NHabitaciones.getText().toString();
                        String txtArea = Area.getText().toString();
                        String txtAncho = Ancho.getText().toString();
                        String txtAlto = Alto.getText().toString();
                        String txtCopete = Copete.getText().toString();
                        String txtProyecciones = Proyecciones.getText().toString();
                        String txtFijacion = Fijacion.getText().toString();
                        String txtComentarios = Comentarios.getText().toString();
/*                        oDB.insertProyecto();
                        oDB.insertProyectoGaleria();*/
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
