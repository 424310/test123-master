package com.example.test123;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class CategoryCreate extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userID;

    EditText name, address, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        name = (EditText) findViewById(R.id.C_name);
        address = (EditText) findViewById(R.id.C_address);
        number = (EditText) findViewById(R.id.C_number);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("DataCategories");
        userID = mFirebaseDatabase.push().getKey();
        Log.d("ttt", "ddd3");
    }

    public void addCategory(String name, String address, String number) {
        Category Categories = new Category(name, address, number);
        Log.d("ttt", "ddd"+name);
        mFirebaseDatabase.child("Categories").child(userID).setValue(Categories);
        Log.d("ttt", "ddd2");
    }

    public void updateCategory(String C_name, String C_address, String C_number) {
        mFirebaseDatabase.child("Categories").child(userID).child("name").setValue(name);
        mFirebaseDatabase.child("Categories").child(userID).child("address").setValue(address);
        mFirebaseDatabase.child("Categories").child(userID).child("number").setValue(number);
    }

    public void addData(View view) {
        addCategory(name.getText().toString().trim(), address.getText().toString().trim(), number.getText().toString().trim());
    }

    public void updateData(View view) {
        updateCategory(name.getText().toString().trim(), address.getText().toString().trim(), number.getText().toString().trim());
    }

    public void readData (View view){
        mFirebaseDatabase.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        String dbnmae = ds.child("name").getValue(String.class);
                        String dbaddress = ds.child("address").getValue(String.class);
                        String dbnumber = ds.child("number").getValue(String.class);
                        Log.d("TAG", dbnmae+"/"+dbaddress+"/"+dbnumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
