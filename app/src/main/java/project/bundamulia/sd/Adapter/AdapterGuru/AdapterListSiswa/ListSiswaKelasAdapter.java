package project.bundamulia.sd.Adapter.AdapterGuru.AdapterListSiswa;

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

import project.bundamulia.sd.Guru.ListSiswa.ListSiswa;
import project.bundamulia.sd.Guru.Tugas.TugasSiswa;
import project.bundamulia.sd.R;

/**
 * Created by Fathurrahman on 08/12/2016.
 */

public class ListSiswaKelasAdapter extends RecyclerView.Adapter<ListSiswaKelasAdapter.MyViewHolder>{

    private Context mContext;
    private List<ListSiswaKelasModel> listModelListSiswaKelas;

    public ListSiswaKelasAdapter(Context mContext, List<ListSiswaKelasModel> listModelListSiswaKelas) {
        this.mContext = mContext;
        this.listModelListSiswaKelas = listModelListSiswaKelas;
    }

    @Override
    public ListSiswaKelasAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tugas_list_item, parent, false);

        return new ListSiswaKelasAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSiswaKelasAdapter.MyViewHolder holder, int position) {
        ListSiswaKelasModel all = listModelListSiswaKelas.get(position);

        // isi data
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

                Intent i = new Intent(mContext, ListSiswa.class);
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
        return listModelListSiswaKelas.size();
    }

    public void setFilter(List<ListSiswaKelasModel> countryModels){
        listModelListSiswaKelas = new ArrayList<>();
        listModelListSiswaKelas.addAll(countryModels);
        notifyDataSetChanged();
    }
}
