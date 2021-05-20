package com.example.food.ShopActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Interfaces.IGetRecipeData;
import com.example.food.Interfaces.IGetSelectedIngredient;
import com.example.food.Interfaces.IGetStringListener;
import com.example.food.R;
import com.example.food.ShopActivity.ShopModels.IShopModel;
import com.example.food.ShopActivity.ShopModels.SectionModel;
import com.example.food.ShopActivity.ShopModels.SelectableModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<IShopModel> list;
    private IGetSelectedIngredient iGetSelectedIngredient;
    private IGetRecipeData getRecipe;

    public MyAdapter(ArrayList<IShopModel> list, IGetSelectedIngredient iGetSelectedIngredient, IGetRecipeData getRecipe)
    {
        this.list = list;
        this.iGetSelectedIngredient = iGetSelectedIngredient;
        this.getRecipe = getRecipe;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getClass().equals(SectionModel.class)) return 1;
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_section, parent, false);
            return new SectionViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cell, parent, false);
            return new SelectableModelViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getClass().equals(SectionViewHolder.class))
        {
            SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
            SectionModel sectionModel = (SectionModel) list.get(position);
            sectionViewHolder._sectionName.setText(sectionModel.getRecipe().name);
            sectionViewHolder._servings.setText(sectionModel.getRecipe().servingSize);

            if(!sectionModel.getRecipe().photo.isEmpty())
            {
                Picasso.get()
                        .load(sectionModel.getRecipe().photo)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .into(sectionViewHolder._photo);
            }

            sectionViewHolder._removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getRecipe.getRecipeData(sectionModel.getRecipe());
                }
            });

        }
        else {
            SelectableModelViewHolder selectableModelViewHolder  = (SelectableModelViewHolder) holder;
            SelectableModel model = (SelectableModel) list.get(position);

            selectableModelViewHolder._ingredientName.setText(model.getSpecialIngredient().get_ingredient().getName_ingredient());
            selectableModelViewHolder._ingredientCantitate.setText(model.getSpecialIngredient().get_ingredient().getQuantity()
                    + model.getSpecialIngredient().get_ingredient().getMeasurements());
            selectableModelViewHolder._checkBox.setChecked(model.getSpecialIngredient().is_isChecked());

            selectableModelViewHolder._checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ((SelectableModel) list.get(position)).getSpecialIngredient().set_isChecked(b);
                    iGetSelectedIngredient.GetSelectedIngredient(model.getSpecialIngredient());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView _sectionName, _servings;
        private ImageView _photo;
        private Button _removeButton;
        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            _sectionName = itemView.findViewById(R.id.sectionName);
            _servings = itemView.findViewById(R.id.servings);
            _photo = itemView.findViewById(R.id.photo);
            _removeButton = itemView.findViewById(R.id.removeButton);
        }
    }

    class SelectableModelViewHolder extends RecyclerView.ViewHolder {
        private TextView _ingredientName,_ingredientCantitate;
        private CheckBox _checkBox;
        public SelectableModelViewHolder(@NonNull View itemView) {
            super(itemView);
            _ingredientName = itemView.findViewById(R.id.ingredientName);
            _ingredientCantitate = itemView.findViewById(R.id.ingredientCantitate);
            _checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}


