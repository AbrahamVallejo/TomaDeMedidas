package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_listaResidencial.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_listaResidencial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_listaResidencial extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;

    private OnFragmentInteractionListener mListener;

    public Fragment_listaResidencial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_listaResidencial.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_listaResidencial newInstance(String param1, String param2) {
        Fragment_listaResidencial fragment = new Fragment_listaResidencial();
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
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.list_activity, container, false);

        lista();

        ListView tlList = ((ListView) vista.findViewById(R.id.lista));

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

    public class CustomAdapter extends ArrayAdapter<String[]>{
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
            TextView txtObservaciones = (TextView) rowView.findViewById(R.id.Observaciones);
            TextView txtA = (TextView) rowView.findViewById(R.id.A);
            TextView txtB = (TextView) rowView.findViewById(R.id.B);
            TextView txtC = (TextView) rowView.findViewById(R.id.C);
            TextView txtD = (TextView) rowView.findViewById(R.id.D);
            TextView txtE = (TextView) rowView.findViewById(R.id.E);
            TextView txtF = (TextView) rowView.findViewById(R.id.F);
            TextView txtG = (TextView) rowView.findViewById(R.id.G);
            TextView txtH = (TextView) rowView.findViewById(R.id.H);
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
            txtObservaciones.setText(_text[position][18]);
            txtA.setText(_text[position][5]);
            txtB.setText(_text[position][6]);
            txtC.setText(_text[position][7]);
            txtD.setText(_text[position][8]);
            txtE.setText(_text[position][9]);
            txtF.setText(_text[position][10]);
            txtG.setText(_text[position][11]);
            txtH.setText(_text[position][12]);
            return rowView;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        lista();
    }

    public void lista(){
        String[][] aRef = MainActivity.oDB.ObtenerProyectosResidencial("0", 1);
        String[][] aDataFolio = null;
        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[37];
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
            }
            ListView list;
            list = (ListView) vista.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(getActivity(), aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
    }
}
