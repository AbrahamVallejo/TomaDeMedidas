package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_listaClientes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_listaClientes#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Fragment_listaClientes extends Fragment {
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

    public Fragment_listaClientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_listaClientes.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_listaClientes newInstance(String param1, String param2) {
        Fragment_listaClientes fragment = new Fragment_listaClientes();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista = inflater.inflate(R.layout.list_activity, container, false);

        lista();

        ListView tlList = ((ListView) vista.findViewById(R.id.lista));

        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem, final int iPosition, long l) {
                customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla);

                ((Button) customDialog.findViewById(R.id.btnModificar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();

                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        Intent rIntent = new Intent(vista.getContext(), listaClientes.class);
                        rIntent.putExtra("idCliente", aDat[0]);
                        rIntent.putExtra("idDisp", aDat[1]);
                        rIntent.putExtra("nombre", aDat[2]);
                        rIntent.putExtra("telefono", aDat[3]);
                        rIntent.putExtra("direccion", aDat[4]);
                        startActivity(rIntent);
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnEliminar)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        String idCliente = aDat[0];
                        String idDisp = aDat[1];
                        MainActivity.oDB.deleteCliente(idCliente, idDisp);
                        Toast.makeText(getActivity(), "REGISTRO ELIMINADO", Toast.LENGTH_SHORT).show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class CustomAdapter extends ArrayAdapter<String[]> {
        private final Activity _context;
        private final String[][] _text;

        public CustomAdapter(Activity context, String[][] text){
            super(context, R.layout.activity_listaclientes, text);
            this._context = context;
            this._text = text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = _context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.activity_listaclientes, null, true);
            TextView txtIdCliente = (TextView) rowView.findViewById(R.id.idCliente);
            TextView txtIdDisp = (TextView) rowView.findViewById(R.id.idDisp);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtTelefono = (TextView) rowView.findViewById(R.id.telefono);
            TextView txtDireccion = (TextView) rowView.findViewById(R.id.direccion);
            txtIdCliente.setText(_text[position][0]);
            txtIdDisp.setText(_text[position][1]);
            txtNombre.setText(_text[position][2]);
            txtTelefono.setText(_text[position][3]);
            txtDireccion.setText(_text[position][4]);
            return rowView;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        lista();
    }

    public void lista(){
        String[][] aRef = MainActivity.oDB.ObtenerClientes("0", 1);
        String[][] aDataFolio = null;

        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[6];
                aDataFolio[iCnt][0] = aRef[iCnt][0];
                aDataFolio[iCnt][1] = aRef[iCnt][1];
                aDataFolio[iCnt][2] = aRef[iCnt][2];
                aDataFolio[iCnt][3] = aRef[iCnt][3];
                aDataFolio[iCnt][4] = aRef[iCnt][4];
                aDataFolio[iCnt][5] = aRef[iCnt][5];
            }

            ListView list;
            list = (ListView) vista.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(getActivity(), aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
    }
}
