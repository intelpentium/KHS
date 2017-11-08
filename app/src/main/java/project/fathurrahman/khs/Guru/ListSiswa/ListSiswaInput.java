package project.fathurrahman.khs.Guru.ListSiswa;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Fathurrahman on 06/12/2016.
 */

public class ListSiswaInput extends AppCompatActivity{

    EditText phone_ortu, phone_rumah, phone_hp;
    TextView nama;
    ImageButton photo;

    private int PICK_IMAGE_REQUEST_1 = 1;
    private int PICK_IMAGE_REQUEST_2 = 2;
    private Bitmap bitmap =null;
    private Uri filePath=null;

    LinearLayout LayoutSiswa;
    Animation shakeAnimation;

    String getphone_ortu, getphone_rumah, getphone_hp, getnama, getlahir, getnis;
    String url          = "http://sdbundamulia.com/ws_khs/ListSiswaInput.php";
    String url_gambar   = "http://sdbundamulia.com/ws_khs/ListSiswaUpload.php";

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

        photo = (ImageButton) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosePhoto();
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

                        uploadImage();

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

    //    ================= upload photo ==============
    private void choosePhoto() {
        final CharSequence[] options = { "Ambil Photo", "Pilih dari Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ListSiswaInput.this);
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
        }
        else if(requestCode == PICK_IMAGE_REQUEST_2) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {

                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    Glide.with(ListSiswaInput.this).load(filePath)
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

                loading = ProgressDialog.show(ListSiswaInput.this, "Menambahkan Data...", "Silahkan Tunggu..." ,false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(ListSiswaInput.this, "Upload sukses", Toast.LENGTH_SHORT).show();

//                Intent a = new Intent(ListSiswaInput.this, MainActivity.class);
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
