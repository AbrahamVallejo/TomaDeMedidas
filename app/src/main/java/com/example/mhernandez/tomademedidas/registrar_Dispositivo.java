package com.example.mhernandez.tomademedidas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tloaiza on 13/10/2017.
 */

public class registrar_Dispositivo extends AppCompatActivity {

    public static DBProvider oDB;
    public int internet = 0;
    boolean aux1 =false;
    public registrar_Dispositivo() { oDB = new DBProvider(this);}


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Quitar Barra de Notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.crear_dispositivo);
        Bundle oExt = this.getIntent().getExtras();
        internet = oExt.getInt("internet");
        if(internet == 1) {
            Toast.makeText(getApplicationContext(), "No hay internet!", Toast.LENGTH_LONG).show();
        }else {
            aux1=true;
        }
    }

    public void onSaveClickDispositivo(View view){
        Log.v("[obtener]", "Internet: " +internet +" Conexion="+aux1);
        if (aux1==true){
            //getdispositivosLista();
            EditText nDisp = (EditText) this.findViewById(R.id.nDispositivo);
            EditText nUser = (EditText) this.findViewById(R.id.nUsuario);

            String part1 = nDisp.getText().toString().replace(" ","");
            String part2 = nUser.getText().toString().replace(" ","");
            int aux=0;
            if(part1.length()==0){
                aux++; nDisp.setText(""); nDisp.setHint("Campo Vacío");
            }
            if(part2.length()==0){
                aux++; nUser.setText(""); nUser.setHint("Campo Vacío");
            }
            if(aux==0){
                    adddispositivos(nDisp.getText().toString(),nUser.getText().toString());
                    finish();
                    Intent intent = new Intent(registrar_Dispositivo.this, log_in.class);
                    startActivity(intent);

            }else {
                Toast.makeText(this, "VERIFIQUE SU CAPTURA", Toast.LENGTH_SHORT).show(); }
        }else {
            Toast.makeText(getApplicationContext(), "Requiere Internet!", Toast.LENGTH_LONG).show();
            aux1= isOnlineNet();
        }
    }

    public void adddispositivos(String dispositivo, String usuario){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("adddispositivos", dispositivo, usuario);
    }

    public void getdispositivosLista(){
        NetServices oNS = new NetServices(new OnTaskCompleted() {
            @Override
            public void OnTaskCompleted(Object freed) {
                /*Toast.makeText(getApplicationContext(),
                        "TODO PERFECTO EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void OnTaskError(Object feed) {
                Toast.makeText(getApplicationContext(),
                        "OCURRIO UN ERROR EN EL WEB SERVICES!",
                        Toast.LENGTH_LONG).show();
            }
        });
        oNS.execute("getdispositivosLista");
    }

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0); //Log.v("[obtener]",String.valueOf(reachable) );
            return true;
        } catch (Exception e) {
            /* TODO Auto-generated catch block* */
            e.printStackTrace();
        }
        return false;
    }

}


//Log.v("[obtener]",part1); Log.v("[obtener]",part2);Log.v("[obtener]",part3);
//if (!nombre.getText().toString().isEmpty() && !telefono.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty()){
/*String[][] aRef = entrada_inicio.oDB.lastDispositivo();
                int ID;
                Log.v("[obtener]", "Traigo un: "+ aRef[0][0].toString());
                if(aRef[0][0].toString() != "0"){
                    ID = Integer.parseInt(aRef[(0)][0]) +1;;
                }else {ID=0;}

String[] partNom = part[1].split(":");
                partNom[1] = partNom[1].replace('"',' '); partNom[1] = partNom[1].replace(" ","");
                String[] partAct = part[2].split(":");
                String[] partUs = part[3].split(":");
                partUs[1] = partUs[1].replace('"',' '); partUs[1] = partUs[1].replace(" ","");
                String[] partFue = part[4].split(":");
                partFue[1] = partFue[1].replace("}","");
+" "+partNom[1]+" "+partAct[1]+" "+partUs[1]+" "+partFue[1]*/
