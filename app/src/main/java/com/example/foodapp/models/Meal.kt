package com.example.foodapp.models

import java.io.Serializable

data class Meal(
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strArea: String?,
    val strIngredient: String?
):Serializable