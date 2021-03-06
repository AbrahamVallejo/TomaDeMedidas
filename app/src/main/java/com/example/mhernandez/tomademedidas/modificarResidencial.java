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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import android.util.Base64;
import android.widget.Toast;


public class modificarResidencial extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "TomaMedidas";
    int auxFoto=0;
    private Uri fileUri;
    String imagen = "";
    String nombreImagen="1", idR="", idD="";
    int DescargarImagen=0;

    public Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public modificarResidencial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerUbicacion(); spinnerControl(); spinnerFijacion(); spinnerCorredera(); spinnerAgpto();
        Bundle oExt = getIntent().getExtras();
        setTitle(oExt.getString("nombre"));
        final int idResidencial = Integer.parseInt(oExt.getString("idResidencial")); idR=""+idResidencial;
        final int idDisp = Integer.parseInt(oExt.getString("idDisp")); idD=""+idDisp;
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        String Piso = oExt.getString("Piso");
        String MedidaSugerida = oExt.getString("MedidaSugerida");
        String ProfMarco = oExt.getString("ProfMarco");
        String ProfJaladera = oExt.getString("ProfJaladera");
        String Observaciones = oExt.getString("Observaciones");
        String A = oExt.getString("A");
        String B = oExt.getString("B");
        String C = oExt.getString("C");
        String D = oExt.getString("D");
        String E = oExt.getString("E");
        String F = oExt.getString("F");
        String G = oExt.getString("G");
        String H = oExt.getString("H");
        final Spinner txtUbicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
        final EditText txtPiso = (EditText) this.findViewById(R.id.txtPiso);
        final EditText txtA = (EditText) this.findViewById(R.id.txtA);
        final EditText txtB = (EditText) this.findViewById(R.id.txtB);
        final EditText txtC = (EditText) this.findViewById(R.id.txtC);
        final EditText txtD = (EditText) this.findViewById(R.id.txtD);
        final EditText txtE = (EditText) this.findViewById(R.id.txtE);
        final EditText txtF = (EditText) this.findViewById(R.id.txtF);
        final EditText txtG = (EditText) this.findViewById(R.id.txtG);
        final EditText txtH = (EditText) this.findViewById(R.id.txtH);
        final EditText txtProfMarco = (EditText) this.findViewById(R.id.txtProfMarco);
        final EditText txtProfJaladera = (EditText) this.findViewById(R.id.txtProfJaladera);
        final Spinner txtControl = (Spinner) this.findViewById(R.id.spinner_control);
        final Spinner txtAgpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        final Spinner txtFijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final Spinner txtCorredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final EditText txtMedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText txtObservaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        Button BtnUbicacion = (Button) this.findViewById(R.id.crearUbicacion);
        txtPiso.setText(Piso.trim());
        txtA.setText(A.trim());
        txtB.setText(B.trim());
        txtC.setText(C.trim());
        txtD.setText(D.trim());
        txtE.setText(E.trim());
        txtF.setText(F.trim());
        txtG.setText(G.trim());
        txtH.setText(H.trim());
        txtProfMarco.setText(ProfMarco.trim());
        txtProfJaladera.setText(ProfJaladera.trim());
        txtMedidaSugerida.setText(MedidaSugerida.trim());
        txtObservaciones.setText(Observaciones.trim());

        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
        final String[][] aRes = modificarResidencial.oDB.ObtenerProyectosResidencial(String.valueOf(idResidencial), String.valueOf(idDisp), 4);
        if (aRes[0][19].length() >50){
            crearImagen(aRes[0][19]); foto.setText("IMG_Residencial" +idResidencial+idDisp); foto.setTextColor(Color.rgb(92, 184, 92));
        }else if(aRes[0][19].length() <5){
            foto.setText("Imagen no disponible"); foto.setTextColor(Color.rgb(204,85,85));
        }else if(aRes[0][19].length() > 5 && aRes[0][19].length() < 50){
            foto.setText("No es posible cargar la imagen"); foto.setTextColor(Color.rgb(92, 184, 92)); DescargarImagen=1;
        }

        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Ubicacion = txtUbicacion.getSelectedItem().toString();
                String Piso = txtPiso.getText().toString();
                String MedidaSugerida = txtMedidaSugerida.getText().toString();
                Double ProfMarco = Double.parseDouble(txtProfMarco.getText().toString());
                Double ProfJaladera = Double.parseDouble(txtProfJaladera.getText().toString());
                String Control = txtControl.getSelectedItem().toString();
                String Agpto = txtAgpto.getSelectedItem().toString();
                String Corredera = txtCorredera.getSelectedItem().toString();
                String Observaciones = txtObservaciones.getText().toString();
                String Fijacion = txtFijacion.getSelectedItem().toString();
                Double A = Double.parseDouble(txtA.getText().toString());
                Double B = Double.parseDouble(txtB.getText().toString());
                Double C = Double.parseDouble(txtC.getText().toString());
                Double D = Double.parseDouble(txtD.getText().toString());
                Double E = Double.parseDouble(txtE.getText().toString());
                Double F = Double.parseDouble(txtF.getText().toString());
                Double G = Double.parseDouble(txtG.getText().toString());
                Double H = Double.parseDouble(txtH.getText().toString());
                if (Integer.parseInt(aRes[0][37]) == 1){
                    oDB.updateProyectoResidencial(idResidencial, idDisp, Ubicacion, A, B, C, D , E, F, G, H, ProfMarco, ProfJaladera, Control, Agpto, MedidaSugerida, imagen, Observaciones, Fijacion, Piso, Corredera, 1);
                }else {
                    oDB.updateProyectoResidencial(idResidencial, idDisp, Ubicacion, A, B, C, D , E, F, G, H, ProfMarco, ProfJaladera, Control, Agpto, MedidaSugerida, imagen, Observaciones, Fijacion, Piso, Corredera, 2);}

                finish();
            }
        });

        BtnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(modificarResidencial.this, crearUbicacion.class);
                startActivity(rIntent);
            }
        });

        nombreImagen=""+idResidencial+idDisp;
        Button Imagenes = ((Button) this.findViewById(R.id.TomarFoto));
        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);

        Imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new Dialog(modificarResidencial.this, R.style.Theme_Dialog_Translucent);
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
            /*
            customDialog = new Dialog(modificarResidencial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
             */
            customDialog = new Dialog(modificarResidencial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
        }
        if(id == R.id.DescargarImg){
            Log.v("[add]",""+DescargarImagen);
            if(DescargarImagen==1){
                BajarImagen();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void BajarImagen(){
        String nombre = "IMG_Residencial"+nombreImagen;
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "SE HA DESCARGADO LA IMAGEN!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
            }
        });
        boolean aux = isOnlineNet();
        if (aux != false){
            Log.v("[add]","Bajar Imagen");
            oNS.execute("Bajar_Imagen", nombre, idR, idD, "1");
        }

    }
    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com ");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            /* TODO Auto-generated catch block* */
            e.printStackTrace();
        }
        return false;
    }

    public void spinnerUbicacion(){
        Bundle oExt = getIntent().getExtras();
        String Ubicacion = oExt.getString("Ubicacion");
        String[][] aRes= modificarResidencial.oDB.ObtenerUbicacion("0",1);
        spUbicacionR= (Spinner)( findViewById(R.id.spinner_ubicacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Ubicacion;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][2]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spUbicacionR.setAdapter(adapter);
    }

    public void spinnerControl(){
        Bundle oExt = getIntent().getExtras();
        String Control = oExt.getString("Control");
        String[][] aRes= modificarResidencial.oDB.ObtenerControl("0",1);
        spControlR= (Spinner)( findViewById(R.id.spinner_control));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Control;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spControlR.setAdapter(adapter);
    }

    public void spinnerFijacion(){
        Bundle oExt = getIntent().getExtras();
        String Fijacion = oExt.getString("Fijacion");
        String[][] aRes= modificarResidencial.oDB.ObtenerFijacion("0",1);
        spFijacionR= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Fijacion;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionR.setAdapter(adapter);
    }

    public void spinnerCorredera(){
        Bundle oExt = getIntent().getExtras();
        String Corredera = oExt.getString("Corredera");
        String[][] aRes= modificarResidencial.oDB.ObtenerCorredera("0",1);
        spCorrederaR= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Corredera;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);  //aData[i+1] = ("Valor " +i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaR.setAdapter(adapter);
    }

    public void spinnerAgpto(){
        Bundle oExt = getIntent().getExtras();
        String Agpto = oExt.getString("Agpto");
        String[][] aRes= modificarResidencial.oDB.ObtenerControl("0",1);
        spAgptoR= (Spinner)( findViewById(R.id.spinner_agpto));
        final String[] aData = new String[aRes.length+1];
        aData[0]=Agpto;
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spAgptoR.setAdapter(adapter);
    }

    //Funciones para Camara
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
        if (Build.VERSION.SDK_INT == 23) {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, 1);
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
                    foto.setText("IMG_Residencial" +nombreImagen);
                }
            }
            else {
                if (resultCode == RESULT_OK){
                    ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(),400,400);
                    imagen = convertToBase64(bit_map);
                    TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);
                    foto.setText("IMG_Residencial" +nombreImagen);
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
