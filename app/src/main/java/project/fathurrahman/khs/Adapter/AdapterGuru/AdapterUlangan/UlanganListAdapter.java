package project.fathurrahman.khs.Adapter.AdapterGuru.AdapterUlangan;

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

import project.fathurrahman.khs.R;
import project.fathurrahman.khs.Guru.Ulangan.UlanganSiswa;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class UlanganListAdapter extends RecyclerView.Adapter<UlanganListAdapter.MyViewHolder>{

    private Context mContext;
    private List<UlanganListModel> UlanganListModelList;

    public UlanganListAdapter(Context mContext, List<UlanganListModel> UlanganListModelList) {
        this.mContext = mContext;
        this.UlanganListModelList = UlanganListModelList;
    }

    @Override
    public UlanganListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new UlanganListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UlanganListAdapter.MyViewHolder holder, int position) {
        UlanganListModel all = UlanganListModelList.get(position);

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

                Intent i = new Intent(mContext, UlanganSiswa.class);
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
        return UlanganListModelList.size();
    }

    public void setFilter(List<UlanganListModel> countryModels){
        UlanganListModelList = new ArrayList<>();
        UlanganListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
