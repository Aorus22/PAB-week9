package com.example.ppab_09_l0122018_alyzakhoirunnadif.data

data class CharacterResponse(val documents: List<Document>)

data class Document(val fields: Fields)

data class Fields(
    val name: StringValue,
    val description: StringValue,
    val splashart: StringValue,
    val pathname: StringValue,
    val element: StringValue
)

data class StringValue(val stringValue: String)
