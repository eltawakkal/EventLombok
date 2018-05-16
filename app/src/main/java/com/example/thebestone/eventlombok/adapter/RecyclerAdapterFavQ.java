package com.example.thebestone.eventlombok.adapter;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.models.UserEvent;

import java.util.List;

import okhttp3.internal.Util;

public class RecyclerAdapterFavQ extends RecyclerView.Adapter<RecyclerAdapterFavQ.ViewHolder> {

    List<UserEvent> userEvents;

    String hari, bulan, tahun;

    int[] imgJenis = {R.drawable.seminar, R.drawable.workshop, R.drawable.competisi, R.drawable.festival};

    Activity context;

    public RecyclerAdapterFavQ(List<UserEvent> userEvents, Activity context) {
        this.userEvents = userEvents;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fav, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sparatedDate(userEvents.get(position).getTglEvent());

        holder.tvBln.setText(bulan);
        holder.tvTgl.setText(hari);
        holder.tvNama.setText(userEvents.get(position).getNamaEvent());
        holder.tvLokEvent.setText(userEvents.get(position).getLokasiEvent());
        holder.tvWaktuEvent.setText(userEvents.get(position).getWaktuEvent());

        if (position > 0) {
            if (userEvents.get(position).getTglEvent().substring(0,2).equals(userEvents.get(position-1)
                    .getTglEvent().substring(0,2))) {

                holder.rlTgl.setVisibility(View.INVISIBLE);

            }
        }

        String jenis = userEvents.get(position).getJenisEvent();

        switch (jenis) {
            case "Seminar":
                holder.imgJenis.setImageResource(imgJenis[0]);
                break;
            case "Workshop":
                holder.imgJenis.setImageResource(imgJenis[1]);
                break;
            case "Kompetisi":
                holder.imgJenis.setImageResource(imgJenis[2]);
                break;
            case "Festival":
                holder.imgJenis.setImageResource(imgJenis[3]);
        }
    }

    @Override
    public int getItemCount() {
        return userEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTgl, tvBln, tvNama, tvLokEvent, tvWaktuEvent;
        RelativeLayout rlTgl;
        ImageView imgJenis;
        public ViewHolder(View v) {
            super(v);

            tvTgl = v.findViewById(R.id.tvTglEventFav);
            tvBln = v.findViewById(R.id.tvBlnEventFav);
            tvNama = v.findViewById(R.id.tvNamaEventFav);
            tvLokEvent = v.findViewById(R.id.tvLokEventFav);
            tvWaktuEvent = v.findViewById(R.id.tvWaktuEventFav);
            rlTgl = v.findViewById(R.id.rlTglEventJoin);
            imgJenis = v.findViewById(R.id.imgJenisJoin);
        }
    }

    public void sparatedDate(String date) {
        String[] dateSparated = date.split("/");

        hari = dateSparated[0];

        switch (dateSparated[1]) {
            case "1":
                bulan = "Jan";
                break;
            case "2":
                bulan = "Feb";
                break;
            case "3":
                bulan = "Mar";
                break;
            case "4":
                bulan = "Apr";
                break;
            case "5":
                bulan = "Mei";
                break;
            case "6":
                bulan = "Jun";
                break;
            case "7":
                bulan = "Jul";
                break;
            case "8":
                bulan = "Aug";
                break;
            case "9":
                bulan = "Sep";
                break;
            case "10":
                bulan = "Okt";
                break;
            case "11":
                bulan = "Nov";
                break;
            case "12":
                bulan = "Des";
                break;
        }
    }
}
