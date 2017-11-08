package project.fathurrahman.khs.Tugas;

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
import project.fathurrahman.khs.Adapter.AdapterTugas.TugasSiswaAdapter;
import project.fathurrahman.khs.Adapter.AdapterTugas.TugasSiswaModel;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class TugasSiswa extends AppCompatActivity {

    String url = "http://sdbundamulia.com/ws_khs/TugasSiswa.php";
    String getnis, getidMtpljrn, getidKelas, getidThnAjaran;

    private RecyclerView recyclerView;
    private TugasSiswaAdapter adapter;
    private List<TugasSiswaModel> TugasSiswaModelList;

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
        getidKelas = a.getString("idKelas");
        getidThnAjaran = a.getString("idThnAjaran");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progress=(ProgressBar) findViewById(R.id.progressbar_loading);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);

        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ViewTugas();

                swLayout.setRefreshing(false);
            }
        });

        ViewTugas();
    }

    private void ViewTugas(){

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
                TugasSiswaModelList = new ArrayList<>();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("siswa");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        TugasSiswaModel kostData = new TugasSiswaModel();
                        kostData.nis     = coun.getString("nis");
                        kostData.nama  = coun.getString("namaSiswa");
                        kostData.idMtPljrn = coun.getString("idMtpljrn");
                        kostData.idThnAjaran = coun.getString("idThnAjaran");
                        TugasSiswaModelList.add(kostData);
                    }

                    adapter = new TugasSiswaAdapter(TugasSiswa.this, TugasSiswaModelList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TugasSiswa.this));
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
            ActivityCompat.finishAfterTransition(TugasSiswa.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
