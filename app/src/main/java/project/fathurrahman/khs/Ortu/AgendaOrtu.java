package project.fathurrahman.khs.Ortu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.AdapterOrtu.AdapterAgenda.AgendaAdapter;
import project.fathurrahman.khs.Adapter.AdapterOrtu.AdapterAgenda.AgendaModel;
import project.fathurrahman.khs.Adapter.AdapterOrtu.Raport.RaportAdapter;
import project.fathurrahman.khs.Adapter.AdapterOrtu.Raport.RaportModel;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 20/12/2016.
 */

public class AgendaOrtu extends AppCompatActivity {

    String url_view = "http://sdbundamulia.com/ws_khs/Agenda.php";

    private ListView messagesContainer;
    private AgendaAdapter adapter;
    private ArrayList<AgendaModel> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ortu_agenda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        messagesContainer = (ListView) findViewById(R.id.messagesContainer);

        NilaiView();
    }

    private void NilaiView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AgendaOrtu.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(AgendaOrtu.this, ""+s, Toast.LENGTH_SHORT).show();

                chatHistory = new ArrayList<AgendaModel>();
                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("view");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

                        AgendaModel msg = new AgendaModel();
                        msg.setMe(true);
                        msg.setMessage(coun.getString("agenda"));
                        msg.setDate(coun.getString("date"));
                        chatHistory.add(msg);

//                        Toast.makeText(AgendaOrtu.this, ""+coun.getString("mtPljrn"), Toast.LENGTH_SHORT).show();
                    }

                    adapter = new AgendaAdapter(AgendaOrtu.this, new ArrayList<AgendaModel>());
                    messagesContainer.setAdapter(adapter);

                    for(int i=0; i<chatHistory.size(); i++) {
                        AgendaModel message = chatHistory.get(i);

                        adapter.add(message);
                        adapter.notifyDataSetChanged();
                        messagesContainer.setSelection(messagesContainer.getCount() - 1);
                    }

                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences prefer = getSharedPreferences("session", Context.MODE_PRIVATE);
                String nis = prefer.getString("nis","Not Available" );

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_view, par);
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

            Intent a = new Intent(AgendaOrtu.this, HomeOrtu.class);
            startActivity(a);
//            ActivityCompat.finishAfterTransition(AgendaOrtu.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
