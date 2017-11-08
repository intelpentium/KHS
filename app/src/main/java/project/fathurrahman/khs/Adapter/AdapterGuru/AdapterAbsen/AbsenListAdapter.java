package project.fathurrahman.khs.Adapter.AdapterGuru.AdapterAbsen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.fathurrahman.khs.Guru.Absen.AbsenSiswa;
import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class AbsenListAdapter extends RecyclerView.Adapter<AbsenListAdapter.MyViewHolder>{

    private Context mContext;
    private List<AbsenListModel> AbsenListModelList;

    public AbsenListAdapter(Context mContext, List<AbsenListModel> AbsenListModelList) {
        this.mContext = mContext;
        this.AbsenListModelList = AbsenListModelList;
    }

    @Override
    public AbsenListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new AbsenListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AbsenListAdapter.MyViewHolder holder, int position) {
        AbsenListModel all = AbsenListModelList.get(position);

        holder.mapel.setText(all.mtPljrn);
        holder.kelas.setText(all.kelas);

        holder.card_view.setOnClickListener(onClickListener(all.nis, all.idMtPljrn, all.idKelas, all.idThnAjaran));
    }


    private View.OnClickListener onClickListener(final String nis, final String idMtPljrn,
                                                 final String idKelas, final String idThnAjaran) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();


                Intent i = new Intent(mContext, AbsenSiswa.class);
                i.putExtra("nis", nis);
                i.putExtra("idMtPljrn", idMtPljrn);
                i.putExtra("idKelas", idKelas);
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
        return AbsenListModelList.size();
    }

    public void setFilter(List<AbsenListModel> countryModels){
        AbsenListModelList = new ArrayList<>();
        AbsenListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
