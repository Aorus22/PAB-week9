package com.example.ppab_09_l0122018_alyzakhoirunnadif.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.Document
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.Fields
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.StringValue
import com.example.ppab_09_l0122018_alyzakhoirunnadif.databinding.ActivityFormBinding
import com.example.ppab_09_l0122018_alyzakhoirunnadif.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etSplashArt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val url = s.toString()
                if (url.isNotEmpty()) {
                    binding.imgPreview.visibility = android.view.View.VISIBLE
                    Glide.with(this@FormActivity)
                        .load(url)
                        .into(binding.imgPreview)
                } else {
                    binding.imgPreview.visibility = android.view.View.GONE
                }
            }
        })

        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val description = binding.etDescription.text.toString()
            val splashArt = binding.etSplashArt.text.toString()
            val pathname = binding.etPathname.text.toString()
            val element = binding.etElement.text.toString()

            val character = Document(
                Fields(
                    StringValue(name),
                    StringValue(description),
                    StringValue(splashArt),
                    StringValue(pathname),
                    StringValue(element)
                )
            )

            sendCharacterData(name, character)
        }
    }

    private fun sendCharacterData(documentName: String, character: Document) {
        val call = ApiConfig.instance.addCharacter(documentName, character)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@FormActivity, "Character added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@FormActivity, "Failed to add character", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@FormActivity, "Failed to connect to server", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}