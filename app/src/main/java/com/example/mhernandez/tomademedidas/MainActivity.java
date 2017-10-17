package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Fragment_clientes.OnFragmentInteractionListener, Fragment_proyecto.OnFragmentInteractionListener, Fragment_listaProyecto.OnFragmentInteractionListener, Fragment_listaClientes.OnFragmentInteractionListener{

    public static DBProvider oDB;

    public MainActivity() { oDB = new DBProvider(this);}

    /*
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;
    private static final String APP_PATH = "droidBH";
    private Uri fileUri;
    String sID;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainActivity.oDB.ObtenerClientes("0",1);
        boolean aux = isOnlineNet();
        if (aux != false){
            getclienteLista();
            getproyectoLista();
        }
        else {
            Toast.makeText(this, "Sin Acceso a la Red", Toast.LENGTH_SHORT).show();
        }
        //oDB.insertCliente(150, 1, "Aaron", "12340183", "Direccion"); //oDB.insertCliente(0, 2, "Mario", "13245768", "Dues");  //oDB.updateCliente( "1", "1", "Modificado", "Modificado", "Modificación");
            //getproyectoCamaLista();
        //Intent rIntent = new Intent(this, registrar_Dispositivo.class);
        //startActivity(rIntent);
    }

    public void onSaveClickClientes(View view){
        EditText nombre = (EditText) this.findViewById(R.id.txt_cliente_nombre);
        EditText telefono = (EditText) this.findViewById(R.id.txt_cliente_telefono);
        EditText direccion = (EditText) this.findViewById(R.id.txt_cliente_direccion);

        String part1 = nombre.getText().toString().replace(" ","");
        String part2 = telefono.getText().toString().replace(" ","");
        String part3 = direccion.getText().toString().replace(" ","");   //Log.v("[obtener]",part1); Log.v("[obtener]",part2);Log.v("[obtener]",part3);
        int aux=0;
        if(part1.length()==0){
            aux++; nombre.setText(""); nombre.setHint("Campo Vacío");
        }
        if(part2.length()==0){
            aux++; telefono.setText(""); telefono.setHint("Campo Vacío");
        }
        if(part3.length()==0){
            aux++; direccion.setText(""); direccion.setHint("Campo Vacío");
        }
        if(aux==0){                                                                                 //if (!nombre.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty()){
            String[][] aRef = MainActivity.oDB.lastCliente();
            int ID;
            Log.v("[obtener]", "Traigo un: "+ aRef[0][0].toString());
                if(aRef[0][0].toString() == "0"){
                    ID = 1;
                }else {
                    ID = Integer.parseInt(aRef[(0)][0]) +1; }
            MainActivity.oDB.insertCliente(ID, 4, nombre.getText().toString(), telefono.getText().toString(), direccion.getText().toString() );
            nombre.setText(""); telefono.setText(""); direccion.setText("");
            Toast.makeText(this, "CLIENTE AGREGADO", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
    }

    public void onSaveClickProyectos(View view){
        Spinner cliente = (Spinner) this.findViewById(R.id.spinner_cliente);
        Spinner formato = (Spinner) this.findViewById(R.id.spinner_formato);
        Spinner agente = (Spinner) this.findViewById(R.id.spinner_agente);
        EditText proyecto = (EditText) this.findViewById(R.id.proyecto_nombre_proyecto);
        EditText AccMuro = (EditText) this.findViewById(R.id.proyecto_accesorios_muro);
        EditText AccTecho = (EditText) this.findViewById(R.id.proyecto_accesorios_techo);
        EditText AccEspecial = (EditText) this.findViewById(R.id.proyecto_accesorios_especiales);
        EditText PedidoSap = (EditText) this.findViewById(R.id.proyecto_pedido_sap);

        String selected = formato.getSelectedItem().toString();
        int formatoSelected = 0;

        if (selected.equals("Hoteleria")){
            formatoSelected = 1;
        }else if (selected.equals("Cama")){
            formatoSelected = 2;
        }else if (selected.equals("Residencial")){
            formatoSelected = 3;
        }else if (selected.equals("Galeria")){
            formatoSelected = 4;
        }else if(selected.equals("Especial")){
            formatoSelected = 5;
        }

        //MainActivity.oDB.insertProyecto(formatoSelected);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean FragmentTransaction = false;
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.NuevoCliente){
            FragmentTransaction = true;
            fragment = new Fragment_clientes();
        } else if (id == R.id.NuevoProyecto){
            FragmentTransaction = true;
            fragment = new Fragment_proyecto();
        } else if (id == R.id.EditarCliente){
            FragmentTransaction = true;
            fragment = new Fragment_listaClientes();
        } else if (id == R.id.EditarProyecto){
            FragmentTransaction = true;
            fragment = new Fragment_listaProyecto();
    }

        if (FragmentTransaction){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            item.setCheckable(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*  Funciones Net Services */
    public void PostHojasList(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("gethojasLista");
    }

    public void getfijacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getfijacionLista");
    }

    public void getcopeteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getcopeteLista");
    }

    public void getcontrolLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getcontrolLista");
    }

    public void getclienteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getclienteLista");
    }

    public void getformatoLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getformatoLista");
    }

    public void getusuarioLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getusuarioLista");
    }

    public void getestatusLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getestatusLista");
    }


    public void getenviardatosLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getenviardatosLista");
    }

    public void getproyectoLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyectoLista");
    }

    public void getTipoImagenLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getTipoImagenLista");
    }

    public void gettipousuarioLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("gettipousuarioLista");
    }

    public void getproyeccionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getubicacionLista");
    }

    public void getubicacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getubicacionLista");
    }

    public void getproyectoResidencialLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_residencialLista");
    }

    public void getproyectoHoteleriaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_hoteleriaLista");
    }

    public void getproyectoEspecialLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_especialLista");
    }

    public void getproyectoImagenLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_imagenLista");
    }

    public void getproyectoGaleriaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_galeriaLista");
    }

    public void getproyectoCamaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_camaLista");
    }


    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0); Log.v("[obtener]",String.valueOf(reachable) );
            return true;
        } catch (Exception e) {
            /* TODO Auto-generated catch block* */
            e.printStackTrace();
        }
        return false;
    }
/*
        public void onFotoClick(View v){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, sID);
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

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
                if (resultCode == RESULT_OK){
                    //ImageView oImg = (ImageView)getActivity().findViewById(R.id.imgFoto);
                    Bitmap bit_map = PictureTools.decodeSampledBitmapFromUri(fileUri.getPath(), 200, 200);
                    //oImg.setImageBitmap(bit_map);
                }else if(resultCode == RESULT_CANCELED){
                    // User cancelled the image capture
                }else {
                    //Image capture failed, advise user
                }
            }
        }*/
}
