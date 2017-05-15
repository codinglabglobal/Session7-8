package com.example.firebase_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;
import android.support.v7.widget.DividerItemDecoration;


public class ContactEnhanceActivity extends AppCompatActivity {


    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_enhance);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add a underline for each item
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final MyRecyclerAdapter adapter = new MyRecyclerAdapter(contactsArrayList);
        recyclerView.setAdapter(adapter);

        final DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("contacts");
        reference_contacts.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contactsArrayList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                    Contacts contact = ds.getValue(Contacts.class);
                    contactsArrayList.add(contact);
                }
                adapter.notifyDataSetChanged();
                Log.d("onDataChange$$$$",Integer.toString(contactsArrayList.size()));
            }

            @Override
            public void onCancelled (DatabaseError dberror){

             }

        });

        adapter.notifyDataSetChanged();

        final EditText name = (EditText) findViewById(R.id.addcontact);
        final EditText address = (EditText) findViewById(R.id.addemail);
        final Button button = (Button) findViewById(R.id.addButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mRef2 =  reference_contacts.push();

                Contacts new_name = new Contacts(name.getText().toString(),address.getText().toString(), "N/A" );
                mRef2.setValue(new_name);
                Log.d("the new name$$",name.getText().toString());

                name.setText("");
                address.setText("");

            }
        });



    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

         ArrayList<Contacts> c_list;

        public MyRecyclerAdapter(ArrayList<Contacts> c_list) {
            this.c_list = c_list;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ViewHolder(v);

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Contacts contact = c_list.get(position);
            Log.d("log$$$",Integer.toString(position));
            holder.setValues(contact);
        }

        @Override
        public int getItemCount() {
            Log.d("Return size**",Integer.toString(c_list.size()));
            return c_list==null ? 0 : c_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView textViewName;
            final TextView textViewAddress;
            final TextView textViewHobby;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewName = (TextView) itemView.findViewById(R.id.name);
                textViewAddress = (TextView) itemView.findViewById(R.id.address);
                textViewHobby = (TextView) itemView.findViewById(R.id.hobby);
            }

            public void setValues(Contacts contact) {
                textViewName.setText(contact.getName());
                Log.d("contact.getName()**", contact.getName());

                textViewAddress.setText(contact.getAddress());
                Log.d("contact.getAddress()**", contact.getAddress());

                textViewHobby.setText(contact.getHobby());
                Log.d("contact.getHobby()**", contact.getHobby());

            }


        }
    }

}







