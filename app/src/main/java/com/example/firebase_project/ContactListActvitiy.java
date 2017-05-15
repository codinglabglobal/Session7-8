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



public class ContactListActvitiy extends AppCompatActivity {

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list_actvitiy);

        // Connect to the Firebase database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Get a reference to the contacts child items it the database
        myRef = FirebaseDatabase.getInstance().getReference("contacts");


        // Get ListView object from xml
        ListView list = (ListView) findViewById(R.id.listView);
        // Create a new Adapter
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1);

        // Assign adapter to ListView
        list.setAdapter(adapter);

       // Assign a listener to detect changes to the child items
      // of the database reference.
        myRef.addChildEventListener(
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
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                    @Override
                    //public void onCancelled(FirebaseError firebaseError) {
                    public void onCancelled(DatabaseError firebaseError) {
                          //Log.w("TAG:", "Failed to read value.", error.toException());
                    }
                });


        // Add items via the Button and EditText at the bottom of the window.
        final EditText text = (EditText) findViewById(R.id.addcontact);
        final Button button = (Button) findViewById(R.id.addButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mRef2 =  myRef.push();
                mRef2.child("name").setValue(text.getText().toString());
                Log.d("the new name$$",text.getText().toString());

                text.setText("");


            }
        });
    }
}
