package project.bundamulia.sd.Guru.Ulangan;

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

public class UlanganEdit extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/UlanganEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/UlanganView.php";

    String nilaiUlangan1, nilaiUlangan2, nilaiUlangan3, nilaiUlangan4, nilaiUlangan5, nilaiUlangan6, nilaiUlangan7;
    String idUlangan1, idUlangan2, idUlangan3, idUlangan4, idUlangan5, idUlangan6, idUlangan7;

    EditText Ulangan1, Ulangan2, Ulangan3, Ulangan4, Ulangan5, Ulangan6, Ulangan7;

    String[] nilaiT;
    String[] idT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ulangan_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        Ulangan1 = (EditText) findViewById(R.id.tugas1);
        Ulangan2 = (EditText) findViewById(R.id.tugas2);
        Ulangan3 = (EditText) findViewById(R.id.tugas3);
        Ulangan4 = (EditText) findViewById(R.id.tugas4);
        Ulangan5 = (EditText) findViewById(R.id.tugas5);
        Ulangan6 = (EditText) findViewById(R.id.tugas6);
        Ulangan7 = (EditText) findViewById(R.id.tugas7);

        UlanganView();

        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nilaiUlangan1 = Ulangan1.getText().toString();
                nilaiUlangan2 = Ulangan2.getText().toString();
                nilaiUlangan3 = Ulangan3.getText().toString();
                nilaiUlangan4 = Ulangan4.getText().toString();
                nilaiUlangan5 = Ulangan5.getText().toString();
                nilaiUlangan6 = Ulangan6.getText().toString();
                nilaiUlangan7 = Ulangan7.getText().toString();

                idUlangan1 = idT[0];
                idUlangan2 = idT[1];
                idUlangan3 = idT[2];
                idUlangan4 = idT[3];
                idUlangan5 = idT[4];
                idUlangan6 = idT[5];
                idUlangan7 = idT[6];

                UlanganEdit();
            }
        });
    }

    private void UlanganView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UlanganEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(UlanganEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("ulangan");

                    nilaiT = new String[hasilArray.length()];
                    idT = new String[hasilArray.length()];

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        nilaiT[it] = coun.getString("nilaiUlangan");
                        idT[it] = coun.getString("idUlangan");
                    }

                    Ulangan1.setText(""+nilaiT[0]);
                    Ulangan2.setText(""+nilaiT[1]);
                    Ulangan3.setText(""+nilaiT[2]);
                    Ulangan4.setText(""+nilaiT[3]);
                    Ulangan5.setText(""+nilaiT[4]);
                    Ulangan6.setText(""+nilaiT[5]);
                    Ulangan7.setText(""+nilaiT[6]);

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

    private void UlanganEdit(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UlanganEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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
                        Intent a = new Intent(UlanganEdit.this, UlanganSiswa.class);
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
                par.put("nilaiUlangan1", nilaiUlangan1);
                par.put("nilaiUlangan2", nilaiUlangan2);
                par.put("nilaiUlangan3", nilaiUlangan3);
                par.put("nilaiUlangan4", nilaiUlangan4);
                par.put("nilaiUlangan5", nilaiUlangan5);
                par.put("nilaiUlangan6", nilaiUlangan6);
                par.put("nilaiUlangan7", nilaiUlangan7);

                par.put("idUlangan1", idUlangan1);
                par.put("idUlangan2", idUlangan2);
                par.put("idUlangan3", idUlangan3);
                par.put("idUlangan4", idUlangan4);
                par.put("idUlangan5", idUlangan5);
                par.put("idUlangan6", idUlangan6);
                par.put("idUlangan7", idUlangan7);

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
            ActivityCompat.finishAfterTransition(UlanganEdit.this);
//            finish();

//            Intent a = new Intent(UlanganEdit.this, ListSiswa.class);
//            startActivity(a);
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
