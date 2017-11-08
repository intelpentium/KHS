package project.bundamulia.sd.Adapter.AdapterGuru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.bundamulia.sd.Guru.Absen.Absen;
import project.bundamulia.sd.R;
import project.bundamulia.sd.Guru.Sikap.Sikap;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class PenilaianAdapter extends RecyclerView.Adapter<PenilaianAdapter.MyViewHolder>{

    private Context mContext;
    private List<PenilaianModel> PenilaianModelList;

    public PenilaianAdapter(Context mContext, List<PenilaianModel> PenilaianModelList) {
        this.mContext = mContext;
        this.PenilaianModelList = PenilaianModelList;
    }

    @Override
    public PenilaianAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nilai_item, parent, false);

        return new PenilaianAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PenilaianAdapter.MyViewHolder holder, int position) {
        PenilaianModel all = PenilaianModelList.get(position);

        holder.tugas.setText(all.menu);

        holder.card_view.setOnClickListener(onClickListener(all.ID));
    }


    private View.OnClickListener onClickListener(final String ID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ID.equals("1")){

                    Intent i = new Intent(mContext, Absen.class);
                    mContext.startActivity(i);

                }if(ID.equals("2")){

                    Intent i = new Intent(mContext, Sikap.class);
                    mContext.startActivity(i);

                }
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tugas;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            tugas    = (TextView) view.findViewById(R.id.tugas);
            card_view    = (CardView) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return PenilaianModelList.size();
    }
}
