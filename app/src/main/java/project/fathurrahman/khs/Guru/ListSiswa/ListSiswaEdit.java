package project.fathurrahman.khs.Guru.ListSiswa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 14/12/2016.
 */

public class ListSiswaEdit extends AppCompatActivity {

    EditText phone_ortu, phone_rumah, phone_hp;
    TextView nama;
    ImageButton photo;

    private int PICK_IMAGE_REQUEST_1 = 1;
    private int PICK_IMAGE_REQUEST_2 = 2;
    private Bitmap bitmap =null;
    private Uri filePath=null;

    String getphone_ortu, getphone_rumah, getphone_hp, getnis;
    String url = "http://sdbundamulia.com/ws_khs/ListSiswaEdit.php";
    String url_view = "http://sdbundamulia.com/ws_khs/ListSiswaView.php";
    String url_gambar   = "http://sdbundamulia.com/ws_khs/ListSiswaUpload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_siswa_input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle a = getIntent().getExtras();
        getnis = a.getString("nis");

        SiswaView();

        phone_ortu = (EditText) findViewById(R.id.phone_ortu);
        phone_rumah = (EditText) findViewById(R.id.phone_rumah);
        phone_hp = (EditText) findViewById(R.id.phone_hp);

        nama = (TextView) findViewById(R.id.nama);

        Button simpanBtn = (Button)findViewById(R.id.simpanBtn);
        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getphone_ortu = phone_ortu.getText().toString().trim();
                getphone_rumah = phone_rumah.getText().toString().trim();
                getphone_hp = phone_hp.getText().toString().trim();

                SiswaEdit();
            }
        });

        photo = (ImageButton) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePhoto();
            }
        });
    }

    private void SiswaView(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListSiswaEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

//                Toast.makeText(ListSiswaEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.equals("1")){


                        Glide.with(ListSiswaEdit.this).load("http://sdbundamulia.com/ws_khs/photo/" +jObj.getString("dp")+".png")
                                .placeholder(R.drawable.error_image)
                                .error(R.drawable.error_image)
                                .into(photo);

                        nama.setText(jObj.getString("namaSiswa"));
                        phone_ortu.setText(jObj.getString("phoneOrtu"));
                        phone_rumah.setText(jObj.getString("phoneRumah"));
                        phone_hp.setText(jObj.getString("phoneHp"));

                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", getnis);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_view, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();

    }

    private void SiswaEdit(){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ListSiswaEdit.this, "Menampilkan Data...", "Silahkan Tunggu..." ,false, false);
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

                        uploadImage();

                        Toast.makeText(getApplication(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                        Intent a = new Intent(ListSiswaEdit.this, ListSiswa.class);
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
                par.put("phoneOrtu", getphone_ortu);
                par.put("phoneRumah", getphone_rumah);
                par.put("phoneHp", getphone_hp);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();

    }

    //    ================= upload photo ==============
    private void choosePhoto() {
        final CharSequence[] options = { "Ambil Photo", "Pilih dari Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ListSiswaEdit.this);
//        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil Photo")){

                    ambilPhoto();
                }
                else if (options[item].equals("Pilih dari Gallery")){

                    gallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void ambilPhoto() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST_1);
    }

    private void gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST_1 && resultCode == RESULT_OK){

            filePath = data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");

            photo.setImageBitmap(bitmap);
            photo.setScaleType(ImageView.ScaleType.FIT_XY);
//            try {
//
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//                Glide.with(ListSiswaEdit.this).load(filePath)
//                        .placeholder(R.drawable.error_image)
//                        .error(R.drawable.error_image)
//                        .into(image);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//            bitmap = (Bitmap) data.getExtras().get("data");

//            ImageButton.setImageBitmap(bitmap);
//            ImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
//            ImageButton.setAdjustViewBounds(true);
        }
        else if(requestCode == PICK_IMAGE_REQUEST_2) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Glide.with(ListSiswaEdit.this).load(filePath)
                            .placeholder(R.drawable.error_image)
                            .error(R.drawable.error_image)
                            .into(photo);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(ListSiswaEdit.this, "Menambahkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(ListSiswaEdit.this, "Upload sukses", Toast.LENGTH_SHORT).show();

//                Intent a = new Intent(ListSiswaEdit.this, MainActivity.class);
//                startActivity(a);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put("photo", uploadImage);
                data.put("nis", getnis);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_gambar, data);
                return res;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ActivityCompat.finishAfterTransition(ListSiswaEdit.this);
//            finish();

//            Intent a = new Intent(ListSiswaEdit.this, ListSiswa.class);
//            startActivity(a);
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
