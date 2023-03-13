package com.example.reycclercrud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.reycclercrud.databinding.ActivityMainBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
int maxid;
Uri uri;
String imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Intent.ACTION_PICK);
                       intent.setType("image/*");
               startActivityForResult(intent,45);
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid= (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      binding.insertbutton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              FirebaseStorage.getInstance().getReference().child("FolderName").child(String.valueOf(maxid+1)).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Task<Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
                      while (!uriTask.isComplete());
                       imageUri=uriTask.getResult().toString();

                      HashMap<String,Object>map=new HashMap<>();
                      map.put("first",binding.editTextTextPersonName.getText().toString());
                      map.put("second",binding.editTextTextPersonName2.getText().toString());
                      map.put("third",binding.editTextTextPersonName3.getText().toString());
                      map.put("ImageUri", imageUri);

                      FirebaseDatabase.getInstance().getReference().child("Student").child(String.valueOf(maxid+1)).setValue(map);

                  }
              });
              Toast.makeText(MainActivity.this, "Data Send", Toast.LENGTH_SHORT).show();
              Intent intent=new Intent(MainActivity.this,MainActivity2.class);
              startActivity(intent);
          }
      });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            uri=data.getData();
            binding.imageView2.setImageURI(uri);
        }
    }
}