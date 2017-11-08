package project.bundamulia.sd.Guru;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import project.bundamulia.sd.Adapter.AdapterGuru.HomeGuruAdapter;
import project.bundamulia.sd.Guru.ListSiswa.ListSiswa;
import project.bundamulia.sd.Login;
import project.bundamulia.sd.R;

/**
 * Created by Fathurrahman on 02/12/2016.
 */

public class HomeGuru extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        gridView.setAdapter(new HomeGuruAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if(position==0){

                    Intent intent = new Intent(HomeGuru.this, ListSiswa.class);
                    startActivity(intent);

                }else if(position==1){
                    Intent a = new Intent(HomeGuru.this, Nilai.class);
                    startActivity(a);

                }else if(position==2) {
//                    Toast.makeText(getApplication(), "Data Berhasil. Selamat !!", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(HomeGuru.this, Penilaian.class);
                    startActivity(a);

                }else if(position==3) {
//                    Toast.makeText(getApplication(), "Data Berhasil. Selamat !!", Toast.LENGTH_LONG).show();
                    Intent a = new Intent(HomeGuru.this, Agenda.class);
                    startActivity(a);

                }else if(position==4){

//                    SharedPreferences preferences = getSharedPreferences("session",Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.clear();
//                    editor.commit();
//
//                    Intent logout = new Intent(getApplication(), Login.class);
//                    startActivity(logout);
//
//                    finish();

                    logout();

                }
            }
        });
    }

    //Method untuk proses Logout User
    private void logout(){
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Untuk logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(HomeGuru.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
