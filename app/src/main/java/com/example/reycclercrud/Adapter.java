package com.example.reycclercrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reycclercrud.databinding.ListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
