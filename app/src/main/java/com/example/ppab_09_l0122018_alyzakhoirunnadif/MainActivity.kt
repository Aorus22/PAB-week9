package com.example.ppab_09_l0122018_alyzakhoirunnadif

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ppab_09_l0122018_alyzakhoirunnadif.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
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
        val client = AsyncHttpClient()
        val url = "https://firestore.googleapis.com/v1/projects/test-20d94/databases/(default)/documents/PPAB-09"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val documentsArray = responseObject.getJSONArray("documents")
                    val randomIndex = Random.nextInt(documentsArray.length())
                    val character = documentsArray.getJSONObject(randomIndex).getJSONObject("fields")

                    val name = character.getJSONObject("name").getString("stringValue")
                    val description = character.getJSONObject("description").getString("stringValue")
                    val splashArtUrl = character.getJSONObject("splashart").getString("stringValue")

                    binding.tvName.text = name
                    binding.tvDescription.text = description
                    Glide.with(this@MainActivity).load(splashArtUrl).into(binding.ivSplashArt)
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
