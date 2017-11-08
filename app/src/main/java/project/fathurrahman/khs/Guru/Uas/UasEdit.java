package project.fathurrahman.khs.Guru.Uas;

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

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Guru.Uas.UasEdit;
import project.fathurrahman.khs.Guru.Uas.UasSiswa;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 14/12/2016.
 */

public class UasEdit extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/UasEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/UasView.php";

    String nilaiUas1, nilaiUas2, nilaiUas3, nilaiUas4, nilaiUas5, nilaiUas6, nilaiUas7;
    String idUas1, idUas2, idUas3, idUas4, idUas5, idUas6, idUas7;

    EditText Uas1, Uas2, Uas3, Uas4, Uas5, Uas6, Uas7;

    String[] nilaiT;
    String[] idT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uas_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        Uas1 = (EditText) findViewById(R.id.tugas1);
        Uas2 = (EditText) findViewById(R.id.tugas2);

        UasView();

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nilaiUas1 = Uas1.getText().toString();
                nilaiUas2 = Uas2.getText().toString();

                idUas1 = idT[0];
                idUas2 = idT[1];

                UasEdit();
            }
        });
    }

    private void UasView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UasEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(UasEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("Uas");

                    nilaiT = new String[hasilArray.length()];
                    idT = new String[hasilArray.length()];

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        nilaiT[it] = coun.getString("nilaiUas");
                        idT[it] = coun.getString("idUas");
                    }

                    Uas1.setText(""+nilaiT[0]);
                    Uas2.setText(""+nilaiT[1]);

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

    private void UasEdit(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UasEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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
                        Intent a = new Intent(UasEdit.this, UasSiswa.class);
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
                par.put("nilaiUas1", nilaiUas1);
                par.put("nilaiUas2", nilaiUas2);

                par.put("idUas1", idUas1);
                par.put("idUas2", idUas2);

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
            ActivityCompat.finishAfterTransition(UasEdit.this);
//            finish();

//            Intent a = new Intent(UasEdit.this, ListSiswa.class);
//            startActivity(a);
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
