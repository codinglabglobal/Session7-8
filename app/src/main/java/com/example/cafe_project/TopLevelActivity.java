package com.example.cafe_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.support.v7.widget.LinearLayoutManager;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.support.v7.widget.DividerItemDecoration;
import android.content.Intent;
import android.content.Context;

import android.widget.Toast;


public class TopLevelActivity extends AppCompatActivity {


    private ArrayList<Main> itemsArrayList = new ArrayList<>();

    //Create an interface ItemClickListener with prototype of onClick method
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add a underline for each item
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //assign an array
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(itemsArrayList);
        recyclerView.setAdapter(adapter);

        //connect to database
        final DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("main");
        reference_contacts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemsArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    Main main = ds.getValue(Main.class);
                    itemsArrayList.add(main);
                }
                adapter.notifyDataSetChanged();
                Log.d("onDataChange$$$$",Integer.toString(itemsArrayList.size()));
            }

            @Override
            public void onCancelled (DatabaseError dberror){}
        });

        adapter.notifyDataSetChanged();
    } //end of onCreate Method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {

        ArrayList<Main> c_list;
        //constructor
        public MyRecyclerAdapter(ArrayList<Main> c_list) {
            this.c_list = c_list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder,  int position) {
            Main main = c_list.get(position);
            Log.d("log$$$",Integer.toString(position));
            holder.setValues(main);

            holder.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
                        Log.d("position**$**",Integer.toString(position));
                        //Toast.makeText(this, "#" + Integer.toString(position) + " - " + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("position**xxxxx**",Integer.toString(position));
                        switch(position){
                            case 0:
                                Intent intentDrink = new Intent(TopLevelActivity.this,DrinkActivity.class);
                                startActivityForResult(intentDrink, 1);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            Log.d("Return size**",Integer.toString(c_list.size()));
            return c_list==null ? 0 : c_list.size();
        }

        // ** Inner class for adapter
        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            final TextView textViewItem;
            final ImageView image;
            private ItemClickListener clickListener;



            public ViewHolder(View itemView) {
                super(itemView);
                textViewItem = (TextView) itemView.findViewById(R.id.item);
                image = (ImageView) itemView.findViewById(R.id.row_photo_image);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

            }

            public void setClickListener(ItemClickListener itemClickListener) {
                this.clickListener = itemClickListener;
            }

            @Override
            public void onClick(View view) {
                clickListener.onClick(view, getPosition(), false);
            }

            @Override
            public boolean onLongClick(View view) {
                clickListener.onClick(view, getPosition(), true);
                return true;
            }

            public void setValues(Main main) {
                textViewItem.setText(main.getItem());
                Log.d("main.getItem()**", main.getItem());
                Log.d("main.getPhoto()**", main.getImage());

                Glide.with(image.getContext())
                        .load(main.getImage())
                        .into(image);
            }



        } // ** end of inner class viewholder

    }// end of RecylerAdapter

} //End of TopLevelActivity Class







