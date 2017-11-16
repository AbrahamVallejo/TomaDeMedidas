package com.example.mhernandez.tomademedidas;

import android.app.Dialog;
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
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Fragment_clientes.OnFragmentInteractionListener,
        Fragment_proyecto.OnFragmentInteractionListener, Fragment_listaProyecto.OnFragmentInteractionListener,
        Fragment_listaClientes.OnFragmentInteractionListener{

    public static DBProvider oDB;
    int UBICACION=0;
    Dialog customDialog = null;
    int miProgreso = 0;

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
        //Quitar Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
        Menu menu = navigationView.getMenu();
        MenuItem Cliente = menu.findItem(R.id.Cliente);
        MenuItem Proyecto = menu.findItem(R.id.Proyecto);
        SpannableString C = new SpannableString(Cliente.getTitle());
        SpannableString P = new SpannableString(Proyecto.getTitle());
        C.setSpan(new TextAppearanceSpan(this, R.style.MenuStyle), 0, C.length(), 0);
        P.setSpan(new TextAppearanceSpan(this, R.style.MenuStyle), 0, P.length(), 0);
        Cliente.setTitle(C);
        Proyecto.setTitle(P);
        navigationView.setNavigationItemSelectedListener(this);

        MainActivity.oDB.ObtenerCorredera("0",1);
        //MainActivity.oDB.ObtenerProyectosEspecial("0",1);


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
            String[][] aRefD = MainActivity.oDB.lastDispositivo();
            int idDisp = Integer.parseInt(aRefD[(0)][0]);

            MainActivity.oDB.insertCliente(ID, idDisp, nombre.getText().toString(), telefono.getText().toString(), direccion.getText().toString(),1);
            nombre.setText(""); telefono.setText(""); direccion.setText("");
            Toast.makeText(this, "CLIENTE AGREGADO", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
    }

    public void onSaveClickProyectos(View view){
        int Validar=0; int idC=0; int idCD = 0;
        Spinner cliente = (Spinner) this.findViewById(R.id.spinner_cliente);
            if(cliente.getSelectedItemPosition() != 0){
                String[] partsC = cliente.getSelectedItem().toString().split("-");
                partsC[0] = partsC[0].replace(".","-");
                String[] Clientes = partsC[0].split("-");    Clientes[1]= Clientes[1].trim();
                idC = Integer.parseInt(Clientes[0].toString()); idCD = Integer.parseInt(Clientes[1].toString());
            }else { Validar++; }

        Spinner formato = (Spinner) this.findViewById(R.id.spinner_formato);
            if (formato.getSelectedItemPosition()==0) { Validar++; }

        Spinner agente = (Spinner) this.findViewById(R.id.spinner_agente);      String[] partsA = new String[2];
            if(agente.getSelectedItemPosition() != 0){
                partsA = agente.getSelectedItem().toString().split("-");
            }else { Validar++; }

        EditText proyecto = (EditText) this.findViewById(R.id.proyecto_nombre_proyecto);
        EditText AccMuro = (EditText) this.findViewById(R.id.proyecto_accesorios_muro);
        EditText AccTecho = (EditText) this.findViewById(R.id.proyecto_accesorios_techo);
        EditText AccEspecial = (EditText) this.findViewById(R.id.proyecto_accesorios_especiales);
        EditText PedidoSap = (EditText) this.findViewById(R.id.proyecto_pedido_sap);
        String nombreProyecto = proyecto.getText().toString();
            String part1 = nombreProyecto.replace(" ","");
            if (part1.length() ==0) { Validar++; }
        String accesorioMuro = AccMuro.getText().toString();
            String part2 = accesorioMuro.replace(" ","");
            if (part2.length() ==0) { Validar++; }
        String accesorioTecho = AccTecho.getText().toString();
            String part3 = accesorioTecho.replace(" ","");
            if (part3.length() ==0) { Validar++; }
        String accesorioEspecial = AccEspecial.getText().toString();
            String part4 = accesorioEspecial.replace(" ","");
            if (part4.length() ==0) { Validar++; }
        String PS = PedidoSap.getText().toString();
            if (PS.length()==0){PS ="S/P";}
        Date FechaAlta = new Date();  Log.v("[spin]","/Date("+FechaAlta.getTime()+")/");

        int selected = formato.getSelectedItemPosition(); Log.v("[spin]", "Sel: "+selected );
        Intent rIntent = new Intent();
            if (selected == 2){
                rIntent = new Intent(MainActivity.this, hoteleria.class);
            }else if (selected == 4){
                rIntent = new Intent(MainActivity.this, cama.class);
            }else if (selected ==1){
                rIntent = new Intent(MainActivity.this, residencial.class);
            }else if (selected == 3){
                rIntent = new Intent(MainActivity.this, galeria.class);
            }else if(selected ==5){
                rIntent = new Intent(MainActivity.this, especial.class); }
        rIntent.putExtra("idFormato", selected);
        rIntent.putExtra("nombreProyecto", nombreProyecto);
        rIntent.putExtra("accesoriosMuro", accesorioMuro);
        rIntent.putExtra("accesoriosTecho", accesorioTecho);
        rIntent.putExtra("accesoriosEspecial", accesorioEspecial);
        rIntent.putExtra("FechaAlta", "/Date("+FechaAlta.getTime()+")/");
        rIntent.putExtra("PedidoSap", PS);
        rIntent.putExtra("Agente", partsA[1] );
        rIntent.putExtra("id_cliente", idC );
        rIntent.putExtra("id_cliente_disp", idCD );
        rIntent.putExtra("idUsuarioVenta",Integer.parseInt(partsA[1]) );
            if (Validar ==0){
                startActivity(rIntent);
            }else {
                Toast.makeText(this, "Llene Todos Los Campos", Toast.LENGTH_SHORT).show(); }

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
        //customDialog = new Dialog(getApplicationContext(), R.style.Theme_Dialog_Translucent);
        //customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //noinspection SimplifiableIfStatement
        if (id == R.id.sincronizar) {
            Sincronizar();
        }
        else if (id == R.id.descargar) {
            descargarWS();
        }
        else if (id == R.id.cerrarSesion) {
            String[][] users = MainActivity.oDB.ObtenerUser("0",3);
            MainActivity.oDB.recordarUser(Integer.parseInt(users[0][0]), 0, 0);
            Intent rIntent = new Intent(this, log_in.class);
            finish();
            startActivity(rIntent);
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
            fragment = new Fragment_clientes(); UBICACION=1;
        } else if (id == R.id.NuevoProyecto){
            FragmentTransaction = true;
            fragment = new Fragment_proyecto(); UBICACION=3;
        } else if (id == R.id.EditarCliente){
            FragmentTransaction = true;
            fragment = new Fragment_listaClientes(); UBICACION=2;
        } else if (id == R.id.EditarProyecto){
            FragmentTransaction = true;
            fragment = new Fragment_listaProyecto(); UBICACION=4;
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
    public void getproyectoLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "WEB SERVICES PROYECTO!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PROYECTO!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getproyectoLista");
    }

    public void getclienteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CLIENTE!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getclienteLista");
    }

    public void getusuarioLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES USUARIO!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getusuarioLista");
    }

    public void getfijacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES FIJACIÓN!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getfijacionLista");
    }

    public void getproyeccionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PROYECCIÓN!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getproyeccionLista");
    }

    public void getcopeteLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES COPETE!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getcopeteLista");
    }

    public void getcontrolLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CONTROL!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getcontrolLista");
    }

    public void getcorrederaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES CORREDERA!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getcorrederaLista");
    }

    public void getformatoLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES FORMATO!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getformatoLista");
    }

    public void getubicacionLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES UBICACIÓN!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getubicacionLista");
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

    public void getproyectoResidencialLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(rIntent);
                Toast.makeText(getApplicationContext(), "Descarga Completa", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PRO-Residencial!", Toast.LENGTH_LONG).show();
                Intent rIntent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(rIntent);
                Toast.makeText(getApplicationContext(), "Descarga Completa", Toast.LENGTH_SHORT).show();
            }
        });
        oNS.execute("getproyecto_residencialLista");
    }

    public void getproyectoHoteleriaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES PRO-HOTEL!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PRO-HOTEL!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }
        });
        oNS.execute("getproyecto_hoteleriaLista");
    }

    public void getproyectoEspecialLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES PRO-ESP!", Toast.LENGTH_LONG).show();
                miProgreso++;
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PRO-ESP!", Toast.LENGTH_LONG).show();
                miProgreso++;
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
                //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES PRO-GALERIA!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PRO-GALERIA!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_galeriaLista");
    }

    public void getproyectoCamaLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                //Toast.makeText(getApplicationContext(), "WEB SERVICES PROYECTO-CAMA!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES PRO-CAMA!", Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getproyecto_camaLista");
    }


    public void Sincronizar(){
        Fragment fragment;
        boolean aux = isOnlineNet();
        if (aux != false) {
            if(UBICACION==2 || UBICACION ==1){
                Sincliente();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if(UBICACION==4 || UBICACION ==3){
                SinProyecto();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        }
        else {
            Toast.makeText(this, "Requiere Acceso a Internet", Toast.LENGTH_LONG).show();
        }
    }

    public void Sincliente(){
        String[][] aux1 = MainActivity.oDB.ObtenerClientes("0",2);
        for (int i =0; i < aux1.length; i++) {
            if (Integer.valueOf(aux1[i][5]) == 1) {
                Log.v("[add]","Entre al if Insert" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                        //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(),
                                "ERROR EN EL WEB SERVICES AddCliente!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("addcliente", aux1[i][0], aux1[i][1], aux1[i][2], aux1[i][4], aux1[i][3]);
            }
            if (Integer.valueOf(aux1[i][5]) == 2) {
                Log.v("[add]","Entre al if Modify" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                        //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(),"ERROR EN EL WEB SERVICES ModificarCliente!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("modifycliente", aux1[i][0], aux1[i][1], aux1[i][2], aux1[i][4], aux1[i][3]);
            }
            if (Integer.valueOf(aux1[i][5]) == 3) {
                Log.v("[add]","Entre al if Eliminar" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                        //Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(),"ERROR EN EL WEB SERVICES EliminarCliente!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("deletecliente", aux1[i][0], aux1[i][1], aux1[i][2], aux1[i][4], aux1[i][3]);
            }
        }
        Toast.makeText(this, "Clientes Sincronizados", Toast.LENGTH_SHORT).show();

    }

    public void SinProyecto(){
        String[][] aux1 = MainActivity.oDB.ObtenerProyectos("0",2);
        Log.v("[add]","Tengo: "+aux1.length );

        for (int i =0; i < aux1.length; i++) {
            if (Integer.valueOf(aux1[i][23]) == 1) {
                Log.v("[add]","Entre al if Insertar" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();*/
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES ADDPROYECTO!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("sincproyecto", "1", aux1[i][0], aux1[i][1] );
            }
            else if (Integer.valueOf(aux1[i][23]) == 2) {
                Log.v("[add]","Entre al if Modificar" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();*/
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES ADDPROYECTO!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("sincproyecto", "2", aux1[i][0], aux1[i][1] );
            }
            else if (Integer.valueOf(aux1[i][23]) == 3) {
                Log.v("[add]","Entre al if Eliminar" );
                NetServices oNS = new NetServices(new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(), "TODO PERFECTO EN EL WEB SERVICES!", Toast.LENGTH_LONG).show();*/
                    }
                    @Override
                    public void OnTaskError(Object feed) {
                        Toast.makeText(getApplicationContext(), "ERROR EN EL WEB SERVICES ADDPROYECTO!", Toast.LENGTH_LONG).show();
                    }
                });
                oNS.execute("sincproyecto", "3", aux1[i][0], aux1[i][1] );
            }
        }
        Toast.makeText(this, "Proyectos Sincronizados", Toast.LENGTH_SHORT).show();
    }

    public void descargarWS(){
        String[][] aux1 = MainActivity.oDB.ObtenerClientes("0",2);
        String[][] aux2 = MainActivity.oDB.ObtenerProyectos("0",2);
        int var = aux2.length;

        if(aux1.length >= 1){
            Toast.makeText(this, "Sincronización Clientes Requerida", Toast.LENGTH_LONG).show();
        }
        else if(var >= 1) {
            Toast.makeText(this, "Sincronización Proyectos Requerida", Toast.LENGTH_LONG).show();
        }else{
            boolean aux = isOnlineNet();
            if (aux != false) {         //customDialog.setContentView(R.layout.activity_cargando);
                setContentView(R.layout.activity_cargando);

                MainActivity.oDB.deleteAllCliente("0","0");
                MainActivity.oDB.deleteAllProyectos("0","0");
                MainActivity.oDB.deleteAllProyectosCama("0","0");
                MainActivity.oDB.deleteAllProyectosEspecial("0","0");
                MainActivity.oDB.deleteAllHoteleria("0","0");
                MainActivity.oDB.deleteAllProyectosGaleria("0","0");
                MainActivity.oDB.deleteAllResidencial("0","0");

                Thread timerThread = new Thread(){
                    public void run(){
                        try {
                            sleep(1000);
                                getclienteLista();
//                                getproyectoLista();
                                getproyectoCamaLista();
                                getproyectoEspecialLista();
                                getproyectoHoteleriaLista();
                                getproyectoGaleriaLista();
                                getproyectoResidencialLista();
                        }catch (InterruptedException e){
                            e.printStackTrace();       }
                    }
                };
                timerThread.start();

                Thread timerThreadDos = new Thread(){
                    public void run(){
                        try {
                            sleep(1000);
                            getcopeteLista();       getformatoLista();
                            getfijacionLista();     getproyeccionLista();
                            getubicacionLista();    getcontrolLista();
                            getcorrederaLista();    getusuarioLista();
                        }catch (InterruptedException e){
                            e.printStackTrace();       }
                    }
                };
                timerThreadDos.start();

            }else {
                Toast.makeText(this, "Requiere Acceso a Internet", Toast.LENGTH_LONG).show();       }
        }
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
        boolean FragmentTransaction = true;
        Fragment fragment = new Fragment_listaClientes();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();

        Dialog customDialog = new Dialog(this.getApplicationContext(), R.style.Theme_Dialog_Translucent);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.cargando);

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
                    return null;}
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

//GregorianCalendar currentTime = new GregorianCalendar();
//String FechaAlta = currentTime.get(GregorianCalendar.YEAR) +"-"+ (currentTime.get(GregorianCalendar.MONTH)+1) +"-"+currentTime.get(GregorianCalendar.DAY_OF_MONTH);
//FechaAlta = FechaAlta +" "+ currentTime.get(GregorianCalendar.HOUR_OF_DAY) +":"+ currentTime.get(GregorianCalendar.MINUTE) +":"+ currentTime.get(GregorianCalendar.SECOND);