package com.example.juan.marvelheroes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.juan.marvelheroes.Models.SuperHero;

public class HeroDetailFragment extends Fragment {

    SuperHero superHero;
    private TextView tvHeroDetailTitle;
    private TextView tvHeroDetailDescription;
    private ImageView ivHeroDetailThumbnail;

    public HeroDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            superHero = getArguments().getParcelable(HeroListFragment.HERO_DETAIL);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hero_detail, container, false);

        tvHeroDetailTitle = view.findViewById(R.id.tvHeroDetailTitle);
        ivHeroDetailThumbnail = view.findViewById(R.id.ivHeroDetailThumbnail);
        tvHeroDetailDescription = view.findViewById(R.id.tvHeroDetailDescription);

        tvHeroDetailTitle.setText(superHero.getName());

        if (superHero.getDescription() != null && !superHero.getDescription().isEmpty()){
            tvHeroDetailDescription.setText(superHero.getDescription());
        }else{
            tvHeroDetailDescription.setText(R.string.no_information_available);
        }
        Glide.with(getContext()).load(superHero.getThumbnail().getFullPath()).into(ivHeroDetailThumbnail);

        return view;
    }

}
