package com.example.testfeb.crimeactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeListActivity extends AppCompatActivity {

//    public void onCrimeSelected(Crime crime){
//        if( findViewById(R.id.detail_fragment_container) == null){
//            // Start an instance of CrimePagerActivity
//            Intent i = new Intent(this, CrimePagerActivity.class);
//            i.putExtra(CrimeFragment.ARG_CRIME_ID, crime.getId());
//            startActivity(i);
//        }
//        else {
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//
//            Fragment oldDetail = fm.findFragmentById(R.id.detail_fragment_container);
//            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
//
//            if ( oldDetail != null ){
//                ft.remove(oldDetail);
//            }
//
//            ft.add(R.id.detail_fragment_container, newDetail);
//            ft.commit();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CrimeListFragment()).commit();
    }

//    public void onCrimeUpdated(Crime crime) {
//        CrimeListFragment listFragment = (CrimeListFragment)
//                getSupportFragmentManager()
//                        .findFragmentById(R.id.fragment_container);
//        listFragment.updateUI();
//    }
}
