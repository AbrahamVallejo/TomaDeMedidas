package com.example.mhernandez.tomademedidas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

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

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "droidBH";
    private Uri fileUri;
    String sID;

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
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.list_activity, container, false);

        lista();

        ListView tlList = ((ListView) vista.findViewById(R.id.lista));

        tlList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(final AdapterView<?> aList, View vItem, final int iPosition, long l){
                customDialog = new Dialog(getActivity(), R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla);

                ((Button) customDialog.findViewById(R.id.btnModificar)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        customDialog.dismiss();

                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        Intent rIntent = new Intent(vista.getContext(), listaProyectos.class);
                        rIntent.putExtra("IdProyecto", aDat[1]);
                        rIntent.putExtra("IdDisp", aDat[2]);
                        rIntent.putExtra("nombre", aDat[6]);
                        rIntent.putExtra("fecha", aDat[7]);
                        rIntent.putExtra("pedidoSap", aDat[8]);
                        rIntent.putExtra("autorizado", aDat[12]);
                        rIntent.putExtra("ATecho", aDat[19]);
                        rIntent.putExtra("AMuro", aDat[20]);
                        startActivity(rIntent);
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnEliminar)).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        customDialog.dismiss();
                        String[] aDat = (String[]) aList.getItemAtPosition(iPosition);
                        String idProyecto = aDat[0];
                        String idDisp = aDat[1];
                        MainActivity.oDB.deleteProyecto(idProyecto, idDisp);
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
            View rowView = inflater.inflate(R.layout.activity_listaproyectos, null, true);
            TextView txtNombre = (TextView) rowView.findViewById(R.id.nombre);
            TextView txtAutorizado = (TextView) rowView.findViewById(R.id.autorizado);
            TextView txtATecho = (TextView) rowView.findViewById(R.id.ATecho);
            TextView txtPedidoSap = (TextView) rowView.findViewById(R.id.pedidoSap);
            TextView txtFecha = (TextView) rowView.findViewById(R.id.fecha);
            TextView txtAMuro = (TextView) rowView.findViewById(R.id.AMuro);
            TextView txtIDDisp = (TextView) rowView.findViewById(R.id.idDisp);
            TextView txtIDProyecto = (TextView) rowView.findViewById(R.id.IDProyecto);
            TextView txtIDCliente = (TextView) rowView.findViewById(R.id.idCliente);
            TextView txtIDUser = (TextView) rowView.findViewById(R.id.IDUser);
            TextView txtIDClienteDisp = (TextView) rowView.findViewById(R.id.IDClienteDisp);
            TextView txtIDFormato = (TextView) rowView.findViewById(R.id.IDFormato);
            String[] parts = _text[position][7].split("T");
            txtNombre.setText(_text[position][6]);
            txtFecha.setText(_text[position][7]);
            txtFecha.setText(parts[0]);
            txtPedidoSap.setText(_text[position][8]);
            txtAutorizado.setText(_text[position][12]);
            txtATecho.setText(_text[position][19].toLowerCase());
            txtAMuro.setText(_text[position][20].toLowerCase());
            txtIDDisp.setText(_text[position][1]);
            txtIDProyecto.setText(_text[position][0]);
            txtIDCliente.setText(_text[position][2]);
            txtIDUser.setText(_text[position][5]);
            txtIDClienteDisp.setText(_text[position][3]);
            txtIDFormato.setText(_text[position][4]);
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
        String[][] aRef = MainActivity.oDB.ObtenerProyectos("0", 1);
        String[][] aDataFolio = null;
        if (aRef != null){
            aDataFolio = new String[aRef.length][];
            for (int iCnt = 0; iCnt < aRef.length; iCnt++){
                aDataFolio[iCnt] = new String[23];
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
            }
            ListView list;
            list = (ListView) vista.findViewById(R.id.lista);
            CustomAdapter adapter = new CustomAdapter(getActivity(), aDataFolio);
            list.setTag(aRef);
            list.setAdapter(adapter);
        }
    }

    public void onFotoClick(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, this.sID);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private static Uri getOutputMediaFileUri(int type, String pID){
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }

    private static File getOutputMediaFile(int type, String pID){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),APP_PATH);
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + pID + ".jpg");
        }else {
            return null;
        }
        return mediaFile;
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                ImageView oImg = (ImageView)getActivity().findViewById(R.id.imgFoto);
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(), 200, 200);
                oImg.setImageBitmap(bit_map);
            }else if(resultCode == RESULT_CANCELED){
                // User cancelled the image capture
            }else {
                //Image capture failed, advise user
            }
        }
    }*/
}
