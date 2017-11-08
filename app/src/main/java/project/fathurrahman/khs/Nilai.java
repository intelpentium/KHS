package project.fathurrahman.khs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import project.fathurrahman.khs.Adapter.NilaiAdapter;
import project.fathurrahman.khs.Adapter.NilaiModel;

/**
 * Created by Fathurrahman on 06/12/2016.
 */

public class Nilai extends AppCompatActivity{

    private RecyclerView recyclerView;
    private NilaiAdapter adapter;
    private List<NilaiModel> NilaiModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nilai);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        NilaiModelList = new ArrayList<>();

        NilaiModel kostData = new NilaiModel("1", "Tugas");
        NilaiModelList.add(kostData);

        kostData = new NilaiModel("2", "Ulangan");
        NilaiModelList.add(kostData);

        kostData = new NilaiModel("3", "Ujian Tengah Semester");
        NilaiModelList.add(kostData);

        kostData = new NilaiModel("4", "Ujian Akhir Semester");
        NilaiModelList.add(kostData);

        kostData = new NilaiModel("5", "Nilai Akhir");
        NilaiModelList.add(kostData);


        adapter = new NilaiAdapter(Nilai.this, NilaiModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Nilai.this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            Intent a = new Intent(Nilai.this, Home.class);
            startActivity(a);

//            ActivityCompat.finishAfterTransition(Nilai.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
