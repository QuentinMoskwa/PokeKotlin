package com.example.pokekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class Equipe : AppCompatActivity() {
    private lateinit var pokeApiClient: PokeApiClient

    //Mise en place du menu burger pour naviguer entre les layouts
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId)
        {
            R.id.nav_Pokedex ->
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    //Mise en place des pokémon dans l'équipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipe)
        val pokemon1ImageView: ImageView = findViewById(R.id.pokemon1)
        val pokemon2ImageView: ImageView = findViewById(R.id.pokemon2)
        val pokemon3ImageView: ImageView = findViewById(R.id.pokemon3)
        val pokemon4ImageView: ImageView = findViewById(R.id.pokemon4)
        val pokemon5ImageView: ImageView = findViewById(R.id.pokemon5)
        val pokemon6ImageView: ImageView = findViewById(R.id.pokemon6)
        val pokemon1TextView: TextView = findViewById(R.id.pokemon1text)
        val pokemon2TextView: TextView = findViewById(R.id.pokemon2text)
        val pokemon3TextView: TextView = findViewById(R.id.pokemon3text)
        val pokemon4TextView: TextView = findViewById(R.id.pokemon4text)
        val pokemon5TextView: TextView = findViewById(R.id.pokemon5text)
        val pokemon6TextView: TextView = findViewById(R.id.pokemon6text)
        val reset: TextView = findViewById(R.id.resetText)
        if(MyApplication.userTeam.size !=0)
        {
            pokeApiClient = PokeApiClient()
            val pokemonIds = listOf(pokemon1ImageView, pokemon2ImageView, pokemon3ImageView, pokemon4ImageView, pokemon5ImageView, pokemon6ImageView)
            val pokemonNames = listOf(pokemon1TextView, pokemon2TextView, pokemon3TextView, pokemon4TextView, pokemon5TextView, pokemon6TextView)
            for (i in 0 until MyApplication.userTeam.size) {
                val ID = MyApplication.userTeam[i][0].toInt()
                pokeApiClient.getPokemon(ID) { pokemon ->
                        if (pokemon != null) {
                            pokemonNames[i].text =  MyApplication.userTeam[i][1]
                            if(MyApplication.userTeam[i][2] == "false")
                            {
                                Glide.with(this).load(pokemon.sprites.front_default).into(pokemonIds[i])
                            }
                            else
                            {
                                Glide.with(this).load(pokemon.sprites.front_shiny).into(pokemonIds[i])
                            }
                        }
                    }
                //}
            }
        }
            reset.setOnClickListener()
            {
                MyApplication.userTeam.clear()
                (application as MyApplication).saveUserTeam()
                val intent = Intent(this, Equipe::class.java)
                finish()
                startActivity(intent)
            }
        //permet d'afficher les stats des différents pokemons de l'équipe
            pokemon1ImageView.setOnClickListener {
                var size = MyApplication.userTeam.size as Int
                if (size >= 1) {
                    val pokemonid = MyApplication.userTeam[0][0].toInt()
                    val pokemonName = MyApplication.userTeam[0][1]
                    val i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 0)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }

            pokemon2ImageView.setOnClickListener {
                if (MyApplication.userTeam.size >= 2) {
                    var pokemonid = MyApplication.userTeam[1][0].toInt()
                    var pokemonName = MyApplication.userTeam[1][1]
                    var i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 1)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }

            pokemon3ImageView.setOnClickListener {
                if (MyApplication.userTeam.size >= 3) {
                    var pokemonid = MyApplication.userTeam[2][0].toInt()
                    var pokemonName = MyApplication.userTeam[2][1]
                    var i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 2)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }

            pokemon4ImageView.setOnClickListener {
                if (MyApplication.userTeam.size >= 4) {
                    var pokemonid = MyApplication.userTeam[3][0].toInt()
                    var pokemonName = MyApplication.userTeam[3][1]
                    var i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 3)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }

            pokemon5ImageView.setOnClickListener {
                if (MyApplication.userTeam.size >= 5) {
                    var pokemonid = MyApplication.userTeam[4][0].toInt()
                    var pokemonName = MyApplication.userTeam[4][1]
                    var i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 4)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }

            pokemon6ImageView.setOnClickListener {
                if (MyApplication.userTeam.size >= 6) {
                    var pokemonid = MyApplication.userTeam[5][0].toInt()
                    var pokemonName = MyApplication.userTeam[5][1]
                    var i = Intent(this, MonPokemon::class.java)
                    i.putExtra("pokemon_id", pokemonid)
                    i.putExtra("pokemon_num", 5)
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Pas de pokémon ici", Toast.LENGTH_SHORT).show()
                }
            }
        }
}
