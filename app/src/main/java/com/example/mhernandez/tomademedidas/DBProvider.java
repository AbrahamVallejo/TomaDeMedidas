package com.example.mhernandez.tomademedidas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBProvider {

    private DBhelper oDB;
    private SQLiteDatabase db;

    public DBProvider(Context context) {
        oDB = new DBhelper(context);
    }

    public void CloseDB() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean isOpenDB() {
        return (db.isOpen());
    }

    public long executeSQL(String sql, Object[] bindArgs) {
        long iRet = 0;
        db = oDB.getWritableDatabase();
        db.execSQL(sql, bindArgs);
        CloseDB();
        return iRet;
    }

    public Cursor querySQL(String sql, String[] selectionArgs) {
        Cursor oRet = null;
        db = oDB.getReadableDatabase();
        oRet = db.rawQuery(sql, selectionArgs);
        return (oRet);
    }


    public void insertCliente(int idCliente,int idDisp,String nombre, String telefono, String direccion, int sinc) {
            Object[] aData = {idCliente, idDisp, nombre, telefono, direccion, sinc};
            executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_CLIENTE + " (" + DBhelper.ID_CLIENTE + ", " + DBhelper.ID_DISP + ", "
                    + DBhelper.COLUMN_NAME_NOMBRE + ", "
                    + DBhelper.COLUMN_NAME_TELEFONO + ", "
                    + DBhelper.COLUMN_NAME_DIRECCION + ", "
                    + DBhelper.COLUMN_NAME_SINCRONIZAR + ") VALUES(?, ?, ?, ?, ?, ?)", aData);
    }

    public String[][] buscarCliente(int client, int Disp) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {(String.valueOf(client)), (String.valueOf(Disp)) };
        Cursor Ars; Log.v("[obtener]", "Voy a buscar tu Cliente");                               //SELECT *FROM registromedidas.dispositivos WHERE id_disp =1;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " = ? AND "
                +DBhelper.ID_DISP +" = ?" , aFils);
        Log.v("[obtener]", "Encontre tu Cliente " );
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][6];
            while (Ars.moveToNext()) {                                                              //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP)) );
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE));               //Log.v("[obtener", aData[0][0] );
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_TELEFONO));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_DIRECCION));
                aData[iCnt][5] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_SINCRONIZAR));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtener]", "Voy de regreso");
        return (aData);
    }

    public String[][] lastCliente() {
        /*Log.v("[obtener", "Voy a buscar tu cliente");     Object[] aData = {nombre};
        executeSQL("SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.COLUMN_NAME_NOMBRE + " = ?", aData);
        Log.v("[obtener", "Encontre al cliente");*/ //Log.v("[obtener", var.toString()); " AND " + DBhelper.COLUMN_NAME_TELEFONO + "=?"
        //SELECT MAX(id) FROM
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = null;
        Cursor Ars; Log.v("[obtener", "Voy a buscar tu ID");
        Ars = querySQL("SELECT MAX(" +DBhelper.ID_CLIENTE +") AS "+DBhelper.ID_CLIENTE+ " FROM " + DBhelper.TABLE_NAME_CLIENTE, aFils);
        //Log.v("[obtener", "Datos:" +String.valueOf(Ars.getCount()) );
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][1];
            while (Ars.moveToNext()){                                               //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE)) );
                if(Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE))==null){   //Log.v("[obtener", "Entre al IF");
                    aData[iCnt][0] = "0";
                }else{                                                              //Log.v("[obtener", "Entre al Else");
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE) );}
                iCnt++;
            }
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Voy de regreso");
        return (aData);

    }

    public void updateCliente(String idCliente,String idDisp, String nombre, String telefono, String direccion, int sinc) {
        int idC=Integer.parseInt(idCliente);
        int idD= Integer.parseInt(idDisp);
        Object[] aData = {nombre,telefono, direccion, sinc, idC, idD};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_CLIENTE + " SET " + DBhelper.COLUMN_NAME_NOMBRE + " = ?, "
                + DBhelper.COLUMN_NAME_TELEFONO + " = ?, "
                + DBhelper.COLUMN_NAME_DIRECCION + " = ?, "
                + DBhelper.COLUMN_NAME_SINCRONIZAR + " = ?"
                + " WHERE " + DBhelper.ID_CLIENTE + " = ?" + " AND " + DBhelper.ID_DISP + " = ?", aData);
        Log.v("[obtener]", "Modificado");
    }

    public void deleteCliente(String idCliente, String idDisp) {
        int idC = Integer.parseInt(idCliente);
        int idD = Integer.parseInt(idDisp);

        Object[] aData = {idC, idD};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " = ?" + " AND " + DBhelper.ID_DISP + " = ?", aData);
        Log.v("[obtener]", "Delete");
    }

    //Ya obtiene los Clientes
    public String[][] ObtenerClientes(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " <> ?", aFils);
            //Log.v("[obtener]", "SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " <> ?");
        }
        else if (tipo == 3) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " <> ? " +
                    "AND "+ DBhelper.COLUMN_NAME_SINCRONIZAR + " <> 3", aFils);

        }else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CLIENTE + " WHERE " + DBhelper.ID_CLIENTE + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[6];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CLIENTE));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_TELEFONO));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_DIRECCION));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_SINCRONIZAR));    //Log.v("[obtener]", aData[iCnt][0] + "  Disp:"+ aData[iCnt][1] + "  Nombre:"+ aData[iCnt][2]+ "  Telefono:"+ aData[iCnt][3]);
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    public Cursor getAllClientes(){
        Cursor aRS;
        aRS = querySQL("Select * From " + DBhelper.TABLE_NAME_CLIENTE, null);
        return aRS;
    }




    public void insertProyecto(int idProyecto, int idDisp, int idCliente, int idClienteDisp, int idFormato, int idUser,
                               String nombreProyecto, String PedidoSap, String fecha, int autorizado, String accesoriosTecho,
                               String accesoriosMuro, String accesoriosEspeciales, int idEstatus, int idUsuarioVenta) {
        Object[] aData = {idProyecto, idDisp, idCliente, idClienteDisp, idFormato, idUser, nombreProyecto, PedidoSap,
                fecha, autorizado, accesoriosTecho, accesoriosMuro, accesoriosEspeciales, idEstatus, idUsuarioVenta};
     //Log.v("[obtener]", "Voy a insertar Proyecto");

        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO + " (" + DBhelper.ID_PROYECTO + ", "
                + DBhelper.ID_DISP + ", " + DBhelper.ID_CLIENTE + ", " + DBhelper.ID_CLIENTE_DISP + ", "
                + DBhelper.ID_FORMATO + ", " + DBhelper.ID_USER + ", " + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + ", "
                + DBhelper.COLUMN_NAME_PEDIDO_SAP + ", " + DBhelper.COLUMN_NAME_FECHA + ", "
                + DBhelper.COLUMN_NAME_AUTORIZADO + ", " + DBhelper.COLUMN_NAME_ACCESORIOS_TECHO + ", "
                + DBhelper.COLUMN_NAME_ACCESORIOS_MURO + ", " + DBhelper.COLUMN_NAME_ACCESORIOS_ESPECIALES + ", " + DBhelper.ID_ESTATUS + ", "+ DBhelper.ID_USUARIO_VENTA
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
    //Log.v("[obtener]", nombreProyecto);
    }

    public String[][] buscarProyecto(int idProyecto, int idDisp) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {(String.valueOf(idProyecto)), (String.valueOf(idDisp)) };
        Cursor Ars; Log.v("[obtener", "Voy a buscar tu Proyecto");                               //SELECT *FROM registromedidas.dispositivos WHERE id_disp =1;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO + " WHERE " + DBhelper.ID_PROYECTO + " = ? AND "
                +DBhelper.ID_DISP +" = ?" , aFils);
        Log.v("[obtener", "Encontre tu Proyecto "+ Ars.getCount() );
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][5];
            while (Ars.moveToNext()) {                                                              //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP)) );
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_PROYECTO));               //Log.v("[obtener", aData[0][0] );
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_FORMATO));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_USER));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Voy de regreso");
        return (aData);
    }

    public void updateProyecto(String idProyecto, String idDisp, String nombreProyecto, String accesoriosMuro, String accesoriosTecho,
                               String accesoriosEspeciales, String PedidoSap) {
        int idP = Integer.parseInt(idProyecto);
        int idD = Integer.parseInt(idDisp);

        Object[] aData = {nombreProyecto, accesoriosMuro, accesoriosTecho, accesoriosEspeciales, PedidoSap, idP, idD};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO + " SET " + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " = ?, "
                + DBhelper.COLUMN_NAME_ACCESORIOS_MURO + " = ?, " + DBhelper.COLUMN_NAME_ACCESORIOS_TECHO + " = ?, "
                + DBhelper.COLUMN_NAME_ACCESORIOS_ESPECIALES + " = ?, " + DBhelper.COLUMN_NAME_PEDIDO_SAP + " = ?, "
                + " WHERE " + DBhelper.ID_PROYECTO + " = ?" + "AND " + DBhelper.ID_DISP + " = ?", aData);
    }

    public void deleteProyecto(String idProyecto, String idDisp) {
        int idP = Integer.parseInt(idProyecto);
        int idD = Integer.parseInt(idDisp);

        Object[] aData = {idP, idD};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO + " WHERE " + DBhelper.ID_PROYECTO + " = ?" + " AND " + DBhelper.ID_DISP + " = ?", aData);
    }

    public String[][] lastProyecto() {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = null;
        Cursor Ars; Log.v("[obtener", "Proyecto ID: ");
        Ars = querySQL("SELECT MAX(" +DBhelper.ID_PROYECTO  +") AS "+DBhelper.ID_PROYECTO + " FROM " + DBhelper.TABLE_NAME_PROYECTO, aFils); //Log.v("[obtener", "Datos:" +String.valueOf(Ars.getCount()) );
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][1];
            while (Ars.moveToNext()){                                               //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE)) );
                if(Ars.getString(Ars.getColumnIndex(DBhelper.ID_PROYECTO))==null){   //Log.v("[obtener", "Entre al IF");
                    aData[iCnt][0] = "0";
                }else{                                                              //Log.v("[obtener", "Entre al Else");
                    aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_PROYECTO) );}
                iCnt++;
            }
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Voy de regreso");
        return (aData);
    }


    public void insertProyectoCama(int idCama,int idDisp,int idProyecto, int idProyectoDisp, String nHabitaciones, double A,
                                   double B, double C, double D, double E, double F, double G, String fecha, String nombreP, int formato,
                                   String Observaciones, int idUsuarioA, int autorizado, int idEstatud, int pagado) {
        Object[] aData = {idCama, idDisp, idProyecto, idProyectoDisp, nHabitaciones, A, B, C, D, E, F, G, fecha,
                         nombreP, formato, Observaciones, idUsuarioA, autorizado, idEstatud, pagado};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " (" + DBhelper.ID_CAMA + ", "
                + DBhelper.ID_DISP + ", " + DBhelper.ID_PROYECTO + ", " + DBhelper.ID_PROYECTO_DISP + ", "
                + DBhelper.COLUMN_NAME_N_HABITACION + ", " + DBhelper.COLUMN_NAME_A + ", " + DBhelper.COLUMN_NAME_B + ", "
                + DBhelper.COLUMN_NAME_C + ", " + DBhelper.COLUMN_NAME_D + ", " + DBhelper.COLUMN_NAME_E + ", "
                + DBhelper.COLUMN_NAME_F + ", " + DBhelper.COLUMN_NAME_G + ", " + DBhelper.COLUMN_NAME_FECHA + ", "
                + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + ", " + DBhelper.COLUMN_NAME_FORMATO + ", " + DBhelper.COLUMN_NAME_OBSERVACIONES + ", "
                + DBhelper.ID_USUARIOAUTORIZA + ", " + DBhelper.COLUMN_NAME_AUTORIZADO + ", " + DBhelper.ID_ESTATUS + ", "
                + DBhelper.COLUMN_NAME_PAGADO
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
        Log.v("[obtenerPC]", nombreP);

    }

    public void updateProyectoCama(String idCama, String nHabitaciones, String A, String B, String C, String D,
                                   String E, String F, String G, String AIMG, String Observaciones) {
        Object[] aData = {idCama, nHabitaciones, A, B, C, D, E, F, G, AIMG, Observaciones};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " SET " + DBhelper.COLUMN_NAME_N_HABITACION + " = ?, "
                + DBhelper.COLUMN_NAME_A + " = ?, " + DBhelper.COLUMN_NAME_B + " = ?, " + DBhelper.COLUMN_NAME_C + " = ?, "
                + DBhelper.COLUMN_NAME_E + " = ?, " + DBhelper.COLUMN_NAME_F + " = ?, " + DBhelper.COLUMN_NAME_G + " = ?, "
                + DBhelper.COLUMN_NAME_AIMG + " = ?, " + DBhelper.COLUMN_NAME_OBSERVACIONES + " = ?, " + " WHERE " + DBhelper.ID_CAMA + " = ?", aData);
    }

    public void deleteProyectoCama(String idCama) {
        Object[] aData = {idCama};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " WHERE " + DBhelper.ID_CAMA + " = ?", aData);
    }

    public void insertProyectoEspecial(int idEsp, int idDisp, int idPro, int idProDisp, String nombre,
                                       double alto, double ancho, double grosor, String observaciones, String AIMG,
                                       String fecha, int formato, int idUserAlta, int idUserMod, String fechaAlta,
                                       int estatus, int autorizado, int idUserAuto, String fechaAuto, int pagado,
                                       String fechaPago, int idUserPago) {
        Log.v("[obtenerPE]", "Insertar Especial");
        Object[] aData = {idEsp, idDisp, idPro, idProDisp, nombre, alto, ancho, grosor, observaciones, AIMG, fecha,
                formato, idUserAlta, idUserMod, fechaAlta, estatus, autorizado, idUserAuto, fechaAuto, pagado, fechaPago, idUserPago};
        /*Log.v("[obtenerPE]", "IdEs:"+idEsp +" IdDisp"+ idDisp +" "+ idPro+" "+ idProDisp+" "+ nombre+" "+ alto+ ancho+ grosor+" Ob:"+ observaciones+" Ima:"+ AIMG+" Fec:"+ fecha+
               "  "+formato+ idUserAlta+ idUserMod+" fe:"+ fechaAlta+ estatus+ autorizado+ idUserAuto+" fe:"+ fechaAuto+ pagado+" fe:"+ fechaPago+ idUserPago);*/
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " ("
                + DBhelper.ID_ESPECIALES + ", "
                + DBhelper.ID_DISP + ", "
                + DBhelper.ID_PROYECTO + ", "
                + DBhelper.ID_PROYECTO_DISP + ", "
                + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + ", "
                + DBhelper.COLUMN_NAME_ALTO + ", "
                + DBhelper.COLUMN_NAME_ANCHO + ", "
                + DBhelper.COLUMN_NAME_GROSOR + ", "
                + DBhelper.COLUMN_NAME_OBSERVACIONES + ", "
                + DBhelper.COLUMN_NAME_AIMG + ", "
                + DBhelper.COLUMN_NAME_FECHA + ", "
                + DBhelper.COLUMN_NAME_FORMATO + ", "
                + DBhelper.ID_USUARIO_ALTA + ", "
                + DBhelper.ID_USUARIO_MOD + ", "
                + DBhelper.COLUMN_NAME_FECHA_ALTA + ", "
                + DBhelper.ID_ESTATUS + ", "
                + DBhelper.COLUMN_NAME_AUTORIZADO + ", "
                + DBhelper.ID_USUARIOAUTORIZA + ", "
                + DBhelper.COLUMN_NAME_FECHAAUTORIZA + ", "
                + DBhelper.COLUMN_NAME_PAGADO + ", "
                + DBhelper.COLUMN_NAME_FECHA_PAGO + ", "
                + DBhelper.ID_USUARIO_PAGO
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
        Log.v("[obtenerPE]", nombre);
    }

    public void updateProyectoEspecial(String idEspecial, String ancho, String alto, String grosor, String AIMG, String observaciones) {
        Object[] aData = {idEspecial, ancho, alto, grosor, AIMG, observaciones};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " SET " + DBhelper.COLUMN_NAME_ANCHO + " = ?, "
                + DBhelper.COLUMN_NAME_ALTO + " = ?, " + DBhelper.COLUMN_NAME_GROSOR + " = ?, " + DBhelper.COLUMN_NAME_AIMG + " = ?, " + DBhelper.COLUMN_NAME_OBSERVACIONES + " = ?, "
                + " WHERE " + DBhelper.ID_ESPECIALES + " = ?", aData);
    }

    public void deleteProyectoEspecial(String idEspecial) {
        Object[] aData = {idEspecial};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " WHERE " + DBhelper.ID_ESPECIALES + " = ?", aData);
    }

    public void insertProyectoGaleria(String nHabitaciones, String area, String ancho, String alto,
                                      String copete, String proyecciones, String fijacion, String AIMG, String comentarios) {
        Object[] aData = {nHabitaciones, area, ancho, alto, copete, proyecciones, fijacion, AIMG, comentarios};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " (" + DBhelper.COLUMN_NAME_N_HABITACION + ", "
                + DBhelper.COLUMN_NAME_AREA + ", " + DBhelper.COLUMN_NAME_ANCHO + ", " + DBhelper.COLUMN_NAME_ALTO + ", "
                + DBhelper.COLUMN_NAME_COPETE + ", " + DBhelper.COLUMN_NAME_PROYECCIONES + ", " + DBhelper.COLUMN_NAME_FIJACION + ", "
                + DBhelper.COLUMN_NAME_AIMG + ", " + DBhelper.COLUMN_NAME_COMENTARIOS + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
    }

    public void updateProyectoGaleria(String idGaleria, String nHabitaciones, String area, String ancho, String alto,
                                      String copete, String proyecciones, String fijacion, String AIMG, String comentarios) {
        Object[] aData = {idGaleria, nHabitaciones, area, ancho, alto, copete, proyecciones, fijacion, AIMG, comentarios};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " SET " + DBhelper.COLUMN_NAME_N_HABITACION + " = ?, "
                + DBhelper.COLUMN_NAME_AREA + " = ?, " + DBhelper.COLUMN_NAME_ANCHO + " ?, " + DBhelper.COLUMN_NAME_ALTO + " = ?, "
                + DBhelper.COLUMN_NAME_COPETE + " = ?, " + DBhelper.COLUMN_NAME_PROYECCIONES + " = ?, " + DBhelper.COLUMN_NAME_FIJACION + " = ?, "
                + DBhelper.COLUMN_NAME_AIMG + " = ?, " + DBhelper.COLUMN_NAME_COMENTARIOS + " = ?, " + " WHERE " + DBhelper.ID_GALERIA + " = ?", aData);
    }

    public void deleteProyectoGaleria(String idGaleria) {
        Object[] aData = {idGaleria};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " WHERE " + DBhelper.ID_GALERIA + " = ?", aData);
    }

    public void insertProyectoHoteleria(String habitacion, String area, String ancho, String alto,
                                        String hojas, String AIMG, String observaciones, String piso, String edificio,
                                        String control, String fijacion, String medidaSujerida, String corredera) {
        Object[] aData = {habitacion, area, ancho, alto, hojas, AIMG, observaciones, piso, edificio,
                control, fijacion, medidaSujerida, corredera};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " (" + DBhelper.COLUMN_NAME_HABITACION + ", "
                + DBhelper.COLUMN_NAME_AREA + ", " + DBhelper.COLUMN_NAME_ANCHO + ", " + DBhelper.COLUMN_NAME_ALTO + ", "
                + DBhelper.COLUMN_NAME_HOJAS + ", " + DBhelper.COLUMN_NAME_AIMG + ", " + DBhelper.COLUMN_NAME_OBSERVACIONES + ", " + DBhelper.COLUMN_NAME_PISO + ", "
                + DBhelper.COLUMN_NAME_EDIFICIO + ", " + DBhelper.COLUMN_NAME_CONTROL + ", " + DBhelper.COLUMN_NAME_FIJACION + ", "
                + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + ", " + DBhelper.COLUMN_NAME_CORREDERA
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
    }

    public void updateProyectoHoteleria(String idHoteleria, String habitacion, String area, String ancho, String alto,
                                        String hojas, String AIMG, String observaciones, String piso, String edificio, String control,
                                        String fijacion, String medidaSujerida, String corredera) {
        Object[] aData = {idHoteleria, habitacion, area, ancho, alto, hojas, AIMG, observaciones, piso, edificio, control, fijacion, medidaSujerida, corredera};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " SET " + DBhelper.COLUMN_NAME_HABITACION + " = ?, "
                + DBhelper.COLUMN_NAME_AREA + " = ?, " + DBhelper.COLUMN_NAME_ANCHO + " = ?, " + DBhelper.COLUMN_NAME_ALTO + " = ?, "
                + DBhelper.COLUMN_NAME_HOJAS + " = ?, " + DBhelper.COLUMN_NAME_AIMG + " = ?, " + DBhelper.COLUMN_NAME_OBSERVACIONES + " = ?, " + DBhelper.COLUMN_NAME_PISO + " = ?, "
                + DBhelper.COLUMN_NAME_EDIFICIO + " = ?, " + DBhelper.COLUMN_NAME_CONTROL + " = ?, " + DBhelper.COLUMN_NAME_FIJACION + " = ?, "
                + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + " = ?, " + DBhelper.COLUMN_NAME_CORREDERA + " = ?, " + " WHERE " + DBhelper.ID_HOTELERIA + " = ?", aData);
    }

    public void deleteProyectoHoteleria(String idHoteleria) {
        Object[] aData = {idHoteleria};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " WHERE " + DBhelper.ID_HOTELERIA + " = ?", aData);
    }

    public void insertProyectoResidencial(String ubicacion, String A, String B, String C, String D,
                                          String E, String F, String G, String H, String profMarco, String profJaladera,
                                          String control, String medidaSujerida, String AIMG, String observaciones, String fijacion,
                                          String piso, String corredera) {
        Object[] aData = {ubicacion, A, B, C, D, E, F, G, H, profMarco, profJaladera, control, medidaSujerida, AIMG, observaciones, fijacion, piso, corredera};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " (" + DBhelper.COLUMN_NAME_UBICACION + ", "
                + DBhelper.COLUMN_NAME_A + ", " + DBhelper.COLUMN_NAME_B + ", " + DBhelper.COLUMN_NAME_C + ", "
                + DBhelper.COLUMN_NAME_D + ", " + DBhelper.COLUMN_NAME_E + ", " + DBhelper.COLUMN_NAME_F + ", "
                + DBhelper.COLUMN_NAME_G + ", " + DBhelper.COLUMN_NAME_H + ", " + DBhelper.COLUMN_NAME_PROF_MARCO + ", "
                + DBhelper.COLUMN_NAME_PROF_JALADERA + ", " + DBhelper.COLUMN_NAME_CONTROL + ", " + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + ", "
                + DBhelper.COLUMN_NAME_AIMG + ", " + DBhelper.COLUMN_NAME_OBSERVACIONES + ", " + DBhelper.COLUMN_NAME_FIJACION + ", " + DBhelper.COLUMN_NAME_PISO + ", "
                + DBhelper.COLUMN_NAME_CORREDERA + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aData);
    }

    public void updateProyectoResidencial(String idResidencial, String ubicacion, String A, String B, String C, String D,
                                          String E, String F, String G, String H, String profMarco, String profJaladera, String control,
                                          String medidaSujerida, String AIMG, String observaciones, String fijacion, String piso, String corredera) {
        Object[] aData = {idResidencial, ubicacion, A, B, C, D, E, F, G, H, profMarco, profJaladera, control, medidaSujerida, AIMG, observaciones, fijacion, piso, corredera};
        executeSQL("UPDATE " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " SET " + DBhelper.COLUMN_NAME_UBICACION + " = ?, " + DBhelper.COLUMN_NAME_A + " = ?, "
                + DBhelper.COLUMN_NAME_B + " = ?, " + DBhelper.COLUMN_NAME_C + " = ?, " + DBhelper.COLUMN_NAME_D + " = ?, " + DBhelper.COLUMN_NAME_E + " = ?, "
                + DBhelper.COLUMN_NAME_F + " = ?, " + DBhelper.COLUMN_NAME_G + " = ?, " + DBhelper.COLUMN_NAME_H + " = ?, " + DBhelper.COLUMN_NAME_PROF_MARCO + " = ?, "
                + DBhelper.COLUMN_NAME_PROF_JALADERA + " = ?, " + DBhelper.COLUMN_NAME_CONTROL + " = ?, " + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + " = ?, "
                + DBhelper.COLUMN_NAME_AIMG + " = ?, " + DBhelper.COLUMN_NAME_OBSERVACIONES + " = ?, " + DBhelper.COLUMN_NAME_FIJACION + " = ?, " + DBhelper.COLUMN_NAME_PISO + " = ?, "
                + DBhelper.COLUMN_NAME_CORREDERA + " = ?, " + " WHERE " + DBhelper.ID_RESIDENCIAL + " = ?", aData);
    }

    public void deleteProyectoResidencial(String idResidencial) {
        Object[] aData = {idResidencial};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " WHERE " + DBhelper.ID_RESIDENCIAL + " = ?", aData);
    }


    //Tabla Dispositivos
    public void insertDispositivo(int idDisp,String nombre, int activo, String usuario, int fuera) {
        Object[] aData = {idDisp, nombre, activo, usuario, fuera};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_DISPOSITIVOS + " (" + DBhelper.ID_DISP + ", " + DBhelper.COLUMN_NAME_NOMBRE + ", "
                + DBhelper.COLUMN_NAME_ACTIVO + ", " + DBhelper.COLUMN_NAME_USUARIO + ", "
                + DBhelper.COLUMN_NAME_FUERA + ") VALUES(?, ?, ?, ?, ?)", aData);
        Log.v("[obtener]", "Insert Disp: "+String.valueOf(idDisp));
    }

    public void deleteAllDispositivos(String ID_DISP) {
        Object[] aData = {ID_DISP};
        executeSQL("DELETE FROM " + DBhelper.TABLE_NAME_DISPOSITIVOS + " WHERE " + DBhelper.ID_DISP + " <> ?", aData);
    }
    public String[][] lastDispositivo() {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = null;
        Cursor Ars; Log.v("[obtener", "Voy a buscar tu ID");
        Ars = querySQL("SELECT MAX(" +DBhelper.ID_DISP  +") AS "+DBhelper.ID_DISP + " FROM " + DBhelper.TABLE_NAME_DISPOSITIVOS, aFils); //Log.v("[obtener", "Datos:" +String.valueOf(Ars.getCount()) );
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][1];
            while (Ars.moveToNext()){                                               //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_CLIENTE)) );
                if(Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP))==null){   //Log.v("[obtener", "Entre al IF");
                    aData[iCnt][0] = "0";
                }else{                                                              //Log.v("[obtener", "Entre al Else");
                    aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP) );}
                iCnt++;
            }
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Voy de regreso");
        return (aData);
    }

    public String[][] buscarDispositivo(int Disp) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(Disp) )};
        Cursor Ars;                //Log.v("[obtener", "Voy a buscar tu Dispositivo");              //SELECT *FROM registromedidas.dispositivos WHERE id_disp =1;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_DISPOSITIVOS + " WHERE " + DBhelper.ID_DISP + " = ?", aFils);  //Log.v("[obtener", "Encontre tu Dispositivo");
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][5];
            while (Ars.moveToNext()) {                                                              //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP)) );
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP));               //Log.v("[obtener", aData[0][0] );
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ACTIVO));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_USUARIO));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_FUERA));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Vuelvo de Buscar Dispositivo");
        return (aData);
    }


    //Tabla User para Login
    public void insertUser(int id,String usuario, String pass, String email, int status, String nombre, String apellido, int verificacion) {
        Object[] aData = {id, usuario, pass, email, status, nombre, apellido, verificacion};
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_USER + " (" + DBhelper._ID + ", " + DBhelper.COLUMN_NAME_USERNAME + ", "
                + DBhelper.COLUMN_NAME_PASSWORD_HASH + ", " + DBhelper.COLUMN_NAME_EMAIL + ", "
                + DBhelper.COLUMN_NAME_STATUS + ", " + DBhelper.COLUMN_NAME_NOMBRE + ", "
                + DBhelper.COLUMN_NAME_APELLIDO + ", " + DBhelper.COLUMN_NAME_VERIFICACION + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?)", aData);
        Log.v("[obtener]", "Insert User: "+String.valueOf(id));
    }

    public String[][] buscarUser(int Disp) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(Disp) )};
        Cursor Ars;                //Log.v("[obtener", "Voy a buscar tu Dispositivo");              //SELECT *FROM registromedidas.dispositivos WHERE id_disp =1;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USER + " WHERE " + DBhelper._ID + " = ?", aFils);  //Log.v("[obtener", "Encontre tu Dispositivo");
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][8];
            while (Ars.moveToNext()) {                                                              //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP)) );
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper._ID));               //Log.v("[obtener", aData[0][0] );
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_USERNAME ));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_PASSWORD_HASH ));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_EMAIL ));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_STATUS ));
                aData[iCnt][5] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE  ));
                aData[iCnt][6] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO  ));
                aData[iCnt][7] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_VERIFICACION  ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Vuelvo de Buscar User");
        return (aData);
    }

    public String[][] iniciarUser(String user, String pass) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(user) ),( String.valueOf(pass) )};
        Cursor Ars;                //Log.v("[obtener", "Voy a buscar tu Dispositivo");              //SELECT *FROM registromedidas.dispositivos WHERE id_disp =1;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USER + " WHERE " + DBhelper.COLUMN_NAME_USERNAME + " = ?"
                + " AND "+ DBhelper.COLUMN_NAME_PASSWORD_HASH + " = ?", aFils);  //Log.v("[obtener", "Encontre tu Dispositivo");
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][8];
            while (Ars.moveToNext()) {                                                              //Log.v("[obtener]","ID= " +Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP)) );
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper._ID));               //Log.v("[obtener", aData[0][0] );
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_USERNAME ));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_PASSWORD_HASH ));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_EMAIL ));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_STATUS ));
                aData[iCnt][5] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE  ));
                aData[iCnt][6] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO  ));
                aData[iCnt][7] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_VERIFICACION  ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtener", "Vuelvo de BuscarDispositivo");
        return (aData);
    }

    public String[][] ObtenerUser(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;             Log.v("[obtener]", "Estoy en Obtener User");
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USER + " WHERE " + DBhelper._ID + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USER + " WHERE " + DBhelper.COLUMN_NAME_USERNAME + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[5];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper._ID));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_USERNAME ));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PASSWORD_HASH ));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_EMAIL ));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_STATUS ));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE  ));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO  ));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_VERIFICACION  ));
                iCnt++;
            }
        } else {
            aData = new String[0][]; Log.v("[obtener]", "No encontre nada");
        }

        aRS.close();
        CloseDB();              Log.v("[obtener]","Llevo todos los User!!!");
        return (aData);
    }


    //Tabla Usuario para sacar los Agentes
    public void insertAgentes(int idUsuario,String nombre, String apellido, int estatus, int tipo) {
        Object[] aData = {idUsuario, nombre, apellido, estatus, tipo};
        Log.v("obtenerA", "Voy a insertar: "+nombre);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_USUARIO + " (" + DBhelper.ID_USUARIO + ", " + DBhelper.COLUMN_NAME_NOMBRE + ", "
                + DBhelper.COLUMN_NAME_APELLIDO + ", " + DBhelper.COLUMN_NAME_ESTATUS + ", "
                + DBhelper.ID_TIPOUSUARIO + ") VALUES(?, ?, ?, ?, ?)", aData);
        Log.v("obtenerA", "Agente: "+nombre);
    }

    public String[][] buscarAgente(int idUsuario) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idUsuario) )};
        Cursor Ars;                Log.v("obtenerA", "Voy a buscar tu Agente");
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USUARIO + " WHERE " + DBhelper.ID_USUARIO + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][5];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_USUARIO));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE ));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO ));
                aData[iCnt][3] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ESTATUS ));
                aData[iCnt][4] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_TIPOUSUARIO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerA", "Vuelvo de Buscar Agente");
        return (aData);
    }

    public String[][] ObtenerAgentes(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USUARIO + " WHERE " + DBhelper.ID_USUARIO + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USUARIO + " WHERE " + DBhelper.ID_USUARIO + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[5];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTATUS));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_TIPOUSUARIO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla Copete
    public void insertCopete(int idCopete, String estado) {
        Object[] aData = {idCopete, estado};
        Log.v("obtenerC", "Voy a insertar: "+estado);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_COPETE + " (" + DBhelper.ID_COPETE + ", "
                + DBhelper.COLUMN_NAME_ESTADO + ") VALUES(?, ?)", aData);
        Log.v("obtenerC", "Copete: "+estado);
    }

    public String[][] buscarCopete(int idCopete) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idCopete) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_COPETE + " WHERE " + DBhelper.ID_COPETE + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_COPETE));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerC", "Vuelvo de Buscar Copete");
        return (aData);
    }

    public String[][] ObtenerCopete(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_COPETE + " WHERE " + DBhelper.ID_COPETE + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_COPETE + " WHERE " + DBhelper.ID_COPETE + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_COPETE));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Fijacion
    public void insertFijacion(int idFijacion, String estado) {
        Object[] aData = {idFijacion, estado};
        Log.v("obtenerF", "Voy a insertar: "+estado);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_FIJACION + " (" + DBhelper.ID_FIJACION + ", "
                + DBhelper.COLUMN_NAME_ESTADO + ") VALUES(?, ?)", aData);
        Log.v("obtenerF", "Fijacion: "+estado);
    }

    public String[][] buscarFijacion(int idFijacion) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idFijacion) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FIJACION + " WHERE " + DBhelper.ID_FIJACION + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_FIJACION));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerF", "Vuelvo de Buscar Fijación");
        return (aData);
    }

    public String[][] ObtenerFijacion(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FIJACION + " WHERE " + DBhelper.ID_FIJACION + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FIJACION + " WHERE " + DBhelper.ID_FIJACION + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_FIJACION));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Proyeccion
    public void insertProyeccion(int idProye, String estado) {
        Object[] aData = {idProye, estado};
        Log.v("obtenerP", "Voy a insertar: "+estado);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_PROYECCION + " (" + DBhelper.ID_PROYECCION + ", "
                + DBhelper.COLUMN_NAME_ESTADO + ") VALUES(?, ?)", aData);
        Log.v("obtenerP", "Proyección: "+estado);
    }

    public String[][] buscarProyeccion(int idProye) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idProye) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECCION + " WHERE " + DBhelper.ID_PROYECCION + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_PROYECCION));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerP", "Vuelvo de Buscar Proyección");
        return (aData);
    }

    public String[][] ObtenerProyeccion(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECCION + " WHERE " + DBhelper.ID_PROYECCION + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECCION + " WHERE " + DBhelper.ID_PROYECCION + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECCION));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Ubicacion
    public void insertUbicacion(int idUbi, int idDisp, String ubi) {
        Object[] aData = {idUbi, idDisp, ubi};
        Log.v("obtenerU", "Voy a insertar: "+ubi);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_UBICACION + " (" + DBhelper.ID_AREA + ", "
                + DBhelper.ID_DISP + ", "
                + DBhelper.COLUMN_NAME_AREA_UBICACION + ") VALUES(?, ?, ?)", aData);
        Log.v("obtenerU", "Ubicación: "+ubi);
    }

    public String[][] buscarUbicacion(int idFijacion) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idFijacion) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_UBICACION + " WHERE " + DBhelper.ID_AREA + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][3];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_AREA));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_DISP ));
                aData[iCnt][2] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_AREA_UBICACION ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerU", "Vuelvo de Buscar Ubicación");
        return (aData);
    }

    public String[][] ObtenerUbicacion(String id, int tipo) {
        Log.v("[obtenerU", "Estoy en ObtenerUbicacion");
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_UBICACION + " WHERE " + DBhelper.ID_AREA + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_UBICACION + " WHERE " + DBhelper.ID_AREA + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[3];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_AREA));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP ));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AREA_UBICACION ));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Control
    public void insertControl(int idControl, String estado) {
        Object[] aData = {idControl, estado};
        Log.v("obtenerC", "Voy a insertar: "+estado);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_CONTROL + " (" + DBhelper.ID_CONTROL + ", "
                + DBhelper.COLUMN_NAME_ESTADO + ") VALUES(?, ?)", aData);
        Log.v("obtenerC", "Control: "+estado);
    }

    public String[][] buscarControl(int idControl) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idControl) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CONTROL + " WHERE " + DBhelper.ID_CONTROL + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_CONTROL));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[obtenerC", "Vuelvo de Buscar Proyección");
        return (aData);
    }

    public String[][] ObtenerControl(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CONTROL + " WHERE " + DBhelper.ID_CONTROL + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CONTROL + " WHERE " + DBhelper.ID_CONTROL + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CONTROL));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTADO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Corredera
    public void insertCorredera(int idCorredera, String valor) {
        Object[] aData = {idCorredera, valor};
        Log.v("PRUEBAC", "Voy a insertar: "+valor);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_CORREDERA + " (" + DBhelper.ID_CORREDERA + ", "
                + DBhelper.COLUMN_NAME_VALOR + ") VALUES(?, ?)", aData);
        Log.v("PRUEBAC", "Corredera: "+valor);
    }

    public String[][] buscarCorredera(int idCorredera) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idCorredera) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CORREDERA + " WHERE " + DBhelper.ID_CORREDERA + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_CORREDERA));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_VALOR ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[PRUEBAC", "Vuelvo de Buscar Corredera");
        return (aData);
    }

    public String[][] ObtenerCorredera(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CORREDERA + " WHERE " + DBhelper.ID_CORREDERA + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_CORREDERA + " WHERE " + DBhelper.ID_CORREDERA + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CORREDERA));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_VALOR));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    //Tabla para sacar Formato
    public void insertFormato(int idFormato, String valor) {
        Object[] aData = {idFormato, valor};
        Log.v("PRUEBAC", "Voy a insertar: "+valor);
        executeSQL("INSERT INTO " + DBhelper.TABLE_NAME_FORMATO + " (" + DBhelper.ID_FORMATO + ", "
                + DBhelper.COLUMN_NAME_FORMATO + ") VALUES(?, ?)", aData);
        Log.v("PRUEBAC", "Corredera: "+valor);
    }

    public String[][] buscarFormato(int idFormato) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils= {( String.valueOf(idFormato) )};
        Cursor Ars;
        Ars = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FORMATO + " WHERE " + DBhelper.ID_FORMATO + " = ?", aFils);
        if (Ars.getCount() > 0) {
            aData = new String[Ars.getCount()][2];
            while (Ars.moveToNext()) {
                aData[iCnt][0] = Ars.getString(Ars.getColumnIndex(DBhelper.ID_FORMATO));
                aData[iCnt][1] = Ars.getString(Ars.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO ));
                iCnt++; }
        }
        else{
            aData = new String[1][1];
            aData[0][0]= "0";
        }
        Ars.close();
        CloseDB();  Log.v("[PRUEBAC", "Vuelvo de Buscar Corredera");
        return (aData);
    }

    public String[][] ObtenerFormato(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FORMATO + " WHERE " + DBhelper.ID_FORMATO + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_FORMATO + " WHERE " + DBhelper.ID_FORMATO + " = ?", aFils);
        }
        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[2];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_FORMATO));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }



    //Ya obtiene los Dispositivos
    public String[][] ObtenerDispositivos(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;             Log.v("[obtener]", "Estoy en Obtener Dispositivos");
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_DISPOSITIVOS + " WHERE " + DBhelper.ID_DISP + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_DISPOSITIVOS + " WHERE " + DBhelper.COLUMN_NAME_ACTIVO + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[5];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACTIVO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_USUARIO));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FUERA));
                iCnt++;
            }
        } else {
            aData = new String[0][]; Log.v("[obtener]", "No encontre nada");
        }

        aRS.close();
        CloseDB();              Log.v("[obtener]","Llevo todos los Dispositivos!!!");
        return (aData);
    }

    //Ya obtiene los proyectos
    public String[][] ObtenerProyectos(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO + " WHERE " + DBhelper.ID_PROYECTO + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO + " WHERE " + DBhelper.ID_PROYECTO + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[23];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CLIENTE));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CLIENTE_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_FORMATO));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USER));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PEDIDO_SAP));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AGENTE_VENTA));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_VENTA));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USER_MOD));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_MODIFICA));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_CIERRA));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_CIERRA));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACCESORIOS_TECHO));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACCESORIOS_MURO));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACCESORIOS_ESPECIALES));
                aData[iCnt][22] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_IDS_CLIENTE));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();              Log.v("[obtener]","Llevo todos los proyectos!!!");
        return (aData);
    }

    public String[][] ObtenerProyectosCama(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " WHERE " + DBhelper.ID_CAMA + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " WHERE " + DBhelper.ID_CAMA + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[27];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_CAMA));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_N_HABITACION));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_A));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_B));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_C));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_D));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_E));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_F));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AIMG));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_G));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_OBSERVACIONES));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_ALTA));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_MOD));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_ALTA));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][22] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][23] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][24] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PAGADO));
                aData[iCnt][25] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_PAGO));
                aData[iCnt][26] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_PAGO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);

    }

    public String[][] ObtenerProyectosEspecial(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " WHERE " + DBhelper.ID_ESPECIALES + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " WHERE " + DBhelper.ID_ESPECIALES + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[22];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESPECIALES));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ALTO));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ANCHO));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_GROSOR));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_OBSERVACIONES));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AIMG));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_ALTA));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_MOD));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_ALTA));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PAGADO));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_PAGO));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_PAGO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    public String[][] ObtenerProyectosGaleria(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " WHERE " + DBhelper.ID_GALERIA + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " WHERE " + DBhelper.ID_GALERIA + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[26];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_GALERIA));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_N_HABITACION));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AREA));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ANCHO));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ALTO));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_COPETE));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PROYECCIONES));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FIJACION));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_COMENTARIOS));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AIMG));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_ALTA));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_MOD));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_ALTA));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][22] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][23] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PAGADO));
                aData[iCnt][24] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_PAGO));
                aData[iCnt][25] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_PAGO));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    public String[][] ObtenerProyectosHoteleria(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;
        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " WHERE " + DBhelper.ID_HOTELERIA + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " WHERE " + DBhelper.ID_HOTELERIA + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[31];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_HOTELERIA));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACCESORIOS_MURO));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ACCESORIOS_TECHO));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_HABITACION));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AREA));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ANCHO));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ALTO));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_HOJAS));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_OBSERVACIONES));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AIMG));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PISO));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_EDIFICIO));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_CONTROL));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FIJACION));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_ALTA));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_ALTA));
                aData[iCnt][22] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_MOD));
                aData[iCnt][23] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][24] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA));
                aData[iCnt][25] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][26] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][27] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][28] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PAGADO));
                aData[iCnt][29] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_PAGO));
                aData[iCnt][30] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_CORREDERA));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    public String[][] ObtenerProyectosResidencial(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;

        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " WHERE " + DBhelper.ID_RESIDENCIAL + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " WHERE " + DBhelper.ID_RESIDENCIAL + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[37];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_RESIDENCIAL));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_DISP));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_PROYECTO_DISP));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_UBICACION));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_A));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_B));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_C));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_D));
                aData[iCnt][9] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_E));
                aData[iCnt][10] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_F));
                aData[iCnt][11] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_G));
                aData[iCnt][12] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_H));
                aData[iCnt][13] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PROF_MARCO));
                aData[iCnt][14] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PROF_JALADERA));
                aData[iCnt][15] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_CONTROL));
                aData[iCnt][16] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AGPTO));
                aData[iCnt][17] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA));
                aData[iCnt][18] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_OBSERVACIONES));
                aData[iCnt][19] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AIMG));
                aData[iCnt][20] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_INSTALADOR));
                aData[iCnt][21] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE_PROYECTO));
                aData[iCnt][22] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][23] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FORMATO));
                aData[iCnt][24] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_ALTA));
                aData[iCnt][25] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_MOD));
                aData[iCnt][26] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_ALTA));
                aData[iCnt][27] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_ESTATUS));
                aData[iCnt][28] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FIJACION));
                aData[iCnt][29] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PISO));
                aData[iCnt][30] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_AUTORIZADO));
                aData[iCnt][31] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIOAUTORIZA));
                aData[iCnt][32] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAAUTORIZA));
                aData[iCnt][33] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_PAGADO));
                aData[iCnt][34] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA_PAGO));
                aData[iCnt][35] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO_PAGO));
                aData[iCnt][36] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_CORREDERA));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }

        aRS.close();
        CloseDB();
        return (aData);
    }

    public String[][] ObtenerUsuarios(String id, int tipo) {
        int iCnt = 0;
        String[][] aData = null;
        String[] aFils = {(id)};
        Cursor aRS;

        if (tipo == 1) {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USUARIO + " WHERE " + DBhelper.ID_USUARIO + " <> ?", aFils);
        } else {
            aRS = querySQL("SELECT * FROM " + DBhelper.TABLE_NAME_USUARIO + " WHERE " + DBhelper.ID_USUARIO + " = ?", aFils);
        }

        if (aRS.getCount() > 0) {
            aData = new String[aRS.getCount()][];
            while (aRS.moveToNext()) {
                aData[iCnt] = new String[9];
                aData[iCnt][0] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_USUARIO));
                aData[iCnt][1] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_NOMBRE));
                aData[iCnt][2] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_APELLIDO));
                aData[iCnt][3] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_USUARIO));
                aData[iCnt][4] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_CONTRASENA));
                aData[iCnt][5] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_ESTATUS));
                aData[iCnt][6] = aRS.getString(aRS.getColumnIndex(DBhelper.ID_TIPOUSUARIO));
                aData[iCnt][7] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHA));
                aData[iCnt][8] = aRS.getString(aRS.getColumnIndex(DBhelper.COLUMN_NAME_FECHAALTA));
                iCnt++;
            }
        } else {
            aData = new String[0][];
        }
        aRS.close();
        CloseDB();
        return (aData);
    }

    //Creación Tablas Base De Datos ++
    public class DBhelper extends SQLiteOpenHelper {
        private static final String TAG = "DBManager";
        private static final String DATABASE_NAME = "registromedidas.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME_CLIENTE = "cliente";
        private static final String ID_CLIENTE = "id_cliente";
        private static final String ID_DISP = "id_disp";
        private static final String COLUMN_NAME_NOMBRE = "nombre";
        private static final String COLUMN_NAME_TELEFONO = "telefono";
        private static final String COLUMN_NAME_DIRECCION = "direccion";
        private static final String COLUMN_NAME_FECHA_ALTA = "fecha_alta";
        private static final String TABLE_NAME_CONTROL = "control";
        private static final String ID_CONTROL = "id_control";
        private static final String COLUMN_NAME_ESTADO = "estado";
        private static final String TABLE_NAME_COPETE = "copete";
        private static final String ID_COPETE = "id_copete";
        private static final String TABLE_NAME_DISPOSITIVOS = "dispositivos";
        private static final String COLUMN_NAME_ACTIVO = "activo";
        private static final String COLUMN_NAME_USUARIO = "USUARIO";
        private static final String COLUMN_NAME_FUERA = "fuera";
        private static final String TABLE_NAME_ENVIARDATOS = "enviardatos";
        private static final String ID_ENVIARDATOS = "idenviardatos";
        private static final String COLUMN_NAME_CONSULTA = "consulta";
        private static final String COLUMN_NAME_ENVIADO = "enviado";
        private static final String COLUMN_NAME_FECHA = "fecha";
        private static final String COLUMN_NAME_FECHA_ENVIO = "fecha_envio";
        private static final String COLUMN_NAME_RUTA_IMAGEN = "ruta_imagen";
        private static final String ID_DISP_ENVIO = "id_disp_envio";
        private static final String TABLE_NAME_ESTATUS = "estatus";
        private static final String ID_ESTATUS = "id_estatus";
        private static final String TABLE_NAME_FIJACION = "fijacion";
        private static final String ID_FIJACION = "id_fijacion";
        private static final String TABLE_NAME_FORMATO = "formato";
        private static final String ID_FORMATO = "id_formato";
        private static final String COLUMN_NAME_FORMATO = "formato";
        private static final String TABLE_NAME_HOJAS = "hojas";
        private static final String ID_HOJAS = "id_hojas";
        private static final String COLUMN_NAME_CANTIDAD = "cantidad";
        private static final String TABLE_NAME_PROYECCION = "proyeccion";
        private static final String ID_PROYECCION = "id_proyeccion";
        private static final String TABLE_NAME_PROYECTO = "proyecto";
        private static final String ID_PROYECTO = "id_proyecto";
        private static final String ID_CLIENTE_DISP = "id_cliente_disp";
        private static final String ID_USER = "id_user";
        private static final String COLUMN_NAME_NOMBRE_PROYECTO = "nombre_proyecto";
        private static final String COLUMN_NAME_PEDIDO_SAP = "pedido_sap";
        private static final String COLUMN_NAME_AGENTE_VENTA = "agente_venta";
        private static final String ID_USUARIO_VENTA = "id_usuario_venta";
        private static final String COLUMN_NAME_AUTORIZADO = "autorizado";
        private static final String ID_USUARIOAUTORIZA = "id_usuarioautoriza";
        private static final String ID_USER_MOD = "id_user_mod";
        private static final String COLUMN_NAME_FECHAAUTORIZA = "fechaautoriza";
        private static final String COLUMN_NAME_FECHA_MODIFICA = "fecha_modifica";
        private static final String ID_USUARIO_CIERRA = "id_usuario_cierra";
        private static final String COLUMN_NAME_FECHA_CIERRA = "fecha_cierra";
        private static final String COLUMN_NAME_ACCESORIOS_TECHO = "accesorios_techo";
        private static final String COLUMN_NAME_ACCESORIOS_MURO = "accesorios_muro";
        private static final String COLUMN_NAME_ACCESORIOS_ESPECIALES = "accesorios_especiales";
        private static final String COLUMN_NAME_IDS_CLIENTE = "ids_cliente";
        private static final String TABLE_NAME_PROYECTO_CAMA = "proyecto_cama";
        private static final String ID_CAMA = "id_cama";
        private static final String ID_PROYECTO_DISP = "id_proyecto_disp";
        private static final String COLUMN_NAME_N_HABITACION = "n_habitacion";
        private static final String COLUMN_NAME_A = "a";
        private static final String COLUMN_NAME_B = "b";
        private static final String COLUMN_NAME_C = "c";
        private static final String COLUMN_NAME_D = "d";
        private static final String COLUMN_NAME_E = "e";
        private static final String COLUMN_NAME_F = "f";
        private static final String COLUMN_NAME_AIMG = "aImg";
        private static final String COLUMN_NAME_G = "g";
        private static final String COLUMN_NAME_OBSERVACIONES = "observaciones";
        private static final String ID_USUARIO_ALTA = "id_usuario_alta";
        private static final String ID_USUARIO_MOD = "id_usuario_mod";
        private static final String COLUMN_NAME_PAGADO = "pagado";
        private static final String COLUMN_NAME_FECHA_PAGO = "fecha_pago";
        private static final String ID_USUARIO_PAGO = "usuario_pago";
        private static final String TABLE_NAME_PROYECTO_ESPECIAL = "proyecto_especial";
        private static final String ID_ESPECIALES = "id_especiales";
        private static final String COLUMN_NAME_ALTO = "alto";
        private static final String COLUMN_NAME_ANCHO = "ancho";
        private static final String COLUMN_NAME_GROSOR = "grosor";
        private static final String TABLE_NAME_PROYECTO_GALERIA = "proyecto_galeria";
        private static final String ID_GALERIA = "id_galeria";
        private static final String COLUMN_NAME_AREA = "area";
        private static final String COLUMN_NAME_COPETE = "copete";
        private static final String COLUMN_NAME_PROYECCIONES = "proyecciones";
        private static final String COLUMN_NAME_FIJACION = "fijacion";
        private static final String COLUMN_NAME_COMENTARIOS = "comentarios";
        private static final String TABLE_NAME_PROYECTO_HOTELERIA = "proyecto_hoteleria";
        private static final String ID_HOTELERIA = "id_hoteleria";
        private static final String COLUMN_NAME_HABITACION = "habitacion";
        private static final String COLUMN_NAME_HOJAS = "hojas";
        private static final String COLUMN_NAME_PISO = "piso";
        private static final String COLUMN_NAME_EDIFICIO = "edificio";
        private static final String COLUMN_NAME_CONTROL = "control";
        private static final String COLUMN_NAME_MEDIDA_SUJERIDA = "medida_sujerida";
        private static final String COLUMN_NAME_CORREDERA = "corredera";
        private static final String TABLE_NAME_PROYECTO_IMAGEN = "proyecto_imagen";
        private static final String ID_IMAGEN = "id_imagen";
        private static final String COLUMN_NAME_TIPO_FOTO = "tipo_foto";
        private static final String COLUMN_NAME_DESCRIPCION = "descripcion";
        private static final String COLUMN_NAME_RUTA = "ruta";
        private static final String TABLE_NAME_PROYECTO_RESIDENCIAL = "proyecto_residencial";
        private static final String ID_RESIDENCIAL = "residencial";
        private static final String COLUMN_NAME_UBICACION = "ubicacion";
        private static final String COLUMN_NAME_H = "h";
        private static final String COLUMN_NAME_PROF_MARCO = "prof_marco";
        private static final String COLUMN_NAME_PROF_JALADERA = "prof_jaladera";
        private static final String COLUMN_NAME_AGPTO = "agpto";
        private static final String COLUMN_NAME_INSTALADOR = "instalador";
        private static final String TABLE_NAME_TIPO_IMAGEN = "tipo_imagen";
        private static final String ID_TIPO = "id_tipo";
        private static final String COLUMN_NAME_TIPO = "tipo";
        private static final String TABLE_NAME_TIPOUSUARIO = "tipousuario";
        private static final String ID_TIPOUSUARIO = "id_tipousuario";
        private static final String COLUMN_NAME_ESTATUS = "estatus";
        private static final String TABLE_NAME_UBICACION = "ubicacion";
        private static final String ID_AREA = "id_area";
        private static final String COLUMN_NAME_AREA_UBICACION = "area_ubicacion";
        private static final String TABLE_NAME_USER = "user";
        private static final String _ID = "id";
        private static final String COLUMN_NAME_USERNAME = "username";
        private static final String COLUMN_NAME_AUTH_KEY = "auth_key";
        private static final String COLUMN_NAME_PASSWORD_HASH = "password_hash";
        private static final String COLUMN_NAME_PASSWORD_RESET_TOKEN = "password_reset_token";
        private static final String COLUMN_NAME_EMAIL = "email";
        private static final String COLUMN_NAME_STATUS = "status";
        private static final String COLUMN_NAME_CREATED_AT = "created_at";
        private static final String COLUMN_NAME_UPDATED_AT = "updated_at";
        private static final String COLUMN_NAME_APELLIDO = "apellido";
        private static final String COLUMN_NAME_FECHAALTA = "fechaalta";
        private static final String COLUMN_NAME_FECHAMODIFICA = "fechamodifica";
        private static final String COLUMN_NAME_VERIFICACION = "verificacion";
        private static final String COLUMN_NAME_FECHA_VERIFICACION = "fecha_verificacion";
        private static final String TABLE_NAME_USUARIO = "usuario";
        private static final String ID_USUARIO = "id_usuario";
        private static final String COLUMN_NAME_CONTRASENA = "contrasena";
        private static final String TABLE_NAME_CORREDERA = "corredera";
        private static final String ID_CORREDERA = "id_corredera";
        private static final String COLUMN_NAME_VALOR = "valor";
        private static final String COLUMN_NAME_SINCRONIZAR = "sincronizar";

        DBhelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v("CHECK", "DBHelper.onCreate...");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_CLIENTE + " ("
                    + DBhelper.ID_CLIENTE + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.COLUMN_NAME_NOMBRE + " TEXT,"
                    + DBhelper.COLUMN_NAME_TELEFONO + " TEXT,"
                    + DBhelper.COLUMN_NAME_DIRECCION + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.COLUMN_NAME_SINCRONIZAR + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_CLIENTE + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Cliente [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO + "("
                    + DBhelper.ID_PROYECTO + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_CLIENTE + " INTEGER,"
                    + DBhelper.ID_CLIENTE_DISP + " INTEGER,"
                    + DBhelper.ID_FORMATO + " INTEGER,"
                    + DBhelper.ID_USER + " INTEGER,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PEDIDO_SAP + " TEXT,"
                    + DBhelper.COLUMN_NAME_AGENTE_VENTA + " TEXT,"
                    + DBhelper.ID_ESTATUS + " INTEGER KEY,"
                    + DBhelper.ID_USUARIO_VENTA + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + " TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.ID_USER_MOD + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA_MODIFICA + " TEXT,"
                    + DBhelper.ID_USUARIO_CIERRA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ACCESORIOS_TECHO + " TEXT,"
                    + DBhelper.COLUMN_NAME_ACCESORIOS_MURO + " TEXT,"
                    + DBhelper.COLUMN_NAME_ACCESORIOS_ESPECIALES + " TEXT," //
                    + DBhelper.COLUMN_NAME_FECHA_CIERRA + " TEXT,"
                    + DBhelper.COLUMN_NAME_IDS_CLIENTE + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_PROYECTO + ", " + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_DISPOSITIVOS + " ("
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.COLUMN_NAME_NOMBRE + " TEXT,"
                    + DBhelper.COLUMN_NAME_ACTIVO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_USUARIO + " TEXT,"
                    + DBhelper.COLUMN_NAME_FUERA + " TINYINT,"
                    + "PRIMARY KEY (" + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Dispositivos  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_CONTROL + " ("
                    + DBhelper.ID_CONTROL + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ESTADO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_CONTROL + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Control Lista");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_CORREDERA + " ("
                    + DBhelper.ID_CORREDERA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_VALOR + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_CORREDERA + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Corredera Lista");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_COPETE + " ("
                    + DBhelper.ID_COPETE + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ESTADO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_COPETE + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Copete  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_ENVIARDATOS + " ("
                    + DBhelper.ID_ENVIARDATOS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_CONSULTA + " TEXT,"
                    + DBhelper.COLUMN_NAME_ENVIADO + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA_ENVIO + " TEXT,"
                    + DBhelper.COLUMN_NAME_RUTA_IMAGEN + " TEXT,"
                    + DBhelper.ID_DISP_ENVIO + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_ENVIARDATOS + ")"
                    + ");");                                                                        Log.v("[obtener]","DB EnviarDatos  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_ESTATUS + " ("
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ESTADO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_ESTATUS + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Estatus  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_FIJACION + " ("
                    + DBhelper.ID_FIJACION + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ESTADO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_FIJACION + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Fijacion  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_FORMATO + " ("
                    + DBhelper.ID_FORMATO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FORMATO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_FORMATO + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Formato  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_HOJAS + " ("
                    + DBhelper.ID_HOJAS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_CANTIDAD + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_HOJAS + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Hojas  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECCION + "("
                    + DBhelper.ID_PROYECCION + " INTEGER,"
                    + DBhelper.COLUMN_NAME_ESTADO + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_PROYECCION + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyeccion  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_CAMA + " ("
                    + DBhelper.ID_CAMA + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_N_HABITACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_A + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_B + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_C + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_D + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_E + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_F + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_AIMG + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_G + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_OBSERVACIONES + " TEXT,"
                    + DBhelper.ID_USUARIO_ALTA + " INTEGER,"
                    + DBhelper.ID_USUARIO_MOD + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + " TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PAGADO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_FECHA_PAGO + " TEXT,"
                    + DBhelper.ID_USUARIO_PAGO + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_CAMA + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto Cama  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL + " ("
                    + DBhelper.ID_ESPECIALES + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_ALTO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_ANCHO + "DOUBLE,"
                    + DBhelper.COLUMN_NAME_GROSOR + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_OBSERVACIONES + " TEXT,"
                    + DBhelper.COLUMN_NAME_AIMG + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + DBhelper.ID_USUARIO_ALTA + " INTEGER,"
                    + DBhelper.ID_USUARIO_MOD + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + " TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PAGADO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_FECHA_PAGO + " TEXT,"
                    + DBhelper.ID_USUARIO_PAGO + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_ESPECIALES + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto Especial  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_GALERIA + " ("
                    + DBhelper.ID_GALERIA + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_N_HABITACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_AREA + " TEXT,"
                    + DBhelper.COLUMN_NAME_ANCHO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_ALTO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_COPETE + " TEXT,"
                    + DBhelper.COLUMN_NAME_PROYECCIONES + " TEXT,"
                    + DBhelper.COLUMN_NAME_FIJACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_COMENTARIOS + " TEXT,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_AIMG + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + DBhelper.ID_USUARIO_ALTA + " INTEGER,"
                    + DBhelper.ID_USUARIO_MOD + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + " TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PAGADO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_FECHA_PAGO + " TEXT,"
                    + DBhelper.ID_USUARIO_PAGO + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_GALERIA + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto Galeria  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA + " ("
                    + DBhelper.ID_HOTELERIA + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_ACCESORIOS_MURO + " TEXT,"
                    + DBhelper.COLUMN_NAME_ACCESORIOS_TECHO + " TEXT,"
                    + DBhelper.COLUMN_NAME_HABITACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_AREA + " TEXT,"
                    + DBhelper.COLUMN_NAME_ANCHO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_ALTO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_HOJAS + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_OBSERVACIONES + " TEXT,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_AIMG + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_PISO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_EDIFICIO + " TEXT,"
                    + DBhelper.COLUMN_NAME_CONTROL + " TEXT,"
                    + DBhelper.COLUMN_NAME_FIJACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.ID_USUARIO_ALTA + " INTEGER,"
                    + DBhelper.ID_USUARIO_MOD + " INTEGER,"
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + " TEXT,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + "TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PAGADO + " TINYINT,"
                    + DBhelper.ID_USUARIO_PAGO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_CORREDERA + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_HOTELERIA + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto Hoteleria  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_IMAGEN + " ("
                    + DBhelper.ID_IMAGEN + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_TIPO_FOTO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_DESCRIPCION + " TEXT,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_RUTA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + "PRIMARY KEY (" + DBhelper.ID_IMAGEN + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto Imagen  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL + " ("
                    + DBhelper.ID_RESIDENCIAL + " INTEGER,"
                    + DBhelper.ID_DISP + " INTEGER,"
                    + DBhelper.ID_PROYECTO + " INTEGER KEY,"
                    + DBhelper.ID_PROYECTO_DISP + " INTEGER KEY,"
                    + DBhelper.COLUMN_NAME_UBICACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_A + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_B + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_C + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_D + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_E + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_F + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_G + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_H + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_PROF_MARCO + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_PROF_JALADERA + " DOUBLE,"
                    + DBhelper.COLUMN_NAME_CONTROL + " TEXT,"
                    + DBhelper.COLUMN_NAME_AGPTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_MEDIDA_SUJERIDA + " TEXT,"
                    + DBhelper.COLUMN_NAME_OBSERVACIONES + " TEXT,"
                    + DBhelper.COLUMN_NAME_AIMG + " TEXT,"
                    + DBhelper.COLUMN_NAME_INSTALADOR + " TEXT,"
                    + DBhelper.COLUMN_NAME_NOMBRE_PROYECTO + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FORMATO + " INTEGER,"
                    + DBhelper.ID_USUARIO_ALTA + " INTEGER,"
                    + DBhelper.ID_USUARIO_MOD + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA_ALTA + " TEXT,"
                    + DBhelper.ID_ESTATUS + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FIJACION + " TEXT,"
                    + DBhelper.COLUMN_NAME_PISO + " TEXT,"
                    + DBhelper.COLUMN_NAME_AUTORIZADO + " TINYINT,"
                    + DBhelper.ID_USUARIOAUTORIZA + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHAAUTORIZA + " TEXT,"
                    + DBhelper.COLUMN_NAME_PAGADO + " TINYINT,"
                    + DBhelper.COLUMN_NAME_FECHA_PAGO + " TEXT,"
                    + DBhelper.ID_USUARIO_PAGO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_CORREDERA + " TEXT,"
                    + "PRIMARY KEY (" + DBhelper.ID_RESIDENCIAL + "," + DBhelper.ID_DISP + ")"
                    + ");");                                                                        Log.v("[obtener]","DB Proyecto RESIDENCIAL  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_TIPO_IMAGEN + " ("
                    + DBhelper.ID_TIPO + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME_TIPO + " TEXT"
                    + ");");                                                                        Log.v("[obtener]","DB Tipo Imagen  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_TIPOUSUARIO + " ("
                    + DBhelper.ID_TIPOUSUARIO  + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME_TIPO + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_ESTATUS + " TINYINT"
                    + ");");                                                                        Log.v("[obtener]","DB Tipo Usuario  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_UBICACION + " ("
                    + DBhelper.ID_AREA + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME_AREA_UBICACION + " TEXT,"
                    + DBhelper.ID_DISP + " INTEGER"
                    + ");");                                                                        Log.v("[obtener]","DB Ubicación  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_USER + " ("
                    + DBhelper._ID + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME_USERNAME + " TEXT,"
                    + DBhelper.COLUMN_NAME_AUTH_KEY + " TEXT,"
                    + DBhelper.COLUMN_NAME_PASSWORD_HASH + " TEXT,"
                    + DBhelper.COLUMN_NAME_PASSWORD_RESET_TOKEN + " TEXT,"
                    + DBhelper.COLUMN_NAME_EMAIL + " TEXT,"
                    + DBhelper.COLUMN_NAME_STATUS + " SMALLINT,"
                    + DBhelper.COLUMN_NAME_CREATED_AT + " INTEGER,"
                    + DBhelper.COLUMN_NAME_UPDATED_AT + " INTEGER,"
                    + DBhelper.COLUMN_NAME_NOMBRE + " TEXT,"
                    + DBhelper.COLUMN_NAME_APELLIDO + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHAALTA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHAMODIFICA + " TEXT,"
                    + DBhelper.COLUMN_NAME_VERIFICACION + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA_VERIFICACION + " TEXT"
                    + ");");                                                                        Log.v("[obtener]","DB User  [lista]");

            db.execSQL("CREATE TABLE " + DBhelper.TABLE_NAME_USUARIO + " ("
                    + DBhelper.ID_USUARIO + " INTEGER PRIMARY KEY,"
                    + DBhelper.COLUMN_NAME_NOMBRE + " TEXT,"
                    + DBhelper.COLUMN_NAME_APELLIDO + " TEXT,"
                    + DBhelper.COLUMN_NAME_USUARIO + " TEXT,"
                    + DBhelper.COLUMN_NAME_CONTRASENA + " TEXT,"
                    + DBhelper.COLUMN_NAME_ESTATUS + " TINYINT,"
                    + DBhelper.ID_TIPOUSUARIO + " INTEGER,"
                    + DBhelper.COLUMN_NAME_FECHA + " TEXT,"
                    + DBhelper.COLUMN_NAME_FECHAALTA + " TEXT"
                    + ");");                                                                        Log.v("[obtener]","DB Usuario  [lista]");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.v("[BASE DE DATOS]", "Actualizacion de Base de Datos"); Log.v("[obtener]","Act DB");

            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_CLIENTE);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO);

            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_CONTROL);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_COPETE);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_DISPOSITIVOS);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_ENVIARDATOS);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_ESTATUS);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_FIJACION);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_FORMATO);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_HOJAS);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECCION);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_CAMA);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_ESPECIAL);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_GALERIA);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_HOTELERIA);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_CONTROL);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_IMAGEN);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_PROYECTO_RESIDENCIAL);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_TIPO_IMAGEN);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_TIPOUSUARIO);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_UBICACION);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_USER);
            db.execSQL(" DROP TABLE IF EXISTS " + DBhelper.TABLE_NAME_USUARIO);
            onCreate(db);

        }
    }

}