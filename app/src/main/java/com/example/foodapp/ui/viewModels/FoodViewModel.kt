package com.example.foodapp.ui.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.FoodApplication
import com.example.foodapp.models.CategoriesResponse
import com.example.foodapp.models.FilterResponse
import com.example.foodapp.models.RecipeResponse
import com.example.foodapp.repository.FoodRepository
import com.example.foodapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class FoodViewModel(
    app: Application,
    private val repository: FoodRepository
) : AndroidViewModel(app) {



    val categories = MutableLiveData<Resource<CategoriesResponse>>()
    var categoriesResponse: CategoriesResponse? = null

    val filters = MutableLiveData<Resource<FilterResponse>>()
    var filtersResponse: FilterResponse? = null

    val recipes = MutableLiveData<Resource<RecipeResponse>>()
    var recipeResponse: RecipeResponse? = null

    fun getCategories() = viewModelScope.launch {
        safeCategoriesCall()
    }

    private suspend fun safeCategoriesCall() {
        categories.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getCategories()
                categories.postValue(handleCategoriesResponse(response = response))
            } else {
                categories.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> categories.postValue(Resource.Error(message = "Network failure"))
                else -> categories.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    private fun handleCategoriesResponse(response: Response<CategoriesResponse>): Resource<CategoriesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    fun getIngredientList() = viewModelScope.launch {
        safeIngredientListCall()
    }

    private suspend fun safeIngredientListCall() {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getIngredientList()
                filters.postValue(handleListResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }

    fun getAreaList() = viewModelScope.launch {
        safeAreaListCall()
    }

    private suspend fun safeAreaListCall() {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getAreaList()
                filters.postValue(handleListResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }

    private fun handleListResponse(response: Response<FilterResponse>): Resource<FilterResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    fun getFilterByCategory(filterQuery:String) = viewModelScope.launch {
        safeFilterByCategoryCall(filterQuery)
    }

    private suspend fun safeFilterByCategoryCall(filterQuery:String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByCategory(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    fun getFilterByArea(filterQuery:String) = viewModelScope.launch {
        safeFilterByAreaCall(filterQuery)
    }

    private suspend fun safeFilterByAreaCall(filterQuery:String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByArea(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    fun getFilterByIngredient(filterQuery:String) = viewModelScope.launch {
        safeFilterByIngredientCall(filterQuery)
    }

    private suspend fun safeFilterByIngredientCall(filterQuery:String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByIngredient(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    fun search(filterQuery:String) = viewModelScope.launch {
        safeSearch(filterQuery)
    }

    private suspend fun safeSearch(filterQuery:String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.search(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun handleFilterResponse(response: Response<FilterResponse>): Resource<FilterResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }


    fun getRecipeById(id:String) = viewModelScope.launch {
        safeGetRecipe(id)
    }

    private suspend fun safeGetRecipe(id:String){
        recipes.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getRecipeById(id)
                recipes.postValue(handleRecipeResponse(response = response))
            } else {
                recipes.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> recipes.postValue(Resource.Error(message = "Network failure"))
                else -> recipes.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun handleRecipeResponse(response: Response<RecipeResponse>): Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    private fun hasInternetConnection(): Boolean {
        // for checking the internet connectivity we need a connectivity manager which is system service which requires context and for that we need a context
        // So rather than using the activity context which gets destroyed when the activity get destroyed we use application context

        val connectivityManager = getApplication<FoodApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}