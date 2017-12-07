package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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


public class listaResidencial extends AppCompatActivity {
    public static DBProvider oDB;
    public listaResidencial() { oDB = new DBProvider(this);}
    Dialog customDialog = null;
    public int idProyecto=0;
    public int EstatusProyecto;
    public int idProyectoDisp = 0;
    public String NombreProyecto;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Quitar Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.list_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle oExt = this.getIntent().getExtras();
        idProyecto = oExt.getInt("idProyecto");
        EstatusProyecto = oExt.getInt("Estatus");
        idProyectoDisp = oExt.getInt("idProyectoDisp");
        NombreProyecto = oExt.getString("Nombre");
        Log.v("[FRAGMENT]", "ID "+idProyecto);

        lista();

        ListView tlList = ((ListView) this.findViewById(R.id.lista));


        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem, final int iPosition, final long l) {
                customDialog = new Dialog(listaResidencial.this, R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_medida);

                final String[] aDat = (String[]) aList.getItemAtPosition(iPosition);

                ((Button) customDialog.findViewById(R.id.btnModificar)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (Integer.parseInt(aDat[27]) !=1){
                            Toast.makeText(listaResidencial.this, "Medida Cerrada", Toast.LENGTH_LONG).show();
                        }else {
                            Intent rIntent = new Intent(listaResidencial.this, modificarResidencial.class);
                            rIntent.putExtra("idResidencial", aDat[0]);
                            rIntent.putExtra("idDisp", aDat[1]);
                            rIntent.putExtra("idProyecto", aDat[2]);
                            rIntent.putExtra("idProyectoDisp", aDat[3]);
                            rIntent.putExtra("Ubicacion", aDat[4]);
                            rIntent.putExtra("Piso", aDat[29]);
                            rIntent.putExtra("MedidaSugerida", aDat[17]);
                            rIntent.putExtra("ProfMarco", aDat[13]);
                            rIntent.putExtra("ProfJaladera", aDat[14]);
                            rIntent.putExtra("Control", aDat[15]);
                            rIntent.putExtra("Agpto", aDat[16]);
                            rIntent.putExtra("Corredera", aDat[36]);
                            rIntent.putExtra("Observaciones", aDat[18]);
                            rIntent.putExtra("Fijacion", aDat[28]);
                            rIntent.putExtra("A", aDat[5]);
                            rIntent.putExtra("B", aDat[6]);
                            rIntent.putExtra("C", aDat[7]);
                            rIntent.putExtra("D", aDat[8]);
                            rIntent.putExtra("E", aDat[9]);
                            rIntent.putExtra("F", aDat[10]);
                            rIntent.putExtra("G", aDat[11]);
                            rIntent.putExtra("H", aDat[12]);
                            rIntent.putExtra("Aimg", aDat[19]);
                            startActivity(rIntent);
                        }
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnCerrar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(aDat[27]) !=1){
                            Toast.makeText(listaResidencial.this, "Proyecto Cerrado", Toast.LENGTH_LONG).show();
                        }else {
                            oDB.cerrarProyectoResidencial(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1]), 2, 2);
                            lista();
                        }
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(aDat[27]) !=1){
                            Toast.makeText(listaResidencial.this, "Proyecto Cerrado", Toast.LENGTH_LONG).show();
                        }else {
                            oDB.cerrarProyectoResidencial(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1]), Integer.parseInt(aDat[27]), 3);
                            //oDB.deleteProyectoResidencial(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1]));
                            lista();
                        }
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
            super(context, R.layout.activity_listaresidencial, text);
            this._context = context;
            this._text = text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = _context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_listaresidencial, null);
            TextView txtIDResidencial = (TextView) rowView.findViewById(R.id.IDResidencial);
            TextView txtIDDisp = (TextView) rowView.findViewById(R.id.IDDisp);
            TextView txtIDProyecto = (TextView) rowView.findViewById(R.id.IDProyecto);
            TextView txtIDProyectoDisp = (TextView) rowView.findViewById(R.id.IDProyectoDisp);
            TextView txtIDEstatus = (TextView) rowView.findViewById(R.id.IDEstatus);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtUbicacion = (TextView) rowView.findViewById(R.id.Ubicacion);
            TextView txtPiso = (TextView) rowView.findViewById(R.id.Piso);
            TextView txtMedidaSugerida = (TextView) rowView.findViewById(R.id.MedidaSugerida);
            TextView txtProfMarco = (TextView) rowView.findViewById(R.id.ProfMarco);
            TextView txtProfJaladera = (TextView) rowView.findViewById(R.id.ProfJaladera);
            TextView txtControl = (TextView) rowView.findViewById(R.id.Control);
            TextView txtAgpto = (TextView) rowView.findViewById(R.id.Agpto);
            TextView txtCorredera = (TextView) rowView.findViewById(R.id.Corredera);
            TextView txtFijacion = (TextView) rowView.findViewById(R.id.Fijacion);
            TextView txtObservaciones = (TextView) rowView.findViewById(R.id.Observaciones);
            TextView txtA = (TextView) rowView.findViewById(R.id.A);
            TextView txtB = (TextView) rowView.findViewById(R.id.B);
            TextView txtC = (TextView) rowView.findViewById(R.id.C);
            TextView txtD = (TextView) rowView.findViewById(R.id.D);
            TextView txtE = (TextView) rowView.findViewById(R.id.E);
            TextView txtF = (TextView) rowView.findViewById(R.id.F);
            TextView txtG = (TextView) rowView.findViewById(R.id.G);
            TextView txtH = (TextView) rowView.findViewById(R.id.H);
            TextView txtEstatus = (TextView) rowView.findViewById(R.id.EstatusProyecto);
            TextView checkCliente = (TextView) rowView.findViewById(R.id.checkCliente);
            TextView Aimg = (TextView) rowView.findViewById(R.id.Aimg);
            txtIDResidencial.setText(_text[position][0]);
            txtIDDisp.setText(_text[position][1]);
            txtIDProyecto.setText(_text[position][2]);
            txtIDProyectoDisp.setText(_text[position][3]);
            txtIDEstatus.setText(_text[position][27]);
            txtNombre.setText(_text[position][21]);
            txtUbicacion.setText(_text[position][4]);
            txtPiso.setText(_text[position][29]);
            txtMedidaSugerida.setText(_text[position][17]);
            txtProfMarco.setText(_text[position][13]);
            txtProfJaladera.setText(_text[position][14]);
            txtControl.setText(_text[position][15]);
            txtAgpto.setText(_text[position][16]);
            txtCorredera.setText(_text[position][36]);
            txtFijacion.setText(_text[position][28]);
            txtObservaciones.setText(_text[position][18]);
            txtA.setText(_text[position][5]);
            txtB.setText(_text[position][6]);
            txtC.setText(_text[position][7]);
            txtD.setText(_text[position][8]);
            txtE.setText(_text[position][9]);
            txtF.setText(_text[position][10]);
            txtG.setText(_text[position][11]);
            txtH.setText(_text[position][12]);
            if (Integer.parseInt(_text[position][27]) == 1){
                txtEstatus.setText("Activo");
            }else{
                txtEstatus.setText("Cerrado");
            }
            if( Integer.parseInt(_text[position][37]) == 0){
                checkCliente.setTextColor(Color.rgb(92, 184, 92));
                checkCliente.setText("Sincronizado");
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
        String[][] aRef = oDB.ProyectoResidencialProyecto(String.valueOf(idProyecto), 3);
        String[][] aDataFolio = null;
        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[38];
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
                aDataFolio[iCnt][31] = aRef[iCnt][31];
                aDataFolio[iCnt][32] = aRef[iCnt][32];
                aDataFolio[iCnt][33] = aRef[iCnt][33];
                aDataFolio[iCnt][34] = aRef[iCnt][34];
                aDataFolio[iCnt][35] = aRef[iCnt][35];
                aDataFolio[iCnt][36] = aRef[iCnt][36];
                aDataFolio[iCnt][37] = aRef[iCnt][37];
            }
            ListView list;
            list = (ListView) this.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(this, aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_crear_medida_galeria, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }
        if (id == R.id.crearMedida){
            if (EstatusProyecto != 1){
                Toast.makeText(listaResidencial.this, "Proyecto Cerrado", Toast.LENGTH_LONG).show();
            }else {
                Intent rIntent = new Intent(listaResidencial.this, medidaResidencial.class);
                rIntent.putExtra("idProyecto", idProyecto);
                rIntent.putExtra("idProyectoDisp", idProyectoDisp);
                rIntent.putExtra("Nombre", NombreProyecto);
                startActivity(rIntent);
            }
        }else if (id == R.id.crearGaleria){
            if (EstatusProyecto != 1){
                Toast.makeText(listaResidencial.this, "Proyecto Cerrado", Toast.LENGTH_LONG).show();
            }else {
                Intent rIntent = new Intent(listaResidencial.this, crearGaleriaImagenes.class);
                rIntent.putExtra("idProyecto", idProyecto);
                rIntent.putExtra("idProyectoDisp", idProyectoDisp);
                rIntent.putExtra("Nombre", NombreProyecto);
                rIntent.putExtra("Formato", 1);
                startActivity(rIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
