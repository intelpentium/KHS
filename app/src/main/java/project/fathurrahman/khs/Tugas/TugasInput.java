package project.fathurrahman.khs.Tugas;

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
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class TugasInput extends AppCompatActivity {

    String idMtPljrn, idThnAjaran, semester, nis;
    String url = "http://sdbundamulia.com/ws_khs/TugasInput.php";
    String nilaiTugas1, nilaiTugas2, nilaiTugas3, nilaiTugas4, nilaiTugas5, nilaiTugas6, nilaiTugas7;

    EditText tugas1, tugas2, tugas3, tugas4, tugas5, tugas6, tugas7;

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


        Button simpan = (Button) findViewById(R.id.simpanBtn);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        nilaiTugas1 = tugas1.getText().toString();
        nilaiTugas2 = tugas2.getText().toString();
        nilaiTugas3 = tugas3.getText().toString();
        nilaiTugas4 = tugas4.getText().toString();
        nilaiTugas5 = tugas5.getText().toString();
        nilaiTugas6 = tugas6.getText().toString();
        nilaiTugas7 = tugas7.getText().toString();

        if (nilaiTugas1.equals("") || nilaiTugas1.length() == 0){
            nilaiTugas1 = "0";
        }
        if (nilaiTugas2.equals("") || nilaiTugas2.length() == 0){
            nilaiTugas2 = "0";
        }
        if (nilaiTugas3.equals("") || nilaiTugas3.length() == 0){
            nilaiTugas3 = "0";
        }
        if (nilaiTugas4.equals("") || nilaiTugas4.length() == 0){
            nilaiTugas4 = "0";
        }
        if (nilaiTugas5.equals("") || nilaiTugas5.length() == 0){
            nilaiTugas5 = "0";
        }
        if (nilaiTugas6.equals("") || nilaiTugas6.length() == 0){
            nilaiTugas6 = "0";
        }
        if (nilaiTugas7.equals("") || nilaiTugas7.length() == 0){
            nilaiTugas7 = "0";
        }
//        else {
            Login();
//        }

//        Toast.makeText(getApplication(), ""+nis, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+idMtPljrn, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+idThnAjaran, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+semester, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiTugas1, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiTugas2, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(), ""+nilaiTugas3, Toast.LENGTH_LONG).show();
    }

    private void Login(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TugasInput.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(TugasInput.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("1")){

                        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.remove("semester");
                        editor.commit();

                        Intent a = new Intent(TugasInput.this, Nilai.class);
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
                par.put("nilaiTugas1", nilaiTugas1);
                par.put("nilaiTugas2", nilaiTugas2);
                par.put("nilaiTugas3", nilaiTugas3);
                par.put("nilaiTugas4", nilaiTugas4);
                par.put("nilaiTugas5", nilaiTugas5);
                par.put("nilaiTugas6", nilaiTugas6);
                par.put("nilaiTugas7", nilaiTugas7);

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
            ActivityCompat.finishAfterTransition(TugasInput.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
