package project.fathurrahman.khs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;

/**
 * Created by Fathurrahman on 06/12/2016.
 */

public class ListSiswaInput extends AppCompatActivity{

    EditText phone_ortu, phone_rumah, phone_hp;
    TextView nama;

    LinearLayout LayoutSiswa;
    Animation shakeAnimation;

    String getphone_ortu, getphone_rumah, getphone_hp, getnama, getlahir, getnis;
    String url = "http://sdbundamulia.com/ws_khs/SiswaInput.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_siswa_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        getnama = a.getString("nama_siswa");
        getlahir = a.getString("lahir_siswa");
        getnis = a.getString("nis");

        phone_ortu = (EditText) findViewById(R.id.phone_ortu);
        phone_rumah = (EditText) findViewById(R.id.phone_rumah);
        phone_hp = (EditText) findViewById(R.id.phone_hp);

        nama = (TextView) findViewById(R.id.nama);
        nama.setText(getnama);

        LayoutSiswa     = (LinearLayout) findViewById(R.id.LayoutSiswa);
        shakeAnimation  = AnimationUtils.loadAnimation(ListSiswaInput.this, R.anim.shake);

        Button simpanBtn = (Button)findViewById(R.id.simpanBtn);
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });
    }

    private void checkValidation() {

        getphone_ortu = phone_ortu.getText().toString().trim();
        getphone_rumah = phone_rumah.getText().toString().trim();
        getphone_hp = phone_hp.getText().toString().trim();

//        if (getphone_ortu.equals("") || getphone_ortu.length() == 0
//                || getphone_rumah.equals("") || getphone_rumah.length() == 0
//                || getphone_hp.equals("") || getphone_hp.length() == 0){
//
//            LayoutSiswa.startAnimation(shakeAnimation);
//            Snackbar.make(findViewById(android.R.id.content), "Data tidak boleh kosong !!", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
//        else {
            SiswaInput();
//        }
    }

    private void SiswaInput(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListSiswaInput.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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

                        Toast.makeText(getApplication(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(ListSiswaInput.this, ListSiswa.class);
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
                par.put("nis", getnis);
                par.put("namaSiswa", getnama);
                par.put("phoneOrtu", getphone_ortu);
                par.put("phoneRumah", getphone_rumah);
                par.put("phoneHp", getphone_hp);
                par.put("tglLahir", getlahir);

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
//            ActivityCompat.finishAfterTransition(ListSiswaInput.this);
//            finish();

            Intent a = new Intent(ListSiswaInput.this, ListSiswa.class);
            startActivity(a);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
