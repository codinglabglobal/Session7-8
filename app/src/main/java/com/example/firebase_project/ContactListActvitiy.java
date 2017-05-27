package com.example.firebase_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.*;
import android.view.View;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class ContactListActvitiy extends AppCompatActivity {

    private DatabaseReference myRef;
    //final ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_actvitiy);

        // Connect to the Firebase database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Get a reference to the contacts child items it the database
        myRef = FirebaseDatabase.getInstance().getReference("contacts");

        //To try limit the result set
        Query query = myRef.orderByKey().limitToLast(10);



        // Get ListView object from xml
        ListView list = (ListView) findViewById(R.id.listView);
        // Create a new Adapter
        adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1);

        // Assign adapter to ListView

        list.setAdapter(adapter);

       // Assign a listener to detect changes to the child items
      // of the database reference.
        query.addChildEventListener (
       // myRef.addChildEventListener(
                new ChildEventListener() {

					// This function is called once for each child that exists
					// when the listener is added. Then it is called
                    // each time a new child is added.
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        adapter.add(
                                (String) dataSnapshot.child("name").getValue());

                    }

                    // This function is called each time a child item is removed.
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.remove(
                                (String) dataSnapshot.child("name").getValue());

                    }

                    // The following functions are also required in ChildEventListener implementations.
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                        adapter.remove(dataSnapshot.getKey());
                        adapter.add(
                                (String) dataSnapshot.child("name").getValue());



                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    //public void onCancelled(FirebaseError firebaseError) {
                    public void onCancelled(DatabaseError firebaseError) {
                          //Log.w("TAG:", "Failed to read value.", error.toException());
                    }
                });


        //add button
        /*addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mRef2 =  myRef.push();
                mRef2.child("name").setValue(text.getText().toString());
                Log.d("the new name$$",text.getText().toString());

                text.setText("");


            }
        });  */


   }


    public void clickFunction (View view){

        switch (view.getId()){
            case R.id.addButton:
                final EditText text = (EditText) findViewById(R.id.addcontact);
                if (text.getText().toString() == null || text.getText().toString().equals("") ) {
                    Toast.makeText(this, "You cannot enter empty name", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseReference mRef2 =  myRef.push();
                mRef2.child("name").setValue(text.getText().toString());
                Log.d("the new name$$",text.getText().toString());

                text.setText("");
                break;

            case R.id.updButton:
             //   myRef.child("1").child("name").setValue("Change Name AAA");
             //   Query query2 = myRef.orderByKey().limitToLast(10);
             //   adapter.clear();
             //   refresh_list(query2);

                break;

            case R.id.searchButton:
                Query query = myRef.orderByChild("name").equalTo("Student C");
                adapter.clear();
                refresh_list(query);

                break;

            case R.id.delButton:
                myRef.child("2").setValue(null);

                break;


        }

    }

    public void refresh_list(Query query){

        // Get ListView object from xml
        ListView list = (ListView) findViewById(R.id.listView);
        // Create a new Adapter
        adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1);

        // Assign adapter to ListView
        list.setAdapter(adapter);

        // Assign a listener to detect changes to the child items
        // of the database reference.
        query.addChildEventListener (
                // myRef.addChildEventListener(
                new ChildEventListener() {
                    // This function is called once for each child that exists
                    // when the listener is added. Then it is called
                    // each time a new child is added.
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        adapter.add(
                                (String) dataSnapshot.child("name").getValue());

                    }

                    // This function is called each time a child item is removed.
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.remove(
                                (String) dataSnapshot.child("name").getValue());

                    }

                    // The following functions are also required in ChildEventListener implementations.
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    //public void onCancelled(FirebaseError firebaseError) {
                    public void onCancelled(DatabaseError firebaseError) {
                        //Log.w("TAG:", "Failed to read value.", error.toException());
                    }
                });

    }


}