package project.fathurrahman.khs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.ListSiswaAdapter;
import project.fathurrahman.khs.Adapter.ListSiswaModel;

/**
 * Created by Fathurrahman on 03/12/2016.
 */

public class ListSiswa extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ListSiswaAdapter adapter;
    private List<ListSiswaModel> ListSiswaModelList;

    String url = "http://sdbundamulia.com/ws_khs/ListSiswa.php";

    ProgressBar progress;
    SwipeRefreshLayout swLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_siswa);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progress=(ProgressBar) findViewById(R.id.progressbar_loading);
        swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);

        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Search();

                swLayout.setRefreshing(false);
            }
        });

        Search();
    }

    private void Search(){

        class ParsingSearch extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s2) {
                super.onPostExecute(s2);

                progress.setVisibility(View.GONE);

//                Toast.makeText(ListSiswa.this, ""+s2, Toast.LENGTH_SHORT).show();
                ListSiswaModelList = new ArrayList<>();

                try {

                    String json = s2.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah

                    JSONArray hasilArray = jObj.getJSONArray("data_siswa");

                    for(int it = 0; it < hasilArray.length(); it++){

                        JSONObject coun = hasilArray.getJSONObject(it);

//                        mCountryModel.add(new CountryModel(coun.getString("nama_kategori"), coun.getString("nama_nama")));

                        ListSiswaModel kostData = new ListSiswaModel();
                        kostData.ID     = coun.getString("nis");
                        kostData.nis  = coun.getString("nis");
                        kostData.nama = coun.getString("namaSiswa");
                        kostData.lahir  = coun.getString("lahir");
//                        kostData.images = coun.getString("lahir");
                        ListSiswaModelList.add(kostData);
                    }

                    adapter = new ListSiswaAdapter(ListSiswa.this, ListSiswaModelList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListSiswa.this));
                    adapter.notifyDataSetChanged();

                }catch (Exception e) {
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences prefer = ListSiswa.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String idGuru = prefer.getString("idGuru","Not Available" );

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("idGuru", idGuru);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingSearch tambah = new ParsingSearch();
        tambah.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
//        item.expandActionView();

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(ListSiswa.this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(ListSiswaModelList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ListSiswaModel> filteredModelList = filter(ListSiswaModelList, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<ListSiswaModel> filter(List<ListSiswaModel> models, String query) {
        query = query.toLowerCase();

        final List<ListSiswaModel> filteredModelList = new ArrayList<>();
        for (ListSiswaModel model : models) {
            final String text = model.getnama().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
//            ActivityCompat.finishAfterTransition(ListSiswa.this);
//            finish();

            Intent a = new Intent(ListSiswa.this, Home.class);
            startActivity(a);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
