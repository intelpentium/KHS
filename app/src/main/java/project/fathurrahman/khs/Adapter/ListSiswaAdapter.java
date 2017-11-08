package project.fathurrahman.khs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.fathurrahman.khs.ListSiswaInput;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 03/12/2016.
 */

public class ListSiswaAdapter extends RecyclerView.Adapter<ListSiswaAdapter.MyViewHolder>{

    private Context mContext;
    private List<ListSiswaModel> ListSiswaModelList;

    public ListSiswaAdapter(Context mContext, List<ListSiswaModel> ListSiswaModelList) {
        this.mContext = mContext;
        this.ListSiswaModelList = ListSiswaModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_siswa_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ListSiswaModel all = ListSiswaModelList.get(position);

        holder.nama.setText(all.nama);
        holder.nis.setText(all.nis);
        holder.lahir.setText(all.lahir);

//        Glide.with(mContext).load(all.getimages()).into(holder.images);
//        Glide.with(mContext).load("http://livetodayapp.com/ws_live/img/" +all.images)
//                .placeholder(R.drawable.error_image)
//                .error(R.drawable.error_image)
//                .into(holder.images);

        holder.cardView.setOnClickListener(onClickListener(all.ID, all.nama, all.lahir));


    }


    private View.OnClickListener onClickListener(final String nis, final String nama, final String lahir) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, ListSiswaInput.class);
                i.putExtra("nis", nis);
                i.putExtra("nama_siswa", nama);
                i.putExtra("lahir_siswa", lahir);
                mContext.startActivity(i);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView images;
        TextView nama, nis, lahir;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            images   = (ImageView) view.findViewById(R.id.images);
            nama    = (TextView) view.findViewById(R.id.nama);
            nis   = (TextView) view.findViewById(R.id.nis);
            lahir   = (TextView) view.findViewById(R.id.lahir);
            cardView   = (CardView) view.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        return ListSiswaModelList.size();
    }

    public void setFilter(List<ListSiswaModel> countryModels){
        ListSiswaModelList = new ArrayList<>();
        ListSiswaModelList.addAll(countryModels);
        notifyDataSetChanged();
    }
}
