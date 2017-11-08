package project.fathurrahman.khs.Adapter.AdapterGuru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import project.fathurrahman.khs.R;


public class HomeGuruAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] gambar = {
            R.drawable.dummy, R.drawable.dummy,
            R.drawable.dummy, R.drawable.dummy,
            R.drawable.dummy
    };

    public String[] text = {
            "List Siswa", "Nilai",
            "Penilaian", "Agenda",
            "logout"
    } ;

    // Constructor
    public HomeGuruAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return gambar.length;
    }

    @Override
    public Object getItem(int position) {
        return gambar[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.home_list, null);
            TextView textView = (TextView) grid.findViewById(R.id.textView);
            ImageView imageView = (ImageView)grid.findViewById(R.id.imageView);

            textView.setText(text[position]);
            imageView.setImageResource(gambar[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}