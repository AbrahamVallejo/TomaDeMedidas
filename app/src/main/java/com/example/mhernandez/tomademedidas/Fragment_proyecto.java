package com.example.mhernandez.tomademedidas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_proyecto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_proyecto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_proyecto extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "droidBH";
    private Uri fileUri;
    String sID;
    private Spinner spClientes, spAgentes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista = null;

    private OnFragmentInteractionListener mListener;

    public Fragment_proyecto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_proyecto.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_proyecto newInstance(String param1, String param2) {
        Fragment_proyecto fragment = new Fragment_proyecto();
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
        vista = inflater.inflate(R.layout.crear_proyecto, container, false);
        Button botonCamara = ((Button) vista.findViewById(R.id.TomarFoto));
        ImageView oImg = (ImageView) vista.findViewById(R.id.imgFoto);

        spinnerCliente();
        spinnerAgente();

        if(savedInstanceState != null){
            fileUri = savedInstanceState.getParcelable("uri");
            if(fileUri != null){
                //Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(savedInstanceState.getString("foto"),200,200);
                //oImg.setImageBitmap(bit_map);
            }
        }

        ((Button) botonCamara.findViewById(R.id.TomarFoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, sID);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        return vista;
    }

    public void spinnerCliente(){
        String[][] aRes= MainActivity.oDB.ObtenerClientes("0",1);
        spClientes= ( vista.findViewById(R.id.spinner_cliente));
        /*List<String> listaClientesSql; //ArrayAdapter<String> comboAdapterSql;
        listaClientesSql = new ArrayList<>();
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]+"."+aRes[i][1]);
            listaClientesSql.add(inde+"- "+aRes[i][2]);
        }
        Log.v("[obtener]",listaClientesSql.toString()); //comboAdapterSql = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientesSql);
        spClientes.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, listaClientesSql));*/
        final String[] aData = new String[aRes.length];
        aData[0]="Seleccione un Cliente:";
        for(int i = 1; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]+"."+aRes[i][1]);
            aData[i] = (inde+" - "+aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),R.layout.simple_spinner_item,aData);
        spClientes.setAdapter(adapter);
    }

    public void spinnerAgente(){
        String[][] aRes= MainActivity.oDB.ObtenerAgentes("0",1);
        spAgentes= ( vista.findViewById(R.id.spinner_agente));
        final String[] aData = new String[aRes.length];
        aData[0]="Seleccione un Agente:";
        for(int i = 1; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),R.layout.simple_spinner_item,aData);
        spAgentes.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (fileUri != null) {
            savedInstanceState.putParcelable("uri", fileUri);
            savedInstanceState.getString("foto", fileUri.getPath());
        }
        super.onSaveInstanceState(savedInstanceState);
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

    /*
    public void onFotoClick(View view){
        Toast.makeText(getActivity(), "si", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, this.sID);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    */

    private static Uri getOutputMediaFileUri(int type, String pID){
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }

    private static File getOutputMediaFile(int type, String pID){
        File mediaStorageDir = new
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),APP_PATH);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK){
                ImageView oImg = (ImageView)getActivity().findViewById(R.id.imgFoto);//
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(), 200, 200);
                oImg.setImageBitmap(bit_map);//
            }else if(resultCode == RESULT_CANCELED){
                // User cancelled the image capture
            }else {
                //Image capture failed, advise user
            }
        }
    }

}
