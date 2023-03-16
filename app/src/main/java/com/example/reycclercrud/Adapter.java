package com.example.reycclercrud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reycclercrud.databinding.ListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;

public class Adapter extends FirebaseRecyclerAdapter<model,Adapter.viewholder> {


    public Adapter(@NonNull FirebaseRecyclerOptions<model> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull model model) {
    holder.binding.textView.setText(model.getFirst());
    holder.binding.textView2.setText(model.getSecond());
    holder.binding.textView3.setText(model.getThird());


       Glide.with(holder.itemView.getContext()).load(model.getImageUri()).into(holder.binding.imageView);
    holder.binding.edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogPlus dialogPlus= DialogPlus.newDialog(holder.binding.imageView.getContext())
                    .setContentHolder(new ViewHolder(R.layout.diaglogdesign))
                    .setExpanded(true,1100)
                    .create();

            View myview=dialogPlus.getHolderView();
           EditText image= myview.findViewById(R.id.diagtwo);
           EditText name=myview.findViewById(R.id.diagthree);
           EditText fathername=myview.findViewById(R.id.diagfour);
           EditText email=myview.findViewById(R.id.diagfive);
            Button button=myview.findViewById(R.id.button2);

           image.setText(model.getImageUri());
           name.setText(model.getFirst());
           fathername.setText(model.getSecond());
           email.setText(model.getThird());

           dialogPlus.show();

           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   HashMap<String,Object>map=new HashMap<>();
                   map.put("imageUri",image.getText().toString());
                   map.put("first",name.getText().toString());
                   map.put("second",fathername.getText().toString());
                   map.put("third",email.getText().toString());

                   FirebaseDatabase.getInstance().getReference().child("Student").child(getRef(position).getKey()).updateChildren(map)
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   dialogPlus.dismiss();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   dialogPlus.dismiss();
                               }
                           });
               }
           });

        }
    });
    holder.binding.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.binding.imageView.getContext());
            builder.setTitle("Delete")
                    .setMessage("Are you sure to delete this");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase.getInstance().getReference().child("Student").child(getRef(position).getKey()).removeValue();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        }
    });
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new viewholder(view);
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ListItemBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=ListItemBinding.bind(itemView);

        }
    }
}
