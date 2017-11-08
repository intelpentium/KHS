package project.bundamulia.sd.Adapter.AdapterGuru.AdapterSikap;

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
import project.bundamulia.sd.Guru.Sikap.SikapEdit;
import project.bundamulia.sd.Guru.Uts.UtsEdit;
import project.bundamulia.sd.Guru.Uts.UtsInput;
import project.bundamulia.sd.R;
import project.bundamulia.sd.Guru.Sikap.SikapInput;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class SikapSiswaAdapter extends RecyclerView.Adapter<SikapSiswaAdapter.MyViewHolder>{

    String url_view = "http://sdbundamulia.com/ws_khs/SikapView.php";

    private Context mContext;
    private List<SikapSiswaModel> SikapSiswaModelList;

    public SikapSiswaAdapter(Context mContext, List<SikapSiswaModel> SikapSiswaModelList) {
        this.mContext = mContext;
        this.SikapSiswaModelList = SikapSiswaModelList;
    }

    @Override
    public SikapSiswaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_siswa_list, parent, false);

        return new SikapSiswaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SikapSiswaAdapter.MyViewHolder holder, int position) {
        final SikapSiswaModel all = SikapSiswaModelList.get(position);

        holder.nis.setText(all.nis);
        holder.nama.setText(all.nama);

        holder.card_view.setOnClickListener(onClickListener(all.idMtPljrn, all.nis, all.idThnAjaran));

        holder.dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nis = all.getnis();
                String idMtPljrn = all.getidMtPljrn();
                String idThnAjaran = all.getidThnAjaran();

                SharedPreferences ambil = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                String semester = ambil.getString("semester","-");

                Intent a = new Intent(mContext, SikapEdit.class);
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

                SikapView(idMtPljrn, nis, idThnAjaran);
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

            dots = (ImageView) view.findViewById(R.id.dots);
        }
    }

    @Override
    public int getItemCount() {
        return SikapSiswaModelList.size();
    }

    public void setFilter(List<SikapSiswaModel> countryModels){
        SikapSiswaModelList = new ArrayList<>();
        SikapSiswaModelList.addAll(countryModels);
        notifyDataSetChanged();
    }

    private void SikapView(final String idMtPljrn, final String nis, final String idThnAjaran){

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

                        Toast.makeText(mContext, "Penilaian sikap telah dimasukan", Toast.LENGTH_LONG).show();
                    }else{

                        Intent i = new Intent(mContext, SikapInput.class);
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
