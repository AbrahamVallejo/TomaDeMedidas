package com.example.mhernandez.tomademedidas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        final EditText Habitacion = (EditText) this.findViewById(R.id.txtHabitacion);
        final EditText Area = (EditText) this.findViewById(R.id.txtArea);
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Hojas = (EditText) this.findViewById(R.id.txtHojas);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText Edificio = (EditText) this.findViewById(R.id.txtEdificio);
        final EditText Control = (EditText) this.findViewById(R.id.txtControl);
        final EditText Fijacion = (EditText) this.findViewById(R.id.txtFijacion);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Corredera = (EditText) this.findViewById(R.id.txtCorredera);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtHabitacion = Habitacion.getText().toString();
                        String txtArea = Area.getText().toString();
                        String txtAncho = Ancho.getText().toString();
                        String txtAlto = Alto.getText().toString();
                        String txtHojas = Hojas.getText().toString();
                        String txtObservaciones = Observaciones.getText().toString();
                        String txtPiso = Piso.getText().toString();
                        String txtEdificio = Edificio.getText().toString();
                        String txtControl = Control.getText().toString();
                        String txtFijacion = Fijacion.getText().toString();
                        String txtMedidaSugerida = MedidaSugerida.getText().toString();
                        String txtCorredera = Corredera.getText().toString();
/*                        oDB.insertProyecto();
                        oDB.insertProyectoHoteleria();*/
                        finish();
                    }
                }
        );
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
