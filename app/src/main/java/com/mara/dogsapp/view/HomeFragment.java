package com.mara.dogsapp.view;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mara.dogsapp.Adapters.DogsAdapter;
import com.mara.dogsapp.R;
import com.mara.dogsapp.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private DogsAdapter dogsAdapter = new DogsAdapter(new ArrayList<>());
    Context context;

    @BindView(R.id.dogsRecyclerView)
    RecyclerView dogsList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.errorTextView)
    TextView error;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.refresh();
        dogsList.setLayoutManager(new LinearLayoutManager(getContext()));
        dogsList.setAdapter(dogsAdapter);
        observeViewModel();
    }

    private void observeViewModel() {
        homeViewModel.dogs.observe(this, dogBreeds -> {
            if (dogBreeds != null && dogBreeds instanceof List) {
                dogsList.setVisibility(View.VISIBLE);
                dogsAdapter.updateList(dogBreeds);
            }
        });

        homeViewModel.loadError.observe(this, isError -> {
            if (isError != null && isError instanceof Boolean) {
                error.setVisibility(isError ? View.VISIBLE : View.GONE);
            }
        });

        homeViewModel.loading.observe(this, isLoading -> {
            if (isLoading != null && isLoading instanceof Boolean) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    error.setVisibility(View.GONE);
                    dogsList.setVisibility(View.GONE);
                }
            }
        });
    }
}
