package com.furkandonertas.idealustam.features.masters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkandonertas.idealustam.databinding.FragmentMastersBinding
import com.furkandonertas.idealustam.features.masters.presentation.adapter.MasterAdapter
import com.furkandonertas.idealustam.features.masters.presentation.viewmodel.MastersViewModel
import com.furkandonertas.idealustam.features.masters.domain.model.Master

class MastersFragment : Fragment() {

    private var _binding: FragmentMastersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MastersViewModel by viewModels()
    private lateinit var masterAdapter: MasterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMastersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        masterAdapter = MasterAdapter(
            masters = emptyList(),
            onMasterClick = { master -> viewModel.onMasterSelected(master) },
            onFavoriteClick = { master -> viewModel.onFavoriteClicked(master) }
        )
        binding.recyclerView.apply {
            adapter = masterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.mastersState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MastersViewModel.MastersState.Loading -> showLoading()
                is MastersViewModel.MastersState.Success -> showMasters(state.masters)
                is MastersViewModel.MastersState.Error -> showError()
                is MastersViewModel.MastersState.Empty -> showEmpty()
                is MastersViewModel.MastersState.Initial -> Unit
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            // TODO: Show error message in a snackbar or toast
        }
    }

    private fun showLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            errorLayout.visibility = View.GONE
            emptyLayout.visibility = View.GONE
        }
    }

    private fun showMasters(masters: List<Master>) {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
            emptyLayout.visibility = View.GONE
        }
        masterAdapter.updateMasters(masters)
    }

    private fun showError() {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            errorLayout.visibility = View.VISIBLE
            emptyLayout.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            errorLayout.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 