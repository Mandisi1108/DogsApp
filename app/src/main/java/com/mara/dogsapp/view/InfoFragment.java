package com.mara.dogsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mara.dogsapp.R;
import com.mara.dogsapp.databinding.FragmentInfoBinding;
import com.mara.dogsapp.model.DogBreed;
import com.mara.dogsapp.model.DogPalette;
import com.mara.dogsapp.util.Util;
import com.mara.dogsapp.viewmodel.InfoViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private int dogUuid;
    private InfoViewModel infoViewModel;
    FragmentInfoBinding fragmentInfoBinding;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container,
                false);
        return fragmentInfoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            dogUuid = InfoFragmentArgs.fromBundle(getArguments()).getDogUuid();
        }

        infoViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        infoViewModel.fetch(dogUuid);
        observeViewModel();
    }

    private void observeViewModel() {
        infoViewModel.dogLiveData.observe(this, dogBreed -> {
            if (dogBreed != null && dogBreed instanceof DogBreed && getContext() != null) {
                fragmentInfoBinding.setDogs(dogBreed);
                if (dogBreed.imageUrl != null) {
                    backgroundColor(dogBreed.imageUrl);
                }
            }
        });
    }

    private void backgroundColor(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<?
                            super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            int color = palette.getLightMutedSwatch().getRgb();
                            DogPalette myPalette = new DogPalette(color);
                            fragmentInfoBinding.setPalette(myPalette);
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
