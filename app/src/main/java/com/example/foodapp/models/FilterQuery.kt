package com.example.foodapp.models

import java.io.Serializable

data class FilterQuery(
    val queryType:String,
    val query:String
):Serializable