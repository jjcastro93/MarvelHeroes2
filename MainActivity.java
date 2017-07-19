package com.example.juan.marvelheroes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.juan.marvelheroes.API.MarvelService;
import com.example.juan.marvelheroes.Models.Data;
import com.example.juan.marvelheroes.Models.Basic;
import com.example.juan.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int AVENGERS_SERIE_ID = 354;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String HERO_LIST_FRAGMENT = "hero_list_fragment";
    public static final int SUCCES_CODE = 200;

    @BindView(R.id.placeholder) FrameLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Call<Basic<Data<ArrayList<SuperHero>>>> superHeroesCall = MarvelService.getMarvelAPI().getSuperHeroes(AVENGERS_SERIE_ID);
        superHeroesCall.enqueue(new Callback<Basic<Data<ArrayList<SuperHero>>>>() {
            @Override
            public void onResponse(Call<Basic<Data<ArrayList<SuperHero>>>> call, Response<Basic<Data<ArrayList<SuperHero>>>> response) {

                if (response.code() == SUCCES_CODE){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    HeroListFragment heroListFragment = new HeroListFragment();
                    fragmentTransaction.add(R.id.placeholder, heroListFragment, HERO_LIST_FRAGMENT);
                    fragmentTransaction.commit();
                    Log.d(TAG, "Hero name: " + response.body().getData().getResults().get(2).getName());
                }else{
                    Log.d(TAG, "Error en la respuesta");
                    Log.d(TAG, "Error en la respuesta");
                }

            }

            @Override
            public void onFailure(Call<Basic<Data<ArrayList<SuperHero>>>> call, Throwable t) {
                Log.d(TAG, "Error en la llamada");
            }
        });

    }
}
