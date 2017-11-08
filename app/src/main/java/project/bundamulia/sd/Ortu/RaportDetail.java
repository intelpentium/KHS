package project.bundamulia.sd.Ortu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.Adapter.AdapterOrtu.Raport.RaportAdapter;
import project.bundamulia.sd.Adapter.AdapterOrtu.Raport.RaportModel;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 17/12/2016.
 */

public class RaportDetail extends AppCompatActivity {

    String idThnAjaran, semester;

    TextView sikap, kerajinan, kebersihan, ijin, sakit, absen, catatan;

    String url_view = "http://sdbundamulia.com/ws_khs/Raport.php";

    private RecyclerView recyclerView;
    private RaportAdapter adapter;
    private List<RaportModel> RaportModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raport_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Bundle a = getIntent().getExtras();
        semester = a.getString("semester");
        idThnAjaran = a.getString("idThnAjaran");

        sikap = (TextView) findViewById(R.id.sikap);
        kerajinan = (TextView) findViewById(R.id.kerajinan);
        kebersihan = (TextView) findViewById(R.id.kebersihan);

        ijin = (TextView) findViewById(R.id.ijin);
        sakit = (TextView) findViewById(R.id.sakit);
        absen = (TextView) findViewById(R.id.absen);
        catatan = (TextView) findViewById(R.id.catatan);

        NilaiView();
    }

    private void NilaiView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RaportDetail.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(RaportDetail.this, ""+s, Toast.LENGTH_SHORT).show();
                RaportModelList = new ArrayList<>();
                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
//                    String result = jObj.getString("success");

                    JSONArray hasilArray = jObj.getJSONArray("raport");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

//                        Toast.makeText(RaportDetail.this, ""+coun.getString("mtPljrn"), Toast.LENGTH_SHORT).show();

                        RaportModel kostData = new RaportModel();
                        kostData.mtPljrn  = coun.getString("mtPljrn");
                        kostData.nilaiAkhir     = coun.getString("nilaiAkhir");
                        kostData.ket  = coun.getString("ket");
                        RaportModelList.add(kostData);
                    }

                    adapter = new RaportAdapter(RaportDetail.this, RaportModelList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RaportDetail.this));
                    adapter.notifyDataSetChanged();

                    catatan.setText(""+jObj.getString("catatan"));
                    sikap.setText(""+jObj.getString("sikap"));
                    kerajinan.setText(""+jObj.getString("kerajinan"));
                    kebersihan.setText(""+jObj.getString("kebersihan"));

                    ijin.setText(""+jObj.getString("izin"));
                    sakit.setText(""+jObj.getString("sakit"));
                    absen.setText(""+jObj.getString("absen"));

                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences prefer = getSharedPreferences("session", Context.MODE_PRIVATE);
                String nis = prefer.getString("nis","Not Available" );

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);
                par.put("semester", semester);
                par.put("idThnAjaran", idThnAjaran);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_view, par);
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

//            Intent a = new Intent(Raport.this, HomeOrtu.class);
//            startActivity(a);
            ActivityCompat.finishAfterTransition(RaportDetail.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
