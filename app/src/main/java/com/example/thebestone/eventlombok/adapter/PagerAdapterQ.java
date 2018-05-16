package com.example.thebestone.eventlombok.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thebestone.eventlombok.fragment.FrgEvent;
import com.example.thebestone.eventlombok.fragment.FrgFavorite;
import com.example.thebestone.eventlombok.fragment.FrgProfile;
import com.example.thebestone.eventlombok.models.UserEvent;

import java.util.List;

public class PagerAdapterQ extends FragmentStatePagerAdapter {

    int pageCount;
    FrgEvent frgEvent;

    public PagerAdapterQ(FragmentManager fm, int pageCount) {
        super(fm);

        this.pageCount = pageCount;
        frgEvent = new FrgEvent();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FrgEvent();
            case 1:
                return new FrgFavorite();
            case 2:
                return new FrgProfile();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
