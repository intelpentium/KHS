package project.fathurrahman.khs.Guru.NilaiAkhir;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterNilaiAkhir.NilaiAkhirSiswaAdapter;
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterNilaiAkhir.NilaiAkhirSiswaModel;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class NilaiAkhirSiswa extends AppCompatActivity {

    String url = "http://sdbundamulia.com/ws_khs/TugasSiswa.php";
    String getnis, getidMtpljrn, getidKelas, getidThnAjaran;

    private RecyclerView recyclerView;
    private NilaiAkhirSiswaAdapter adapter;
    private List<NilaiAkhirSiswaModel> NilaiAkhirSiswaModelList;

    ProgressBar progress;
    SwipeRefreshLayout swLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas_siswa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        getnis = a.getString("nis");
        getidMtpljrn = a.getString("idMtPljrn");
        getidThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        getidKelas = s.getString("idKelas","-");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progress=(ProgressBar) findViewById(R.id.progressbar_loading);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);

        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ViewNilaiAkhir();

                swLayout.setRefreshing(false);
            }
        });

        ViewNilaiAkhir();
    }

    private void ViewNilaiAkhir(){

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

//                Toast.makeText(NilaiAkhirSiswa.this, ""+s2, Toast.LENGTH_SHORT).show();
                NilaiAkhirSiswaModelList = new ArrayList<>();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("siswa");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        NilaiAkhirSiswaModel kostData = new NilaiAkhirSiswaModel();
                        kostData.nis     = coun.getString("nis");
                        kostData.nama  = coun.getString("namaSiswa");
                        kostData.idMtPljrn = coun.getString("idMtpljrn");
                        kostData.idThnAjaran = coun.getString("idThnAjaran");
                        NilaiAkhirSiswaModelList.add(kostData);
                    }

                    adapter = new NilaiAkhirSiswaAdapter(NilaiAkhirSiswa.this, NilaiAkhirSiswaModelList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NilaiAkhirSiswa.this));
                    adapter.notifyDataSetChanged();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(NilaiAkhirSiswa.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
