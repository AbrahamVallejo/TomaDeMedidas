package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_listaProyecto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_listaProyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_listaProyecto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Dialog customDialog = null;

    private OnFragmentInteractionListener mListener;

    public Fragment_listaProyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_listaProyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_listaProyecto newInstance(String param1, String param2) {
        Fragment_listaProyecto fragment = new Fragment_listaProyecto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.list_activity, container, false);
        lista();

        ListView tlList = ((ListView) vista.findViewById(R.id.lista));

        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem, final int iPosition, long l){
                customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_proyecto);
                final Date FechaCierre = new Date();  Log.v("[spin]","/Date("+FechaCierre.getTime()+")/");

                ((Button) customDialog.findViewById(R.id.btnCerrar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        if ( Integer.parseInt(aDat[10]) ==1){
                            MainActivity.oDB.cerrarProyecto(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1]), 2, 5, "/Date("+FechaCierre.getTime()+")/", 2);
                            lista();
                        }
                        Toast.makeText(getActivity(), "PROYECTO CERRADO", Toast.LENGTH_SHORT).show();
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnVer)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        customDialog.dismiss();
                        String Formato = aDat[4];
                        if (Formato.equals("2")){
                            Log.v("[FRAGMENT]", Formato);
                            Intent rIntent = new Intent(vista.getContext(), listaHoteleria.class);
                            rIntent.putExtra("idProyecto", Integer.valueOf(aDat[0]) );
                            rIntent.putExtra("Estatus", Integer.valueOf(aDat[10]));
                            startActivity(rIntent);
                        }else if (Formato.equals("4")){
                            Log.v("[FRAGMENT]", Formato );
                            Intent rIntent = new Intent(vista.getContext(), listaCama.class);
                            rIntent.putExtra("idProyecto", Integer.valueOf(aDat[0]) );
                            rIntent.putExtra("Estatus", Integer.valueOf(aDat[10]));
                            startActivity(rIntent);
                        }else if (Formato.equals("1")){
                            Log.v("[FRAGMENT]", Formato);
                            Intent rIntent = new Intent(vista.getContext(), listaResidencial.class);
                            rIntent.putExtra("idProyecto", Integer.valueOf(aDat[0]) );
                            rIntent.putExtra("Estatus", Integer.valueOf(aDat[10]));
                            startActivity(rIntent);
                        }else if (Formato.equals("3")){
                            Log.v("[FRAGMENT]", Formato);
                            Intent rIntent = new Intent(vista.getContext(), listaGaleria.class);
                            rIntent.putExtra("idProyecto", Integer.valueOf(aDat[0]) );
                            rIntent.putExtra("Estatus", Integer.valueOf(aDat[10]));
                            startActivity(rIntent);
                        }else if (Formato.equals("5")){
                            Log.v("[FRAGMENT]", Formato);
                            Intent rIntent = new Intent(vista.getContext(), listaEspecial.class);
                            rIntent.putExtra("idProyecto", Integer.valueOf(aDat[0]) );
                            rIntent.putExtra("Estatus", Integer.valueOf(aDat[10]));
                            startActivity(rIntent);
                        }

/*                        Intent rIntent = new Intent(vista.getContext(), listaProyectos.class);
                        rIntent.putExtra("IdProyecto", aDat[0]);
                        rIntent.putExtra("IdDisp", aDat[1]);
                        rIntent.putExtra("IdFormato", aDat[4]);
                        rIntent.putExtra("nombre", aDat[6]);
                        rIntent.putExtra("fecha", aDat[7]);
                        rIntent.putExtra("pedidoSap", aDat[8]);
                        rIntent.putExtra("autorizado", aDat[12]);
                        rIntent.putExtra("ATecho", aDat[19]);
                        rIntent.putExtra("AMuro", aDat[20]);
                        rIntent.putExtra("AEspeciales", aDat[21]);
                        startActivity(rIntent);*/
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnBorrar)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        customDialog.dismiss();
                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        String idProyecto = aDat[0];
                        String idDisp = aDat[1];

                        String[][] aRef = MainActivity.oDB.buscarProyecto(Integer.parseInt(idProyecto), Integer.parseInt(idDisp) );
                        Log.v("[addC]",aRef[0][5]);

                        if (Integer.parseInt(aRef[0][15]) == 1) {
                            MainActivity.oDB.deleteProyecto(idProyecto, idDisp);
                            Toast.makeText(getActivity(), "REGISTRO ELIMINADO", Toast.LENGTH_SHORT).show();
                        }
                        if (Integer.parseInt(aRef[0][15]) != 1) {
                            MainActivity.oDB.cerrarProyecto(Integer.parseInt(aDat[0]), Integer.parseInt(aDat[1]), 2, 5,"/Date("+FechaCierre.getTime()+")/", 2);
                            Toast.makeText(getActivity(), "PROYECTO CERRADO", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(), "PROYECTO CERRADO", Toast.LENGTH_SHORT).show();
                        lista();
                    }
                });
                customDialog.show();
                return false;
            }

        });

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class CustomAdapter extends ArrayAdapter<String[]> {
        private final Activity _context;
        private final String[][] _text;

        public CustomAdapter(Activity context, String[][] text){
            super(context, R.layout.activity_listaproyectos, text);
            this._context = context;
            this._text = text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = _context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_listaproyectos, null);
            TextView txtIDProyecto = (TextView) rowView.findViewById(R.id.IDProyecto);
            TextView txtIDDisp = (TextView) rowView.findViewById(R.id.IDDisp);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtAutorizado = (TextView) rowView.findViewById(R.id.autorizado);
            TextView txtATecho = (TextView) rowView.findViewById(R.id.ATecho);
            TextView txtPedidoSap = (TextView) rowView.findViewById(R.id.pedidoSap);
            TextView txtFecha = (TextView) rowView.findViewById(R.id.fecha);
            TextView txtAMuro = (TextView) rowView.findViewById(R.id.AMuro);
            TextView txtAEspeciales = (TextView) rowView.findViewById(R.id.AEspeciales);
            TextView txtIDCliente = (TextView) rowView.findViewById(R.id.IDCliente);
            TextView txtIDUser = (TextView) rowView.findViewById(R.id.IDUser);
            TextView txtIDClienteDisp = (TextView) rowView.findViewById(R.id.IDClienteDisp);
            TextView txtIDFormato = (TextView) rowView.findViewById(R.id.IDFormato);
            TextView txtEstatus = (TextView) rowView.findViewById(R.id.EstatusProyecto);
            TextView checkCliente = (TextView) rowView.findViewById(R.id.checkCliente);
            Log.v("[AQUIANDO", _text[position][7]);
            Log.v("[AQUIANDO","H"+ _text[position][7].indexOf("T"));
            if ( _text[position][7].indexOf("T") != 10){
                txtFecha.setText("no disponible" );
            }else {
            String[] parts = _text[position][7].split("T");
            txtFecha.setText(parts[0]);}


            txtNombre.setText(_text[position][6]);
            txtPedidoSap.setText(_text[position][8]);
            txtAutorizado.setText(_text[position][12]);
            txtATecho.setText(_text[position][19].toLowerCase());
            txtAMuro.setText(_text[position][20].toLowerCase());
            if (_text[position][21].length() >2){
                txtAEspeciales.setText(_text[position][21].toLowerCase());
            }else{
                txtAEspeciales.setText("(no definido)"); //txtAEspeciales.setTextSize(10);
                txtAEspeciales.setTextColor(Color.rgb(204,85,85));
            }

            txtIDDisp.setText(_text[position][1]);
            txtIDProyecto.setText(_text[position][0]);
            txtIDCliente.setText(_text[position][2]);
            txtIDUser.setText(_text[position][5]);
            txtIDClienteDisp.setText(_text[position][3]);
            txtIDFormato.setText(_text[position][4]);

            if (Integer.parseInt(_text[position][10]) == 1){
                txtEstatus.setText("Activo");
            }else{
                txtEstatus.setText("Cerrado");
            }
            if( Integer.parseInt(_text[position][23]) == 0){
                checkCliente.setTextColor(Color.BLACK);
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
        Log.v("[obtener]","Voy por datos");
        String[][] aRef = MainActivity.oDB.ObtenerProyectos("0", 3);
        String[][] aDataFolio = null;
        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[24];
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
            }
            ListView list;
            list = (ListView) vista.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(getActivity(), aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
    }
}
