package project.bundamulia.sd.Adapter.AdapterGuru.AdapterNilaiAkhir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.bundamulia.sd.R;
import project.bundamulia.sd.Guru.NilaiAkhir.NilaiAkhirSiswa;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class NilaiAkhirListAdapter extends RecyclerView.Adapter<NilaiAkhirListAdapter.MyViewHolder>{

    private Context mContext;
    private List<NilaiAkhirListModel> NilaiAkhirListModelList;

    public NilaiAkhirListAdapter(Context mContext, List<NilaiAkhirListModel> NilaiAkhirListModelList) {
        this.mContext = mContext;
        this.NilaiAkhirListModelList = NilaiAkhirListModelList;
    }

    @Override
    public NilaiAkhirListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new NilaiAkhirListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NilaiAkhirListAdapter.MyViewHolder holder, int position) {
        NilaiAkhirListModel all = NilaiAkhirListModelList.get(position);

        holder.mapel.setText(all.mtPljrn);
        holder.kelas.setText(all.kelas);

        holder.card_view.setOnClickListener(onClickListener(all.nis, all.idMtPljrn, all.idKelas, all.idThnAjaran));
    }


    private View.OnClickListener onClickListener(final String nis, final String idMtPljrn,
                                                 final String idKelas, final String idThnAjaran) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = mContext.getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("idKelas", idKelas);
                editor.commit();

                Intent i = new Intent(mContext, NilaiAkhirSiswa.class);
                i.putExtra("nis", nis);
                i.putExtra("idMtPljrn", idMtPljrn);
                i.putExtra("idThnAjaran", idThnAjaran);
                mContext.startActivity(i);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mapel, kelas;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            mapel    = (TextView) view.findViewById(R.id.mapel);
            kelas    = (TextView) view.findViewById(R.id.kelas);
            card_view    = (CardView) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return NilaiAkhirListModelList.size();
    }

    public void setFilter(List<NilaiAkhirListModel> countryModels){
        NilaiAkhirListModelList = new ArrayList<>();
        NilaiAkhirListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
