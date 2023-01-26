package com.example.pokekotlin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var pokeApiClient: PokeApiClient
    private lateinit var pokemonRecyclerView: RecyclerView


    //Mise en place du menu burger pour naviguer entre les layouts
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId)
        {
            R.id.nav_Equipe ->
            {
                val intent = Intent(this, Equipe::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    //permet d'ajouter le nombre de pokemon que l'on souhaite sur la page d'accueil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PokemonAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.pokemon_recycler_view)
        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = gridLayoutManager

        pokeApiClient = PokeApiClient()
        for (i in 0..1008) {
            pokeApiClient.getPokemon(i) { pokemon ->
                if (pokemon != null) {
                    adapter.addPokemon(pokemon)
                }
            }
        }
        val searchBar = findViewById<EditText>(R.id.search_bar)
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    // Réinitialisation de la liste de Pokémon à son état d'origine
                    adapter.updatePokemonList(adapter.originalPokemonList)
                }
                else
                {
                    // Appel de la méthode de filtrage de la liste des pokémons
                    val filteredList = adapter.originalPokemonList.filter { pokemon ->
                        pokemon.name.lowercase().contains(s.toString().lowercase())
                    }

                    adapter.updatePokemonList(filteredList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
}