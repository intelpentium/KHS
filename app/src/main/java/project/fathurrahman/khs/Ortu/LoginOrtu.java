package project.fathurrahman.khs.Ortu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 02/12/2016.
 */

public class LoginOrtu extends AppCompatActivity {

    EditText et_username,et_password;

    LinearLayout LayoutLogin;
    Animation shakeAnimation;

    String getusernameId, getPassword;
    String url = "http://sdbundamulia.com/ws_khs/LoginOrtu.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ortu);

        // panggil edittext
        et_username         = (EditText) findViewById(R.id.login_nis);
        et_password         = (EditText) findViewById(R.id.login_password);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });

        LayoutLogin     = (LinearLayout) findViewById(R.id.LayoutLogin);
        shakeAnimation  = AnimationUtils.loadAnimation(LoginOrtu.this, R.anim.shake);
    }

    // untuk check validasi
    private void checkValidation() {

        // ambil data didalam edittext
        getusernameId = et_username.getText().toString().trim();
        getPassword = et_password.getText().toString().trim();

        if (getusernameId.equals("") || getusernameId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0){

            // validasi
            LayoutLogin.startAnimation(shakeAnimation);
            Snackbar.make(findViewById(android.R.id.content), "Data tidak boleh kosong !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else {
            Login();
        }
    }

    private void Login(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // loading buka
                loading = ProgressDialog.show(LoginOrtu.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // loading tutup
                loading.dismiss();

//                Toast.makeText(Login.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah pesan
                    String result = jObj.getString("success"); // ambil dari API

                    if(result.trim().equals("1")){

                        // session
                        SharedPreferences preferences = LoginOrtu.this
                                .getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("nis", jObj.getString("nis"));
                        editor.commit();

                        // lempar tampilan (header location)
                        Intent a = new Intent(LoginOrtu.this, HomeOrtu.class);
                        startActivity(a);
                        finish();
                    }else{

                        LayoutLogin.startAnimation(shakeAnimation);
                        Snackbar.make(findViewById(android.R.id.content), "Username anda tidak terdaftar !!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }catch (Exception e) {

                }
            }

            // fungsi untuk kirim ke API
            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                // dikirim ke API
                HashMap<String, String> par = new HashMap<String, String>();
                par.put("username", getusernameId);
                par.put("password", getPassword);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        // execute class
        ParsingLogin a = new ParsingLogin();
        a.execute();

    }
}
