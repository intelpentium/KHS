package project.bundamulia.sd.Guru.NilaiAkhir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 14/12/2016.
 */

public class NilaiAkhirEdit extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String nilaiTugas, nilaiUlangan, nilaiUts, nilaiUas, idNAkhir, pembagiUl, pembagiTugas;
    int hasilTugas, hasilUlangan, hasil_nilai_akhir;

    EditText nilai_pembagi_tugas, nilai_pembagi_ulangan;
    TextView jml_tugas, rata_tugas, jml_ulangan, rata_ulangan, nilai_uts, nilai_uas, nilai_akhir;

    String url = "http://sdbundamulia.com/ws_khs/NilaiAkhirEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/NilaiAkhirView.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nilai_akhir_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        rata_tugas = (TextView) findViewById(R.id.rata_tugas);
        rata_ulangan = (TextView) findViewById(R.id.rata_ulangan);

        nilai_uts = (TextView) findViewById(R.id.nilai_uts);
        nilai_uas = (TextView) findViewById(R.id.nilai_uas);
        nilai_akhir = (TextView) findViewById(R.id.nilai_akhir);

        NilaiView();

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NilaiInput();
//                checkValidation();
            }
        });
    }

    private void NilaiView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NilaiAkhirEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(NilaiAkhirEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    nilaiTugas = jObj.getString("nilaiTugas");
//                    jml_tugas.setText(""+nilaiTugas);

                    nilaiUlangan = jObj.getString("nilaiUl");
//                    jml_ulangan.setText(""+nilaiUlangan);

                    nilaiUts = jObj.getString("nilaiUts");
                    nilai_uts.setText(""+nilaiUts);

                    nilaiUas = jObj.getString("nilaiUas");
                    nilai_uas.setText(""+nilaiUas);

                    pembagiTugas = jObj.getString("pembagiTugas");
//                    nilai_pembagi_tugas.setText(""+pembagiTugas);

                    pembagiUl = jObj.getString("pembagiUl");
//                    nilai_pembagi_ulangan.setText(""+pembagiUl);

                    hasilTugas = Integer.parseInt(nilaiTugas) / Integer.parseInt(pembagiTugas);
                    hasilUlangan = Integer.parseInt(nilaiUlangan) / Integer.parseInt(pembagiUl);
                    hasil_nilai_akhir = (hasilTugas + Integer.parseInt(nilaiUts)+ Integer.parseInt(nilaiUas))/4;

                    rata_tugas.setText("" + hasilTugas);
                    rata_ulangan.setText("" + hasilUlangan);
                    nilai_akhir.setText("" + hasil_nilai_akhir);

                    idNAkhir = jObj.getString("idNAkhir");

//                    Toast.makeText(getApplicationContext(), ""+idNAkhir, Toast.LENGTH_LONG).show();

                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("idMtpljrn", idMtPljrn);
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

    private void NilaiInput(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NilaiAkhirEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(NilaiAkhirEdit.this, ""+s, Toast.LENGTH_SHORT).show();

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

                        Intent a = new Intent(NilaiAkhirEdit.this, NilaiAkhirSiswa.class);
                        a.putExtra("nis", nis);
                        a.putExtra("idMtPljrn", idMtPljrn);
                        a.putExtra("idThnAjaran", idThnAjaran);
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
                par.put("idNAkhir", idNAkhir);
                par.put("nilaiAkhir", String.valueOf(hasil_nilai_akhir));

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
            ActivityCompat.finishAfterTransition(NilaiAkhirEdit.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
