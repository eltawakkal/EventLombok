package com.example.thebestone.eventlombok.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.adapter.RecyclerAdapterFavQ;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.example.thebestone.eventlombok.sqlite.SqliteHelper;

import java.util.List;

public class FrgFavorite extends Fragment {

    SqliteHelper dbHelper;
    Activity contex;
    RecyclerAdapterFavQ adapterFavQ;

    List<UserEvent> userEvents;

    RecyclerView mRecyclerFav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_favorite, container, false);

        init(v);

        mRecyclerFav.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerFav.setAdapter(adapterFavQ);

        return v;
    }

    private void init(View v) {
        contex = getActivity();

        dbHelper = new SqliteHelper(getActivity());
        userEvents = dbHelper.selectAllFav();
        adapterFavQ = new RecyclerAdapterFavQ(userEvents, getActivity());

        mRecyclerFav = v.findViewById(R.id.mRecyclerFavorite);
    }
}
