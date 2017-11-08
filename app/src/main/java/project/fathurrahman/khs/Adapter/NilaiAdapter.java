package project.fathurrahman.khs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import project.fathurrahman.khs.NilaiAkhir.NilaiAkhir;
import project.fathurrahman.khs.R;
import project.fathurrahman.khs.Tugas.Tugas;
import project.fathurrahman.khs.Uas.Uas;
import project.fathurrahman.khs.Ulangan.Ulangan;
import project.fathurrahman.khs.Uts.Uts;

/**
 * Created by Fathurrahman on 06/12/2016.
 */

public class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.MyViewHolder>{

    private Context mContext;
    private List<NilaiModel> NilaiModelList;

    public NilaiAdapter(Context mContext, List<NilaiModel> NilaiModelList) {
        this.mContext = mContext;
        this.NilaiModelList = NilaiModelList;
    }

    @Override
    public NilaiAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nilai_item, parent, false);

        return new NilaiAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NilaiAdapter.MyViewHolder holder, int position) {
        NilaiModel all = NilaiModelList.get(position);

        holder.tugas.setText(all.menu);

        holder.card_view.setOnClickListener(onClickListener(all.ID));
    }


    private View.OnClickListener onClickListener(final String ID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();

                if(ID.equals("1")){

                    Intent i = new Intent(mContext, Tugas.class);
                    mContext.startActivity(i);

                }if(ID.equals("2")){

                    Intent i = new Intent(mContext, Ulangan.class);
                    mContext.startActivity(i);

                }if(ID.equals("3")){

                    Intent i = new Intent(mContext, Uts.class);
                    mContext.startActivity(i);

                }if(ID.equals("4")){

                    Intent i = new Intent(mContext, Uas.class);
                    mContext.startActivity(i);

                }if(ID.equals("5")){

                    Intent i = new Intent(mContext, NilaiAkhir.class);
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
        return NilaiModelList.size();
    }
}
