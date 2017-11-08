package project.fathurrahman.khs.Adapter;

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
import project.fathurrahman.khs.UtsSiswa;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class UtsListAdapter extends RecyclerView.Adapter<UtsListAdapter.MyViewHolder>{

    private Context mContext;
    private List<UtsListModel> UtsListModelList;

    public UtsListAdapter(Context mContext, List<UtsListModel> UtsListModelList) {
        this.mContext = mContext;
        this.UtsListModelList = UtsListModelList;
    }

    @Override
    public UtsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new UtsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UtsListAdapter.MyViewHolder holder, int position) {
        UtsListModel all = UtsListModelList.get(position);

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


                Intent i = new Intent(mContext, UtsSiswa.class);
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
        return UtsListModelList.size();
    }

    public void setFilter(List<UtsListModel> countryModels){
        UtsListModelList = new ArrayList<>();
        UtsListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
