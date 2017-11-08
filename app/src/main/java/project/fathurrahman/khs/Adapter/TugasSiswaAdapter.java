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
import project.fathurrahman.khs.TugasInput;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class TugasSiswaAdapter extends RecyclerView.Adapter<TugasSiswaAdapter.MyViewHolder>{

    private Context mContext;
    private List<TugasSiswaModel> TugasSiswaModelList;

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
    public void onBindViewHolder(final TugasSiswaAdapter.MyViewHolder holder, int position) {
        TugasSiswaModel all = TugasSiswaModelList.get(position);

        holder.nis.setText(all.nis);
        holder.nama.setText(all.nama);

        holder.card_view.setOnClickListener(onClickListener(all.idMtPljrn, all.nis, all.idThnAjaran));
    }


    private View.OnClickListener onClickListener(final String idMtPljrn, final String nis, final String idThnAjaran) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "Position " +idMtPljrn, Toast.LENGTH_SHORT).show();


                Intent i = new Intent(mContext, TugasInput.class);
                i.putExtra("nis", nis);
                i.putExtra("idMtPljrn", idMtPljrn);
                i.putExtra("idThnAjaran", idThnAjaran);
                mContext.startActivity(i);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nis, nama;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            nis    = (TextView) view.findViewById(R.id.nis);
            nama    = (TextView) view.findViewById(R.id.nama);
            card_view    = (CardView) view.findViewById(R.id.card_view);
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
}
