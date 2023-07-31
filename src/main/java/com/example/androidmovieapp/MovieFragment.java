package com.example.androidmovieapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmovieapp.databinding.FragmentMovieBinding;
import com.example.androidmovieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentMovieBinding binding;
    private String tag;
    private boolean displayedMovies=false;
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false);
        tag = getTag();
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this);
        displayMovies();
        return binding.getRoot();
    }

    private void displayMovies(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String apiKey = sharedPreferences.getString("eb7ec9ba24accf0d7d9ad36bce53408f","eb7ec9ba24accf0d7d9ad36bce53408f");
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(apiKey)){
            switch (tag){
                case MainActivity.FRAG_TAG_POPULAR:
                    TMDbClient.getInstance().getPopularMovies(apiKey).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(List<Movie> movies) {
                            if (movies!=null){
                                displayedMovies=true;
                                binding.groupMessage.setVisibility(View.GONE);
                                MovieAdapter adapter = new MovieAdapter(movies);
                                binding.rvMovie.setAdapter(adapter);
                            }
                        }
                    });
                    break;
                case MainActivity.FRAG_TAG_TOP_RATED:
                    TMDbClient.getInstance().getTopRatedMovies(apiKey).observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(List<Movie> movies) {
                            if (movies!=null){
                                displayedMovies=true;
                                binding.groupMessage.setVisibility(View.GONE);
                                MovieAdapter adapter = new MovieAdapter(movies);
                                binding.rvMovie.setAdapter(adapter);
                            }
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!displayedMovies){
            displayMovies();
        }
    }
}