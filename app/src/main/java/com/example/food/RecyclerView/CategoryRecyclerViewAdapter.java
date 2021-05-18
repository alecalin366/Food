package com.example.food.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food.Interfaces.IGetCategory;
import com.example.food.Models.Category;
import com.example.food.R;


import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>{

    private Context mContext;
    private ArrayList<Category> _options;
    private IGetCategory GetCategory;

    public CategoryRecyclerViewAdapter(Context mContext, ArrayList<Category> options, IGetCategory category) {
        this.mContext = mContext;
        _options = options;
        GetCategory = category;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category model = _options.get(position);

        holder.category_text.setText(model.getName_category());

        Glide.with(holder.category_imageView.getContext())
                .load(model.getPictureURL())
                .into(holder.category_imageView);

        holder._parentView.setOnClickListener(view -> GetCategory.getCategory(model));
    }

    @Override
    public int getItemCount() {
        return _options.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private ImageView category_imageView;
        private TextView category_text;
        private View _parentView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_imageView = itemView.findViewById(R.id.category_imageView);
            category_text = itemView.findViewById(R.id.category_text);
            _parentView = itemView.findViewById(R.id.categoryParent);
        }
    }

}
