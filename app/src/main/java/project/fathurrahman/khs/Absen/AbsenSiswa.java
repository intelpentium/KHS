package project.fathurrahman.khs.Absen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.AdapterAbsen.AbsenSiswaAdapter;
import project.fathurrahman.khs.Penilaian;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class AbsenSiswa extends AppCompatActivity {

    ListView simpleList;
    String[] questions;
    Button submit;

    String url = "http://sdbundamulia.com/ws_khs/TugasSiswa.php";
    String url_input = "http://sdbundamulia.com/ws_khs/AbsenInput.php";

    String getnis, getidMtpljrn, getidKelas, getidThnAjaran;
    String message = "", nis="", ket="";

    ProgressBar progress;
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_siswa);

        // get the string array from string.xml file
//        questions = getResources().getStringArray(R.array.questions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress=(ProgressBar) findViewById(R.id.progressbar_loading);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);

        // get the reference of ListView and Button
        simpleList = (ListView) findViewById(R.id.simpleListView);
        submit = (Button) findViewById(R.id.submit);

        Bundle a = getIntent().getExtras();
        getnis = a.getString("nis");
        getidMtpljrn = a.getString("idMtPljrn");
        getidKelas = a.getString("idKelas");
        getidThnAjaran = a.getString("idThnAjaran");

        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ViewAbsen();

                swLayout.setRefreshing(false);
            }
        });

        ViewAbsen();
    }

    private void ViewAbsen(){

        class ParsingSearch extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s2) {
                super.onPostExecute(s2);

                progress.setVisibility(View.GONE);

//                Toast.makeText(TugasSiswa.this, ""+s2, Toast.LENGTH_SHORT).show();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("siswa");

                    String[] mResources = new String[hasilArray.length()];
                    final int[] isi = new int[hasilArray.length()];

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        mResources[it] = coun.getString("namaSiswa");
                        isi[it] = coun.getInt("nis");
                    }

                    // set the adapter to fill the data in the ListView
                    AbsenSiswaAdapter absen = new AbsenSiswaAdapter(getApplicationContext(), mResources);
                    simpleList.setAdapter(absen);

                    // perform setOnClickListerner event on Button
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // get the value of selected answers from custom adapter
                            for (int i = 0; i < AbsenSiswaAdapter.selectedAnswers.size(); i++) {

                                nis = String.valueOf(isi[i]);
                                ket = AbsenSiswaAdapter.selectedAnswers.get(i);

                                AbsenInput();
//                                message = message + "\n" + isi[i] + " " + AbsenSiswaAdapter.selectedAnswers.get(i);
                            }

                            // display the message on screen with the help of Toast.
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e) {
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

//                SharedPreferences prefer = getSharedPreferences("session", Context.MODE_PRIVATE);
//                String idGuru = prefer.getString("idGuru","Not Available" );

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", getnis);
                par.put("idMtpljrn", getidMtpljrn);
                par.put("idKelas", getidKelas);
                par.put("idThnAjaran", getidThnAjaran);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingSearch tambah = new ParsingSearch();
        tambah.execute();
    }

    private void AbsenInput(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AbsenSiswa.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(AbsenSiswa.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("1")){

                        Intent a = new Intent(AbsenSiswa.this, Penilaian.class);
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

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);
                par.put("ket", ket);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_input, par);
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

//            Intent a = new Intent(Absen.this, Home.class);
//            startActivity(a);
            ActivityCompat.finishAfterTransition(AbsenSiswa.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
