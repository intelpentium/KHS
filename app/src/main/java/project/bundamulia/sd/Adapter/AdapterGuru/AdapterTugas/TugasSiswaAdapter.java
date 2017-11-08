package project.bundamulia.sd.Adapter.AdapterGuru.AdapterTugas;

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

import project.bundamulia.sd.Adapter.A_ParsingRequest;
import project.bundamulia.sd.Guru.Tugas.TugasEdit;
import project.bundamulia.sd.R;
import project.bundamulia.sd.Guru.Tugas.TugasInput;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class TugasSiswaAdapter extends RecyclerView.Adapter<TugasSiswaAdapter.MyViewHolder>{

//    String url_del = "http://sdbundamulia.com/ws_khs/TugasDelete.php";
    String url_view = "http://sdbundamulia.com/ws_khs/TugasView.php";
    String idMtPljrn, idThnAjaran, nis;

    private Context mContext;
    private List<TugasSiswaModel> TugasSiswaModelList;

    int pos;

    public TugasSiswaAdapter(Context mContext, List<TugasSiswaModel> TugasSiswaModelList) {
        this.mContext = mContext;
        this.TugasSiswaModelList = TugasSiswaModelList;
    }

    @Override
    public TugasSiswaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_siswa_list, parent, false);

        return new TugasSiswaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TugasSiswaAdapter.MyViewHolder holder, final int position) {
        final TugasSiswaModel all = TugasSiswaModelList.get(position);

        holder.nis.setText(all.nis);
        holder.nama.setText(all.nama);

        // ini buat input
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nis = all.getnis();
                idMtPljrn = all.getidMtPljrn();
                idThnAjaran = all.getidThnAjaran();

                TugasView();
            }
        });

        // ini buat edit
        holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nis = all.getnis();
                idMtPljrn = all.getidMtPljrn();
                idThnAjaran = all.getidThnAjaran();

                SharedPreferences ambil = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                String semester = ambil.getString("semester","-");

                Intent a = new Intent(mContext, TugasEdit.class);
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
        return TugasSiswaModelList.size();
    }

    public void setFilter(List<TugasSiswaModel> countryModels){
        TugasSiswaModelList = new ArrayList<>();
        TugasSiswaModelList.addAll(countryModels);
        notifyDataSetChanged();
    }


    private void TugasView(){

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

                        Toast.makeText(mContext, "Nilai tugas telah dimasukan", Toast.LENGTH_LONG).show();
                    }else{

                        Intent i = new Intent(mContext, TugasInput.class);
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
}
