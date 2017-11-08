package project.bundamulia.sd.Guru;

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

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.R;

/**
 * Created by Fathurrahman on 02/12/2016.
 */

public class LoginGuru extends AppCompatActivity {

    EditText et_username,et_password;

    LinearLayout LayoutLogin;
    Animation shakeAnimation;

    String getusernameId, getPassword;
    String url = "http://sdbundamulia.com/ws_khs/LoginGuru.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_teacher);

        et_username         = (EditText) findViewById(R.id.login_username);
        et_password         = (EditText) findViewById(R.id.login_password);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();

//                Intent it = new Intent(LoginGuru.this(), HomeOrtu.class);
//                startActivity(it);
            }
        });

        LayoutLogin     = (LinearLayout) findViewById(R.id.LayoutLogin);
        shakeAnimation  = AnimationUtils.loadAnimation(LoginGuru.this, R.anim.shake);
    }

    private void checkValidation() {

        getusernameId = et_username.getText().toString().trim();
        getPassword = et_password.getText().toString().trim();

        if (getusernameId.equals("") || getusernameId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0){

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
                loading = ProgressDialog.show(LoginGuru.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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

                    if(result.trim().equals("1")){

                        SharedPreferences preferences = LoginGuru.this
                                .getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("idGuru", jObj.getString("idGuru"));
                        editor.putString("status", jObj.getString("status"));
                        editor.commit();

                        Intent a = new Intent(LoginGuru.this, HomeGuru.class);
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

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("username", getusernameId);
                par.put("password", getPassword);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();

    }
}
