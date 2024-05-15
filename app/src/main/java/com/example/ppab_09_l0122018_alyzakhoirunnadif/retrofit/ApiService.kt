package com.example.ppab_09_l0122018_alyzakhoirunnadif.retrofit

import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.CharacterResponse
import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.Document
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("PPAB-09")
    suspend fun getCharacters(): CharacterResponse

    @PATCH("PPAB-09/{documentName}")
    fun addCharacter(@Path("documentName") documentName: String, @Body character: Document): Call<Void>
}