package com.example.juan.marvelheroes;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private static final String HERO_GRID_FRAGMENT = "hero_grid_fragment";
    public static final int SUCCES_CODE = 200;
    public static final String HERO_LIST = "hero_list";

    private ArrayList<SuperHero> superHeros;

    @BindView(R.id.placeholder) FrameLayout placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getHeroList();

    }

    private void getHeroList(){

        Call<Basic<Data<ArrayList<SuperHero>>>> superHeroesCall = MarvelService.getMarvelAPI().getSuperHeroes(AVENGERS_SERIE_ID);
        superHeroesCall.enqueue(new Callback<Basic<Data<ArrayList<SuperHero>>>>() {
            @Override
            public void onResponse(Call<Basic<Data<ArrayList<SuperHero>>>> call, Response<Basic<Data<ArrayList<SuperHero>>>> response) {

                if (response.code() == SUCCES_CODE){

                    superHeros = response.body().getData().getResults();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(HERO_LIST, superHeros);

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
                    if (isTablet) {
                        Toast.makeText(MainActivity.this, "Esta es una tablet", Toast.LENGTH_LONG).show();
                        HeroGridFragment savedHeroGridFragment = (HeroGridFragment) fragmentManager.findFragmentByTag(HERO_GRID_FRAGMENT);

                        if(savedHeroGridFragment == null){
                            HeroGridFragment heroGridFragment = new HeroGridFragment();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            heroGridFragment.setArguments(bundle);
                            fragmentTransaction.add(R.id.placeholder, heroGridFragment, HERO_GRID_FRAGMENT);
                            fragmentTransaction.commit();
                        }
                    }else {
                        Toast.makeText(MainActivity.this, "Esta es un telefono", Toast.LENGTH_LONG).show();
                        HeroListFragment savedHeroListFragment = (HeroListFragment) fragmentManager.findFragmentByTag(HERO_LIST_FRAGMENT);

                        if(savedHeroListFragment == null){
                            HeroListFragment heroListFragment = new HeroListFragment();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            heroListFragment.setArguments(bundle);
                            fragmentTransaction.add(R.id.placeholder, heroListFragment, HERO_LIST_FRAGMENT);
                            fragmentTransaction.commit();
                        }
                    }


                    Log.d(TAG, "Hero name: " + superHeros.get(2).getName());
                }else{
                    displayErrorMessage(getString(R.string.server_error_message));
                }

            }

            @Override
            public void onFailure(Call<Basic<Data<ArrayList<SuperHero>>>> call, Throwable t) {
                displayErrorMessage(getString(R.string.network_error_message));
            }
        });
    }

    public void displayErrorMessage(String message){
        Snackbar snackbar = Snackbar.make(placeholder, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getHeroList();
                    }
                });

        snackbar.show();
    }
}
