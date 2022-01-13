package com.example.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.repository.FoodRepository
import com.example.foodapp.ui.viewModels.FoodViewModel
import com.example.foodapp.ui.viewModels.FoodViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel:FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository=FoodRepository()
        val viewModelProviderFactory=FoodViewModelProviderFactory(application,repository)
        viewModel=ViewModelProvider(this,viewModelProviderFactory)[FoodViewModel::class.java]

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView.setupWithNavController(foodNavHostFragment.findNavController())
    }
}