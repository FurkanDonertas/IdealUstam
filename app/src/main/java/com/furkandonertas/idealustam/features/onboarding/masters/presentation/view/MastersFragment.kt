package com.furkandonertas.idealustam.features.onboarding.masters.presentation.view

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
import com.furkandonertas.idealustam.features.onboarding.masters.presentation.viewmodel.MastersViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class MastersFragment : Fragment() {

    private val viewModel: MastersViewModel by viewModels()
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var emptyView: View
    private lateinit var errorView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_masters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupListeners()
        observeViewModel()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        emptyView = view.findViewById(R.id.emptyView)
        errorView = view.findViewById(R.id.errorView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupListeners() {
        errorView.findViewById<View>(R.id.retryButton).setOnClickListener {
            viewModel.loadMasters()
        }
    }

    private fun observeViewModel() {
        viewModel.mastersState.observe(viewLifecycleOwner) { state ->
            when (state) {
                MastersViewModel.MastersState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.GONE
                    errorView.visibility = View.GONE
                }
                MastersViewModel.MastersState.Empty -> {
                    progressIndicator.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    emptyView.visibility = View.VISIBLE
                    errorView.visibility = View.GONE
                }
                is MastersViewModel.MastersState.Success -> {
                    progressIndicator.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    emptyView.visibility = View.GONE
                    errorView.visibility = View.GONE
                    // TODO: RecyclerView adapter'ını güncelle
                }
                MastersViewModel.MastersState.Error -> {
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
        fun newInstance() = MastersFragment()
    }
} 