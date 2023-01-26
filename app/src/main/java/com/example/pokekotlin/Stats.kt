package com.example.pokekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide

class Stats : AppCompatActivity() {
    private lateinit var pokeApiClient: PokeApiClient
    val pokemonData = mutableListOf<String>()
    var isShiny: Boolean = false

    //Mise en place du menu burger pour naviguer entre les layouts
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_Pokedex -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_Equipe -> {
                val intent = Intent(this, Equipe::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        val pokemonId = intent.getIntExtra("pokemon_id", 0)
        pokeApiClient = PokeApiClient()

        //bouton pour ajouter à l'équipe
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (MyApplication.userTeam.size as Int <= 5) {
                pokeApiClient.getPokemon(pokemonId) { pokemon ->
                    if (pokemon != null) {
                        pokemonData.add(pokemonId.toString())
                        pokemonData.add(pokemon.name)
                        pokemonData.add(isShiny.toString())
                        MyApplication.userTeam.add(pokemonData)
                        (application as MyApplication).saveUserTeam()
                        Toast.makeText(this, "$pokemonData", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "l'équipe est déjà au complet", Toast.LENGTH_SHORT).show()
            }
        }

        //affichage de la carte
            val pokemonName: TextView = findViewById(R.id.nom_poke_stat)
            val pokemonAttaque: TextView = findViewById(R.id.statAttaque)
            val pokemonDefense: TextView = findViewById(R.id.statDefense)
            val pokemonHP: TextView = findViewById(R.id.statHP)
            val pokemonVitesse: TextView = findViewById(R.id.statVitesse)
            val pokemonImage: ImageView = findViewById(R.id.img_stat)
            val pokemonType: TextView = findViewById(R.id.type_pokemon)
            val pokemonHeight: TextView = findViewById(R.id.statHeight)
            val pokemonWeight: TextView = findViewById(R.id.statWeight)
            val pokemonIsShiny: Switch = findViewById(R.id.isShiny)

            pokeApiClient.getPokemon(pokemonId) { pokemon ->
                if (pokemon != null) {
                    //val pokemon = pokemonResponse.toPokemon()
                    pokemonIsShiny.text = "Normal"
                    pokemonIsShiny.setOnClickListener{ true
                        if (isShiny == false){
                            Glide.with(this)
                                .load(pokemon.sprites.front_shiny)
                                .into(pokemonImage)
                            isShiny = true
                            pokemonIsShiny.text = "* * * Shiny * * *"

                        }
                        else if (isShiny == true){
                            Glide.with(this)
                                .load(pokemon.sprites.front_default)
                                .into(pokemonImage)
                            isShiny = false
                            pokemonIsShiny.text = "Normal"
                        }
                    }
                    pokemonName.text = "#" + pokemon.id.toString() + " " + pokemon.name
                    Glide.with(this)
                        .load(pokemon.sprites.front_default)
                        .into(pokemonImage)
                    for (statname in pokemon.stats) {
                        if (statname.stat.name == "attack") {
                            //        Log.d("pokeApi","$pokemon.stats.stat")
                            pokemonAttaque.text = statname.base_stat.toString()
                        } else if (statname.stat.name == "defense") {
                            pokemonDefense.text = statname.base_stat.toString()

                        } else if (statname.stat.name == "speed") {
                            pokemonVitesse.text = statname.base_stat.toString()

                        } else if (statname.stat.name == "hp") {
                            pokemonHP.text = statname.base_stat.toString()
                        }
                    }
                    //permet d'afficher tous les types si il en a plusieurs
                    if (pokemon.types.size >1){
                        var alltype = ""
                        //for (i in 0 until pokemon.types.size-1){
                            for ((index, typename) in pokemon.types.withIndex()) {
                                if(index != 0 )
                                {
                                    alltype += " + " + typename.type.name
                                }
                                else
                                {
                                    alltype += typename.type.name
                                }
                            }
                            pokemonType.text = alltype
                        //}
                    }
                    else{
                        var alltype = ""
                        for (i in 0 until pokemon.types.size){
                            for (typename in pokemon.types) {
                                alltype += typename.type.name
                            }
                            pokemonType.text = alltype
                        }
                    }
                    pokemonHeight.text = pokemon.height.toString() + " dm"
                    pokemonWeight.text = pokemon.weight.toString() + " hg"
                    Log.d("pokeApi", "${pokemon}")
                }

            }
        }
    }
