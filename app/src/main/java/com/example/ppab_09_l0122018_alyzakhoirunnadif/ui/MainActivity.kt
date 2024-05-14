package com.example.ppab_09_l0122018_alyzakhoirunnadif.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ppab_09_l0122018_alyzakhoirunnadif.retrofit.ApiConfig
import com.example.ppab_09_l0122018_alyzakhoirunnadif.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRandomCharacter()
        binding.btnAllBody.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListBodyActivity::class.java))
        }
    }

    private fun getRandomCharacter() {
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.instance.getCharacters()
                val randomIndex = Random.nextInt(response.documents.size)
                val character = response.documents[randomIndex].fields

                withContext(Dispatchers.Main) {
                    val name = character.name.stringValue
                    val description = character.description.stringValue
                    val splashArtUrl = character.splashart.stringValue
                    val pathname = character.pathname.stringValue
                    val element = character.pathname.stringValue
                    val stringElementPath = "${element} - ${pathname}"
                    binding.tvName.text = name
                    binding.tvDescription.text = description
                    binding.tvElementPath.text = stringElementPath
                    Glide.with(this@MainActivity).load(splashArtUrl).into(binding.ivSplashArt)
                    binding.progressBar.visibility = View.INVISIBLE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }
}

