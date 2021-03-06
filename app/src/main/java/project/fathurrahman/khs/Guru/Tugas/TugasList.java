package project.fathurrahman.khs.Guru.Tugas;

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
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterTugas.TugasListAdapter;
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterTugas.TugasListModel;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class TugasList extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TugasListAdapter adapter;
    private List<TugasListModel> TugasListModelList;

    ProgressBar progress;
    SwipeRefreshLayout swLayout;

    String url_thn = "http://sdbundamulia.com/ws_khs/TugasList.php";

    String getsemester, getidthnajaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas_list);

        // untuk menampilkan toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // untuk menampilkan tombol back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // untuk mengambil data yang dikirim
        Bundle a = getIntent().getExtras();
        getsemester = a.getString("semester");
        getidthnajaran = a.getString("idThnAjaran");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progress=(ProgressBar) findViewById(R.id.progressbar_loading);

        // refresh
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

//                Toast.makeText(TugasList.this, ""+s2, Toast.LENGTH_SHORT).show();
                TugasListModelList = new ArrayList<>();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("guru");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        // ambil data dari database
                        TugasListModel kostData = new TugasListModel();
                        kostData.nis     = coun.getString("nis");
                        kostData.idMtPljrn  = coun.getString("idMtpljrn");
                        kostData.mtPljrn  = coun.getString("mtPljrn");
                        kostData.idKelas = coun.getString("idKelas");
                        kostData.kelas = coun.getString("kelas");
                        kostData.idThnAjaran = coun.getString("idThnAjaran");
                        TugasListModelList.add(kostData);
                    }

                    adapter = new TugasListAdapter(TugasList.this, TugasListModelList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TugasList.this));
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
                par.put("idThnAjaran", getidthnajaran);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_thn, par);
                return res;
            }
        }

        ParsingSearch tambah = new ParsingSearch();
        tambah.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // fungsi untuk mengembalikan tampilan
        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(TugasList.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}