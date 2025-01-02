package com.furkandonertas.idealustam.features.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.home.presentation.viewmodel.MyCarViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator

class MyCarFragment : Fragment() {

    private val viewModel: MyCarViewModel by viewModels()
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var addCarButton: FloatingActionButton
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var emptyView: View
    private lateinit var errorView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupListeners()
        observeViewModel()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        addCarButton = view.findViewById(R.id.addCarButton)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        emptyView = view.findViewById(R.id.emptyView)
        errorView = view.findViewById(R.id.errorView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        addCarButton.setOnClickListener {
            // TODO: Araç ekleme ekranına yönlendir
        }

        errorView.findViewById<View>(R.id.retryButton).setOnClickListener {
            viewModel.loadUserCars()
        }
    }

    private fun observeViewModel() {
        viewModel.carState.observe(viewLifecycleOwner) { state ->
            when (state) {
                MyCarViewModel.CarState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.GONE
                    errorView.visibility = View.GONE
                }
                MyCarViewModel.CarState.Empty -> {
                    progressIndicator.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                    errorView.visibility = View.GONE
                }
                is MyCarViewModel.CarState.Success -> {
                    progressIndicator.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                    errorView.visibility = View.GONE
                    // TODO: RecyclerView adapter'ını güncelle
                }
                MyCarViewModel.CarState.Error -> {
                    progressIndicator.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.GONE
                    errorView.visibility = View.VISIBLE
                }
                else -> {
                    progressIndicator.visibility = View.GONE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = MyCarFragment()
    }
} 