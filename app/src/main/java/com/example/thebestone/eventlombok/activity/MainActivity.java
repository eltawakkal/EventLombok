package com.example.thebestone.eventlombok.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.background.ServiceQ;
import com.example.thebestone.eventlombok.fragment.FrgEvent;
import com.example.thebestone.eventlombok.fragment.FrgFavorite;
import com.example.thebestone.eventlombok.fragment.FrgProfile;

public class MainActivity extends AppCompatActivity {

//    ViewPager mPager;
//    PagerAdapterQ pagerAdapter;
//    Toolbar toolbar;
//    TabLayout tabLayoutMain;

    FrgEvent frgEvent;
    FrgFavorite frgFavorite;
    FrgProfile frgProfile;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getFrg(frgEvent);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_beranda:
                       getFrg(frgEvent);
                       item.setChecked(true);
                       break;
                    case R.id.menu_join:
                       getFrg(frgFavorite);
                       item.setChecked(true);
                       break;
                    case R.id.menu_profil:
                       getFrg(frgProfile);
                       item.setChecked(true);
                       break;
                }
                return false;
            }
        });

//        imgFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertFilter();
//            }
//        });

//        starService();

//        tabLayoutMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                mPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        mPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutMain));

    }

    public void getFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frgContainer, frg).commit();
    }

//    public void alertFilter() {
//        ListView mListKab = new ListView(this);
//        ListViewKabAdapter adapter = new ListViewKabAdapter(this, arrayKab);
//        mListKab.setAdapter(adapter);
//
//        AlertDialog.Builder setKab = new AlertDialog.Builder(this);
//        setKab
//                .setTitle("Filter")
//                .setView(mListKab)
//                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
////                        frgEvent.filterData("silicon");
//                    }
//                }).create().show();
//    }

    private void init() {
        frgEvent = new FrgEvent();
        frgFavorite = new FrgFavorite();
        frgProfile = new FrgProfile();
        bottomNavigationView = findViewById(R.id.bnvBeranda);

//        tabLayoutMain = findViewById(R.id.tabMain);
//        mPager = findViewById(R.id.view_pager_main);
//        pagerAdapter = new PagerAdapterQ(getSupportFragmentManager(), 3);
//        mPager.setAdapter(pagerAdapter);
    }

    public void starService() {
        startService(new Intent(this, ServiceQ.class));
    }

}
