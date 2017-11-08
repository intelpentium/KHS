package project.fathurrahman.khs.Adapter.AdapterUas;

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
import project.fathurrahman.khs.Uas.UasSiswa;

/**
 * Created by Fathurrahman on 12/12/2016.
 */

public class UasListAdapter extends RecyclerView.Adapter<UasListAdapter.MyViewHolder>{

    private Context mContext;
    private List<UasListModel> UasListModelList;

    public UasListAdapter(Context mContext, List<UasListModel> UasListModelList) {
        this.mContext = mContext;
        this.UasListModelList = UasListModelList;
    }

    @Override
    public UasListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new UasListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UasListAdapter.MyViewHolder holder, int position) {
        UasListModel all = UasListModelList.get(position);

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


                Intent i = new Intent(mContext, UasSiswa.class);
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
        return UasListModelList.size();
    }

    public void setFilter(List<UasListModel> countryModels){
        UasListModelList = new ArrayList<>();
        UasListModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
