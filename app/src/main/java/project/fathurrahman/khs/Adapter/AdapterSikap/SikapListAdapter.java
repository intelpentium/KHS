package project.fathurrahman.khs.Adapter.AdapterSikap;

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

import project.fathurrahman.khs.R;
import project.fathurrahman.khs.Sikap.SikapSiswa;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class SikapListAdapter extends RecyclerView.Adapter<SikapListAdapter.MyViewHolder>{

    private Context mContext;
    private List<SikapListModel> SikapListModelList;

    public SikapListAdapter(Context mContext, List<SikapListModel> SikapListModelList) {
        this.mContext = mContext;
        this.SikapListModelList = SikapListModelList;
    }

    @Override
    public SikapListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new SikapListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SikapListAdapter.MyViewHolder holder, int position) {
        SikapListModel all = SikapListModelList.get(position);

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


                Intent i = new Intent(mContext, SikapSiswa.class);
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
        return SikapListModelList.size();
    }

    public void setFilter(List<SikapListModel> countryModels){
        SikapListModelList = new ArrayList<>();
        SikapListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
