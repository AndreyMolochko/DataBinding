package com.example.databinding.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.example.databinding.R;
import com.example.databinding.databinding.ItemRecyclerDishesBinding;
import com.example.databinding.model.Dish;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishHolder> {

    private List<Dish> dishList;
    private List<Dish> dishesForSearchViewList;
    private OnClickListener callback;

    public interface OnClickListener {
        void onClickMore(View view, Dish dish);

        void onClickCooking(Dish dish);
    }

    public DishAdapter(List<Dish> dishList, OnClickListener onClickListener) {
        this.dishList = new ArrayList<>();
        this.dishList.addAll(dishList);
        dishesForSearchViewList = dishList;
        callback = onClickListener;
    }

    @NonNull
    @Override
    public DishHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemRecyclerDishesBinding binding = DataBindingUtil.inflate(inflater,
                                                                    R.layout.item_recycler_dishes,
                                                                    viewGroup, false);
        return new DishHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final DishHolder dishHolder, int i) {
        dishHolder.bind(dishesForSearchViewList.get(i));
        initRecycler(dishHolder, i);
    }

    @Override
    public int getItemCount() {
        return dishesForSearchViewList.size();
    }

    public void setData(List<Dish> dishList) {
        this.dishList.clear();
        this.dishList.addAll(dishList);
        dishesForSearchViewList = dishList;
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Dish> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.clear();
                filteredList.addAll(dishList);
            } else {
                String filterPattern = constraint.toString()
                                                 .toLowerCase()
                                                 .trim();

                for (Dish item : dishList) {
                    if (item.getName()
                            .toLowerCase()
                            .contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dishesForSearchViewList.clear();
            dishesForSearchViewList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private void initRecycler(DishHolder dishHolder, int position) {
        IngredientAdapter ingredientAdapter = new IngredientAdapter(
                dishesForSearchViewList.get(position)
                                       .getIngredientList());
        dishHolder.binding.recyclerIngredients.setLayoutManager(new LinearLayoutManager(
                dishHolder.binding.getRoot()
                                  .getContext()));
        dishHolder.binding.recyclerIngredients.setAdapter(ingredientAdapter);
        initListeners(dishHolder, dishesForSearchViewList.get(position));
    }

    private void initListeners(DishHolder dishHolder, Dish dish) {
        dishHolder.binding.itemRecyclerDishesArrowImageView.setOnClickListener(
                v -> dishHolder.onClickArrow());
        dishHolder.binding.itemRecyclerDishesCookButton.setOnClickListener(v -> {
            callback.onClickCooking(dish);
        });
        dishHolder.binding.itemRecyclerDishesMoreImageView.setOnClickListener(v -> {
            callback.onClickMore(dishHolder.binding.itemRecyclerDishesMoreImageView, dish);
        });
    }

    public class DishHolder extends RecyclerView.ViewHolder {

        private ItemRecyclerDishesBinding binding;
        boolean isShowingIngredients = false;

        DishHolder(ItemRecyclerDishesBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(Dish dish) {
            binding.setDish(dish);
        }

        public void onClickArrow() {
            if (isShowingIngredients) {
                binding.itemRecyclerDishesArrowImageView.setImageDrawable(binding.getRoot()
                                                                                 .getContext()
                                                                                 .getDrawable(
                                                                                         R.drawable.ic_arrow_down));
                setVisibilityRecyclerIngredients(View.GONE);
                isShowingIngredients = false;
            } else {
                binding.itemRecyclerDishesArrowImageView.setImageDrawable(binding.getRoot()
                                                                                 .getContext()
                                                                                 .getDrawable(
                                                                                         R.drawable.ic_arrow_up));
                setVisibilityRecyclerIngredients(View.VISIBLE);
                isShowingIngredients = true;
            }
        }

        private void setVisibilityRecyclerIngredients(int visibility) {
            binding.recyclerIngredients.setVisibility(visibility);
        }
    }
}