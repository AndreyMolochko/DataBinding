package com.example.databinding.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.databinding.R;
import com.example.databinding.databinding.ItemRecyclerIngredientsBinding;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    private List<String> ingredientList;

    public IngredientAdapter(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemRecyclerIngredientsBinding binding = DataBindingUtil.inflate(inflater,
                                                                         R.layout.item_recycler_ingredients,
                                                                         viewGroup, false);
        return new IngredientHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder ingredientHolder, int i) {
        ingredientHolder.bind(ingredientList.get(i));
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setData(List<String> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {

        ItemRecyclerIngredientsBinding binding;

        IngredientHolder(ItemRecyclerIngredientsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(String ingredient) {
            binding.setIngredient(ingredient);
        }
    }
}
