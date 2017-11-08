package project.fathurrahman.khs.Guru.Ulangan;

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
import project.fathurrahman.khs.Guru.Nilai;
import project.fathurrahman.khs.Guru.Uts.UtsSiswa;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class UlanganInput extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/UlanganInput.php";
    String nilaiUl1, nilaiUl2, nilaiUl3, nilaiUl4, nilaiUl5, nilaiUl6, nilaiUl7;

    EditText tugas1, tugas2, tugas3, tugas4, tugas5, tugas6, tugas7;

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

        tugas1 = (EditText) findViewById(R.id.tugas1);
        tugas2 = (EditText) findViewById(R.id.tugas2);
        tugas3 = (EditText) findViewById(R.id.tugas3);
        tugas4 = (EditText) findViewById(R.id.tugas4);
        tugas5 = (EditText) findViewById(R.id.tugas5);
        tugas6 = (EditText) findViewById(R.id.tugas6);
        tugas7 = (EditText) findViewById(R.id.tugas7);


        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        nilaiUl1 = tugas1.getText().toString();
        nilaiUl2 = tugas2.getText().toString();
        nilaiUl3 = tugas3.getText().toString();
        nilaiUl4 = tugas4.getText().toString();
        nilaiUl5 = tugas5.getText().toString();
        nilaiUl6 = tugas6.getText().toString();
        nilaiUl7 = tugas7.getText().toString();

        if (nilaiUl1.equals("") || nilaiUl1.length() == 0){
            nilaiUl1 = "0";
        }
        if (nilaiUl2.equals("") || nilaiUl2.length() == 0){
            nilaiUl2 = "0";
        }
        if (nilaiUl3.equals("") || nilaiUl3.length() == 0){
            nilaiUl3 = "0";
        }
        if (nilaiUl4.equals("") || nilaiUl4.length() == 0){
            nilaiUl4 = "0";
        }
        if (nilaiUl5.equals("") || nilaiUl5.length() == 0){
            nilaiUl5 = "0";
        }
        if (nilaiUl6.equals("") || nilaiUl6.length() == 0){
            nilaiUl6 = "0";
        }
        if (nilaiUl7.equals("") || nilaiUl7.length() == 0){
            nilaiUl7 = "0";
        }
//        else {
        Login();
//        }

//        Toast.makeText(getApplication(), ""+nis, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+idMtPljrn, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+idThnAjaran, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+semester, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiUl1, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiUl2, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiUl3, Toast.LENGTH_LONG).show();
    }

    private void Login(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UlanganInput.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(UlanganInput.this, ""+s, Toast.LENGTH_SHORT).show();

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

                        Intent a = new Intent(UlanganInput.this, UlanganSiswa.class);
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
                par.put("idMtpljrn", idMtPljrn);
                par.put("nis", nis);
                par.put("semester", semester);
                par.put("idThnAjaran", idThnAjaran);
                par.put("nilaiUl1", nilaiUl1);
                par.put("nilaiUl2", nilaiUl2);
                par.put("nilaiUl3", nilaiUl3);
                par.put("nilaiUl4", nilaiUl4);
                par.put("nilaiUl5", nilaiUl5);
                par.put("nilaiUl6", nilaiUl6);
                par.put("nilaiUl7", nilaiUl7);

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
            ActivityCompat.finishAfterTransition(UlanganInput.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
