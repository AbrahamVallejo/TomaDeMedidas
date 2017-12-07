package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mhernandez on 06/11/2017.
 */


public class medidaResidencial extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    int auxFoto=0;
    private Uri fileUri;
    String imagen = "";
    String nombreImagen="1";

    public Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public medidaResidencial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerUbicacion(); spinnerControl(); spinnerFijacion(); spinnerCorredera(); spinnerAgpto();
        final Bundle oExt = getIntent().getExtras();
        final int idProyecto = oExt.getInt("idProyecto");
        final int idProyectoDisp = oExt.getInt("idProyectoDisp");
        final String Nombre = oExt.getString("Nombre");
        final Spinner Ubicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText A = (EditText) this.findViewById(R.id.txtA);
        final EditText B = (EditText) this.findViewById(R.id.txtB);
        final EditText C = (EditText) this.findViewById(R.id.txtC);
        final EditText D = (EditText) this.findViewById(R.id.txtD);
        final EditText E = (EditText) this.findViewById(R.id.txtE);
        final EditText F = (EditText) this.findViewById(R.id.txtF);
        final EditText G = (EditText) this.findViewById(R.id.txtG);
        final EditText H = (EditText) this.findViewById(R.id.txtH);
        final EditText ProfMarco = (EditText) this.findViewById(R.id.txtProfMarco);
        final EditText ProfJaladera = (EditText) this.findViewById(R.id.txtProfJaladera);
        final Spinner Control = (Spinner) this.findViewById(R.id.spinner_control);
        final Spinner agpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner Corredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final Button crearUbicacion = (Button) this.findViewById(R.id.crearUbicacion);
        crearUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(medidaResidencial.this, crearUbicacion.class);
                startActivity(rIntent);
            }
        });
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        String[][] aRefD = MainActivity.oDB.lastDispositivo();
        String[][] aRefR = MainActivity.oDB.lastResidencial();
        final int idResidencial = Integer.parseInt(aRefR[(0)][0]) + 1;
        final int idDisp = Integer.parseInt(aRefD[(0)][0]);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date FechaA = new Date();
                String FechaAlta = "/Date("+FechaA.getTime()+")/";

                String txtUbicacion = Ubicacion.getSelectedItem().toString();
                String txtPiso = Piso.getText().toString();
                Double txtA = Double.parseDouble(A.getText().toString());
                Double txtB = Double.parseDouble(B.getText().toString());
                Double txtC = Double.parseDouble(C.getText().toString());
                Double txtD = Double.parseDouble(D.getText().toString());
                Double txtE = Double.parseDouble(E.getText().toString());
                Double txtF = Double.parseDouble(F.getText().toString());
                Double txtG = Double.parseDouble(G.getText().toString());
                Double txtH = Double.parseDouble(H.getText().toString());
                Double txtProfMarco = Double.parseDouble(ProfMarco.getText().toString());
                Double txtProfJaladera = Double.parseDouble(ProfJaladera.getText().toString());
                String txtControl = Control.getSelectedItem().toString();
                String txtAgpto = agpto.getSelectedItem().toString();
                String txtFijacion = Fijacion.getSelectedItem().toString();
                String txtCorredera = Corredera.getSelectedItem().toString();
                String txtMedidasSugerida = MedidaSugerida.getText().toString();
                String txtObservaciones = Observaciones.getText().toString();
                oDB.insertProyectoResidencial(idResidencial, idDisp, idProyecto, idProyectoDisp, txtUbicacion, txtA, txtB, txtC, txtD,
                        txtE, txtF, txtG, txtH, txtProfMarco, txtProfJaladera, txtControl, txtAgpto, txtMedidasSugerida, txtObservaciones,
                        imagen, Nombre, FechaAlta, 1, FechaAlta, 1, txtFijacion, txtPiso, 1,
                        1, 1, txtCorredera, 1);
                finish();
            }
        });

        nombreImagen=""+idResidencial+idDisp;
        Button Imagenes = ((Button) this.findViewById(R.id.TomarFoto));
        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);
        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);

        Imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new Dialog(medidaResidencial.this, R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_imagenes);

                ((Button) customDialog.findViewById(R.id.btnCamara)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        auxFoto=1;
                        Intent rIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, nombreImagen);
                        rIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(rIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                        customDialog.dismiss();
                    }
                });

                ((Button) customDialog.findViewById(R.id.btnGaleria)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        auxFoto=2;
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_referencia_medida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }

        if(id == R.id.imagenReferencia){
            customDialog = new Dialog(medidaResidencial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerUbicacion(){
        String[][] aRes= medidaResidencial.oDB.ObtenerUbicacion("0",1);
        spUbicacionR= (Spinner)( findViewById(R.id.spinner_ubicacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione una ubicación...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spUbicacionR.setAdapter(adapter);
    }

    public void spinnerControl(){
        String[][] aRes= medidaResidencial.oDB.ObtenerControl("0",1);
        spControlR= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un valor...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlR.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        String[][] aRes= medidaResidencial.oDB.ObtenerFijacion("0",1);
        spFijacionR= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionR.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        String[][] aRes= medidaResidencial.oDB.ObtenerCorredera("0",1);
        spCorrederaR= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione uno...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);  //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaR.setAdapter(adapter);
    }

    public void spinnerAgpto(){
        String[][] aRes= medidaResidencial.oDB.ObtenerControl("0",1);
        spAgptoR= (Spinner)( findViewById(R.id.spinner_agpto));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAgptoR.setAdapter(adapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (fileUri != null) {
            savedInstanceState.putParcelable("uri", fileUri);
            savedInstanceState.getString("foto", fileUri.getPath());
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_RESIDENCIAL" + pID + ".jpg");
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
            if (auxFoto==2) {
                if(resultCode == RESULT_OK && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                    fileUri = data.getData();
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);
                    oImg.setImageURI(fileUri);
                    //Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    oImg.buildDrawingCache();
                    Bitmap bit_map = oImg.getDrawingCache();
                    imagen = convertToBase64(bit_map);
                    foto.setText("IMG_Residencial" +nombreImagen); foto.setTextColor(Color.rgb(92, 184, 92));
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText("IMG_Residencial" +nombreImagen); foto.setTextColor(Color.rgb(92, 184, 92));
                    oImg.setImageBitmap(bit_map);//
                }else if(resultCode == RESULT_CANCELED){
                    // User cancelled the image capture
                }else {
                    //Image capture failed, advise user
                }
            }
        }
    }


    private String convertToBase64(Bitmap imagenMap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagenMap.compress(Bitmap.CompressFormat.JPEG, 45, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }
}

    /*
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
    }*/

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