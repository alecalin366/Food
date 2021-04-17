package com.example.food.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Models.Category;
import com.example.food.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CategoryRecyclerViewAdapter extends FirestoreRecyclerAdapter<Category, CategoryRecyclerViewAdapter.CategoryViewHolder>{

    private Context mContext;
    public CategoryRecyclerViewAdapter(Context mContext, @NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
        this.mContext = mContext;;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {
        holder.category_text.setText(model.getName_category());

        Glide.with(holder.category_imageView.getContext())
                .load(model.getPictureURL())
                .into(holder.category_imageView);

        holder.category_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, model.getPictureURL(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView category_imageView;
        private TextView category_text;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_imageView = itemView.findViewById(R.id.category_imageView);
            category_text = itemView.findViewById(R.id.category_text);
        }
    }

}
