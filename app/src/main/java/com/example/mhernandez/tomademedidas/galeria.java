package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class galeria extends AppCompatActivity {

    private Spinner spArea, spFijacion, spCopete, spProye;

    public static DBProvider oDB;
    public galeria() {oDB = new DBProvider(this);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        //Llenar los spinner
        spinnerArea(); spinnerCopete(); spinnerFijacion(); spinnerProyecciones();

        final int idCliente = oExt.getInt("id_cliente");
        final int idclienteDisp = oExt.getInt("id_cliente_disp");
        final String Agente = oExt.getString("Agente");

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
        final Spinner Copete = (Spinner) this.findViewById(R.id.spinner_copete);
        final Spinner Proyecciones = (Spinner) this.findViewById(R.id.spinner_proyecciones);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner Area = (Spinner) this.findViewById(R.id.spinner_area);
        final EditText Comentarios = (EditText) this.findViewById(R.id.txt_comentarios);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numeroHabitaciones = NHabitaciones.getText().toString();
                        String txtArea = Area.getSelectedItem().toString();
                        Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                        Double txtAlto = Double.parseDouble(Alto.getText().toString());
                        String txtCopete = Copete.getSelectedItem().toString();
                        String txtProyecciones = Proyecciones.getSelectedItem().toString();
                        String txtFijacion = Fijacion.getSelectedItem().toString();
                        String txtComentarios = Comentarios.getText().toString();
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefG = MainActivity.oDB.lastGaleria();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idGaleria = Integer.parseInt(aRefG[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        oDB.insertProyecto(idProyecto, idDisp, idCliente, idclienteDisp, idFormato, 5, nombreProyecto, PedidoSap, FechaAlta,
                                           0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, 1, Agente, 1);
                        oDB.insertProyectoGaleria(idGaleria, idDisp, idProyecto, idDisp, FechaAlta, numeroHabitaciones, txtArea,
                                txtAncho, txtAlto, txtCopete, txtProyecciones, txtFijacion, txtComentarios, nombreProyecto, "IMAGEN",
                                idFormato, FechaAlta, 1, 1, 1, 1);
                        Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(rIntent);
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
        String[][] aRes= galeria.oDB.ObtenerUbicacion("0",1);
        spArea= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spArea.setAdapter(adapter);
    }

    public void spinnerCopete(){
        String[][] aRes= galeria.oDB.ObtenerCopete("0",1);
        spCopete= (Spinner)( findViewById(R.id.spinner_copete));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCopete.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= galeria.oDB.ObtenerFijacion("0",1);
        spFijacion= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacion.setAdapter(adapter);
    }

    public void spinnerProyecciones(){
        String[][] aRes= galeria.oDB.ObtenerProyeccion("0",1);
        spProye= (Spinner)( findViewById(R.id.spinner_proyecciones));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spProye.setAdapter(adapter);
    }



}
