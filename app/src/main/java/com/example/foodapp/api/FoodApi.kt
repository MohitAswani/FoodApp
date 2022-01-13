package com.example.foodapp.api

import com.example.foodapp.models.CategoriesResponse
import com.example.foodapp.models.FilterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(
    ) : Response<CategoriesResponse>

    @GET("api/json/v1/1/list.php?i=list")
    suspend fun getIngredientList(
    ) : Response<FilterResponse>

    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getAreaList(
    ) : Response<FilterResponse>

    @GET("api/json/v1/1/filter.php")
    suspend fun getFilterByCategory(
        @Query("c")
        category:String
    ) : Response<FilterResponse>

    @GET("api/json/v1/1/filter.php")
    suspend fun getFilterByIngredient(
        @Query("i")
        ingredient:String
    ) : Response<FilterResponse>

    @GET("api/json/v1/1/filter.php")
    suspend fun getFilterByArea(
        @Query("c")
        area:String
    ) : Response<FilterResponse>

}