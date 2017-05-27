package com.example.cafe_project;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import java.util.ArrayList;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v7.widget.LinearLayoutManager;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.support.v7.widget.DividerItemDecoration;
import android.app.ActionBar;
import android.app.Activity;

public class DrinkActivity extends AppCompatActivity {

    private ArrayList<Drinks> itemsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Start get action bar to go back to Main. Also manifest file need to set the parent activity
          this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         // End setting Action Bar


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        //2 options - LinearLayoutManager or GridLayoutManager
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //add a underline for each item
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //3. getting database reference
        final DrinkActivity.MyRecyclerAdapter adapter = new DrinkActivity.MyRecyclerAdapter(itemsArrayList);
        recyclerView.setAdapter(adapter);
        final DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("drinks");
        reference_contacts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemsArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    Drinks drinks = ds.getValue(Drinks.class);
                    itemsArrayList.add(drinks);
                }
                adapter.notifyDataSetChanged();
                Log.d("onDataChange$$$$",Integer.toString(itemsArrayList.size()));
            }

            @Override
            public void onCancelled (DatabaseError dberror){

            }

        });
        //3. end getting database reference

        adapter.notifyDataSetChanged();

    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<DrinkActivity.MyRecyclerAdapter.ViewHolder> {

        ArrayList<Drinks> c_list;

        public MyRecyclerAdapter(ArrayList<Drinks> c_list) {
            this.c_list = c_list;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.drinks, parent, false);
            return new DrinkActivity.MyRecyclerAdapter.ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(DrinkActivity.MyRecyclerAdapter.ViewHolder holder, int position) {
            Drinks drinks = c_list.get(position);
            Log.d("log$$$",Integer.toString(position));

            CardView cardView = holder.cardView;
            ImageView imageView = (ImageView)cardView.findViewById(R.id.info_image);
            Glide.with(imageView.getContext())
                    .load(drinks.getImage())
                    .into(imageView);

            TextView textView = (TextView)cardView.findViewById(R.id.info_text);
            textView.setText(drinks.getCoffee());

        }

        @Override
        public int getItemCount() {
            Log.d("Return size**", Integer.toString(c_list.size()));
             return c_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private CardView cardView;

            public ViewHolder(CardView v) {
                super(v);
                cardView=v;
            }

        }
    }

}
