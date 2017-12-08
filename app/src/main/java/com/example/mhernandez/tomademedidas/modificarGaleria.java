package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

/**
 * Created by mhernandez on 08/11/2017.
 */


public class modificarGaleria extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    private Uri fileUri;
    int auxFoto=0;
    String nombreImagen="1";
    String imagen = "";

    public Spinner spArea, spFijacion, spCopete, spProye;
    public static DBProvider oDB;
    public modificarGaleria() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_galeria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        spinnerArea(); spinnerCopete(); spinnerFijacion(); spinnerProyecciones();
        final int idGaleria = Integer.parseInt(oExt.getString("idGaleria"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        nombreImagen= "" + idGaleria + idDisp;
        String NHabitaciones = oExt.getString("NHabitaciones");
        String Ancho = oExt.getString("Ancho");
        String Alto = oExt.getString("Alto");
        String Comentarios = oExt.getString("Comentarios");
        String Aimg = oExt.getString("Aimg");
        setTitle(oExt.getString("nombre"));
        final EditText txtNHabitaciones = (EditText) findViewById(R.id.txt_numero_habitaciones);
        final Spinner txtArea = (Spinner) findViewById(R.id.spinner_area);
        final EditText txtAncho = (EditText) findViewById(R.id.txt_ancho);
        final EditText txtAlto = (EditText) findViewById(R.id.txt_alto);
        final Spinner txtCopete = (Spinner) findViewById(R.id.spinner_copete);
        final Spinner txtProyecciones = (Spinner) findViewById(R.id.spinner_proyecciones);
        final Spinner txtFijacion = (Spinner) findViewById(R.id.spinner_fijacion);
        final EditText txtComentarios = (EditText) findViewById(R.id.txt_comentarios);
        Button Guardar = (Button) findViewById(R.id.Guardar);
        txtNHabitaciones.setText(NHabitaciones.trim());
        txtAncho.setText(Ancho.trim());
        txtAlto.setText(Alto.trim());
        txtComentarios.setText(Comentarios.trim());

        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
        final String[][] aRes = modificarGaleria.oDB.ObtenerProyectosGaleria(String.valueOf(idGaleria), String.valueOf(idDisp), 4);
        if (aRes[0][14].length() >50){
            crearImagen(aRes[0][14]); foto.setText("IMG_Galeria" +idGaleria+idDisp); foto.setTextColor(Color.rgb(92, 184, 92));
        }else if(aRes[0][14].length() <5){
            foto.setText("Imagen no disponible"); foto.setTextColor(Color.rgb(204,85,85));
        }else if(aRes[0][14].length() > 5 && aRes[0][14].length() < 50){
            foto.setText("No es posible cargar la imagen"); foto.setTextColor(Color.rgb(92, 184, 92));
        }

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NHabitaciones = txtNHabitaciones.getText().toString();
                String Area = txtArea.getSelectedItem().toString();
                Double Ancho = Double.parseDouble(txtAncho.getText().toString());
                Double Alto = Double.parseDouble(txtAlto.getText().toString());
                String Copete = txtCopete.getSelectedItem().toString();
                String Proyecciones = txtProyecciones.getSelectedItem().toString();
                String Fijacion =  txtFijacion.getSelectedItem().toString();
                String Comentarios = txtComentarios.getText().toString();
                if (Integer.parseInt(aRes[0][26]) == 1){
                    oDB.updateProyectoGaleria(idGaleria, idDisp, NHabitaciones, Area, Ancho, Alto, Copete, Proyecciones, Fijacion, imagen, Comentarios, 1);
                }else{
                    oDB.updateProyectoGaleria(idGaleria, idDisp, NHabitaciones, Area, Ancho, Alto, Copete, Proyecciones, Fijacion, imagen, Comentarios, 2);
                }

                finish();
            }
        });

        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);

        if(savedInstanceState != null){
            fileUri = savedInstanceState.getParcelable("uri");
            if(fileUri != null){
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(savedInstanceState.getString("foto"),200,200);
                oImg.setImageBitmap(bit_map);
                foto.setText(imagen);
            }
        }
        Button Imagenes = (Button) this.findViewById(R.id.TomarFoto);
        Imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new Dialog(modificarGaleria.this, R.style.Theme_Dialog_Translucent);
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
                        /*
                        Intent rIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, "");
                        rIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(rIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);   */
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

        if (id == R.id.imagenReferencia){
            customDialog = new Dialog(modificarGaleria.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_galeria);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerArea(){
        Bundle oExt = getIntent().getExtras();
        String Area = oExt.getString("Area");
        String[][] aRes= modificarGaleria.oDB.ObtenerUbicacion("0",1);
        spArea= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Area;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spArea.setAdapter(adapter);
    }

    public void spinnerCopete(){
        Bundle oExt = getIntent().getExtras();
        String Copete = oExt.getString("Copete");
        String[][] aRes= modificarGaleria.oDB.ObtenerCopete("0",1);
        spCopete= (Spinner)( findViewById(R.id.spinner_copete));
        final String[] aData = new String[aRes.length+1];
        aData[0]= Copete;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCopete.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        Bundle oExt = getIntent().getExtras();
        String Fijacion = oExt.getString("Fijacion");
        String[][] aRes= modificarGaleria.oDB.ObtenerFijacion("0",1);
        spFijacion= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Fijacion;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacion.setAdapter(adapter);
    }

    public void spinnerProyecciones(){
        Bundle oExt = getIntent().getExtras();
        String Proyecciones = oExt.getString("Proyecciones");
        String[][] aRes= modificarGaleria.oDB.ObtenerProyeccion("0",1);
        spProye= (Spinner)( findViewById(R.id.spinner_proyecciones));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Proyecciones;
        for(int i = 0; i < aRes.length; i++){
            String inde = String.valueOf(aRes[i][0]);
            aData[i+1] = (inde+" - "+aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spProye.setAdapter(adapter);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*private static Uri getOutputMediaFileUri(int type, String pID){
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }*/
    public Uri getOutputMediaFileUri(int type, String pID) {
        requestRuntimePermission();
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }

    public void requestRuntimePermission() {
        //if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        //}
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_GALERIA" + pID + ".jpg");
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
                    foto.setText("IMG_Galeria" +nombreImagen);
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText("IMG_Galeria" +nombreImagen);
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

    private void crearImagen(String IMG){
        byte[] decodedString = Base64.decode(IMG, Base64.DEFAULT); Log.v("[imagen]", ""+decodedString);
        Bitmap bit_map = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length); Log.v("[imagen]", ""+bit_map);
        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);
        oImg.setImageBitmap(bit_map);
    }


}