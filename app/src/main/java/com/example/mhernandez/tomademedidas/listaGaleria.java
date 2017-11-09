package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mhernandez on 30/10/2017.
 */


public class listaGaleria extends AppCompatActivity {
    public static DBProvider oDB;
    public listaGaleria() { oDB = new DBProvider(this);}
    Dialog customDialog = null;
    public int idProyecto=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Quitar Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle oExt = this.getIntent().getExtras();
        idProyecto = oExt.getInt("idProyecto");
        Log.v("[FRAGMENT]", "ID "+idProyecto);

        lista();

        ListView tlList = ((ListView) this.findViewById(R.id.lista));


        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem,final int iPosition, long l) {
                customDialog = new Dialog(listaGaleria.this, R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_medida);
                final String[] aDat = (String[]) aList.getItemAtPosition(iPosition);

                ((Button) customDialog.findViewById(R.id.btnNuevaMedida)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(listaGaleria.this, medidaGaleria.class);
                        intent.putExtra("idProyecto", aDat[2]);
                        intent.putExtra("idProyectoDisp", aDat[3]);
                        startActivity(intent);
                      
                        customDialog.dismiss();
                    }
                });

              ((Button) customDialog.findViewById(R.id.btnNuevaGaleria)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        customDialog.dismiss();
                    }
                });
              
                ((Button) customDialog.findViewById(R.id.btnModificar)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (Integer.parseInt(aDat[19]) !=1){
                            Toast.makeText(listaGaleria.this, "Proyecto Cerrado", Toast.LENGTH_LONG).show();
                        }else{
                            Intent rIntent = new Intent(listaGaleria.this, modificarGaleria.class);
                            rIntent.putExtra("idGaleria", aDat[0]);
                            rIntent.putExtra("idDisp", aDat[1]);
                            rIntent.putExtra("idProyecto", aDat[2]);
                            rIntent.putExtra("idProyectoDisp", aDat[3]);
                            rIntent.putExtra("NHabitaciones", aDat[5]);
                            rIntent.putExtra("Area", aDat[6]);
                            rIntent.putExtra("Ancho", aDat[7]);
                            rIntent.putExtra("Alto", aDat[8]);
                            rIntent.putExtra("Copete", aDat[9]);
                            rIntent.putExtra("Proyecciones", aDat[10]);
                            rIntent.putExtra("Fijacion", aDat[11]);
                            rIntent.putExtra("Comentarios", aDat[12]);
                            startActivity(rIntent);
                        }
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnCerrar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                return false;
            }
        });    }


    public class CustomAdapter extends ArrayAdapter<String[]> {
        private final Activity _context;
        private final String[][] _text;

        public CustomAdapter(Activity context, String[][] text){
            super(context, R.layout.activity_listagalerias, text);
            this._context = context;
            this._text = text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = _context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_listagalerias, null);
            TextView txtIDGaleria = (TextView) rowView.findViewById(R.id.IDGaleria);
            TextView txtIDDisp = (TextView) rowView.findViewById(R.id.IDDisp);
            TextView txtIDProyecto = (TextView) rowView.findViewById(R.id.IDProyecto);
            TextView txtIDProyectoDisp = (TextView) rowView.findViewById(R.id.IDProyectoDisp);
            TextView txtIDEstatus = (TextView) rowView.findViewById(R.id.IDEstatus);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtFecha = (TextView) rowView.findViewById(R.id.Fecha);
            TextView txtComentarios = (TextView) rowView.findViewById(R.id.Comentarios);
            TextView txtNHabitacion = (TextView) rowView.findViewById(R.id.NHabitacion);
            TextView txtArea = (TextView) rowView.findViewById(R.id.Area);
            TextView txtAncho = (TextView) rowView.findViewById(R.id.Ancho);
            TextView txtAlto = (TextView) rowView.findViewById(R.id.Alto);
            TextView txtCopete = (TextView) rowView.findViewById(R.id.Copete);
            TextView txtProyecciones = (TextView) rowView.findViewById(R.id.Proyecciones);
            TextView txtFijacion = (TextView) rowView.findViewById(R.id.Fijacion);
            TextView txtEstatus = (TextView) rowView.findViewById(R.id.EstatusProyecto);
            String[] parts = _text[position][4].split("T");
            txtFecha.setText(parts[0]);

            txtIDGaleria.setText(_text[position][0]);
            txtIDDisp.setText(_text[position][1]);
            txtIDProyecto.setText(_text[position][2]);
            txtIDProyectoDisp.setText(_text[position][3]);
            txtIDEstatus.setText(_text[position][19]);
            txtNombre.setText(_text[position][13]);
            txtComentarios.setText(_text[position][12]);
            txtNHabitacion.setText(_text[position][5]);
            txtArea.setText(_text[position][6]);
            txtAncho.setText(_text[position][7]);
            txtAlto.setText(_text[position][8]);
            txtCopete.setText(_text[position][9]);
            txtProyecciones.setText(_text[position][10]);
            txtFijacion.setText(_text[position][11]);
            if (Integer.parseInt(_text[position][19]) == 1){
                txtEstatus.setText("Activo");
            }else{
                txtEstatus.setText("Cerrado");
            }
            return rowView;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        lista();
    }

    public void lista(){
        String[][] aRef = oDB.ProyectosGaleriaProyecto(String.valueOf(idProyecto), 2);
        String[][] aDataFolio = null;
        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[26];
                aDataFolio[iCnt][0] = aRef[iCnt][0];
                aDataFolio[iCnt][1] = aRef[iCnt][1];
                aDataFolio[iCnt][2] = aRef[iCnt][2];
                aDataFolio[iCnt][3] = aRef[iCnt][3];
                aDataFolio[iCnt][4] = aRef[iCnt][4];
                aDataFolio[iCnt][5] = aRef[iCnt][5];
                aDataFolio[iCnt][6] = aRef[iCnt][6];
                aDataFolio[iCnt][7] = aRef[iCnt][7];
                aDataFolio[iCnt][8] = aRef[iCnt][8];
                aDataFolio[iCnt][9] = aRef[iCnt][9];
                aDataFolio[iCnt][10] = aRef[iCnt][10];
                aDataFolio[iCnt][11] = aRef[iCnt][11];
                aDataFolio[iCnt][12] = aRef[iCnt][12];
                aDataFolio[iCnt][13] = aRef[iCnt][13];
                aDataFolio[iCnt][14] = aRef[iCnt][14];
                aDataFolio[iCnt][15] = aRef[iCnt][15];
                aDataFolio[iCnt][16] = aRef[iCnt][16];
                aDataFolio[iCnt][17] = aRef[iCnt][17];
                aDataFolio[iCnt][18] = aRef[iCnt][18];
                aDataFolio[iCnt][19] = aRef[iCnt][19];
                aDataFolio[iCnt][20] = aRef[iCnt][20];
                aDataFolio[iCnt][21] = aRef[iCnt][21];
                aDataFolio[iCnt][22] = aRef[iCnt][22];
                aDataFolio[iCnt][23] = aRef[iCnt][23];
                aDataFolio[iCnt][24] = aRef[iCnt][24];
                aDataFolio[iCnt][25] = aRef[iCnt][25];
            }
            ListView list;
            list = (ListView) this.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(this, aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
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
