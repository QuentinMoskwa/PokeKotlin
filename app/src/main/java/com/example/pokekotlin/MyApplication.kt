package com.example.pokekotlin
import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyApplication : Application() {
    companion object {
        var userTeam: MutableList<MutableList<String>> = mutableListOf()
    }
    override fun onCreate() {
        super.onCreate()
        // Récupération des données enregistrées dans SharedPreferences
        val sharedPref = getSharedPreferences("pokekotlin", Context.MODE_PRIVATE)
        val userTeamJson = sharedPref.getString("userTeam", null)
        if (userTeamJson != null) {
            val gson = Gson()
            userTeam = gson.fromJson(userTeamJson, object : TypeToken<MutableList<MutableList<Any>>>() {}.type)
        }
    }

    public fun saveUserTeam() {
        val sharedPref = getSharedPreferences("pokekotlin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val userTeamJson = gson.toJson(MyApplication.userTeam)
        editor.putString("userTeam", userTeamJson)
        editor.apply()
    }
}