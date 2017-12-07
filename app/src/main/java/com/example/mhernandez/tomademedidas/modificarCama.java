package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class modificarCama extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    private Uri fileUri;
    int auxFoto=0;
    String nombreImagen="1";
    String imagen = "";

    public static DBProvider oDB;
    public modificarCama() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_cama);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        final int idCama =  Integer.parseInt(oExt.getString("idCama"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        nombreImagen=""+idCama+idDisp;

        String NHabitaciones = oExt.getString("NHabitaciones");
        String A = oExt.getString("A");
        String B = oExt.getString("B");
        String C = oExt.getString("C");
        String D = oExt.getString("D");
        String E = oExt.getString("E");
        String F = oExt.getString("F");
        String G = oExt.getString("G");
        String Observaciones = oExt.getString("Observaciones");
        final EditText txtNHabitaciones = (EditText) this.findViewById(R.id.txt_numero_habitaciones);
        final EditText txtA = (EditText) this.findViewById(R.id.txt_A);
        final EditText txtB = (EditText) this.findViewById(R.id.txt_B);
        final EditText txtC = (EditText) this.findViewById(R.id.txt_C);
        final EditText txtD = (EditText) this.findViewById(R.id.txt_D);
        final EditText txtE = (EditText) this.findViewById(R.id.txt_E);
        final EditText txtF = (EditText) this.findViewById(R.id.txt_F);
        final EditText txtG = (EditText) this.findViewById(R.id.txt_G);
        final EditText txtObservaciones = (EditText) this.findViewById(R.id.txt_observaciones);
        txtNHabitaciones.setText(NHabitaciones.trim());
        txtA.setText(A.trim());
        txtB.setText(B.trim());
        txtC.setText(C.trim());
        txtD.setText(D.trim());
        txtE.setText(E.trim());
        txtF.setText(F.trim());
        txtG.setText(G.trim());

        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
        final String[][] aRes = modificarCama.oDB.ObtenerProyectosCama(String.valueOf(idCama), String.valueOf(idDisp), 4);
        if (aRes[0][12].length() >50){
            crearImagen(aRes[0][12]); foto.setText("IMG_HOTELERIA" +idCama+idDisp); foto.setTextColor(Color.rgb(92, 184, 92));
        }else if(aRes[0][12].length() <5){
            foto.setText("Imagen no disponible"); foto.setTextColor(Color.rgb(204,85,85));
        }else if(aRes[0][12].length() > 5 && aRes[0][12].length() < 50){
            foto.setText("No es posible cargar la imagen"); foto.setTextColor(Color.rgb(92, 184, 92));
        }

        txtObservaciones.setText(Observaciones.trim());
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NHabitaciones = txtNHabitaciones.getText().toString();
                Double A = Double.parseDouble(txtA.getText().toString());
                Double B = Double.parseDouble(txtB.getText().toString());
                Double C = Double.parseDouble(txtC.getText().toString());
                Double D = Double.parseDouble(txtD.getText().toString());
                Double E = Double.parseDouble(txtE.getText().toString());
                Double F = Double.parseDouble(txtF.getText().toString());
                Double G = Double.parseDouble(txtG.getText().toString());
                String Observaciones = txtObservaciones.getText().toString();

                if (Integer.parseInt(aRes[0][27]) == 1){
                    oDB.updateProyectoCama(idCama, idDisp, NHabitaciones, A, B , C, D , E , F, G, imagen, Observaciones, 1);
                }else{
                    oDB.updateProyectoCama(idCama, idDisp, NHabitaciones, A, B , C, D , E , F, G, imagen, Observaciones, 2);}
                finish();
            }
        });


        Button Imagenes = (Button) this.findViewById(R.id.TomarFoto);
        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);


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
                customDialog = new Dialog(modificarCama.this, R.style.Theme_Dialog_Translucent);
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
            customDialog = new Dialog(modificarCama.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_cama);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Funciones para Camara    // TODO: Rename method, update argument and hook method into UI event
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

    /*private static Uri getOutputMediaFileUri(int type, String pID){
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }*/

    // Permisos para Api 23
    public Uri getOutputMediaFileUri(int type, String pID) {
        requestRuntimePermission();
        return Uri.fromFile(getOutputMediaFile(type,pID));
    }

    // Permisos para Api 23
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_CAMA" + pID + ".jpg");
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
                    foto.setText("IMG_CAMA" +nombreImagen);
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText("IMG_CAMA" +nombreImagen);
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