package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentRecipeBinding
import com.example.foodapp.ui.MainActivity
import com.example.foodapp.ui.viewModels.FoodViewModel
import com.example.foodapp.util.Resource
import java.util.regex.Matcher
import java.util.regex.Pattern

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    companion object {
        const val TAG = "RecipeFragment"
    }

    private lateinit var viewModel: FoodViewModel
    private lateinit var binding: FragmentRecipeBinding
    private val args: RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false)

        viewModel = (activity as MainActivity).viewModel

        val id = args.recipeID

        viewModel.getRecipeById(id)

        viewModel.recipes.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
//                        val meal= it.meals?.get(0)
//                        Glide.with(binding.root)
//                            .load(meal?.strMealThumb)
//                            .into(binding.recipeImage)
//
//                        binding.recipeText.text=meal?.strMeal
//                        meal?.strMeal?.let { it1 -> Log.d(TAG, it1) }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recipeText.text="LAUDA"
    }

    private val expression =
        "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"

    fun getVideoId(videoUrl: String): String? {
        if (videoUrl.trim { it <= ' ' }.isEmpty()) {
            return null
        }
        val pattern: Pattern = Pattern.compile(expression)
        val matcher: Matcher = pattern.matcher(videoUrl)
        try {
            if (matcher.find()) return matcher.group()
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
        return null
    }

    private fun hideProgressBar() {
//        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
//        binding.progressBar.visibility = View.VISIBLE
    }
}