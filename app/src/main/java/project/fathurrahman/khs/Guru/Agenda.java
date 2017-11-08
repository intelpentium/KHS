package project.fathurrahman.khs.Guru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Guru.Agenda;
import project.fathurrahman.khs.Guru.Uts.UtsSiswa;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 17/12/2016.
 */

public class Agenda extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/Agenda.php";
    String getAgenda, getdate;

    EditText agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agenda = (EditText) findViewById(R.id.agenda);

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

//        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => "+c.getTime());
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        formattedDate = df.format(c.getTime());

        Calendar c = Calendar.getInstance();

        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String time = hour+":"+minutes+":"+seconds;


        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day+"-"+month+"-"+year;

        getdate = time+" "+date;

        // formattedDate have current date/time
//        Toast.makeText(this, getdate, Toast.LENGTH_SHORT).show();

    }

    private void checkValidation() {

        getAgenda = agenda.getText().toString();

        if (getAgenda.equals("") || getAgenda.length() == 0){
            Toast.makeText(Agenda.this, "Agenda tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else{

            Login();
        }

    }

    private void Login(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Agenda.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(Agenda.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("1")){

//                        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = preferences.edit();
//
//                        editor.remove("semester");
//                        editor.commit();

                        Intent a = new Intent(Agenda.this, HomeGuru.class);
//                        a.putExtra("nis", nis);
//                        a.putExtra("idMtPljrn", idMtPljrn);
//                        a.putExtra("idThnAjaran", idThnAjaran);
                        startActivity(a);
                        finish();

                        Toast.makeText(getApplication(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
                String idGuru = s.getString("idGuru","-");

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("idGuru", idGuru);
                par.put("agenda", getAgenda);
                par.put("date", getdate);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(Agenda.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
