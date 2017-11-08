package project.bundamulia.sd.Ortu;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.Adapter.AdapterGuru.SpinnerGuruKelas;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 16/12/2016.
 */

public class Grafik extends AppCompatActivity {

    String getkelas, getkelasname, getmapel, getmapelname, getsemester = "1";

    android.widget.Spinner kelas;
    ArrayList<SpinnerGuruKelas> kelasList;

    String url_thn = "http://sdbundamulia.com/ws_khs/GrafikThnAjaran.php";
    String url = "http://sdbundamulia.com/ws_khs/Grafik.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kelas 		= (android.widget.Spinner) findViewById(R.id.thn_ajaran);
        kelasList   = new ArrayList<SpinnerGuruKelas>();

        ListSpinner();

        Button nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Grafik();
            }
        });
    }

    public void ListSpinner() {

        class ParsingNegara extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Grafik.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s2) {
                super.onPostExecute(s2);
                loading.dismiss();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("thn_ajaran");
                    for(int i = 0; i < hasilArray.length(); i++){

                        JSONObject coun = hasilArray.getJSONObject(i);

                        SpinnerGuruKelas kelas = new SpinnerGuruKelas(coun.getString("idThnAjaran"), coun.getString("thnAjaran"));
                        kelasList.add(kelas);
                    }

                }catch (Exception e) {

                }

                kelasSpinner();
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences prefer = getSharedPreferences("session", Context.MODE_PRIVATE);
                String idGuru = prefer.getString("idGuru","Not Available" );

                HashMap<String, String> par2 = new HashMap<String, String>();
                par2.put("idGuru", idGuru);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res2 = parsing.sendPostRequest(url_thn, par2);
                return res2;
            }
        }

        ParsingNegara tambah = new ParsingNegara();
        tambah.execute();
    }

    private void kelasSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < kelasList.size(); i++) {
//            lables.add(kelasList.get(i).getId());
            lables.add(kelasList.get(i).getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        kelas.setAdapter(spinnerAdapter);
        kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                getkelas = kelasList.get(position).getId();
                getkelasname = kelasList.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void Grafik() {

        class parsingImg extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s2) {
                super.onPostExecute(s2);

//                Toast.makeText(PurchaseOutlet.this, ""+s2, Toast.LENGTH_SHORT).show();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    String hasil = jObj.getString("success");

                    if(hasil.equals("1")){

//                        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor e = s.edit();
//
//                        e.putString("semester", getsemester);
//                        e.commit();

                        Intent a = new Intent(Grafik.this, GrafikView.class);
//                        a.putExtra("semester", getsemester);
                        a.putExtra("idThnAjaran", getkelas);
                        startActivity(a);
                    }else{
                        Toast.makeText(getApplication(), "Data tidak ada", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences prefer = getSharedPreferences("session", Context.MODE_PRIVATE);
                String nis = prefer.getString("nis","Not Available" );

                HashMap<String, String> par2 = new HashMap<String, String>();
                par2.put("nis", nis);
                par2.put("idThnAjaran", getkelas);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res2 = parsing.sendPostRequest(url, par2);
                return res2;
            }
        }

        parsingImg tambah = new parsingImg();
        tambah.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

//            Intent a = new Intent(Grafik.this, Penilaian.class);
//            startActivity(a);
            ActivityCompat.finishAfterTransition(Grafik.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
