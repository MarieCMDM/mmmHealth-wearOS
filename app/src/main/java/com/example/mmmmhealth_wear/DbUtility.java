package com.example.mmmmhealth_wear;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DbUtility {
    private static String ip            = "2.234.155.178";// this is the host ip that your data base exists on you can use 10.0.2.2 for local host                                                    found on your pc. use if config for windows to find the ip if the database exists on                                                    your pc
    private static String port          = "5432";
    public  static String Classes       = "org.postgresql.Driver";
    private static String database      = "mhealth";
    public  static String username      = "mhealth";
    public  static String password      = "BIGsmeni42";
    public  static String url           = "jdbc:postgresql://"+ip+":"+port+"/"+database+"?currentSchema=public";
    public static Connection connection = null;

    /**
     * Instaura la connessione con il database e ritorna vero se la connessione è avvenuta
     * @return boolean
     */
    public static boolean connectionToDb(){
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(DbUtility.url, DbUtility.username, DbUtility.password);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * cerca e restituisce la data dell'ultima visita del paziente nel db
     * @param id String REQUIRE not null;
     * @return String
     */
    public static String getUserLastReport(String id) throws SQLException {
        String query = "select r.date  from patient p inner join report r  \n" +
                "on p.patient_id  = r.patient_id \n" +
                "where p.patient_id  = " + id;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        String result = rs.getString(1);
        rs.close();
        st.close();
        return result;
    }




    /**
     * carica i dati di feeling, peso, eventuali note, pressione, ecg e spo2 del paziente nel db
     * @param id String REQUIRE not null
     */
    public static void submitData1(String id, int feelings, int weight, String notes,  int SYS, int DIA, int BPM, int SpO2) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

        String query =  "insert into report (feelings, weight, notes, sys, dia, bpm, spo2, date, patient_id) values ("+feelings+", "+weight+", "+notes+", "+SYS+", "+DIA+", "+BPM+", "+SpO2+", '"+date+"', "+id+")";
        Log.e("query", query);
        Statement st = connection.createStatement();
        st.executeUpdate(query);
        st.close();
    }

    /**
     * carica i dati di pressione, ecg e spo2 del paziente nel db
     * @param id String REQUIRE not null
     */
    public static void submitDataWearable(String id){ // da implementare
    }

}
