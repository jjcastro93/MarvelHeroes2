package com.example.juan.marvelheroes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.MimeTypeFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.juan.marvelheroes.Adapters.HeroAdapter;
import com.example.juan.marvelheroes.Models.SuperHero;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroListFragment extends Fragment {

    public static final String HERO_DETAIL_FRAGMENT = "HERO_DETAIL_FRAGMENT";
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

        Bundle bundle = getArguments();
        superHeros = bundle.getParcelableArrayList(MainActivity.HERO_LIST);

        if(superHeros == null) {
            Log.d(TAG, "Error al obtener a los uper heroes");

        }else{
            Log.d(TAG, "Hero: " + superHeros.get(2).getName());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);

        rvHeroesList = view.findViewById(R.id.rvHerosList);
        rvHeroesList.setLayoutManager(new LinearLayoutManager(getContext()));

        HeroAdapter heroAdapter = new HeroAdapter(getContext(), superHeros, new HeroClickListener() {
            @Override
            public void onHeroClicked(SuperHero superHero) {
                //cambiar de fragment  a el HeroDetailFragment
                goToHeroDetailFragment(superHero);
            }
        });

        HeroClickListener heroe = new HeroClickListener() {
            @Override
            public void onHeroClicked(SuperHero superHero) {
                Toast.makeText(getContext(), "hiiiiiiiiii", Toast.LENGTH_SHORT).show();
            }
        };
        rvHeroesList.setAdapter(heroAdapter);
        return view;
    }

    private void goToHeroDetailFragment(SuperHero superHero) {
        Toast.makeText(getContext(), "Hero Clicked: " + superHero.getName(), Toast.LENGTH_SHORT).show();

        HeroDetailFragment heroDetailFragment = new HeroDetailFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, heroDetailFragment, HERO_DETAIL_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
