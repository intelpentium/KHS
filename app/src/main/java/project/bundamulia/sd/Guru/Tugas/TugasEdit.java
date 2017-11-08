package project.bundamulia.sd.Guru.Tugas;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 14/12/2016.
 */

public class TugasEdit extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/TugasEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/TugasView.php";

    String nilaiTugas1, nilaiTugas2, nilaiTugas3, nilaiTugas4, nilaiTugas5, nilaiTugas6, nilaiTugas7;
    String idTugas1, idTugas2, idTugas3, idTugas4, idTugas5, idTugas6, idTugas7;

    EditText tugas1, tugas2, tugas3, tugas4, tugas5, tugas6, tugas7;

    String[] nilaiT;
    String[] idT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        tugas1 = (EditText) findViewById(R.id.tugas1);
        tugas2 = (EditText) findViewById(R.id.tugas2);
        tugas3 = (EditText) findViewById(R.id.tugas3);
        tugas4 = (EditText) findViewById(R.id.tugas4);
        tugas5 = (EditText) findViewById(R.id.tugas5);
        tugas6 = (EditText) findViewById(R.id.tugas6);
        tugas7 = (EditText) findViewById(R.id.tugas7);

        TugasView();

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nilaiTugas1 = tugas1.getText().toString();
                nilaiTugas2 = tugas2.getText().toString();
                nilaiTugas3 = tugas3.getText().toString();
                nilaiTugas4 = tugas4.getText().toString();
                nilaiTugas5 = tugas5.getText().toString();
                nilaiTugas6 = tugas6.getText().toString();
                nilaiTugas7 = tugas7.getText().toString();

                idTugas1 = idT[0];
                idTugas2 = idT[1];
                idTugas3 = idT[2];
                idTugas4 = idT[3];
                idTugas5 = idT[4];
                idTugas6 = idT[5];
                idTugas7 = idT[6];

                TugasEdit();
            }
        });
    }

    private void TugasView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TugasEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(TugasEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("tugas");

                    nilaiT = new String[hasilArray.length()];
                    idT = new String[hasilArray.length()];

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        nilaiT[it] = coun.getString("nilaiTugas");
                        idT[it] = coun.getString("idTugas");
                    }

                    // untuk menampilkan data
                    tugas1.setText(""+nilaiT[0]);
                    tugas2.setText(""+nilaiT[1]);
                    tugas3.setText(""+nilaiT[2]);
                    tugas4.setText(""+nilaiT[3]);
                    tugas5.setText(""+nilaiT[4]);
                    tugas6.setText(""+nilaiT[5]);
                    tugas7.setText(""+nilaiT[6]);

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

    private void TugasEdit(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TugasEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(Login.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.equals("1")){


                        Toast.makeText(getApplication(), "Data berhasil diupdate", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(TugasEdit.this, TugasSiswa.class);
                        a.putExtra("nis", nis);
                        a.putExtra("idMtPljrn", idMtPljrn);
                        a.putExtra("idThnAjaran", idThnAjaran);
                        startActivity(a);
                        finish();
                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nilaiTugas1", nilaiTugas1);
                par.put("nilaiTugas2", nilaiTugas2);
                par.put("nilaiTugas3", nilaiTugas3);
                par.put("nilaiTugas4", nilaiTugas4);
                par.put("nilaiTugas5", nilaiTugas5);
                par.put("nilaiTugas6", nilaiTugas6);
                par.put("nilaiTugas7", nilaiTugas7);

                par.put("idTugas1", idTugas1);
                par.put("idTugas2", idTugas2);
                par.put("idTugas3", idTugas3);
                par.put("idTugas4", idTugas4);
                par.put("idTugas5", idTugas5);
                par.put("idTugas6", idTugas6);
                par.put("idTugas7", idTugas7);

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
            ActivityCompat.finishAfterTransition(TugasEdit.this);
//            finish();

//            Intent a = new Intent(TugasEdit.this, ListSiswa.class);
//            startActivity(a);
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
