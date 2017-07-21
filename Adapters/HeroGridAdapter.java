package com.example.juan.marvelheroes.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.juan.marvelheroes.HeroGridFragment;
import com.example.juan.marvelheroes.Models.SuperHero;
import com.example.juan.marvelheroes.R;

import java.util.ArrayList;

public class HeroGridAdapter extends RecyclerView.Adapter<HeroGridAdapter.MyViewHolder>{

    Context context;
    ArrayList<SuperHero> superHeros;
    HeroGridFragment.HeroClickListener heroClickListener;

    public HeroGridAdapter(Context context, ArrayList<SuperHero> superHeros, HeroGridFragment.HeroClickListener heroClickListener) {
        this.context = context;
        this.superHeros = superHeros;
        this.heroClickListener = heroClickListener;
    }

    @Override
    public HeroGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.hero_grid_item, parent, false);

        return new HeroGridAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HeroGridAdapter.MyViewHolder holder, int position) {

        SuperHero superHero = superHeros.get(position);

        holder.bind(context, superHero, heroClickListener);
    }

    @Override
    public int getItemCount() {
        return superHeros.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivHeroPicture;
        TextView tvHeroDescription;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivHeroPicture = itemView.findViewById(R.id.ivHeroPicture);
            tvHeroDescription = itemView.findViewById(R.id.tvHeroDetailName);
        }

        public void bind(Context context, final SuperHero superHero, final HeroGridFragment.HeroClickListener heroClickListener){
            tvHeroDescription.setText(superHero.getName());
            Glide.with(context).load(superHero.getThumbnail().getFullPath()).into(ivHeroPicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    heroClickListener.onHeroClicked(superHero);
                }
            });
        }
    }
}

