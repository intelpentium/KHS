package project.fathurrahman.khs.Adapter.AdapterGuru.AdapterAbsen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import project.fathurrahman.khs.R;

/**
 * Created by ACER V13 on 13/12/2016.
 */

public class AbsenSiswaAdapter extends BaseAdapter {
    Context context;
    String[] questionsList;
    LayoutInflater inflter;
    public static ArrayList<String> selectedAnswers;

    public AbsenSiswaAdapter(Context applicationContext, String[] questionsList) {

        this.context = context;
        this.questionsList = questionsList;

        // initialize arraylist and add static string for all the questions
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < questionsList.length; i++) {
            selectedAnswers.add("not Attempted");
        }
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return questionsList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.absen_siswa_item, null);

        // get the reference of TextView and Button's
        TextView question = (TextView) view.findViewById(R.id.question);
        RadioButton masuk = (RadioButton) view.findViewById(R.id.masuk);
        RadioButton izin = (RadioButton) view.findViewById(R.id.izin);
        RadioButton sakit = (RadioButton) view.findViewById(R.id.sakit);
        RadioButton absen = (RadioButton) view.findViewById(R.id.absen);

        // perform setOnCheckedChangeListener event on masuk button
        masuk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set masuk values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(i, "Masuk");
            }
        });
        // perform setOnCheckedChangeListener event on izin button
        izin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set izin values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(i, "Izin");

            }
        });
        // perform setOnCheckedChangeListener event on masuk button
        sakit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set masuk values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(i, "Sakit");
            }
        });
        // perform setOnCheckedChangeListener event on izin button
        absen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set izin values in ArrayList if RadioButton is checked
                if (isChecked)
                    selectedAnswers.set(i, "Absen");

            }
        });
        // set the value in TextView
        question.setText(questionsList[i]);
        return view;
    }
}
