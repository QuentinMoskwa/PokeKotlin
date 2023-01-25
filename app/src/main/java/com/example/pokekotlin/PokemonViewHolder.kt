package com.example.pokekotlin

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonViewHolder(itemView: View, val id: Int) : RecyclerView.ViewHolder(itemView) {
    private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
    private val pokemonImage: ImageView = itemView.findViewById(R.id.image_pokemon)


    fun bind(pokemon: Pokemon) {
        pokemonImage.setOnClickListener {
            val i = Intent(itemView.context, Stats::class.java)
            i.putExtra("pokemon_id", pokemon.id)
            itemView.context.startActivity(i)
        }
        pokemonName.text = pokemon.name
        Glide.with(itemView.context).load(pokemon.sprites.front_default).into(pokemonImage)
    }
}