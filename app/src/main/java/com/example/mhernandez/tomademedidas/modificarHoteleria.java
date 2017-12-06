package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Created by mhernandez on 08/11/2017.
 */


public class modificarHoteleria extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    private Uri fileUri;
    int auxFoto=0;
    String nombreImagen="1";
    String imagen = "";

    public Spinner spAreaH, spFijacionH, spControlH, spCorrederaH;
    public static DBProvider oDB;
    public modificarHoteleria() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_hoteleria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
        // Llenar spinner
        spinnerArea();  spinnerControl();   spinnerFijacion();  spinnerCorredera();

        final int idHoteleria = Integer.parseInt(oExt.getString("idHoteleria"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
        String Edificio = oExt.getString("Edificio");
        String Piso = oExt.getString("Piso");
        String Habitacion = oExt.getString("Habitacion");
        String Ancho = oExt.getString("Ancho");
        String Alto = oExt.getString("Alto");
        String Hojas = oExt.getString("Hojas");
        String MedidaSugerida = oExt.getString("MedidaSugerida");
        String Observaciones = oExt.getString("Observaciones");
        final EditText txtEdificio = (EditText) findViewById(R.id.txtEdificio);
        final EditText txtPiso = (EditText) findViewById(R.id.txtPiso);
        final EditText txtHabitacion = (EditText) findViewById(R.id.txtHabitacion);
        final Spinner txtArea = (Spinner) findViewById(R.id.spinner_area);
        final EditText txtAncho = (EditText) findViewById(R.id.txtAncho);
        final EditText txtAlto = (EditText) findViewById(R.id.txtAlto);
        final EditText txtHojas = (EditText) findViewById(R.id.txtHojas);
        final Spinner txtControl = (Spinner) findViewById(R.id.spinner_control);
        final Spinner txtFijacion = (Spinner) findViewById(R.id.spinner_fijacion);
        final Spinner txtCorredera = (Spinner) findViewById(R.id.spinner_corredera);
        final EditText txtMedidaSugerida = (EditText) findViewById(R.id.txtMedidaSugerida);
        final EditText txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);
        Button Guardar = (Button) findViewById(R.id.Guardar);
        txtEdificio.setText(Edificio.trim());
        txtPiso.setText(Piso.trim());
        txtHabitacion.setText(Habitacion.trim());
        txtAncho.setText(Ancho.trim());
        txtAlto.setText(Alto.trim());
        txtHojas.setText(Hojas.trim());
        txtMedidaSugerida.setText(MedidaSugerida.trim());
        txtObservaciones.setText(Observaciones.trim());
        final String[][] aRes = modificarHoteleria.oDB.ObtenerProyectosHoteleria(String.valueOf(idHoteleria), String.valueOf(idDisp), 4);
        if (aRes[0][13].length() >50){
            crearImagen(aRes[0][13]);
        }

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Edificio = txtEdificio.getText().toString();
                String Piso = txtPiso.getText().toString();
                String Habitacion = txtHabitacion.getText().toString();
                String Control = txtControl.getSelectedItem().toString();
                String Area = txtArea.getSelectedItem().toString();
                Double Ancho = Double.parseDouble(txtAncho.getText().toString());
                Double Alto = Double.parseDouble(txtAlto.getText().toString());
                String Hojas = txtHojas.getText().toString();
                String Fijacion = txtFijacion.getSelectedItem().toString();
                String Corredera = txtCorredera.getSelectedItem().toString();
                String MedidaSugerida = txtMedidaSugerida.getText().toString();
                String Observaciones = txtObservaciones.getText().toString();
                if (Integer.parseInt(aRes[0][31]) != 1){
                oDB.updateProyectoHoteleria(idHoteleria, idDisp, Habitacion, Area, Ancho, Alto, Hojas, imagen,
                        Observaciones, Piso, Edificio, Control, Fijacion, MedidaSugerida, Corredera, 2);}
                else{
                    oDB.updateProyectoHoteleria(idHoteleria, idDisp, Habitacion, Area, Ancho, Alto, Hojas, imagen,
                            Observaciones, Piso, Edificio, Control, Fijacion, MedidaSugerida, Corredera, 1);}
                finish();
            }
        });

        nombreImagen=""+idHoteleria+idDisp;
        Button Imagenes = ((Button) this.findViewById(R.id.TomarFoto));
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
                customDialog = new Dialog(modificarHoteleria.this, R.style.Theme_Dialog_Translucent);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.menu_tabla_imagenes);

                ((Button) customDialog.findViewById(R.id.btnCamara)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
            customDialog = new Dialog(modificarHoteleria.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerArea(){
        Bundle oExt = this.getIntent().getExtras();
        String Area = oExt.getString("Area");
        String[][] aRes= modificarHoteleria.oDB.ObtenerUbicacion("0",1);
        spAreaH= (Spinner)( findViewById(R.id.spinner_area));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Area;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAreaH.setAdapter(adapter);
    }

    public void spinnerControl(){
        Bundle oExt = this.getIntent().getExtras();
        String Control = oExt.getString("Control");
        String[][] aRes= modificarHoteleria.oDB.ObtenerControl("0",1);
        spControlH= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Control;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlH.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        Bundle oExt = this.getIntent().getExtras();
        String Fijacion = oExt.getString("Fijacion");
        String[][] aRes= modificarHoteleria.oDB.ObtenerFijacion("0",1);
        spFijacionH= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Fijacion;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionH.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        Bundle oExt = this.getIntent().getExtras();
        String Corredera = oExt.getString("Corredera");
        String[][] aRes= modificarHoteleria.oDB.ObtenerCorredera("0",1);
        spCorrederaH= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Corredera;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);   //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaH.setAdapter(adapter);
    }

    //Funciones de Camara

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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_HOTELERIA" + pID + ".jpg");
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
                    foto.setText(imagen);
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText(imagen);
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
