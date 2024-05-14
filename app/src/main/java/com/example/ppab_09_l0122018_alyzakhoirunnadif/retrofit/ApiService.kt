package com.example.ppab_09_l0122018_alyzakhoirunnadif.retrofit

import com.example.ppab_09_l0122018_alyzakhoirunnadif.data.CharacterResponse
import retrofit2.http.*

interface ApiService {
    @GET("PPAB-09")
    suspend fun getCharacters(): CharacterResponse
}