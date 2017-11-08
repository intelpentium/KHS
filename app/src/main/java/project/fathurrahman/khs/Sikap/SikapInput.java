package project.fathurrahman.khs.Sikap;

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

import org.json.JSONObject;

import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Nilai;
import project.fathurrahman.khs.Penilaian;
import project.fathurrahman.khs.R;
import project.fathurrahman.khs.Tugas.TugasInput;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class SikapInput extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/SikapInput.php";
    String nilaisikap, nilaikerajinan, nilaikebersihan;

    EditText sikap, kerajinan, kebersihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sikap_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        nis = a.getString("nis");
        idMtPljrn = a.getString("idMtPljrn");
        idThnAjaran = a.getString("idThnAjaran");

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        semester = s.getString("semester","-");

        sikap = (EditText) findViewById(R.id.sikap);
        kerajinan = (EditText) findViewById(R.id.kerajinan);
        kebersihan = (EditText) findViewById(R.id.kebersihan);


        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        nilaisikap = sikap.getText().toString();
        nilaikerajinan = kerajinan.getText().toString();
        nilaikebersihan = kebersihan.getText().toString();

        if (nilaisikap.equals("") || nilaisikap.length() == 0){
            nilaisikap = "0";
        }
        if (nilaikerajinan.equals("") || nilaikerajinan.length() == 0){
            nilaikerajinan = "0";
        }
        if (nilaikebersihan.equals("") || nilaikebersihan.length() == 0){
            nilaikebersihan = "0";
        }
        Login();
    }

    private void Login(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SikapInput.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(SikapInput.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("1")){

                        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.remove("semester");
                        editor.commit();

                        Intent a = new Intent(SikapInput.this, Penilaian.class);
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
                par.put("idMtpljrn", idMtPljrn);
                par.put("nis", nis);
                par.put("semester", semester);
                par.put("idThnAjaran", idThnAjaran);
                par.put("sikap", nilaisikap);
                par.put("kerajinan", nilaikerajinan);
                par.put("kebersihan", nilaikebersihan);

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
            ActivityCompat.finishAfterTransition(SikapInput.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
