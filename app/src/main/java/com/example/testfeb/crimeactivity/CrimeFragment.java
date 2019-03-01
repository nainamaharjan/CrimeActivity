package com.example.testfeb.crimeactivity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;
import static android.widget.CompoundButton.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private Crime crime;
    private EditText titlefield_et;
    private Button date_btn, add_btn;
    private CheckBox solved_cb;
    private String newDate;
    private Boolean isSolved = false;
    private Button delete;
    private Button reportButton;
    private Button suspectButton;
    private Button seen_btn;


    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public CrimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        crime = new Crime();


        Calendar calendar = Calendar.getInstance();
        Date date1 = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        newDate = sdf.format(date1);

        if (getArguments()!=null)
        {
//            if (getArguments().get("date") != null) {
//                newDate= getArguments().getString("date");
//            }

            UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
            crime = CrimeLab.get(getActivity()).getCrime(crimeId);
        }



        titlefield_et = v.findViewById(R.id.enter_title);
        titlefield_et.setText(crime.getTitle());



        Date newDate = crime.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMDD_HHmmss");
        String dateTime = sdf1.format(newDate);
        Log.d(TAG, "onCreateView: "+dateTime);

        Log.d(TAG, "onCreate: "+isSolved);
//        if (!isSolved){
//            crime.setSolved(false);
//        }

        titlefield_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        date_btn= v.findViewById(R.id.crime_date);
        updateDate();
        date_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager manager= getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);

            }
        });

        solved_cb=v.findViewById(R.id.crime_solved);
        solved_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   isSolved = true;
//                   crime.setSolved(isSolved);
               }else{
                   Log.d(TAG, "onCheckedChanged: "+isSolved);
                   isSolved = false;
//                   crime.setSolved(isSolved);
               }
            }
        });


        add_btn= v.findViewById(R.id.add);
        add_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                addCrime();
//                Fragment fragment = new CrimeListFragment();
//                FragmentManager fragmentManager =  (getActivity()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//


            }
        });

        reportButton = v.findViewById(R.id.crime_report);
        reportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                startActivity(i);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//        pickContact.addCategory(Intent.CATEGORY_HOME);
        suspectButton = v.findViewById(R.id.choose_suspect);
        suspectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);

            }
        });
        if(crime.getCrimeSuspect()== null){
//            suspectButton.setText(crime.getCrimeSuspect());

        }
        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, packageManager.MATCH_DEFAULT_ONLY)== null){
            suspectButton.setEnabled(false);
        }

        return v;


    }
    @Override
    public void onPause() {
        super.onPause();
//        CrimeLab.get(getActivity())
//                .updateCrime(crime);
    }

    private void addCrime() {
        String title= titlefield_et.getText().toString();

        if(TextUtils.isEmpty(title)){
            titlefield_et.setError("Enter title");
            titlefield_et.requestFocus();

        }else{
            Date date;
            try{
                Log.d(TAG, "addCrime: "+newDate);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                date = simpleDateFormat.parse(newDate);
                crime.setDate(date);
            }catch (ParseException e){


            }

            crime.setTitle(title);
            crime.setSolved(isSolved);
            CrimeLab.get(getActivity()).addCrime(crime);
            Intent intent = new Intent(getActivity(), CrimeListActivity.class);
            startActivity(intent);

        }

    }


    public void returnResult(){
        getActivity().setResult(Activity.RESULT_OK, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setDate(date);

            updateDate();

        }else if(requestCode == REQUEST_CONTACT && data!=null){
            Uri contactUri = data.getData();
//            specifying data that you want query to return
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
//            perform query like where by contacturi

            Cursor c = getActivity().getContentResolver().query(contactUri,queryFields,null, null,null);
            try{
                if(c.getCount() == 0){
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                crime.setCrimeSuspect(suspect);
                suspectButton.setText(suspect);

            }
            finally {
                c.close();
            }

        }
    }
    public void updateDate(){
        date_btn.setText(crime.getDate().toString());
    }

    private String getCrimeReport(){
        String solvedString = null;
        if (crime.getSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        }else{
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "yyyy, MM, dd";
        String dateString = DateFormat.format(dateFormat, crime.getDate()).toString();

        String suspect = crime.getCrimeSuspect();
        if(suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        }else{
            suspect = getString(R.string.crime_report_suspect);
        }
        String report = getString(R.string.crime_report, crime.getTitle(), dateString, solvedString, suspect);
        return report;
    }



}
