package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by mhernandez on 16/10/2017.
 */

public class especial extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    private Uri fileUri;
    int auxFoto=0;
    String nombreImagen="1";
    String imagen = "";

    public static DBProvider oDB;
    public especial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_especial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Bundle oExt = this.getIntent().getExtras();
        final int idCliente = oExt.getInt("id_cliente");
        final int idclienteDisp = oExt.getInt("id_cliente_disp");
        final int idUsuarioVenta = oExt.getInt("idUsuarioVenta");
        final String Agente = oExt.getString("Agente");
        final int idFormato = oExt.getInt("idFormato");
        final String nombreProyecto = oExt.getString("nombreProyecto");
        final String accesoriosMuro = oExt.getString("accesoriosMuro");
        final String accesoriosTecho = oExt.getString("accesoriosTecho");
        final String accesoriosEspecial = oExt.getString("accesoriosEspecial");
        final String PedidoSap = oExt.getString("PedidoSap");
        final String FechaAlta = oExt.getString("FechaAlta");
        String[][] users = MainActivity.oDB.ObtenerUser("0",3);
        final int usuario = Integer.parseInt(users[0][0]);
        final EditText Ancho = (EditText) this.findViewById(R.id.txtAncho);
        final EditText Alto = (EditText) this.findViewById(R.id.txtAlto);
        final EditText Grosor = (EditText) this.findViewById(R.id.txtGrosor);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        String[][] aRefD = MainActivity.oDB.lastDispositivo();
        String[][] aRefP = MainActivity.oDB.lastProyecto();

        final int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
        final int idDisp = Integer.parseInt(aRefD[(0)][0]);
        nombreImagen = "" + idProyecto + idDisp;

        Button Imagenes = (Button) this.findViewById(R.id.TomarFoto);
        Guardar.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Double txtAncho = Double.parseDouble(Ancho.getText().toString());
                        Double txtAlto = Double.parseDouble(Alto.getText().toString());
                        Double txtGrosor = Double.parseDouble(Grosor.getText().toString());
                        String OBS = Observaciones.getText().toString();
                        String[][] aRefE = MainActivity.oDB.lastEspecial();
                        int idEspecial = Integer.parseInt(aRefE[(0)][0]) + 1;
                        oDB.insertProyecto(idProyecto, idDisp, idCliente, idclienteDisp, idFormato, usuario, nombreProyecto, PedidoSap, FechaAlta,
                                           0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, idUsuarioVenta, Agente, 1);
                        oDB.insertProyectoEspecial(idEspecial, idDisp, idProyecto, idDisp, nombreProyecto, txtAlto, txtAncho, txtGrosor,
                        OBS, imagen, FechaAlta, idFormato, FechaAlta, 1, 0, 0, 0, 1);
                        Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(rIntent);
                    }
                }
        );

        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);
        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);

        if(savedInstanceState != null){
            fileUri = savedInstanceState.getParcelable("uri");
            if(fileUri != null){
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(savedInstanceState.getString("foto"),200,200);
                oImg.setImageBitmap(bit_map);
                foto.setText(imagen);
            }
        }


        Imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new Dialog(especial.this, R.style.Theme_Dialog_Translucent);
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

        if (id == R.id.imagenReferencia){
            customDialog = new Dialog(especial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
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

    public Uri getOutputMediaFileUri(int type, String pID) {
        requestRuntimePermission();
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }

    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_ESPECIAL" + pID + ".jpg");
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
                    foto.setText("IMG_Especial" +nombreImagen); foto.setTextColor(Color.rgb(92, 184, 92));
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText("IMG_Especial" +nombreImagen); foto.setTextColor(Color.rgb(92, 184, 92));
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

}    /*
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