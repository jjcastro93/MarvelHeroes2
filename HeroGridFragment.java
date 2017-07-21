package com.example.juan.marvelheroes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juan.marvelheroes.Adapters.HeroGridAdapter;
import com.example.juan.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

public class HeroGridFragment extends Fragment {

    public static final String HERO_DETAIL_FRAGMENT = "HERO_DETAIL_FRAGMENT";
    public static final String HERO_DETAIL = "HERO_DETAIL";
    RecyclerView rvHeroesGrid ;

    public static final String TAG = HeroGridFragment.class.getSimpleName();
    ArrayList<SuperHero> superHeros;

    public HeroGridFragment() {
        // Required empty public constructor
    }

    public interface HeroClickListener{
        void onHeroClicked(SuperHero superHero);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            superHeros = getArguments().getParcelableArrayList(MainActivity.HERO_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero_grid, container, false);

        rvHeroesGrid = view.findViewById(R.id.rvHerosGrid);

        HeroGridAdapter heroGridAdapter = new HeroGridAdapter(getContext(), superHeros, new HeroGridFragment.HeroClickListener() {
            @Override
            public void onHeroClicked(SuperHero superHero) {
                //cambiar de fragment  a el HeroDetailFragment
                goToHeroDetailFragment(superHero);
            }
        });

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numColums = (int) (dpWidth/200);
        rvHeroesGrid.setLayoutManager(new GridLayoutManager(getContext(),numColums));

        rvHeroesGrid.setAdapter(heroGridAdapter);
        return view;
    }

    private void goToHeroDetailFragment(SuperHero superHero) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(HERO_DETAIL, superHero);

        HeroDetailFragment heroDetailFragment = new HeroDetailFragment();
        heroDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, heroDetailFragment, HERO_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}