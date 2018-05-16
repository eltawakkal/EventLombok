package com.example.thebestone.eventlombok.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thebestone.eventlombok.R;

public class ListViewKabAdapter extends ArrayAdapter<String> {

    TextView txtKab;
    ImageView imgKabupaten;

    String[] arrayKab;

    int[] imgKab = {R.drawable.bima, R.drawable.dompu, R.drawable.lombok_barat, R.drawable.lombok_tengah,
            R.drawable.lombok_timur, R.drawable.lombok_utara, R.drawable.sumbawa, R.drawable.sumbawa_barat,
            R.drawable.kota_bima, R.drawable.mataram};

    public ListViewKabAdapter(Activity context, String[] kabupaten) {
        super(context, R.layout.custom_listview, kabupaten);

        this.arrayKab = kabupaten;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_listview, parent, false);

        txtKab = v.findViewById(R.id.txtKabCustomList);
        imgKabupaten = v.findViewById(R.id.imgKab);

        txtKab.setText(arrayKab[position]);
        imgKabupaten.setImageResource(imgKab[position]);

        return v;
    }
}
