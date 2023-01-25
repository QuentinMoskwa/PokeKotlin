package com.example.pokekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter(private val context: Context) : RecyclerView.Adapter<PokemonViewHolder>() {
    private val pokemonList = mutableListOf<Pokemon>()

    // Ajoutez cette fonction pour trier les pokemons
    fun sortPokemons() {
        pokemonList.sortWith(compareBy { it.id})
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        val id = pokemonList[viewType].id
        return PokemonViewHolder(view, id)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun addPokemon(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        sortPokemons() // trier les pokemons après chaque ajout
    }

}