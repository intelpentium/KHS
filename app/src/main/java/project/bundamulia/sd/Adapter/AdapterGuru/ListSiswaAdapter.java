package project.bundamulia.sd.Adapter.AdapterGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.Guru.ListSiswa.ListSiswaEdit;
import project.bundamulia.sd.Guru.ListSiswa.ListSiswaInput;
import project.bundamulia.sd.R;

/**
 * Created by Fathurrahman on 03/12/2016.
 */

public class ListSiswaAdapter extends RecyclerView.Adapter<ListSiswaAdapter.MyViewHolder>{

    String url_del = "http://sdbundamulia.com/ws_khs/ListSiswaDelete.php";
    String url_view = "http://sdbundamulia.com/ws_khs/ListSiswaView.php";

    private Context mContext;
    private List<ListSiswaModel> ListSiswaModelList;

    String id = "";
    int pos;

    public ListSiswaAdapter(Context mContext, List<ListSiswaModel> ListSiswaModelList) {
        this.mContext = mContext;
        this.ListSiswaModelList = ListSiswaModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_siswa_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ListSiswaModel all = ListSiswaModelList.get(position);

        holder.nama.setText(all.nama);
        holder.nis.setText(all.nis);
        holder.lahir.setText(all.lahir);

//        Glide.with(mContext).load(all.getimages()).into(holder.images);
        Glide.with(mContext).load("http://sdbundamulia.com/ws_khs/photo/" +all.images+".png")
                .placeholder(R.drawable.error_image)
                .error(R.drawable.error_image)
                .into(holder.images);

        holder.cardView.setOnClickListener(onClickListener(all.ID, all.nama, all.lahir));

        holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = all.getID();

                pos = position;
                showPopupMenu(holder.dots);
            }
        });

    }


    private View.OnClickListener onClickListener(final String nis, final String nama, final String lahir) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SiswaView(nis, nama, lahir);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView images, dots;
        TextView nama, nis, lahir;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            images   = (ImageView) view.findViewById(R.id.images);
            dots   = (ImageView) view.findViewById(R.id.dots);
            nama    = (TextView) view.findViewById(R.id.nama);
            nis   = (TextView) view.findViewById(R.id.nis);
            lahir   = (TextView) view.findViewById(R.id.lahir);
            cardView   = (CardView) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return ListSiswaModelList.size();
    }

    public void setFilter(List<ListSiswaModel> countryModels){
        ListSiswaModelList = new ArrayList<>();
        ListSiswaModelList.addAll(countryModels);
        notifyDataSetChanged();
    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_wishlist, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit:

                    SharedPreferences preferences = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("name_wish", "wish");
                    editor.commit();

                    Intent a = new Intent(mContext, ListSiswaEdit.class);
                    a.putExtra("nis", id);
                    mContext.startActivity(a);


                    return true;

                case R.id.hapus:

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("Confirm Hapus");
                    alertDialog.setMessage("Apakah anda yakin ?");
//                    alertDialog.setIcon(R.drawable.delete);

                    alertDialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

                            deleteData();
                            delete(pos);
                        }
                    });
                    alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();

                    return true;
                default:
            }
            return false;
        }
    }

    public void delete(int position) { //removes the row
        ListSiswaModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ListSiswaModelList.size());
    }

    public void deleteData() {

        class ParsingTambah extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.trim().equals("1")){

//                        Toast.makeText(PaymentDetail.this, "Anda telah berhasil melakukan transaksi!!", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", id);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_del, par);
                return res;
            }
        }

        ParsingTambah tambah = new ParsingTambah();
        tambah.execute();
    }

    private void SiswaView(final String nis, final String nama, final String lahir){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

//                Toast.makeText(ListSiswaEdit.this, ""+s, Toast.LENGTH_SHORT).show();

                try {

                    String json = s.toString(); // Respon di jadikan sebuah string
                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
                    String result = jObj.getString("success");

                    if(result.equals("1")){

                        Toast.makeText(mContext, "Data telah terdaftar", Toast.LENGTH_LONG).show();
                    }else{
                        Intent i = new Intent(mContext, ListSiswaInput.class);
                        i.putExtra("nis", nis);
                        i.putExtra("nama_siswa", nama);
                        i.putExtra("lahir_siswa", lahir);
                        mContext.startActivity(i);
                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

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
}
