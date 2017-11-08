package project.bundamulia.sd.Guru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import project.bundamulia.sd.Adapter.AdapterGuru.PenilaianAdapter;
import project.bundamulia.sd.Adapter.AdapterGuru.PenilaianModel;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class Penilaian extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PenilaianAdapter adapter;
    private List<PenilaianModel> PenilaianModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penilaian);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        PenilaianModelList = new ArrayList<>();

        SharedPreferences s = getSharedPreferences("session", Context.MODE_PRIVATE);
        String status = s.getString("status","-");

        if(status == "GURU WALI KELAS" || status.equals("GURU WALI KELAS")){

            PenilaianModel kostData = new PenilaianModel("1", "Presensi Siswa");
            PenilaianModelList.add(kostData);

            kostData = new PenilaianModel("2", "Penilaian Sikap");
            PenilaianModelList.add(kostData);
        }

        adapter = new PenilaianAdapter(Penilaian.this, PenilaianModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Penilaian.this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            Intent a = new Intent(Penilaian.this, HomeGuru.class);
            startActivity(a);

//            ActivityCompat.finishAfterTransition(Penilaian.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
