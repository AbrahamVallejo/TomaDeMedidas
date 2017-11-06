package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mhernandez on 30/10/2017.
 */


public class listaHoteleria extends AppCompatActivity {
    public static DBProvider oDB;
    public listaHoteleria() { oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista();

        ListView tlList = ((ListView) this.findViewById(R.id.lista));


        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem,final int iPosition, long l) {
                customDialog = new Dialog(listaHoteleria.this, R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_medida);

                ((Button) customDialog.findViewById(R.id.btnVer)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnNuevaMedida)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

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
            super(context, R.layout.activity_listahoteleria, text);
            this._context = context;
            this._text = text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = _context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_listahoteleria, null);
            TextView txtIDHoteleria = (TextView) rowView.findViewById(R.id.IDHoteleria);
            TextView txtIDDisp = (TextView) rowView.findViewById(R.id.IDDisp);
            TextView txtIDProyecto = (TextView) rowView.findViewById(R.id.IDProyecto);
            TextView txtIDProyectoDisp = (TextView) rowView.findViewById(R.id.IDProyectoDisp);
            TextView txtIDEstatus = (TextView) rowView.findViewById(R.id.IDEstatus);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtPiso = (TextView) rowView.findViewById(R.id.Piso);
            TextView txtEdificio = (TextView) rowView.findViewById(R.id.Edificio);
            TextView txtHabitacion = (TextView) rowView.findViewById(R.id.Habitacion);
            TextView txtObservaciones = (TextView) rowView.findViewById(R.id.Observaciones);
            TextView txtArea = (TextView) rowView.findViewById(R.id.Area);
            TextView txtAncho = (TextView) rowView.findViewById(R.id.Ancho);
            TextView txtAlto = (TextView) rowView.findViewById(R.id.Alto);
            TextView txtHojas = (TextView) rowView.findViewById(R.id.Hojas);
            TextView txtControl = (TextView) rowView.findViewById(R.id.Control);
            TextView txtFijacion = (TextView) rowView.findViewById(R.id.Fijacion);
            TextView txtCorredera = (TextView) rowView.findViewById(R.id.Corredera);
            txtIDHoteleria.setText(_text[position][0]);
            txtIDDisp.setText(_text[position][1]);
            txtIDProyecto.setText(_text[position][2]);
            txtIDProyectoDisp.setText(_text[position][3]);
            txtIDEstatus.setText(_text[position][23]);
            txtNombre.setText(_text[position][12]);
            txtPiso.setText(_text[position][16]);
            txtEdificio.setText(_text[position][17]);
            txtHabitacion.setText(_text[position][6]);
            txtObservaciones.setText(_text[position][11]);
            txtArea.setText(_text[position][7]);
            txtAncho.setText(_text[position][8]);
            txtAlto.setText(_text[position][9]);
            txtHojas.setText(_text[position][10]);
            txtControl.setText(_text[position][18]);
            txtFijacion.setText(_text[position][19]);
            txtCorredera.setText(_text[position][30]);
            return rowView;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        lista();
    }

    public void lista(){
        String[][] aRef = oDB.ObtenerProyectosHoteleria("0", 1);
        String[][] aDataFolio = null;
        if (aRef !=  null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[31];
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
                aDataFolio[iCnt][26] = aRef[iCnt][26];
                aDataFolio[iCnt][27] = aRef[iCnt][27];
                aDataFolio[iCnt][28] = aRef[iCnt][28];
                aDataFolio[iCnt][29] = aRef[iCnt][29];
                aDataFolio[iCnt][30] = aRef[iCnt][30];
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
