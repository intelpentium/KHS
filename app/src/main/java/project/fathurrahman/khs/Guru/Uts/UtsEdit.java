package project.fathurrahman.khs.Guru.Uts;

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
import project.fathurrahman.khs.Guru.Uts.UtsEdit;
import project.fathurrahman.khs.Guru.Uts.UtsSiswa;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 14/12/2016.
 */

public class UtsEdit extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/UtsEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/UtsView.php";

    String nilaiUts1, nilaiUts2, nilaiUts3, nilaiUts4, nilaiUts5, nilaiUts6, nilaiUts7;
    String idUts1, idUts2, idUts3, idUts4, idUts5, idUts6, idUts7;

    EditText Uts1, Uts2, Uts3, Uts4, Uts5, Uts6, Uts7;

    String[] nilaiT;
    String[] idT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uts_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        Uts1 = (EditText) findViewById(R.id.tugas1);
        Uts2 = (EditText) findViewById(R.id.tugas2);

        UtsView();

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nilaiUts1 = Uts1.getText().toString();
                nilaiUts2 = Uts2.getText().toString();

                idUts1 = idT[0];
                idUts2 = idT[1];

                UtsEdit();
            }
        });
    }

    private void UtsView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UtsEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(UtsEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("Uts");

                    nilaiT = new String[hasilArray.length()];
                    idT = new String[hasilArray.length()];

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        nilaiT[it] = coun.getString("nilaiUts");
                        idT[it] = coun.getString("idUts");
                    }

                    Uts1.setText(""+nilaiT[0]);
                    Uts2.setText(""+nilaiT[1]);

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

    private void UtsEdit(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UtsEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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
                        Intent a = new Intent(UtsEdit.this, UtsSiswa.class);
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
                par.put("nilaiUts1", nilaiUts1);
                par.put("nilaiUts2", nilaiUts2);

                par.put("idUts1", idUts1);
                par.put("idUts2", idUts2);

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
            ActivityCompat.finishAfterTransition(UtsEdit.this);
//            finish();

//            Intent a = new Intent(UtsEdit.this, ListSiswa.class);
//            startActivity(a);
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
