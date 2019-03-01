package com.example.testfeb.crimeactivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;


public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.CrimeHolder>{
    private List<Crime> crimes;
    private Context context;


    public CrimeListAdapter(List<Crime> crimes, Context context) {
        this.crimes = crimes;
        this.context = context;
    }


    @NonNull
    @Override
    public CrimeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_crime, viewGroup, false);
        return new CrimeHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull CrimeHolder holder, final int i) {

        final Crime crime1= crimes.get(i);
        Log.d("asd", "onBindViewHolder: "+crime1.getTitle());
        holder.title_tv.setText(crime1.getTitle());

        holder.bind(crime1);
       /* holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, CrimeActivity.class);
                Intent intent = CrimeActivity.newIntent(context, crime.getId());
                context.startActivity(intent);
            }
        });*/


//       holder.mainView.setOnClickListener(
//               new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       Intent intent = CrimePagerActivity.newIntent(context, crime1.getId());
//                       context.startActivity(intent);
//                   }
//               }
//       );

       holder.deleteTV.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       if (CrimeLab.get(context).deleteCrime(crime1.getId())>0)
                       {
                        //   crimes.remove(crime);
                           crimes=CrimeLab.get(context).getCrimes();
                           notifyDataSetChanged();
                       }

//
                       Log.d("msg", "onclickListener:" +i);
                   }

               }
       );

       holder.seen_iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = CrimePagerActivity.newIntent(context, crime1.getId());
               context.startActivity(intent);

           }
       });



    }

    @Override
    public int getItemCount()
    {
        return crimes.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
    }

    public class CrimeHolder extends RecyclerView.ViewHolder
    {
        private static final int REQUEST_CRIME = 1;
        private TextView title_tv;
        private TextView date_tv;
        private Crime crime;
        private ImageView solve_iv;
        private View mainView;
        private ImageView seen_iv;

        private ImageView deleteTV;
//        public void Crime(Crime crime){
//
//        }


        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.crime_title);
            date_tv = itemView.findViewById(R.id.crime_date);
            solve_iv = itemView.findViewById(R.id.crime_solved);
            mainView = itemView.findViewById(R.id.mainView);
            deleteTV=itemView.findViewById(R.id.delete_crime_item);
            seen_iv = itemView.findViewById(R.id.crime_seen);
//            list ma jata click gareni kholna paryo detail view crimeActivity
        }

        public void bind(Crime crime) {
            this.crime=crime;
            title_tv.setText(crime.getTitle());
            date_tv.setText(crime.getDate().toString());
            if(crime.getSolved()){
                solve_iv.setVisibility(View.VISIBLE);
            } else{
                solve_iv.setVisibility(View.GONE);
            }
        }
    }
}
