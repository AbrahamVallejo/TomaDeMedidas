package com.example.mhernandez.tomademedidas;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;



public class NetServices extends AsyncTask<String, Void, Object> {
    private static final String URL_WS1 = "http://192.168.1.47/wcfmedidas/";
    private static final String URL_WS2 = "http://192.168.1.20/imagenmedidas/";

    private static final String RUTA_IMG = "imagenmedidas/img/";
    private OnTaskCompleted listener;
    private Exception exception;

    public NetServices(OnTaskCompleted Listener){
        exception = null;
        this.listener = Listener;
    }

    private static String convertStreamtoString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static String connectPost(String pUrl, String imagen, String nombre) throws IOException{
        URL url = new URL(pUrl);

        URLConnection urlConnection = url.openConnection();
        String sRes = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            Uri.Builder foto = new Uri.Builder()
                    .appendQueryParameter("imagen",imagen)
                    .appendQueryParameter("nombre",nombre);
            String UrlParameters = foto.build().getEncodedQuery();

            writer.write(UrlParameters);
            writer.flush();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream instream = httpURLConnection.getInputStream();
                String result = convertStreamtoString(instream);
                sRes = result;
                instream.close();
            }
        } catch (Exception e){
            Log.v("[ERROR]", e.toString());
        }

        return sRes;
    }

    public static String connectGet(String pUrl, String uId, String uName) throws IOException{
        URL url = new URL(pUrl);

        URLConnection urlConnection = url.openConnection();
        String sRes = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(false);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream instream = httpURLConnection.getInputStream();
                String result = convertStreamtoString(instream);
                sRes = result;
                instream.close();
            }
        } catch (Exception e){
            Log.v("[ERROR]", e.toString());
        }

        return sRes;
    }

    public static String connectPost2(String pUrl) throws IOException{
        URL url = new URL(pUrl);

        URLConnection urlConnection = url.openConnection();
        String sRes = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            JSONObject jobject = new JSONObject();
            String urlParameters = jobject.toString();
            writer.write(urlParameters);
            writer.flush();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream instream = httpURLConnection.getInputStream();
                String result = convertStreamtoString(instream);
                sRes = result;
                instream.close();
            }
        } catch (Exception e){
            Log.v("[ERROR]", e.toString());
        }

        return sRes;
    }

    public static String connectPost3(String pUrl, String dispositivo) throws IOException{
        URL url = new URL(pUrl);

        URLConnection urlConnection = url.openConnection();
        String sRes = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
            writer.write(dispositivo);
            writer.flush();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream instream = httpURLConnection.getInputStream();
                String result = convertStreamtoString(instream);
                sRes = result;
                instream.close();
            }
        } catch (Exception e){
            Log.v("[ERROR]", e.toString());
        }

        return sRes;
    }

    @Override
    protected Object doInBackground(String... urls) {
        Object x = null;
        String sResp = "";
        if(urls[0] == "gethojasLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wshojas.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("cantidad");
                    aFujs[i] = joFuj.getString("id_hojas");
                    Log.v("PRUEBA", joFuj.getString("id_hojas"));
                    Log.v("PRUEBA", joFuj.getString("cantidad"));
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyeccionLista"){
            try{
                Log.v("[obtenerP]","Estoy en getproyeccionLista");
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyeccion.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_proyeccion");
                    aFujs[i] = joFuj.getString("estado");
                    Log.v("PRUEBA", joFuj.getString("id_proyeccion"));
                    Log.v("PRUEBA", joFuj.getString("estado")); Log.v("PRUEBA","...");
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarProyeccion(Integer.parseInt(joFuj.getString("id_proyeccion")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_proyeccion"))) {
                            MainActivity.oDB.insertProyeccion(Integer.parseInt(joFuj.getString("id_proyeccion")), joFuj.getString("estado"));
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarProyeccion(Integer.parseInt(joFuj.getString("id_proyeccion")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_proyeccion"))) {
                            registrar_Dispositivo.oDB.insertProyeccion(Integer.parseInt(joFuj.getString("id_proyeccion")), joFuj.getString("estado"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getfijacionLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsfijacion.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("estado");
                    aFujs[i] = joFuj.getString("id_fijacion");
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarFijacion(Integer.parseInt(joFuj.getString("id_fijacion")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_fijacion"))) {
                            MainActivity.oDB.insertFijacion(Integer.parseInt(joFuj.getString("id_fijacion")), joFuj.getString("estado"));
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarFijacion(Integer.parseInt(joFuj.getString("id_fijacion")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_fijacion"))) {
                            registrar_Dispositivo.oDB.insertFijacion(Integer.parseInt(joFuj.getString("id_fijacion")), joFuj.getString("estado"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getcopeteLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wscopete.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("estado");
                    aFujs[i] = joFuj.getString("id_copete");
                    Log.v("PRUEBA", joFuj.getString("id_copete"));
                    Log.v("PRUEBA", joFuj.getString("estado"));
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarCopete(Integer.parseInt(joFuj.getString("id_copete")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_copete"))) {
                            MainActivity.oDB.insertCopete(Integer.parseInt(joFuj.getString("id_copete")), joFuj.getString("estado"));
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarCopete(Integer.parseInt(joFuj.getString("id_copete")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_copete"))) {
                            registrar_Dispositivo.oDB.insertCopete(Integer.parseInt(joFuj.getString("id_copete")), joFuj.getString("estado"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getcontrolLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wscontrol.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("estado");
                    aFujs[i] = joFuj.getString("id_control");
                    Log.v("PRUEBA", joFuj.getString("estado"));
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarControl(Integer.parseInt(joFuj.getString("id_control")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_control"))) {
                            MainActivity.oDB.insertControl(Integer.parseInt(joFuj.getString("id_control")), joFuj.getString("estado"));
                        }
                    }else {
                        String[][] aRef = registrar_Dispositivo.oDB.buscarControl(Integer.parseInt(joFuj.getString("id_control")));//Log.v("[obtener]",aRef[0][0]);
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_control"))) {
                            registrar_Dispositivo.oDB.insertControl(Integer.parseInt(joFuj.getString("id_control")), joFuj.getString("estado"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getuserLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsuser.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id");
                    aFujs[i] = joFuj.getString("username");
                    aFujs[i] = joFuj.getString("password_hash");
                    aFujs[i] = joFuj.getString("email");
                    aFujs[i] = joFuj.getString("status");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("apellido");
                    aFujs[i] = joFuj.getString("verificacion"); //Log.v("PRUEBA", joFuj.getString("id"));Log.v("PRUEBA","...");
                    //Log.v("[obtener]",aRef[0][0]);
                        entrada_inicio.oDB.insertUser(Integer.parseInt(joFuj.getString("id")),joFuj.getString("username"),
                                joFuj.getString("password_hash"), joFuj.getString("email"), 0,
                                joFuj.getString("nombre"), joFuj.getString("apellido"), 0 );
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getdispositivosLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsdispositivos.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("activo");
                    aFujs[i] = joFuj.getString("USUARIO");
                    aFujs[i] = joFuj.getString("fuera");
                    Log.v("PRUEBA", joFuj.getString("id_disp"));
                    Log.v("PRUEBA", joFuj.getString("nombre")); Log.v("PRUEBA","...");
                    String[][] aRef = entrada_inicio.oDB.buscarDispositivo(Integer.parseInt(joFuj.getString("id_disp")));
                    if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_disp"))) {
                        entrada_inicio.oDB.insertDispositivo(Integer.parseInt(joFuj.getString("id_disp")), joFuj.getString("nombre"),
                                Integer.parseInt(joFuj.getString("activo")), joFuj.getString("USUARIO"), Integer.parseInt(joFuj.getString("fuera")));
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "adddispositivos"){
            try{
                JSONObject json = new JSONObject();
                JSONObject dipositivo = new JSONObject();
                try {
                    dipositivo.put("id_disp", "0");
                    dipositivo.put("nombre", urls[1]);
                    dipositivo.put("USUARIO", urls[2]);
                    dipositivo.put("activo", "0");
                    dipositivo.put("fuera", "0");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json.put("dispositivo",dipositivo);

                sResp = NetServices.connectPost3(URL_WS1 + "wsdispositivos.svc/"+ urls[0],json.toString());
                String[] part = sResp.split(",");
                String[] partId = part[0].split(":");
                Log.v("[disp2]",partId[1]); //registrar_Dispositivo.oDB.insertDispositivo(ID, nDisp.getText().toString(),1, nUser.getText().toString(),1 );
                registrar_Dispositivo.oDB.insertDispositivo(Integer.parseInt(partId[1]) , urls[1], 1, urls[2], 1);
                //registrar_Dispositivo.oDB.deleteAllDispositivos(String.valueOf(ID));
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getclienteLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wscliente.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                    Log.v("[obtener]","Estoy en GetClienteLista");
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_cliente");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("telefono");
                    aFujs[i] = joFuj.getString("direccion");
                    Log.v("PRUEBA", "Cliente: "+joFuj.getString("nombre"));
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarCliente(Integer.parseInt(joFuj.getString("id_cliente")),Integer.parseInt(joFuj.getString("id_disp")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_cliente"))) {
                            MainActivity.oDB.insertCliente(Integer.parseInt(joFuj.getString("id_cliente")), Integer.parseInt(joFuj.getString("id_disp")),
                                    joFuj.getString("nombre"), joFuj.getString("telefono"), joFuj.getString("direccion"), 0);
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarCliente(Integer.parseInt(joFuj.getString("id_cliente")),Integer.parseInt(joFuj.getString("id_disp")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_cliente"))) {
                            registrar_Dispositivo.oDB.insertCliente(Integer.parseInt(joFuj.getString("id_cliente")), Integer.parseInt(joFuj.getString("id_disp")),
                                    joFuj.getString("nombre"), joFuj.getString("telefono"), joFuj.getString("direccion"), 0);
                        }
                    }

                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "addcliente"){
                    Log.v("[add]","Voy a insertar en WS" );
                    try {
                        JSONObject json = new JSONObject();
                        JSONObject dipositivo = new JSONObject();
                        try {
                            dipositivo.put("id_cliente", urls[1] );
                            dipositivo.put("id_disp", urls[2] );
                            dipositivo.put("nombre", urls[3] );
                            dipositivo.put("direccion", urls[4] );
                            dipositivo.put("telefono", urls[5] );
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        json.put("cliente", dipositivo);
                        sResp = NetServices.connectPost3(URL_WS1 + "wscliente.svc/" + urls[0], json.toString());
                        Log.v("[add]", "T: "+sResp.length() +" "+sResp);
                        if (sResp.length() == 6){
                            Log.v("[add]","Retorno nulo" );
                        }
                        else{
                            Log.v("[add]","Insertar en Local" );
                            MainActivity.oDB.updateCliente(urls[1], urls[2], urls[3], urls[5],urls[4], 0);
                        }
                    } catch (Exception e) {
                        exception = e;
                    }
        }
        else if(urls[0] == "modifycliente"){
            Log.v("[add]","Voy a modificar en WS" );
            try {
                JSONObject json = new JSONObject();
                JSONObject dipositivo = new JSONObject();
                try {
                    dipositivo.put("id_cliente", urls[1] );
                    dipositivo.put("id_disp", urls[2] );
                    dipositivo.put("nombre", urls[3] );
                    dipositivo.put("direccion", urls[4] );
                    dipositivo.put("telefono", urls[5] );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json.put("cliente", dipositivo);    //Log.v("[add]",json.toString() );
                sResp = NetServices.connectPost3(URL_WS1 + "wscliente.svc/" + urls[0], json.toString());
                Log.v("[add]", "T: "+sResp.length() +" "+sResp);
                if (sResp.length() == 6){
                    Log.v("[add]","Retorno nulo" );
                }
                else{
                    Log.v("[add]","Modificar en Local" );
                    MainActivity.oDB.updateCliente(urls[1], urls[2], urls[3], urls[5],urls[4], 0);
                }
            } catch (Exception e) {
                exception = e;
            }
        }
        else if(urls[0] == "deletecliente"){
            Log.v("[add]","Voy a Eliminar en WS" );
            try {
                JSONObject json = new JSONObject();
                JSONObject dipositivo = new JSONObject();
                try {
                    dipositivo.put("id_cliente", urls[1] );
                    dipositivo.put("id_disp", urls[2] );
                    dipositivo.put("nombre", urls[3] );
                    dipositivo.put("direccion", urls[4] );
                    dipositivo.put("telefono", urls[5] );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json.put("cliente", dipositivo);    //Log.v("[add]",json.toString() );
                sResp = NetServices.connectPost3(URL_WS1 + "wscliente.svc/" + urls[0], json.toString());
                Log.v("[add]", "T: "+sResp.length() +" "+sResp);
                if (sResp.length() == 6){
                    MainActivity.oDB.updateCliente(urls[1], urls[2], urls[3], urls[5], urls[4], 0);
                }
                else{
                    Log.v("[add]","Eliminar en Local" );
                    MainActivity.oDB.deleteCliente(urls[1], urls[2]);
                }
            } catch (Exception e) {
                exception = e;
            }
        }
        else if(urls[0] == "getproyectoLista"){
            try{
                Log.v("[Pro]", "Estoy en GetProyectoLista");
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto.svc/"+ urls[0]);
                Log.v("[Pro]", sResp);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];    //Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_proyecto");      aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_cliente");       aFujs[i] = joFuj.getString("id_cliente_disp");
                    aFujs[i] = joFuj.getString("id_formato");       aFujs[i] = joFuj.getString("id_user");
                    aFujs[i] = joFuj.getString("nombre_proyecto");  aFujs[i] = joFuj.getString("pedido_sap");
                    aFujs[i] = joFuj.getString("fecha");            aFujs[i] = joFuj.getString("autorizado");
                    //aFujs[i] = joFuj.getString("fechaautoriza"); //aFujs[i] = joFuj.getString("fecha_modifica"); //aFujs[i] = joFuj.getString("accesorios_especiales");
                    //aFujs[i] = joFuj.getString("id_usuarioautoriza"); //aFujs[i] = joFuj.getString("id_user_mod"); //aFujs[i] = joFuj.getString("id_usuario_cierra");
                    aFujs[i] = joFuj.getString("accesorios_techo"); aFujs[i] = joFuj.getString("accesorios_muro");
                    aFujs[i] = joFuj.getString("id_estatus");       aFujs[i] = joFuj.getString("id_usuario_venta");
                    Log.v("PRUEBA","Proyecto: "+ joFuj.getString("nombre_proyecto"));
                        MainActivity.oDB.insertProyecto(Integer.parseInt(joFuj.getString("id_proyecto")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_cliente")),
                                Integer.parseInt(joFuj.getString("id_cliente_disp")), Integer.parseInt(joFuj.getString("id_formato")), Integer.parseInt(joFuj.getString("id_user")), joFuj.getString("nombre_proyecto"), joFuj.getString("pedido_sap"),
                                joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("autorizado")), joFuj.getString("accesorios_techo"), joFuj.getString("accesorios_muro"),"0", Integer.parseInt(joFuj.getString("id_estatus")),
                                Integer.parseInt(joFuj.getString("id_usuario_venta")),"agente", 0 );
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "sincproyecto"){
            Log.v("[add]","Voy a Sincronizar en WS ");
            try{
                String[][] aref = MainActivity.oDB.buscarProyecto(Integer.parseInt(urls[2]), Integer.parseInt(urls[3]) );
                    JSONObject json = new JSONObject();
                    JSONObject proyecto = new JSONObject();

                    proyecto.put("id_proyecto", aref[0][0] );       proyecto.put("id_disp", aref[0][1] );
                    proyecto.put("id_cliente", aref[0][2] );        proyecto.put("id_cliente_disp", aref[0][3] );
                    proyecto.put("id_formato", aref[0][4]);         proyecto.put("id_user", aref[0][5] );
                    proyecto.put("nombre_proyecto", aref[0][6] );   proyecto.put("pedido_sap", aref[0][7] );
                    proyecto.put("autorizado", aref[0][9] );        proyecto.put("accesorios_techo", aref[0][10] );
                    proyecto.put("accesorios_muro", aref[0][11] );  proyecto.put("accesorios_especiales", aref[0][12] );
                    proyecto.put("id_estatus", aref[0][13] );       proyecto.put("id_usuario_venta", aref[0][14]);
                    proyecto.put("agente_venta", aref[0][16]);
                if ( aref[0][8].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][13] ); }

                json.put("proyecto",proyecto);  Log.v("[add]",urls[1]+": "+json.toString() );

                if (Integer.parseInt(urls[1]) ==1){
                    sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto.svc/addproyecto",json.toString());
                    Log.v("[add]","Tam: "+sResp.length() +" Ca: "+sResp);
                    if (sResp.length() == 6){
                        Log.v("[add]","Retorno nulo" );
                    }else{
                        MainActivity.oDB.updateProyecto(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][2]),
                                Integer.parseInt(aref[0][3]), Integer.parseInt(aref[0][4]), Integer.parseInt(aref[0][5]), aref[0][6], aref[0][7], aref[0][8],
                                Integer.parseInt(aref[0][9]), aref[0][10], aref[0][11], aref[0][12], Integer.parseInt(aref[0][13]), Integer.parseInt(aref[0][14]),0);
                    }
                }else if (Integer.parseInt(urls[1]) == 2){
                    sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto.svc/modifyproyecto",json.toString());
                    Log.v("[add]","Tam: "+sResp.length() +" Ca: "+sResp);
                    if (sResp.length() == 6){
                        Log.v("[add]","Retorno nulo" );
                    }else{
                        MainActivity.oDB.updateProyecto(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][2]),
                                Integer.parseInt(aref[0][3]), Integer.parseInt(aref[0][4]), Integer.parseInt(aref[0][5]), aref[0][6], aref[0][7], aref[0][8],
                                Integer.parseInt(aref[0][9]), aref[0][10], aref[0][11], aref[0][12], Integer.parseInt(aref[0][13]), Integer.parseInt(aref[0][14]), 0);
                    }
                }
                else if (Integer.parseInt(urls[1]) == 3){
                    sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto.svc/deleteproyecto",json.toString());
                    Log.v("[add]","Tam: "+sResp.length() +" Ca: "+sResp);
                    if (sResp.length() == 0){
                        MainActivity.oDB.cerrarProyecto(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][13]), 0, " ", 0);
                        Log.v("[add]","Retorno nulo y lo cambio a Sinc" );
                    }else {
                        MainActivity.oDB.deleteProyecto( urls[2], urls[3] );
                    }
                }

            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_camaLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_cama.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_cama");              aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");          aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("n_habitacion");         aFujs[i] = joFuj.getString("a");
                    aFujs[i] = joFuj.getString("b");                    aFujs[i] = joFuj.getString("c");
                    aFujs[i] = joFuj.getString("d");                    aFujs[i] = joFuj.getString("e");
                    aFujs[i] = joFuj.getString("f");                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("fecha");                aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("g");                    aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("id_usuario_alta");      aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("autorizado");           aFujs[i] = joFuj.getString("pagado");
                    Log.v("[obtenerPC]","Cama: " + joFuj.getString("nombre_proyecto") );
                    MainActivity.oDB.insertProyectoCama(Integer.parseInt(joFuj.getString("id_cama")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")),
                            Integer.parseInt(joFuj.getString("id_proyecto_disp")), joFuj.getString("n_habitacion"), Double.parseDouble(joFuj.getString("a")), Double.parseDouble(joFuj.getString("b")),
                            Double.parseDouble(joFuj.getString("c")), Double.parseDouble(joFuj.getString("d")), Double.parseDouble(joFuj.getString("e")), Double.parseDouble(joFuj.getString("f")),
                            Double.parseDouble(joFuj.getString("g")), joFuj.getString("fecha"), joFuj.getString("nombre_proyecto"), Integer.parseInt(joFuj.getString("formato")),
                            joFuj.getString("observaciones"), Integer.parseInt(joFuj.getString("id_usuario_alta")), Integer.parseInt(joFuj.getString("autorizado")), Integer.parseInt(joFuj.getString("id_estatus")), Integer.parseInt(joFuj.getString("pagado")), "", 0);
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "sincPro_cama"){
            Log.v("[add]","Voy a Sincronizar en WS "+urls[1] );
            try{
                String[][] aref = MainActivity.oDB.ObtenerProyectosCama( urls[2], urls[3], 5);
                String IMAGEN =RUTA_IMG+"IMG_Cama"+aref[0][0]+aref[0][1] ;
                    JSONObject json = new JSONObject();
                    JSONObject proyecto = new JSONObject();
                    proyecto.put("id_cama", aref[0][0] );           proyecto.put("id_disp", aref[0][1] );
                    proyecto.put("id_proyecto", aref[0][2] );       proyecto.put("id_proyecto_disp", aref[0][3] );
                    proyecto.put("n_habitacion", aref[0][4]);       proyecto.put("a", aref[0][5] );
                    proyecto.put("b", aref[0][6] );                 proyecto.put("c", aref[0][7] );
                    proyecto.put("d", aref[0][8] );                 proyecto.put("e", aref[0][9] );
                    proyecto.put("f", aref[0][10] );                proyecto.put("g", aref[0][15] );
                    proyecto.put("nombre_proyecto", aref[0][11] );
                    proyecto.put("formato", aref[0][14] );          proyecto.put("observaciones", aref[0][16] );
                    proyecto.put("id_usuario_alta", aref[0][17] );  proyecto.put("id_estatus", aref[0][20] );
                    proyecto.put("autorizado", aref[0][21] );       proyecto.put("pagado", aref[0][24] );
                    if ( aref[0][13].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][13] ); }

                    if ( aref[0][12].length() > 60){proyecto.put("aImg", IMAGEN ); }
                    else if (aref[0][12].length() >5 && aref[0][12].length() <50){
                        proyecto.put("aImg", aref[0][12] ); }

                    Log.v("[add]", "Entre aqui");
                        if (Integer.parseInt(urls[1]) == 1) {
                            sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_cama.svc/addproyectoCama", json.toString());
                            Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                            if (sResp.length() == 6) {
                                Log.v("[add]", "Retorno nulo");
                            } else {
                                MainActivity.oDB.updateProyectoCama(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][4], Double.parseDouble(aref[0][5]),
                                        Double.parseDouble(aref[0][6]), Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), Double.parseDouble(aref[0][9]),
                                        Double.parseDouble(aref[0][10]), Double.parseDouble(aref[0][15]), IMAGEN, aref[0][16], 0);
                            }
                        } else if (Integer.parseInt(urls[1]) == 2) {
                            sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_cama.svc/modifyproyectoCama", json.toString());
                            Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                            if (sResp.length() == 6) {
                                Log.v("[add]", "Retorno nulo");
                            } else {
                                MainActivity.oDB.updateProyectoCama(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][4], Double.parseDouble(aref[0][5]),
                                        Double.parseDouble(aref[0][6]), Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), Double.parseDouble(aref[0][9]),
                                        Double.parseDouble(aref[0][10]), Double.parseDouble(aref[0][15]), IMAGEN, aref[0][16], 0);
                            }
                        } else if (Integer.parseInt(urls[1]) == 3) {
                            sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_cama.svc/deleteproyectoCama", json.toString());
                            Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                            if (sResp.length() == 0) {
                                Log.v("[add]", "Retorno nulo");
                                MainActivity.oDB.cerrarProyectoCama(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][20]), 0);
                            } else {
                                MainActivity.oDB.deleteProyectoCama(Integer.parseInt(urls[2]), Integer.parseInt(urls[3]));
                            }
                        }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_hoteleriaLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1+ "wsproyecto_hoteleria.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("[PRUEBA","Hoteleria:  " +sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_hoteleria");         aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");          aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("habitacion");           aFujs[i] = joFuj.getString("area");
                    aFujs[i] = joFuj.getString("alto");                 aFujs[i] = joFuj.getString("ancho");
                    aFujs[i] = joFuj.getString("hojas");                aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("nombre_proyecto");      aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("formato");              aFujs[i] = joFuj.getString("piso");
                    // aFujs[i] = joFuj.getString("id_usuario_mod");    //aFujs[i] = joFuj.getString("fecha_pago"); //aFujs[i] = joFuj.getString("id_usuario_pago");
                    aFujs[i] = joFuj.getString("edificio");             aFujs[i] = joFuj.getString("control");
                    aFujs[i] = joFuj.getString("id_usuario_alta");      aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("medida_sujerida");      aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");
                    MainActivity.oDB.insertProyectoHoteleria(Integer.parseInt(joFuj.getString("id_hoteleria")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")),
                            Integer.parseInt(joFuj.getString("id_proyecto_disp")), joFuj.getString("habitacion"), joFuj.getString("area"), Double.parseDouble(joFuj.getString("ancho")),
                            Double.parseDouble(joFuj.getString("alto")), Double.parseDouble(joFuj.getString("hojas")), joFuj.getString("observaciones"), joFuj.getString("nombre_proyecto")," ",
                            joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("formato")), Integer.parseInt(joFuj.getString("piso")), joFuj.getString("edificio"), joFuj.getString("control"), "", " ",
                            Integer.parseInt(joFuj.getString("id_estatus")), joFuj.getString("medida_sujerida"), Integer.parseInt(joFuj.getString("autorizado")), 0, Integer.parseInt(joFuj.getString("pagado")), " ", 0);
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "sincPro_Hotel"){                        Log.v("[add]","Voy a Sincronizar en WS Hotel "+urls[1] );
            try{
                String[][] aref = MainActivity.oDB.ObtenerProyectosHoteleria( urls[2], urls[3], 4);
                String IMAGEN =RUTA_IMG+"IMG_Hoteleria"+aref[0][0]+aref[0][1] ;
                Log.v("[add]","1: "+aref[0][1] );
                JSONObject json = new JSONObject();         JSONObject proyecto = new JSONObject();
                    proyecto.put("id_hoteleria", aref[0][0] );          proyecto.put("id_disp", aref[0][1] );
                    proyecto.put("id_proyecto", aref[0][2] );           proyecto.put("id_proyecto_disp", aref[0][3] );
                    proyecto.put("habitacion", aref[0][6]);             proyecto.put("area", aref[0][7] );
                    proyecto.put("alto", aref[0][9] );                  proyecto.put("ancho", aref[0][8] );
                    proyecto.put("hojas", aref[0][10] );                proyecto.put("observaciones", aref[0][11] );
                    proyecto.put("nombre_proyecto", aref[0][12] );      proyecto.put("fijacion", aref[0][19] );
                    proyecto.put("formato", aref[0][15] );              proyecto.put("piso", aref[0][16] );
                    proyecto.put("edificio", aref[0][17] );             proyecto.put("control", aref[0][18] );
                    proyecto.put("id_usuario_alta", aref[0][21] );      proyecto.put("id_estatus", aref[0][23] );
                    proyecto.put("medida_sujerida", aref[0][24] );      proyecto.put("autorizado", aref[0][25] );
                    proyecto.put("corredera", aref[0][30] );
                    if ( aref[0][14].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][14] ); }

                    if ( aref[0][13].length() > 60){proyecto.put("aImg", IMAGEN ); }
                    else if (aref[0][13].length() >5 && aref[0][13].length() <50){
                        proyecto.put("aImg", aref[0][13] ); }

                    Log.v("[add]", "Entre aqui y voy al "+urls[1]);
                    if (Integer.parseInt(urls[1]) == 1) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_hoteleria.svc/addproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                            MainActivity.oDB.updateProyectoHoteleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][6], aref[0][7], Double.parseDouble(aref[0][8]),
                                    Double.parseDouble(aref[0][9]), aref[0][10], aref[0][12], aref[0][11], aref[0][16], aref[0][17], aref[0][18], aref[0][19], aref[0][24], aref[0][30],0);
                        }
                    } else if (Integer.parseInt(urls[1]) == 2) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_hoteleria.svc/modifyproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                            MainActivity.oDB.updateProyectoHoteleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][6], aref[0][7], Double.parseDouble(aref[0][8]),
                                    Double.parseDouble(aref[0][9]), aref[0][10], aref[0][12], aref[0][11], aref[0][16], aref[0][17], aref[0][18], aref[0][19], aref[0][24], aref[0][30],0);
                        }
                    } else if (Integer.parseInt(urls[1]) == 3) {
                        sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_hoteleria.svc/deleteproyecto", json.toString());
                        Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                        if (sResp.length() == 0) {
                            Log.v("[add]", "Retorno nulo");
                            MainActivity.oDB.cerrarProyectoHoteleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][23]), 3);
                        } else {
                            MainActivity.oDB.deleteProyectoHoteleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]));
                        }
                    }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_residencialLista"){
            try{
                Log.v("PRUEBA", "Hola");
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_residencial.svc/"+ urls[0]);     Log.v("PRUEBA", sResp);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_residencial");   aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_disp");          aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("ubicacion");        aFujs[i] = joFuj.getString("a");
                    aFujs[i] = joFuj.getString("b");                aFujs[i] = joFuj.getString("c");
                    aFujs[i] = joFuj.getString("d");                aFujs[i] = joFuj.getString("e");
                    aFujs[i] = joFuj.getString("f");                aFujs[i] = joFuj.getString("g");
                    aFujs[i] = joFuj.getString("h");                aFujs[i] = joFuj.getString("prof_marco");
                    aFujs[i] = joFuj.getString("prof_jaladera");    aFujs[i] = joFuj.getString("control");
                    aFujs[i] = joFuj.getString("agpto");            aFujs[i] = joFuj.getString("medida_sujerida");
                    aFujs[i] = joFuj.getString("observaciones");    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("fecha");            aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("id_usuario_alta");  aFujs[i] = joFuj.getString("id_usuario_mod");
                    aFujs[i] = joFuj.getString("id_estatus");       aFujs[i] = joFuj.getString("fijacion");
                    aFujs[i] = joFuj.getString("piso");             aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");           aFujs[i] = joFuj.getString("fecha_pago");
                    aFujs[i] = joFuj.getString("id_usuario_pago");

                    MainActivity.oDB.insertProyectoResidencial(Integer.parseInt(joFuj.getString("id_residencial")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")), Integer.parseInt(joFuj.getString("id_proyecto_disp")),
                            joFuj.getString("ubicacion"), Double.parseDouble(joFuj.getString("a")), Double.parseDouble(joFuj.getString("b")), Double.parseDouble(joFuj.getString("c")), Double.parseDouble(joFuj.getString("d")),
                            Double.parseDouble(joFuj.getString("e")), Double.parseDouble(joFuj.getString("f")), Double.parseDouble(joFuj.getString("g")), Double.parseDouble(joFuj.getString("h")), Double.parseDouble(joFuj.getString("prof_marco")),
                            Double.parseDouble(joFuj.getString("prof_jaladera")), joFuj.getString("control"), joFuj.getString("agpto"), joFuj.getString("medida_sujerida"), joFuj.getString("observaciones"), " ", joFuj.getString("nombre_proyecto"),
                            joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("formato")), joFuj.getString("id_usuario_alta"), Integer.parseInt(joFuj.getString("id_estatus")), joFuj.getString("fijacion"),
                            joFuj.getString("piso"), Integer.parseInt(joFuj.getString("autorizado")), 0, Integer.parseInt(joFuj.getString("pagado")), " " , 0);
                }
            }catch (Exception e){   exception = e;  }
        }
        else if(urls[0] == "sincPro_Residencial"){                        Log.v("[add]","Voy a Sincronizar en WS Residencial " );
            try{
                String[][] aref = MainActivity.oDB.ObtenerProyectosResidencial( urls[2], urls[3], 4);
                String IMAGEN =RUTA_IMG+"IMG_Residencial"+aref[0][0]+aref[0][1] ;
                JSONObject json = new JSONObject();         JSONObject proyecto = new JSONObject();
                proyecto.put("id_residencial", aref[0][0] );    proyecto.put("id_disp", aref[0][1] );
                proyecto.put("id_proyecto", aref[0][2] );       proyecto.put("id_proyecto_disp", aref[0][3] );
                proyecto.put("ubicacion", aref[0][4]);          proyecto.put("a", aref[0][5] );
                proyecto.put("b", aref[0][6] );                 proyecto.put("c", aref[0][7] );
                proyecto.put("d", aref[0][8] );                 proyecto.put("e", aref[0][9] );
                proyecto.put("f", aref[0][10] );                proyecto.put("g", aref[0][11] );
                proyecto.put("h", aref[0][12] );                proyecto.put("prof_marco", aref[0][13] );
                proyecto.put("prof_jaladera", aref[0][14] );    proyecto.put("control", aref[0][15] );
                proyecto.put("agpto", aref[0][16] );            proyecto.put("medida_sujerida", aref[0][17] );
                proyecto.put("observaciones", aref[0][18] );    proyecto.put("nombre_proyecto", aref[0][21] );
                proyecto.put("formato", aref[0][23] );          proyecto.put("id_usuario_alta", aref[0][24] );
                proyecto.put("id_estatus", aref[0][27] );       proyecto.put("fijacion", aref[0][28] );
                proyecto.put("id_usuario_mod", aref[0][25] );   proyecto.put("piso", aref[0][29] );
                proyecto.put("autorizado", aref[0][30] );       proyecto.put("pagado", aref[0][33] );
                proyecto.put("fecha_pago", aref[0][34] );       proyecto.put("id_usuario_pago", aref[0][35] );
                proyecto.put("corredera", aref[0][36] );

                if ( aref[0][22].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][22] ); }
                if ( aref[0][19].length() > 60){proyecto.put("aImg", IMAGEN ); }
                else if (aref[0][19].length() >5 && aref[0][19].length() <50){
                    proyecto.put("aImg", aref[0][19] ); }

                    Log.v("[add]", "Entre aqui y voy al "+urls[1]);
                    if (Integer.parseInt(urls[1]) == 1) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_residencial.svc/addproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                                MainActivity.oDB.updateProyectoResidencial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][4], Double.parseDouble(aref[0][5]), Double.parseDouble(aref[0][6]),
                                    Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), Double.parseDouble(aref[0][9]), Double.parseDouble(aref[0][10]), Double.parseDouble(aref[0][11]), Double.parseDouble(aref[0][12]),
                                    Double.parseDouble(aref[0][13]), Double.parseDouble(aref[0][14]), aref[0][15], aref[0][16], aref[0][17], aref[0][19], aref[0][18], aref[0][28], aref[0][29], aref[0][36], 0);
                        }
                    } else if (Integer.parseInt(urls[1]) == 2) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_residencial.svc/modifyproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                            MainActivity.oDB.updateProyectoResidencial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][4], Double.parseDouble(aref[0][5]), Double.parseDouble(aref[0][6]),
                                    Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), Double.parseDouble(aref[0][9]), Double.parseDouble(aref[0][10]), Double.parseDouble(aref[0][11]), Double.parseDouble(aref[0][12]),
                                    Double.parseDouble(aref[0][13]), Double.parseDouble(aref[0][14]), aref[0][15], aref[0][16], aref[0][17], aref[0][19], aref[0][18], aref[0][28], aref[0][29], aref[0][36], 0);
                       }
                    } else if (Integer.parseInt(urls[1]) == 3) {
                        sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_residencial.svc/deleteproyecto", json.toString());
                        Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                        if (sResp.length() == 0) {
                            Log.v("[add]", "Retorno nulo");
                            MainActivity.oDB.cerrarProyectoResidencial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][27]), 0);
                        } else {
                            MainActivity.oDB.deleteProyectoResidencial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]));
                        }
                    }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_especialLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_especial.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];                    Log.v("[obtenerPE]", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_especiales");        aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");          aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("nombre_proyecto");      aFujs[i] = joFuj.getString("alto");
                    aFujs[i] = joFuj.getString("ancho");                aFujs[i] = joFuj.getString("grosor");
                    aFujs[i] = joFuj.getString("observaciones");        aFujs[i] = joFuj.getString("aImg");
                    aFujs[i] = joFuj.getString("fecha");                aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("id_usuario_alta");      aFujs[i] = joFuj.getString("id_usuario_mod");
                    aFujs[i] = joFuj.getString("id_estatus");           aFujs[i] = joFuj.getString("autorizado");
                    //aFujs[i] = joFuj.getString("pagado"); //aFujs[i] = joFuj.getString("fecha_pago");
                    Log.v("PRUEBA", "Especial: "+ joFuj.getString("nombre_proyecto"));
                    MainActivity.oDB.insertProyectoEspecial(Integer.parseInt(joFuj.getString("id_especiales")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")),
                            Integer.parseInt(joFuj.getString("id_proyecto_disp")), joFuj.getString("nombre_proyecto"), Double.parseDouble(joFuj.getString("alto")), Double.parseDouble(joFuj.getString("ancho")),
                            Double.parseDouble(joFuj.getString("grosor")), joFuj.getString("observaciones"), joFuj.getString("aImg"), joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("formato")),
                            joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("id_estatus")), Integer.parseInt(joFuj.getString("autorizado")),0,0, 0);
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "sincPro_Especial"){                        Log.v("[add]","Voy a Sincronizar en WS Especial " );
            try{
                String[][] aref = MainActivity.oDB.ObtenerProyectosEspecial( urls[2], urls[3], 4);   Log.v("[add]","1: "+aref[0][1] );
                String IMAGEN =RUTA_IMG+"IMG_Especial"+aref[0][0]+aref[0][1] ;
                JSONObject json = new JSONObject();         JSONObject proyecto = new JSONObject();
                proyecto.put("id_especiales", aref[0][0] );     proyecto.put("id_disp", aref[0][1] );
                proyecto.put("id_proyecto", aref[0][2] );       proyecto.put("id_proyecto_disp", aref[0][3] );
                proyecto.put("nombre_proyecto", aref[0][4]);    proyecto.put("alto", aref[0][5] );
                proyecto.put("ancho", aref[0][6] );             proyecto.put("grosor", aref[0][7] );
                proyecto.put("observaciones", aref[0][8] );     proyecto.put("formato", aref[0][11] );
                proyecto.put("id_usuario_alta", aref[0][12] );  proyecto.put("id_usuario_mod", aref[0][13] );
                proyecto.put("id_estatus", aref[0][15] );       proyecto.put("autorizado", aref[0][16] );

                if ( aref[0][10].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][10] ); proyecto.put("fecha_alta", aref[0][10] );}
                if ( aref[0][9].length() > 60){
                    proyecto.put("aImg", IMAGEN ); }
                else if (aref[0][9].length() >5 && aref[0][9].length() <50){
                    proyecto.put("aImg", aref[0][9] ); }

                Log.v("[add]", "Entre aqui y voy al "+urls[1]);

                    if (Integer.parseInt(urls[1]) == 1) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_especial.svc/addproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                            MainActivity.oDB.updateProyectoEspecial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Double.parseDouble(aref[0][6]),
                                    Double.parseDouble(aref[0][5]), Double.parseDouble(aref[0][7]),aref[0][9], aref[0][8], 0);
                        }
                    } else if (Integer.parseInt(urls[1]) == 2) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_especial.svc/modifyproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        } else {
                            MainActivity.oDB.updateProyectoEspecial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Double.parseDouble(aref[0][6]),
                                    Double.parseDouble(aref[0][5]), Double.parseDouble(aref[0][7]),aref[0][9], aref[0][8], 0);
                        }
                    } else if (Integer.parseInt(urls[1]) == 3) {
                        sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_especial.svc/deleteproyecto", json.toString());
                        Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                        if (sResp.length() == 0) { Log.v("[add]", "Retorno nulo");
                            MainActivity.oDB.cerrarProyectoEspecial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][15]), 0);
                        } else {
                            MainActivity.oDB.deleteProyectoEspecial(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]));
                        }
                    }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_galeriaLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_galeria.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_galeria");           aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");          aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("fecha");                aFujs[i] = joFuj.getString("n_habitacion");
                    aFujs[i] = joFuj.getString("area");                 aFujs[i] = joFuj.getString("alto");
                    aFujs[i] = joFuj.getString("ancho");                aFujs[i] = joFuj.getString("copete");
                    aFujs[i] = joFuj.getString("proyecciones");         aFujs[i] = joFuj.getString("fijacion");
                    aFujs[i] = joFuj.getString("comentarios");          aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("formato");              aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_usuario_mod");       aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("autorizado");           aFujs[i] = joFuj.getString("pagado");
                    aFujs[i] = joFuj.getString("id_usuario_pago");
                    //aFujs[i] = joFuj.getString("fecha_pago");
                    //Log.v("PRUEBA","Galeria: "+ joFuj.getString("nombre_proyecto"));
                    MainActivity.oDB.insertProyectoGaleria(Integer.parseInt(joFuj.getString("id_galeria")), Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")), Integer.parseInt(joFuj.getString("id_proyecto_disp")), joFuj.getString("fecha"),
                            joFuj.getString("n_habitacion"), joFuj.getString("area"), Double.parseDouble(joFuj.getString("ancho")), Double.parseDouble(joFuj.getString("alto")), joFuj.getString("copete"), joFuj.getString("proyecciones"), joFuj.getString("fijacion"), joFuj.getString("comentarios"),
                            joFuj.getString("nombre_proyecto"), " ", Integer.parseInt(joFuj.getString("formato")), " ", Integer.parseInt(joFuj.getString("id_estatus")),
                            Integer.parseInt(joFuj.getString("autorizado")), Integer.parseInt(joFuj.getString("id_usuario_alta")), Integer.parseInt(joFuj.getString("pagado")), 0);
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "sincPro_Galeria"){                        Log.v("[add]","Voy a Sincronizar en WS Galeria " );
            try{
                String[][] aref = MainActivity.oDB.ObtenerProyectosGaleria( urls[2], urls[3], 4);
                String IMAGEN =RUTA_IMG+"IMG_Galeria"+aref[0][0]+aref[0][1] ;
                JSONObject json = new JSONObject();         JSONObject proyecto = new JSONObject();
                proyecto.put("id_galeria", aref[0][0] );        proyecto.put("id_disp", aref[0][1] );
                proyecto.put("id_proyecto", aref[0][2] );       proyecto.put("id_proyecto_disp", aref[0][3] );
                proyecto.put("n_habitacion", aref[0][5]);       proyecto.put("alto", aref[0][8] );
                proyecto.put("ancho", aref[0][7] );             proyecto.put("area", aref[0][6] );
                proyecto.put("copete", aref[0][9] );            proyecto.put("proyecciones", aref[0][10] );
                proyecto.put("fijacion", aref[0][11] );         proyecto.put("comentarios", aref[0][12] );
                proyecto.put("nombre_proyecto", aref[0][13] );  proyecto.put("formato", aref[0][15] );
                proyecto.put("id_usuario_alta", aref[0][16] );  proyecto.put("id_estatus", aref[0][19] );
                proyecto.put("autorizado", aref[0][20] );       proyecto.put("pagado", aref[0][23] );
                if ( Integer.parseInt(urls[1]) == 1){
                    proyecto.put("id_usuario_mod", "0" );
                    proyecto.put("id_usuario_pago", "0" );}
                if ( aref[0][4].indexOf("D") >= 1){  proyecto.put("fecha", aref[0][4] ); }
                if ( aref[0][14].length() > 60){
                    proyecto.put("aImg", IMAGEN );}
                else if (aref[0][14].length() >5 && aref[0][14].length() <50){
                    proyecto.put("aImg", aref[0][14] ); }


                    if (Integer.parseInt(urls[1]) == 1) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_galeria.svc/addproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        }else {
                            MainActivity.oDB.updateProyectoGaleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][5],aref[0][6],
                                    Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), aref[0][9], aref[0][10], aref[0][11], aref[0][14], aref[0][12], 0);
                        }
                    }else if (Integer.parseInt(urls[1]) == 2) {
                        String Resp = NetServices.connectPost3(URL_WS1 + "wsproyecto_galeria.svc/modifyproyecto", json.toString());
                        Log.v("[add]", "Tam: " + Resp.length() + " Ca: " + Resp);
                        if (sResp.length() == 6) {
                            Log.v("[add]", "Retorno nulo");
                        }else {
                            MainActivity.oDB.updateProyectoGaleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), aref[0][5],aref[0][6],
                                    Double.parseDouble(aref[0][7]), Double.parseDouble(aref[0][8]), aref[0][9], aref[0][10], aref[0][11], aref[0][14], aref[0][12], 0);
                        }
                    }else if (Integer.parseInt(urls[1]) == 3) {
                        sResp = NetServices.connectPost3(URL_WS1 + "wsproyecto_galeria.svc/deleteproyecto", json.toString());
                        Log.v("[add]", "Tam: " + sResp.length() + " Ca: " + sResp);
                        if (sResp.length() == 0) { Log.v("[add]", "Retorno nulo");
                            MainActivity.oDB.cerrarProyectoGaleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]), Integer.parseInt(aref[0][19]), 0);
                        }else {
                            MainActivity.oDB.deleteProyectoGaleria(Integer.parseInt(aref[0][0]), Integer.parseInt(aref[0][1]));
                        }

                    }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "Subir_IMG"){                        Log.v("[add]","Voy a Subir Imagenes" );
            try{
                sResp = NetServices.connectPost(URL_WS2 + "decodeImage.php", urls[1],urls[2] );
                sResp= sResp.trim();
                if ( sResp.equalsIgnoreCase("OK")) {
                    Log.v("[add]", "Se subieron las imagenes");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getubicacionLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsubicacion.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_area");
                    aFujs[i] = joFuj.getString("area_ubicacion");
                    aFujs[i] = joFuj.getString("id_disp");
                    Log.v("PRUEBA", joFuj.getString("area_ubicacion")); Log.v("PRUEBA","...");
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarUbicacion(Integer.parseInt(joFuj.getString("id_area")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_area"))) {   Log.v("obtenerU", "aRef:"+aRef[0][0] +" area:"+joFuj.getString("id_area") );
                            MainActivity.oDB.insertUbicacion(Integer.parseInt(joFuj.getString("id_area")), Integer.parseInt(joFuj.getString("id_disp")), joFuj.getString("area_ubicacion"), 0);
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarUbicacion(Integer.parseInt(joFuj.getString("id_area")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_area"))) {   Log.v("obtenerU", "aRef:"+aRef[0][0] +" area:"+joFuj.getString("id_area") );
                            registrar_Dispositivo.oDB.insertUbicacion(Integer.parseInt(joFuj.getString("id_area")), Integer.parseInt(joFuj.getString("id_disp")), joFuj.getString("area_ubicacion"), 0);
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "addubicacion"){
            Log.v("[add]","Voy a insertar en WS Ubicación" );
            try {
                JSONObject json = new JSONObject();
                JSONObject dipositivo = new JSONObject();
                try {
                    dipositivo.put("id_area", urls[1] );
                    dipositivo.put("id_disp", urls[2] );
                    dipositivo.put("area_ubicacion", urls[3] );
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                json.put("ubicacion", dipositivo);
                sResp = NetServices.connectPost3(URL_WS1 + "wsubicacion.svc/" + urls[0], json.toString());
                Log.v("[add]", "U: "+sResp.length() +" "+sResp);
                if (sResp.length() == 6){
                    Log.v("[add]","Retorno nulo" );
                }
                else{
                    Log.v("[add]","Insertar en Local" );
                    MainActivity.oDB.updateUbicacion(urls[1], urls[2], urls[3], 0);
                }
            } catch (Exception e) {
                exception = e;
            }
        }
        else if(urls[0] == "getusuarioLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsusuario.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("[PRUEBA]", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_usuario");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("apellido");
                    //aFujs[i] = joFuj.getString("usuario1");
                    //aFujs[i] = joFuj.getString("contrasena");
                    aFujs[i] = joFuj.getString("estatus");
                    aFujs[i] = joFuj.getString("id_tipousuario");
                    //aFujs[i] = joFuj.getString("fecha");
                    Log.v("PRUEBA", joFuj.getString("id_usuario"));
                    Log.v("PRUEBA", joFuj.getString("nombre")); Log.v("PRUEBA","...");
                    if (joFuj.getString("estatus") == "true"){
                        Log.v("obtenerA","IdUser: "+joFuj.getString("id_usuario") );
                        if (Integer.parseInt(joFuj.getString("id_tipousuario") )== 1){
                            if (urls[1] == "1"){
                                String[][] aRef = MainActivity.oDB.buscarAgente(Integer.parseInt(joFuj.getString("id_usuario")) );
                                Log.v("obtenerA",aRef[0][0]);
                                if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_usuario"))) {
                                    MainActivity.oDB.insertAgentes(Integer.parseInt(joFuj.getString("id_usuario")),
                                            joFuj.getString("nombre"), joFuj.getString("apellido"),
                                            1, Integer.parseInt(joFuj.getString("id_tipousuario")) );
                                }
                            }else{
                                String[][] aRef = registrar_Dispositivo.oDB.buscarAgente(Integer.parseInt(joFuj.getString("id_usuario")) );
                                Log.v("obtenerA",aRef[0][0]);
                                if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_usuario"))) {
                                    registrar_Dispositivo.oDB.insertAgentes(Integer.parseInt(joFuj.getString("id_usuario")),
                                            joFuj.getString("nombre"), joFuj.getString("apellido"),
                                            1, Integer.parseInt(joFuj.getString("id_tipousuario")) );
                                }
                            }

                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getformatoLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsformato.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("formato1");
                    aFujs[i] = joFuj.getString("id_formato");
                    Log.v("PRUEBA", joFuj.getString("id_formato"));
                    Log.v("PRUEBA", joFuj.getString("formato1")); Log.v("PRUEBA","...");
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarFormato(Integer.parseInt(joFuj.getString("id_formato")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_formato"))) {
                            MainActivity.oDB.insertFormato(Integer.parseInt(joFuj.getString("id_formato")), joFuj.getString("formato1"));
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarFormato(Integer.parseInt(joFuj.getString("id_formato")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_formato"))) {
                            registrar_Dispositivo.oDB.insertFormato(Integer.parseInt(joFuj.getString("id_formato")), joFuj.getString("formato1"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getcorrederaLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wscorredera.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBAC", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_corredera");
                    aFujs[i] = joFuj.getString("valor");
                    Log.v("PRUEBAC", joFuj.getString("id_corredera"));
                    Log.v("PRUEBAC", joFuj.getString("valor")); Log.v("PRUEBAC","...");
                    if (urls[1] == "1"){
                        String[][] aRef = MainActivity.oDB.buscarCorredera(Integer.parseInt(joFuj.getString("id_corredera")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_corredera"))) {
                            MainActivity.oDB.insertCorredera(Integer.parseInt(joFuj.getString("id_corredera")), joFuj.getString("valor"));
                        }
                    }else{
                        String[][] aRef = registrar_Dispositivo.oDB.buscarCorredera(Integer.parseInt(joFuj.getString("id_corredera")));
                        if (Integer.parseInt(aRef[0][0]) != Integer.parseInt(joFuj.getString("id_corredera"))) {
                            registrar_Dispositivo.oDB.insertCorredera(Integer.parseInt(joFuj.getString("id_corredera")), joFuj.getString("valor"));
                        }
                    }
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getestatusLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsestatus.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("estado");
                    Log.v("PRUEBA", joFuj.getString("id_estatus"));
                    Log.v("PRUEBA", joFuj.getString("estado")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getenviardatosLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsenviardatos.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("idenviardatos");
                    aFujs[i] = joFuj.getString("enviado");
                    aFujs[i] = joFuj.getString("consulta");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("id_disp_envio");
                    Log.v("PRUEBA", joFuj.getString("idenviardatos"));
                    Log.v("PRUEBA", joFuj.getString("enviado")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getTipoImagenLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsTipoImagen.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_tipo");
                    aFujs[i] = joFuj.getString("tipo");
                    Log.v("PRUEBA", joFuj.getString("id_tipo"));
                    Log.v("PRUEBA", joFuj.getString("tipo")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "gettipousuarioLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wstipousuario.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_tipousuario");
                    aFujs[i] = joFuj.getString("tipo");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("estatus");
                    Log.v("PRUEBA", joFuj.getString("id_tipousuario"));
                    Log.v("PRUEBA", joFuj.getString("tipo")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_imagenLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_imagen.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_imagen");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("tipo_foto");
                    aFujs[i] = joFuj.getString("descripcion");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("ruta");
                    aFujs[i] = joFuj.getString("formato");
                    Log.v("PRUEBA", joFuj.getString("id_imagen"));
                    Log.v("PRUEBA", joFuj.getString("tipo_foto")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }



        return (x);
    }



    protected void onPostExecute(Object feed){
        if(exception == null){
            listener.OnTaskCompleted(feed);
        }
        else {
            listener.OnTaskError(exception.toString());
        }
    }


}


/*
                json.put("proyectoGaleria",proyecto);  Log.v("[add]","::: "+json.toString() );
                if (aref[0][14].length() >60){
                    sResp = NetServices.connectPost(URL_WS2 + "decodeImage.php", aref[0][19], "IMG_Galeria"+aref[0][0]+aref[0][1]);
                }
                sResp= sResp.trim();
                if (aref[0][14].length() < 50 || sResp.equalsIgnoreCase("OK")) {    Log.v("[add]", "Entre aqui y voy al "+urls[1]);

 */