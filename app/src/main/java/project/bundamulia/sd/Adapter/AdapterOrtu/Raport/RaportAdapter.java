package project.bundamulia.sd.Adapter.AdapterOrtu.Raport;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import project.bundamulia.sd.R;

/**
 * Created by ACER V13 on 17/12/2016.
 */

public class RaportAdapter extends RecyclerView.Adapter<RaportAdapter.MyViewHolder>{

    private Context mContext;
    private List<RaportModel> RaportModelList;

    public RaportAdapter(Context mContext, List<RaportModel> RaportModelList) {
        this.mContext = mContext;
        this.RaportModelList = RaportModelList;
    }

    @Override
    public RaportAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raport_detail_item, parent, false);

        return new RaportAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RaportAdapter.MyViewHolder holder, int position) {
        RaportModel all = RaportModelList.get(position);

        holder.mp.setText(all.mtPljrn);
        holder.nilai.setText(all.nilaiAkhir);
        holder.ket.setText(all.ket);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mp, nilai, ket;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            mp    = (TextView) view.findViewById(R.id.mp);
            nilai    = (TextView) view.findViewById(R.id.nilai);
            ket    = (TextView) view.findViewById(R.id.ket);
            card_view    = (CardView) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return RaportModelList.size();
    }
}
