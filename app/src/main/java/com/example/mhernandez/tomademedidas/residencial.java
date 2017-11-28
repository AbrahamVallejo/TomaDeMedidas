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

public class residencial extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "droidBH";
    private Uri fileUri;
    String sID;
    String imagen = "";

    private Spinner spUbicacionR, spFijacionR, spControlR, spCorrederaR, spAgptoR;
    public static DBProvider oDB;
    public residencial() {oDB = new DBProvider(this);}
    Dialog customDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_medida_residencial);
        //Llenar los spinner
        spinnerCorredera(); spinnerControl();   spinnerFijacion();  spinnerAgpto(); spinnerUbicacion();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle oExt = this.getIntent().getExtras();
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

        final Spinner Ubicacion = (Spinner) this.findViewById(R.id.spinner_ubicacion);
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
        final EditText MedidaSugerida = (EditText) this.findViewById(R.id.txtMedidaSugerida);
        final EditText Observaciones = (EditText) this.findViewById(R.id.txtObservaciones);
        final Spinner Fijacion = (Spinner) this.findViewById(R.id.spinner_fijacion);
        final EditText Piso = (EditText) this.findViewById(R.id.txtPiso);
        final Spinner Corredera = (Spinner) this.findViewById(R.id.spinner_corredera);
        final Spinner Agpto = (Spinner) this.findViewById(R.id.spinner_agpto);
        Button Guardar = (Button) this.findViewById(R.id.Guardar);
        final Button crearUbicacion = (Button) this.findViewById(R.id.crearUbicacion);
        crearUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent(residencial.this, crearUbicacion.class);
                startActivity(rIntent);
            }
        });
        Guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txtUbicacion = Ubicacion.getSelectedItem().toString();
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
                        String txtMedidaSugeria = MedidaSugerida.getText().toString();
                        String txtObservaciones = Observaciones.getText().toString();
                        String txtFijacion = Fijacion.getSelectedItem().toString();
                        String txtPiso = Piso.getText().toString();
                        String txtCorredera = Corredera.getSelectedItem().toString();
                        String txtAgpto = Agpto.getSelectedItem().toString();
                        String[][] aRefD = MainActivity.oDB.lastDispositivo();
                        String[][] aRefP = MainActivity.oDB.lastProyecto();
                        String[][] aRefR = MainActivity.oDB.lastResidencial();
                        int idProyecto = Integer.parseInt(aRefP[(0)][0]) + 1;
                        int idResidencial = Integer.parseInt(aRefR[(0)][0]) + 1;
                        int idDisp = Integer.parseInt(aRefD[(0)][0]);
                        oDB.insertProyecto(idProyecto, idDisp, idCliente, idclienteDisp, idFormato, usuario, nombreProyecto, PedidoSap, FechaAlta,
                                           0, accesoriosTecho, accesoriosMuro, accesoriosEspecial, 1, idUsuarioVenta, Agente, 1);
                        oDB.insertProyectoResidencial(idResidencial, idDisp, idProyecto, idDisp, txtUbicacion, txtA, txtB, txtC, txtD,
                                txtE, txtF, txtG, txtH, txtProfMarco, txtProfJaladera, txtControl, txtAgpto, txtMedidaSugeria, txtObservaciones,
                                "IMAGEN", nombreProyecto, FechaAlta, idFormato, FechaAlta, 1, txtFijacion, txtPiso, 0, 0, 0, txtCorredera);
                        Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(rIntent);
                    }
                }
        );

        Button botonCamara = ((Button) this.findViewById(R.id.TomarFoto));
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
            customDialog = new Dialog(residencial.this, R.style.Theme_Dialog_Translucent);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.referencia_medida_ventana);
            customDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void spinnerCorredera(){
        String[][] aRes= residencial.oDB.ObtenerCorredera("0",1);
        spCorrederaR= (Spinner)( findViewById(R.id.spinner_corredera));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione uno...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spCorrederaR.setAdapter(adapter);
    }

    public void spinnerUbicacion(){
        String[][] aRes= residencial.oDB.ObtenerUbicacion("0",1);
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
        String[][] aRes= residencial.oDB.ObtenerControl("0",1);
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
        String[][] aRes= residencial.oDB.ObtenerFijacion("0",1);
        spFijacionR= (Spinner)( findViewById(R.id.spinner_fijacion));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
        for(int i = 0; i < aRes.length; i++){
            aData[i+1] = (aRes[i][1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.simple_spinner_item,aData);
        spFijacionR.setAdapter(adapter);
    }

    public void spinnerAgpto(){
        String[][] aRes= residencial.oDB.ObtenerControl("0",1);
        spAgptoR= (Spinner)( findViewById(R.id.spinner_agpto));
        final String[] aData = new String[aRes.length+1];
        aData[0]="Seleccione un lado...";
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
