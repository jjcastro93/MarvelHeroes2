package com.example.juan.marvelheroes.API;

import com.example.juan.marvelheroes.Models.Data;
import com.example.juan.marvelheroes.Models.Basic;
import com.example.juan.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Marvel {

    String BASE_URL = "https://gateway.marvel.com/";

    String API_KEY = "apikey";
    String API_KEY_VALUE = "42e522979ef7df35a44fb2e1d5ae7291";

    String TIME_STAMP = "ts";
    String TIME_STAMP_VALUE = "1";

    String HASH = "hash";
    String HASH_VALUE = "daf7f1f07bd132f87352ec10ee9d2ede";

    @GET("v1/public/series/{seriesId}/characters")
    Call<Basic<Data<ArrayList<SuperHero>>>> getSuperHeroes(@Path("seriesId") int seriesId);

}
