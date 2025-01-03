package com.furkandonertas.idealustam.features.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.furkandonertas.idealustam.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton

class MyCarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.addButton).setOnClickListener {
            showAddCarBottomSheet()
        }
    }

    private fun showAddCarBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_add_car, null)
        
        // Save Car butonuna tıklama
        bottomSheetView.findViewById<MaterialButton>(R.id.saveCarButton).setOnClickListener {
            bottomSheetDialog.dismiss()
            // Car Detail Fragment'a geçiş
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarDetailFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
        
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    companion object {
        fun newInstance() = MyCarFragment()
    }
} 