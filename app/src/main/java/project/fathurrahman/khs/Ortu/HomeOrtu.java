package project.fathurrahman.khs.Ortu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.AdapterOrtu.HomeOrtuAdapter;
import project.fathurrahman.khs.Guru.Agenda;
import project.fathurrahman.khs.Guru.HomeGuru;
import project.fathurrahman.khs.Login;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 16/12/2016.
 */

public class HomeOrtu extends AppCompatActivity {

    Handler mHandler;

    String url = "http://sdbundamulia.com/ws_khs/Agenda.php";
    String getAgenda, getdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        gridView.setAdapter(new HomeOrtuAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(position==0){

                    Intent intent = new Intent(HomeOrtu.this, Grafik.class);
                    startActivity(intent);

                }else if(position==1){
                    Intent a = new Intent(HomeOrtu.this, Raport.class);
                    startActivity(a);

                }else if(position==2) {
//                    Toast.makeText(getApplication(), "Data Berhasil. Selamat !!", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(HomeOrtu.this, AgendaOrtu.class);
                    startActivity(a);

                }else if(position==3){

                    logout();

                }
            }
        });

        // untuk meminta data
        mHandler = new Handler();
        m_Runnable.run();


    }

    //Method untuk proses Logout User
    private void logout(){
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Untuk logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(HomeOrtu.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


    private final Runnable m_Runnable = new Runnable(){
        public void run(){

            View();

            HomeOrtu.this.mHandler.postDelayed(m_Runnable,1000);
        }
    };

    private void View(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

//            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(Agenda.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();

                // ini buat ambil date
                Calendar c = Calendar.getInstance();

                int seconds = c.get(Calendar.SECOND)-5;
                int minutes = c.get(Calendar.MINUTE);
                int hour = c.get(Calendar.HOUR);
                String time = hour+":"+minutes+":"+seconds;

                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                String date = day+"-"+month+"-"+year;

                getdate = time+" "+date;

//                Toast.makeText(HomeOrtu.this, getdate, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("2")){

                        // sound
                        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                        android.support.v4.app.NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(HomeOrtu.this)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle("Sekolah Bunda Mulia")
                                        .setContentText("Agenda : "+jObj.getString("agenda"))
                                        .setSound(soundUri);

                        Intent notificationIntent = new Intent(HomeOrtu.this, AgendaOrtu.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(HomeOrtu.this, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);

                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                // cara ambil session
                SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
                String nis = s.getString("nis","-");

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);
                par.put("date", getdate);
//                par.put("agenda", getAgenda);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();

    }
}
