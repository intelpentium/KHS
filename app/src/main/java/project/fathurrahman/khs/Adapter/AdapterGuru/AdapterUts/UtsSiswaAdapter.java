package project.fathurrahman.khs.Adapter.AdapterGuru.AdapterUts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.fathurrahman.khs.Adapter.A_ParsingRequest;
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterUts.UtsSiswaAdapter;
import project.fathurrahman.khs.Adapter.AdapterGuru.AdapterUts.UtsSiswaModel;
import project.fathurrahman.khs.Guru.Uts.UtsEdit;
import project.fathurrahman.khs.Guru.Uts.UtsInput;
import project.fathurrahman.khs.R;
import project.fathurrahman.khs.Guru.Uts.UtsInput;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class UtsSiswaAdapter extends RecyclerView.Adapter<UtsSiswaAdapter.MyViewHolder>{

    //    String url_del = "http://sdbundamulia.com/ws_khs/UtsDelete.php";
    String url_view = "http://sdbundamulia.com/ws_khs/UtsView.php";
    String idMtPljrn, idThnAjaran, nis;

    private Context mContext;
    private List<UtsSiswaModel> UtsSiswaModelList;

    int pos;

    public UtsSiswaAdapter(Context mContext, List<UtsSiswaModel> UtsSiswaModelList) {
        this.mContext = mContext;
        this.UtsSiswaModelList = UtsSiswaModelList;
    }

    @Override
    public UtsSiswaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_siswa_list, parent, false);

        return new UtsSiswaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UtsSiswaAdapter.MyViewHolder holder, final int position) {
        final UtsSiswaModel all = UtsSiswaModelList.get(position);

        holder.nis.setText(all.nis);
        holder.nama.setText(all.nama);

        holder.card_view.setOnClickListener(onClickListener(all.idMtPljrn, all.nis, all.idThnAjaran));

        holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nis = all.getnis();
                idMtPljrn = all.getidMtPljrn();
                idThnAjaran = all.getidThnAjaran();

                SharedPreferences ambil = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                String semester = ambil.getString("semester","-");

                Intent a = new Intent(mContext, UtsEdit.class);
                a.putExtra("nis", nis);
                a.putExtra("idMtPljrn", idMtPljrn);
                a.putExtra("idThnAjaran", idThnAjaran);
                a.putExtra("semester", semester);
                mContext.startActivity(a);

//                pos = position;
//                showPopupMenu(holder.dots);
            }
        });
    }


    private View.OnClickListener onClickListener(final String idMtPljrn, final String nis, final String idThnAjaran) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "Position " +idMtPljrn, Toast.LENGTH_SHORT).show();
                UtsView(idMtPljrn, nis, idThnAjaran);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nis, nama;
        CardView card_view;
        ImageView images, dots;

        public MyViewHolder(View view) {
            super(view);
            nis    = (TextView) view.findViewById(R.id.nis);
            nama    = (TextView) view.findViewById(R.id.nama);
            card_view    = (CardView) view.findViewById(R.id.card_view);

            images   = (ImageView) view.findViewById(R.id.images);
            dots   = (ImageView) view.findViewById(R.id.dots);
        }
    }

    @Override
    public int getItemCount() {
        return UtsSiswaModelList.size();
    }

    public void setFilter(List<UtsSiswaModel> countryModels){
        UtsSiswaModelList = new ArrayList<>();
        UtsSiswaModelList.addAll(countryModels);
        notifyDataSetChanged();
    }


    private void UtsView(final String idMtPljrn, final String nis, final String idThnAjaran){

        class ParsingLogin extends AsyncTask<Void, Void, String> {

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

                    if(result.equals("1")){

                        Toast.makeText(mContext, "Nilai Uts telah dimasukan", Toast.LENGTH_LONG).show();
                    }else{

                        Intent i = new Intent(mContext, UtsInput.class);
                        i.putExtra("nis", nis);
                        i.putExtra("idMtPljrn", idMtPljrn);
                        i.putExtra("idThnAjaran", idThnAjaran);
                        mContext.startActivity(i);
                    }
                }catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... params) {
                // TODO Auto-generated method stub

                SharedPreferences ambil = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                String semester = ambil.getString("semester","-");

                HashMap<String, String> par = new HashMap<String, String>();
                par.put("nis", nis);
                par.put("idMtpljrn", idMtPljrn);
                par.put("idThnAjaran", idThnAjaran);
                par.put("semester", semester);

                A_ParsingRequest parsing = new A_ParsingRequest();
                String res = parsing.sendPostRequest(url_view, par);
                return res;
            }
        }

        ParsingLogin tambah = new ParsingLogin();
        tambah.execute();
    }

//    private void showPopupMenu(View view) {
//
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//
//        inflater.inflate(R.menu.menu_wishlist, popup.getMenu());
//
//        popup.setOnMenuItemClickListener(new UtsSiswaAdapter.MyMenuItemClickListener());
//        popup.show();
//    }
//
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.edit:
//
//                    Intent a = new Intent(mContext, UtsEdit.class);
//                    a.putExtra("nis", nis);
//                    mContext.startActivity(a);
//
//                    return true;
//
//                case R.id.hapus:
//
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//                    alertDialog.setTitle("Confirm Hapus");
//                    alertDialog.setMessage("Apakah anda yakin ?");
////                    alertDialog.setIcon(R.drawable.delete);
//
//                    alertDialog.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog,int which) {
//
//                            deleteData();
//                            delete(pos);
//                        }
//                    });
//                    alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    alertDialog.show();
//
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

//    public void delete(int position) { //removes the row
//        UtsSiswaModelList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, UtsSiswaModelList.size());
//    }
//
//    public void deleteData() {
//
//        class ParsingTambah extends AsyncTask<Void, Void, String> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//                try {
//
//                    String json = s.toString(); // Respon di jadikan sebuah string
//                    JSONObject jObj = new JSONObject(json); // Response di jadikan sebuah
//                    String result = jObj.getString("success");
//
//                    if(result.trim().equals("1")){
//
//                    }
//
//                }catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                // TODO Auto-generated method stub
//
//                SharedPreferences ambil = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
//                String semester = ambil.getString("semester","-");
//
//                HashMap<String, String> par = new HashMap<String, String>();
//                par.put("nis", nis);
//                par.put("idMtPljrn", idMtPljrn);
//                par.put("idThnAjaran", idThnAjaran);
//                par.put("semester", semester);
//
//                A_ParsingRequest parsing = new A_ParsingRequest();
//                String res = parsing.sendPostRequest(url_del, par);
//                return res;
//            }
//        }
//
//        ParsingTambah tambah = new ParsingTambah();
//        tambah.execute();
//    }

}
