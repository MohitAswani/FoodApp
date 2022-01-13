package com.example.foodapp.repository

import com.example.foodapp.api.RetrofitInstance
import com.example.foodapp.models.CategoriesResponse
import com.example.foodapp.models.FilterResponse
import retrofit2.Response

class FoodRepository {

    suspend fun getCategories(): Response<CategoriesResponse> {
        return RetrofitInstance.api.getCategories()
    }

    suspend fun getFilterByCategory(category:String): Response<FilterResponse> {
        return RetrofitInstance.api.getFilterByCategory(category)
    }

    suspend fun getFilterByArea(area:String): Response<FilterResponse> {
        return RetrofitInstance.api.getFilterByArea(area)
    }

    suspend fun getFilterByIngredient(ingredient:String): Response<FilterResponse> {
        return RetrofitInstance.api.getFilterByIngredient(ingredient)
    }

    suspend fun getIngredientList(): Response<FilterResponse>{
        return RetrofitInstance.api.getIngredientList()
    }

    suspend fun getAreaList(): Response<FilterResponse>{
        return RetrofitInstance.api.getAreaList()
    }
}