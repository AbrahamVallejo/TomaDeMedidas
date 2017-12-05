package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class modificarResidencial extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "droidBH";
    private Uri fileUri;
    String sID;
    String imagen = "";

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
        final int idResidencial = Integer.parseInt(oExt.getString("idResidencial"));
        final int idDisp = Integer.parseInt(oExt.getString("idDisp"));
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
                oDB.updateProyectoResidencial(idResidencial, idDisp, Ubicacion, A, B, C, D , E, F, G, H,
                        ProfMarco, ProfJaladera, Control, Agpto, MedidaSugerida, imagen, Observaciones, Fijacion, Piso, Corredera, 2);
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


        Button botonCamara = ((Button) this.findViewById(R.id.TomarFoto));
        ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);
        final TextView foto = (TextView) this.findViewById(R.id.TV_Imagen);

        if(savedInstanceState != null)
        {   fileUri = savedInstanceState.getParcelable("uri");
            if(fileUri != null)
            {   Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(savedInstanceState.getString("foto"),200,200);
                oImg.setImageBitmap(bit_map);
                foto.setText(imagen);   }
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
        return super.onOptionsItemSelected(item);
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
                ImageView oImg = (ImageView) this.findViewById(R.id.imgFoto);//
                Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(), 200, 200);
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

    private String convertToBase64(Bitmap imagenMap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagenMap.compress(Bitmap.CompressFormat.JPEG, 45, baos);
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

}
