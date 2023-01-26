package com.example.pokekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.bumptech.glide.Glide

class MonPokemon : AppCompatActivity() {
    private lateinit var pokeApiClient: PokeApiClient
    val pokemonData = mutableListOf<String>()

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

    //affiche les stats dpokemon sélectionner dans l'équipe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mon_pokemon)
        val pokemonId = intent.getIntExtra("pokemon_id", 0)
        val pokemonNum = intent.getIntExtra("pokemon_num", 0)
        pokeApiClient = PokeApiClient()

        //affichage de la carte
        val pokemonID: TextView = findViewById(R.id.id_pokemon)
        val pokemonName: TextView = findViewById(R.id.nom_poke_stat)
        val newPokemonName: EditText = findViewById(R.id.nom_poke_modif)
        val buttonModif: Button = findViewById(R.id.modif_nom_poke)
        val pokemonAttaque: TextView = findViewById(R.id.statAttaque)
        val pokemonDefense: TextView = findViewById(R.id.statDefense)
        val pokemonHP: TextView = findViewById(R.id.statHP)
        val pokemonVitesse: TextView = findViewById(R.id.statVitesse)
        val pokemonImage: ImageView = findViewById(R.id.img_stat)
        val pokemonIsShiny: Switch = findViewById(R.id.isShiny)
        val pokemonType: TextView = findViewById(R.id.type_pokemon)
        val pokemonHeight: TextView = findViewById(R.id.statHeight)
        val pokemonWeight: TextView = findViewById(R.id.statWeight)

        //Log.d("pokeAPI", "$pokemonIsShiny")


        buttonModif.setOnClickListener {
            var pokemonNameTemp = MyApplication.userTeam[pokemonNum][1]
            val newName = newPokemonName.text.toString()
            if(!newName.isEmpty())
            {
                pokemonNameTemp = newName
                pokemonName.text = newName
            }
            MyApplication.userTeam[pokemonNum][1] = pokemonNameTemp
            if (pokemonIsShiny.isChecked()) {
                MyApplication.userTeam[pokemonNum][2] = "true"
                // switch est en position on
            } else {
                MyApplication.userTeam[pokemonNum][2] = "false"
                // switch est en position off
            }
            // mettre à jour le nom dans votre liste
            (application as MyApplication).saveUserTeam()
            val intent = Intent(this, Equipe::class.java)
            startActivity(intent)
        }

        pokeApiClient.getPokemon(pokemonId) { pokemon ->
            if (pokemon != null) {
                //val pokemon = pokemonResponse.toPokemon()
                var isShiny: Boolean = false
                pokemonIsShiny.text = "Normal"
                pokemonIsShiny.setOnClickListener{ true
                    if (isShiny == false){
                        Glide.with(this)
                            .load(pokemon.sprites.front_shiny)
                            .into(pokemonImage)
                        isShiny = true
                        pokemonIsShiny.text = "Shiny"

                    }
                    else if (isShiny == true){
                        Glide.with(this)
                            .load(pokemon.sprites.front_default)
                            .into(pokemonImage)
                        isShiny = false
                        pokemonIsShiny.text = "Normal"
                    }
                    (application as MyApplication).saveUserTeam()
                }
                pokemonID.text = "ID in the national pokedex : " + "#" + pokemon.id.toString()
                pokemonName.text =  MyApplication.userTeam[pokemonNum][1]

                if(MyApplication.userTeam[pokemonNum][2] == "false")
                {
                    pokemonIsShiny.setChecked(false);
                    pokemonIsShiny.text = "Normal"
                    isShiny = false
                    Glide.with(this).load(pokemon.sprites.front_default).into(pokemonImage)
                }
                else
                {
                    pokemonIsShiny.setChecked(true);
                    pokemonIsShiny.text = "Shiny"
                    isShiny = true
                    Glide.with(this).load(pokemon.sprites.front_shiny).into(pokemonImage)
                }

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
                if (pokemon.types.size > 1){
                    var alltype = ""
                    for (i in 0 until pokemon.types.size-1){
                        for (typename in pokemon.types) {
                            if(i < pokemon.types.size-1)
                            {
                                alltype += typename.type.name + " + "
                            }
                            else
                            {
                                alltype += typename.type.name
                            }
                        }
                        pokemonType.text = alltype
                    }
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
            }
        }
    }
}
