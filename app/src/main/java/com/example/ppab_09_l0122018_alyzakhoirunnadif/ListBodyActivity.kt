package com.example.ppab_09_l0122018_alyzakhoirunnadif

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ppab_09_l0122018_alyzakhoirunnadif.databinding.ActivityListBodyBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ListBodyActivity : AppCompatActivity() {

    companion object {
        private val TAG = ListBodyActivity::class.java.simpleName
    }
    private lateinit var binding: ActivityListBodyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.listBody.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listBody.addItemDecoration(itemDecoration)
        getListBody()
    }

    private fun getListBody() {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://firestore.googleapis.com/v1/projects/test-20d94/databases/(default)/documents/PPAB-09/list_char"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE

                val listCharacters = ArrayList<Character>()
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val list = jsonObject.getJSONObject("fields").getJSONObject("list").getJSONObject("arrayValue").getJSONArray("values")
                    for (i in 0 until list.length()) {
                        val item = list.getJSONObject(i).getJSONObject("mapValue").getJSONObject("fields")
                        val name = item.getJSONObject("name").getString("stringValue")
                        val description = item.getJSONObject("description").getString("stringValue")
                        val splashart = item.getJSONObject("splashart").getString("stringValue")
                        listCharacters.add(Character(name, description, splashart))
                    }
                    val adapter = BodyAdapter(listCharacters)
                    binding.listBody.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@ListBodyActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray?, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@ListBodyActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
