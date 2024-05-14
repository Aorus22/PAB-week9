package com.example.ppab_09_l0122018_alyzakhoirunnadif.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ppab_09_l0122018_alyzakhoirunnadif.BodyAdapter
import com.example.ppab_09_l0122018_alyzakhoirunnadif.retrofit.ApiConfig
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.Chara
import com.example.ppab_09_l0122018_alyzakhoirunnadif.databinding.ActivityListBodyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListBodyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.listBody.layoutManager = layoutManager
        getListBody()
    }

    private fun getListBody() {
        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.instance.getCharacters()
                val listCharas = ArrayList<Chara>()
                for (document in response.documents) {
                    val name = document.fields.name.stringValue
                    val description = document.fields.description.stringValue
                    val splashart = document.fields.splashart.stringValue
                    listCharas.add(Chara(name, description, splashart))
                }
                withContext(Dispatchers.Main) {
                    val adapter = BodyAdapter(listCharas)
                    binding.listBody.adapter = adapter
                    binding.progressBar.visibility = View.INVISIBLE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@ListBodyActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }
}

