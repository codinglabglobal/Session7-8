package com.example.cafe_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //**20 May Step 1 declaration
    private ArrayList<Main> itemsArrayList = new ArrayList<>();

    //**  20 MayCreate an interface ItemClickListener with prototype of onClick method
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent email = new Intent(Intent.ACTION_SEND);
                startActivityForResult(email,1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //**added on 020 May for navigation draower
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, menuItem.getTitle() + " pressed", Toast.LENGTH_LONG).show();

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //** add 20 May for recycler view
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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    } */



    class MyRecyclerAdapter extends RecyclerView.Adapter<MainActivity.MyRecyclerAdapter.ViewHolder>  {

        ArrayList<Main> c_list;
        //constructor
        public MyRecyclerAdapter(ArrayList<Main> c_list) {
            this.c_list = c_list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new MyRecyclerAdapter.ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
            Main main = c_list.get(position);
            Log.d("log$$$",Integer.toString(position));
            holder.setValues(main);

            holder.setClickListener(new TopLevelActivity.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
                        Log.d("position**$**",Integer.toString(position));
                        //Toast.makeText(this, "#" + Integer.toString(position) + " - " + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("position**xxxxx**",Integer.toString(position));
                        switch(position){
                            case 0:
                                Intent intentDrink = new Intent(MainActivity.this,DrinkActivity.class);
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
            private TopLevelActivity.ItemClickListener clickListener;



            public ViewHolder(View itemView) {
                super(itemView);
                textViewItem = (TextView) itemView.findViewById(R.id.item);
                image = (ImageView) itemView.findViewById(R.id.row_photo_image);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

            }

            public void setClickListener(TopLevelActivity.ItemClickListener itemClickListener) {
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

    } //** end of recycler adapter



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //** 20 May
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        //return super.onCreateOptionsMenu(menu);
        //end 20 May

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
