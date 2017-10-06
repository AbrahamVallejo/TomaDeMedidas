package com.example.mhernandez.tomademedidas;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
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

    public static String connectPost(String pUrl, String uId, String uName) throws IOException{
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
            jobject.put("idUsuario", uId);
            jobject.put("nombreUsuario", uName);
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
                    Log.v("PRUEBA", joFuj.getString("id_fijacion"));
                    Log.v("PRUEBA", joFuj.getString("estado"));
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
                    Log.v("PRUEBA", joFuj.getString("id_control"));
                    Log.v("PRUEBA", joFuj.getString("estado"));
                }
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
                String var = "María Nuñez"; Log.v("[obtener]","Estoy en GetClienteLista");
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_cliente");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("telefono");
                    aFujs[i] = joFuj.getString("direccion");
                    Log.v("PRUEBA", joFuj.getString("id_cliente"));
                    Log.v("PRUEBA", joFuj.getString("nombre")); Log.v("PRUEBA","...");
                    MainActivity.oDB.insertCliente(Integer.parseInt(joFuj.getString("id_cliente")), Integer.parseInt(joFuj.getString("id_disp")),
                           joFuj.getString("nombre"), joFuj.getString("telefono"), joFuj.getString("direccion"));
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
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getusuarioLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsusuario.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_usuario");
                    aFujs[i] = joFuj.getString("nombre");
                    aFujs[i] = joFuj.getString("apellido");
                    aFujs[i] = joFuj.getString("usuario1");
                    aFujs[i] = joFuj.getString("contrasena");
                    aFujs[i] = joFuj.getString("estatus");
                    aFujs[i] = joFuj.getString("id_tipousuario");
                    aFujs[i] = joFuj.getString("fecha");
                    Log.v("PRUEBA", joFuj.getString("id_usuario"));
                    Log.v("PRUEBA", joFuj.getString("nombre")); Log.v("PRUEBA","...");
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
                    aFujs[i] = joFuj.getString("verificacion");
                    Log.v("PRUEBA", joFuj.getString("id"));
                    Log.v("PRUEBA", joFuj.getString("username")); Log.v("PRUEBA","...");
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
        else if(urls[0] == "getproyectoLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp); Log.v("[obtener]", "Estoy en GetProyectoLista");
                for (int i = 0; i<jaData.length(); i++){
                    Log.v("PRUEBA", "Hola");
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_cliente");
                    aFujs[i] = joFuj.getString("id_cliente_disp");
                    aFujs[i] = joFuj.getString("id_formato");
                    aFujs[i] = joFuj.getString("id_user");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("pedido_sap");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("autorizado");
                    //aFujs[i] = joFuj.getString("fechaautoriza"); //aFujs[i] = joFuj.getString("fecha_modifica"); //aFujs[i] = joFuj.getString("accesorios_especiales");
                    //aFujs[i] = joFuj.getString("id_usuarioautoriza"); //aFujs[i] = joFuj.getString("id_user_mod"); //aFujs[i] = joFuj.getString("id_usuario_cierra");
                    aFujs[i] = joFuj.getString("accesorios_techo");
                    aFujs[i] = joFuj.getString("accesorios_muro");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("id_usuario_venta");
                    Log.v("PRUEBA", joFuj.getString("id_proyecto"));
                    Log.v("PRUEBA", joFuj.getString("nombre_proyecto")); Log.v("PRUEBA","...");
                MainActivity.oDB.insertProyecto(Integer.parseInt(joFuj.getString("id_proyecto")),
                        Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_cliente")),
                        Integer.parseInt(joFuj.getString("id_cliente_disp")), Integer.parseInt(joFuj.getString("id_formato")),
                        Integer.parseInt(joFuj.getString("id_user")), joFuj.getString("nombre_proyecto"), joFuj.getString("pedido_sap"),
                        joFuj.getString("fecha"), Integer.parseInt(joFuj.getString("autorizado")), joFuj.getString("accesorios_techo"),
                        joFuj.getString("accesorios_muro"), Integer.parseInt(joFuj.getString("id_estatus")),
                        Integer.parseInt(joFuj.getString("id_usuario_venta")) );
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
        else if(urls[0] == "getproyeccionLista"){
            try{
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
                    Log.v("PRUEBA", joFuj.getString("id_area"));
                    Log.v("PRUEBA", joFuj.getString("area_ubicacion")); Log.v("PRUEBA","...");
                }
            }catch (Exception e){
                exception = e;
            }
        }
        else if(urls[0] == "getproyecto_residencialLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_residencial.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                Log.v("PRUEBA", "Hola");
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_residencial");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("ubicacion");
                    aFujs[i] = joFuj.getString("a");
                    aFujs[i] = joFuj.getString("b");
                    aFujs[i] = joFuj.getString("c");
                    aFujs[i] = joFuj.getString("d");
                    aFujs[i] = joFuj.getString("e");
                    aFujs[i] = joFuj.getString("f");
                    aFujs[i] = joFuj.getString("g");
                    aFujs[i] = joFuj.getString("h");
                    aFujs[i] = joFuj.getString("prof_marco");
                    aFujs[i] = joFuj.getString("prof_jaladera");
                    aFujs[i] = joFuj.getString("control");
                    aFujs[i] = joFuj.getString("agpto");
                    aFujs[i] = joFuj.getString("medida_sujerida");
                    aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_usuario_mod");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("fijacion");
                    aFujs[i] = joFuj.getString("piso");
                    aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");
                    aFujs[i] = joFuj.getString("fecha_pago");
                    aFujs[i] = joFuj.getString("id_usuario_pago");
                    Log.v("PRUEBA", joFuj.getString("id_residencial"));
                    Log.v("PRUEBA", joFuj.getString("ubicacion")); Log.v("PRUEBA","...");
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
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_hoteleria");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("habitacion");
                    aFujs[i] = joFuj.getString("area");
                    aFujs[i] = joFuj.getString("alto");
                    aFujs[i] = joFuj.getString("ancho");
                    aFujs[i] = joFuj.getString("hojas");
                    aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("formato");
                    //aFujs[i] = joFuj.getString("piso"); //aFujs[i] = joFuj.getString("id_usuario_mod");
                    //aFujs[i] = joFuj.getString("fecha_pago"); //aFujs[i] = joFuj.getString("id_usuario_pago");
                    aFujs[i] = joFuj.getString("edificio");
                    aFujs[i] = joFuj.getString("control");
                    aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("medida_sujerida");
                    aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");
                    Log.v("PRUEBA", joFuj.getString("id_hoteleria"));
                    Log.v("PRUEBA", joFuj.getString("habitacion")); Log.v("PRUEBA","...");
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
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_especiales");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("alto");
                    aFujs[i] = joFuj.getString("ancho");
                    aFujs[i] = joFuj.getString("grosor");
                    aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("aImg");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_usuario_mod");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("autorizado");
                    //aFujs[i] = joFuj.getString("pagado"); //aFujs[i] = joFuj.getString("fecha_pago");
                    Log.v("PRUEBA", joFuj.getString("id_especiales"));
                    Log.v("PRUEBA", joFuj.getString("nombre_proyecto")); Log.v("PRUEBA","...");
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
        else if(urls[0] == "getproyecto_galeriaLista"){
            try{
                sResp = NetServices.connectPost2(URL_WS1 + "wsproyecto_galeria.svc/"+ urls[0]);
                String[] aFujs = null;
                JSONArray jaData = new JSONArray((sResp));
                aFujs = new String[jaData.length()];
                Log.v("PRUEBA", sResp);
                for (int i = 0; i<jaData.length(); i++){
                    JSONObject joFuj = jaData.getJSONObject(i);
                    aFujs[i] = joFuj.getString("id_galeria");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("n_habitacion");
                    aFujs[i] = joFuj.getString("area");
                    aFujs[i] = joFuj.getString("alto");
                    aFujs[i] = joFuj.getString("ancho");
                    aFujs[i] = joFuj.getString("copete");
                    aFujs[i] = joFuj.getString("proyecciones");
                    aFujs[i] = joFuj.getString("fijacion");
                    aFujs[i] = joFuj.getString("comentarios");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_usuario_mod");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");
                    aFujs[i] = joFuj.getString("fecha_pago");
                    aFujs[i] = joFuj.getString("id_usuario_pago");
                    Log.v("PRUEBA", joFuj.getString("id_galeria"));
                    Log.v("PRUEBA", joFuj.getString("nombre_proyecto")); Log.v("PRUEBA","...");
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
                    aFujs[i] = joFuj.getString("id_cama");
                    aFujs[i] = joFuj.getString("id_disp");
                    aFujs[i] = joFuj.getString("id_proyecto");
                    aFujs[i] = joFuj.getString("id_proyecto_disp");
                    aFujs[i] = joFuj.getString("n_habitacion");
                    aFujs[i] = joFuj.getString("a");
                    aFujs[i] = joFuj.getString("b");
                    aFujs[i] = joFuj.getString("c");
                    aFujs[i] = joFuj.getString("d");
                    aFujs[i] = joFuj.getString("e");
                    aFujs[i] = joFuj.getString("f");
                    aFujs[i] = joFuj.getString("nombre_proyecto");
                    aFujs[i] = joFuj.getString("fecha");
                    aFujs[i] = joFuj.getString("formato");
                    aFujs[i] = joFuj.getString("g");
                    aFujs[i] = joFuj.getString("observaciones");
                    aFujs[i] = joFuj.getString("id_usuario_alta");
                    aFujs[i] = joFuj.getString("id_estatus");
                    aFujs[i] = joFuj.getString("autorizado");
                    aFujs[i] = joFuj.getString("pagado");
                    Log.v("PRUEBA", joFuj.getString("id_cama"));
                    Log.v("PRUEBA", joFuj.getString("n_habitacion")); Log.v("PRUEBA","...");
                    MainActivity.oDB.insertProyectoCama(Integer.parseInt(joFuj.getString("id_cama")),
                            Integer.parseInt(joFuj.getString("id_disp")), Integer.parseInt(joFuj.getString("id_proyecto")),
                            Integer.parseInt(joFuj.getString("id_proyecto_disp")), joFuj.getString("n_habitacion"),
                            Double.parseDouble(joFuj.getString("a")), Double.parseDouble(joFuj.getString("b")),
                            Double.parseDouble(joFuj.getString("c")), Double.parseDouble(joFuj.getString("d")),
                            Double.parseDouble(joFuj.getString("e")), Double.parseDouble(joFuj.getString("f")),
                            Double.parseDouble(joFuj.getString("g")), joFuj.getString("fecha"),
                            joFuj.getString("nombre_proyecto"), Integer.parseInt(joFuj.getString("formato")),
                            joFuj.getString("observaciones"), Integer.parseInt(joFuj.getString("id_usuario_alta")),
                            Integer.parseInt(joFuj.getString("autorizado")), Integer.parseInt(joFuj.getString("id_estatus")),
                            Integer.parseInt(joFuj.getString("pagado"))
                             );
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
