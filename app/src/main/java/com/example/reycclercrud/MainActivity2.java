package com.example.reycclercrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.reycclercrud.databinding.ActivityMain2Binding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity2 extends AppCompatActivity {
ActivityMain2Binding binding;
Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recylcerview.setLayoutManager(layoutManager);
        Query query=FirebaseDatabase.getInstance().getReference().child("Student");
        FirebaseRecyclerOptions<model> options =new FirebaseRecyclerOptions.Builder<model> ()
         .setQuery(query, model.class)
                .build();
        
        adapter=new Adapter(options);
        binding.recylcerview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}