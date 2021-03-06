package com.example.juan.marvelheroes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juan.marvelheroes.Adapters.HeroListAdapter;
import com.example.juan.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

public class HeroListFragment extends Fragment {

    public static final String HERO_DETAIL_FRAGMENT = "HERO_DETAIL_FRAGMENT";
    public static final String HERO_DETAIL = "HERO_DETAIL";
    RecyclerView rvHeroesList;

    public static final String TAG = HeroListFragment.class.getSimpleName();
    ArrayList<SuperHero> superHeros;

    public HeroListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);

        rvHeroesList = view.findViewById(R.id.rvHerosList);
        rvHeroesList.setLayoutManager(new LinearLayoutManager(getContext()));

        HeroListAdapter heroListAdapter = new HeroListAdapter(getContext(), superHeros, new HeroClickListener() {
            @Override
            public void onHeroClicked(SuperHero superHero) {
                //cambiar de fragment  a el HeroDetailFragment
                goToHeroDetailFragment(superHero);
            }
        });

        rvHeroesList.setAdapter(heroListAdapter);
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
