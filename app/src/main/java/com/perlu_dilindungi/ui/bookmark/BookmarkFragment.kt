package com.perlu_dilindungi.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perlu_dilindungi.databinding.FragmentBookmarkBinding
import com.perlu_dilindungi.ui.detailHealthCenter.DetailHealthCenterSharedViewModel
import com.perlu_dilindungi.ui.healthCenter.HealthCenterAdapter
import com.perlu_dilindungi.ui.qrScanner.QRScannerActivity

class BookmarkFragment : Fragment() {
    // Declare the viewModel in this Fragment with some DAO.
    private val viewModel: FaskesViewModel by viewModels {
        FaskesViewModelFactory(
            (activity?.application as FaskesApplication).database.faskesDao()
        )
    }
    // Shared view models.
    private val sharedViewModel: DetailHealthCenterSharedViewModel by activityViewModels()

    // Binding attribute.
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Set the binding.
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the viewModel
        binding.viewModel = viewModel

        // Sets the layout manager
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create the Adapter for recycler view.
        val healthCenterAdapter = HealthCenterAdapter {
            // Set the selected Health Center.
            sharedViewModel.setHealthCenter(it)

            // Get the navigation action.
            val action = BookmarkFragmentDirections.actionNavigationBookmarkToDetailHealthCenterFragment()

            // Navigate to Fragment Detail Health Center.
            view.findNavController().navigate(action)
        }

        // Bind the Adapter.
        binding.recyclerView.adapter = healthCenterAdapter

        // SetOnClickListener on FAB.
        binding.fabScanQr.setOnClickListener {
            val intent = Intent(this.context, QRScannerActivity::class.java)
            startActivity(intent);
        }
    }
}