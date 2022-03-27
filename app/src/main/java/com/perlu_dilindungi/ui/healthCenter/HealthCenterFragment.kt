package com.perlu_dilindungi.ui.healthCenter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perlu_dilindungi.R
import com.perlu_dilindungi.databinding.FragmentHealthCenterBinding
import com.perlu_dilindungi.helper.LocationHelper
import com.perlu_dilindungi.ui.detailHealthCenter.DetailHealthCenterSharedViewModel
import com.perlu_dilindungi.ui.qrScanner.QRScannerActivity

class HealthCenterFragment : Fragment() {

    // View models.
    private val viewModel: HealthCenterViewModel by viewModels()

    // Shared view models.
    private val sharedViewModel: DetailHealthCenterSharedViewModel by activityViewModels()

    // Binding attribute.
    private var _binding: FragmentHealthCenterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout and binding.
        _binding = FragmentHealthCenterBinding.inflate(inflater, container, false)

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
            val action = HealthCenterFragmentDirections.actionNavigationLocationToDetailHealthCenterFragment()

            // Navigate to Fragment Detail Health Center.
            view.findNavController().navigate(action)
        }

        // Bind the Adapter.
        binding.recyclerView.adapter = healthCenterAdapter

        binding.fabScanQr.setOnClickListener {
            val intent = Intent(this.context, QRScannerActivity::class.java)
            startActivity(intent);
        }

        // Update the Province Menu
        updateProvinceMenu()

        // Listen to any change in Province Menu.
        listenProvinceMenu()

        // Listen to any change in City Menu.
        listenCityMenu()

        // Listen to the search button.
        listenSearchButton()
    }

    // When the viewStateRestored, resume the dropdown menu selection.
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // Update the location.
        updateLocation()

        // Resume the Menu.
        resumeMenu()
    }

    //  Additional Function

    /**
     * Resume the dropdown menu after the fragment recreated.
     */
    private fun resumeMenu() {
        // Province menu list.
        val provinces = viewModel.provinces.value
        if (!provinces.isNullOrEmpty()) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, provinces)
            binding.provinceMenuText.setAdapter(adapter)
        }

        // City menu list.
        val cities = viewModel.cities.value
        if (!cities.isNullOrEmpty()) {
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cities)
            binding.cityMenuText.setAdapter(adapter)
        }
    }

    /**
     * Updating the Province Menu if viewModel provinces change.
     */
    private fun updateProvinceMenu() {
        // Update the provinceMenuText's adapter if viewModel changes.
        viewModel.provinces.observe(viewLifecycleOwner) { items ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            binding.provinceMenuText.setAdapter(adapter)
        }
    }

    /**
     * Listen to any change in provinceMenu.
     */
    private fun listenProvinceMenu() {
        // List to any value change in provinceMenuText.
        binding.provinceMenuText.setOnItemClickListener { adapterView, _, position, _ ->
            // Remove all healthCenter items.
            viewModel.clearHealthCenters()

            val province = adapterView.getItemAtPosition(position).toString()
            updateCityMenu(province)
        }
    }

    /**
     * Updating the Cities Menu if viewModel cities change.
     */
    private fun updateCityMenu(province: String) {
        // Reset the value in cityMenu.
        binding.cityMenuText.setText("")

        // Get cities by its province.
        viewModel.getCities(province)

        // Update the cityMenuText's adapter if viewModel changes.
        viewModel.cities.observe(viewLifecycleOwner) { list ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, list)
            binding.cityMenuText.setAdapter(adapter)
        }
    }

    /**
     * Listen to any change in cityMenu.
     */
    private fun listenCityMenu() {
        // List to any value change in cityMenuText.
        binding.cityMenuText.setOnItemClickListener { _, _, _, _ ->
            // Remove all healthCenter items.
            viewModel.clearHealthCenters()
        }
    }

    /**
     * Listen when search button is clicked.
     */
    private fun listenSearchButton() {
        binding.searchButton.setOnClickListener {
            // Remove all healthCenter items.
            viewModel.clearHealthCenters()

            // Get the province and city selection.
            val province = binding.provinceMenuText.text.toString()
            val city = binding.cityMenuText.text.toString()

            if (city.isNotEmpty() && province.isNotEmpty()) {
                // Update the health centers in recycle view.
                updateHealthCenter(province, city)
            } else {
                // Show the toast message.
                Toast.makeText(
                    requireContext(),
                    viewModel.baseMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Update the Health Center in ViewModels.
     */
    private fun updateHealthCenter(province: String, city: String) {
        // Update the healthCenters in viewModel.
        // Note: It will update the view via bindingAdapters.
        viewModel.getHealthCenters(province, city)

        // Update the location.
        updateLocation()
    }

    /**
     * Update the location in viewModels.
     */
    private fun updateLocation() {
        LocationHelper.getCurrentLocation(requireContext()) {
            // Get the location or return.
            val location = it.result ?: return@getCurrentLocation
            viewModel.updateLocation(location)
        }
    }
}