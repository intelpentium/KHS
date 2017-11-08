package project.bundamulia.sd.Ortu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 16/12/2016.
 */

public class GrafikView extends AppCompatActivity {

    String url = "http://sdbundamulia.com/ws_khs/Grafik.php";
    String idThnAjaran;

    ArrayList<IBarDataSet> dataSets = null;
    BarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik_siswa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chart = (BarChart) findViewById(R.id.chart);

        Intent ambil = getIntent();

        idThnAjaran = ambil.getStringExtra("idThnAjaran");

        Dailyluck();
    }

    private void Dailyluck(){

        class ParsingDailyluck extends AsyncTask<Void, Void, String> {

//            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(VerifyCofirmPin.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();

//                Toast.makeText(getApplication(), ""+s, Toast.LENGTH_SHORT).show();

                ArrayList<BarEntry> valueSet1 = new ArrayList<>();
                ArrayList<BarEntry> valueSet2 = new ArrayList<>();

                ArrayList<String> xAxis = new ArrayList<>();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    JSONArray hasilArray = jObj.getJSONArray("ulangan");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        String ambil = coun.getString("nilai");
                        BarEntry v1e1 = new BarEntry(Integer.parseInt(ambil), it);
                        valueSet1.add(v1e1);

                        xAxis.add(coun.getString("mp"));

                        if(coun.getString("semester").equals("2")){
                            valueSet1.remove(v1e1);
                            xAxis.remove(coun.getString("mp"));
                            xAxis.add(coun.getString("mp"));

                            String ambil2 = coun.getString("nilai");
                            BarEntry v2e1 = new BarEntry(Integer.parseInt(ambil2), it); // Jan
                            valueSet2.add(v2e1);
                        }




//                        if(coun.getString("semester").equals("2")) {
//                            xAxis.remove(coun.getString("mp"));
//                            xAxis.add("");
//                        }
                    }

                    BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Semester Ganjil");
                    barDataSet1.setColor(Color.rgb(0, 155, 0));
                    BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Semester Genap");
                    barDataSet2.setColor(Color.rgb(193, 37, 82));

                    dataSets = new ArrayList<>();
                    dataSets.add(barDataSet1);
                    dataSets.add(barDataSet2);

                    BarData data = new BarData(xAxis, dataSets);
                    chart.setData(data);
                    chart.setDescription("");
                    chart.animateXY(2, 200);
                    chart.invalidate();

                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
                String nis = s.getString("nis", "-");

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);
                par.put("idThnAjaran", idThnAjaran);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingDailyluck tambah = new ParsingDailyluck();
        tambah.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(GrafikView.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
