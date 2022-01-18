package com.example.foodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapters.FilterAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.ui.MainActivity
import com.example.foodapp.ui.viewModels.FoodViewModel
import com.example.foodapp.util.Constants.Companion.SEARCH_DELAY
import com.example.foodapp.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        const val TAG = "SearchFragment"
    }
    private lateinit var viewModel: FoodViewModel
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)

        viewModel=(activity as MainActivity).viewModel

        setUpRecyclerView()

        var job: Job?=null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        binding.searchImage.visibility = View.INVISIBLE
                        binding.rvSearchNews.visibility = View.VISIBLE
                        viewModel.search(it.toString())
                    }
                }
            }
        }

        viewModel.filters.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        filterAdapter.differ.submitList(it.meals?.toList())
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


        return binding.root
    }

    private fun setUpRecyclerView() {
        filterAdapter = FilterAdapter()
        binding.rvSearchNews.apply {
            adapter = filterAdapter
            layoutManager= LinearLayoutManager(requireContext())
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}