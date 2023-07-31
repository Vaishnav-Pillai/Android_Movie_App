package com.example.androidmovieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {
    private static final String BASE_IMAGE_URL="https://image.tmdb.org/t/p";
    private static final String BASE_POSTER_LARGE_URL = BASE_IMAGE_URL + "/w342";

    @SerializedName("title") String title;
    @SerializedName("vote_average") float votes;
    @SerializedName("poster_path") String posterPath;

    public String getLargePosterUrl() { return BASE_POSTER_LARGE_URL + posterPath; }

}
