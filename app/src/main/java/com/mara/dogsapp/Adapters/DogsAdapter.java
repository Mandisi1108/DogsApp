package com.mara.dogsapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mara.dogsapp.R;
import com.mara.dogsapp.databinding.ItemDogBinding;
import com.mara.dogsapp.model.DogBreed;
import com.mara.dogsapp.util.Util;
import com.mara.dogsapp.view.DogClickListener;
import com.mara.dogsapp.view.HomeFragmentDirections;
import com.mara.dogsapp.view.InfoFragmentDirections;
import com.mara.dogsapp.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.DogViewHolder> implements DogClickListener {
    private ArrayList<DogBreed> dogsList;

    public DogsAdapter(ArrayList<DogBreed> dogsList) {
        this.dogsList = dogsList;
    }

    public void updateList(List<DogBreed> newDogsList) {
        dogsList.clear();
        dogsList.addAll(newDogsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDogBinding view = DataBindingUtil.inflate(inflater, R.layout.item_dog, parent,
                false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        holder.itemView.setDog(dogsList.get(position));
        holder.itemView.setClickListener(this);
    }

    @Override
    public void onDogClicked(View view) {
        String uuidString = ((TextView) view.findViewById(R.id.dogId)).getText().toString();
        int uuid = Integer.valueOf(uuidString);
        HomeFragmentDirections.ActionInfo action = HomeFragmentDirections.actionInfo();
        action.setDogUuid(uuid);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    class DogViewHolder extends RecyclerView.ViewHolder {
        public ItemDogBinding itemView;

        public DogViewHolder(@NonNull ItemDogBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
