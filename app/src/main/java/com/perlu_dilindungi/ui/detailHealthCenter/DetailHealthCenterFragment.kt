package com.perlu_dilindungi.ui.detailHealthCenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.perlu_dilindungi.R
import com.perlu_dilindungi.databinding.FragmentDetailHealthCenterBinding
import com.perlu_dilindungi.ui.bookmark.FaskesApplication

class DetailHealthCenterFragment : Fragment() {
    // Shared view models.
    private val sharedViewModel: DetailHealthCenterSharedViewModel by activityViewModels()

    private val viewModel: DetailHealthCenterViewModel by viewModels {
        DetailHealthCenterViewFactory(
            (activity?.application as FaskesApplication).database.faskesDao()
        )
    }

    // Binding attribute.
    private var _binding: FragmentDetailHealthCenterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout and binding.
        _binding = FragmentDetailHealthCenterBinding.inflate(inflater, container, false)

        // Return the binding root.
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the viewModel
        binding.viewModel = sharedViewModel


        // Cek status vaksinasi faskes
        updateStatus()

        // Cek bookmark faskes
        updateBookmarkBtn()

        // Set fungsional tombol bookmark
        listenBookmark()

        // Listen to the map.
        listenMaps()
    }

    private fun listenBookmark() {
        binding.btnBookmark.setOnClickListener {
            if (binding.btnBookmark.text.toString() == getString(R.string.bookmark)) {
                viewModel.insertHealthCenter(sharedViewModel.healthCenter.value!!)
            } else {
                viewModel.deleteHealthCenter(sharedViewModel.healthCenter.value!!)
            }
            updateBookmarkBtn()
        }
    }

    private fun listenMaps() {
        val faskes = sharedViewModel.healthCenter.value
        if (faskes != null){
            binding.btnGmaps.setOnClickListener {
                // Buat sebuat URI dari string intent. Hasil digunakan untuk membuat intent
                val gmmIntentUri = Uri.parse("geo:0,0?q=${faskes.latitude},${faskes.longitude}")

                // Membuat sebuah intent dengan action view dan URI yang telah dibuat
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                // Set intent menjadi explisit dengan menunjuk maps langsung
                mapIntent.setPackage("com.google.android.apps.maps")

                // Jalankan activity dari intent
                    startActivity(mapIntent)
            }
        }
    }

    private fun updateBookmarkBtn() {
        val isExist = viewModel.checkExist(sharedViewModel.healthCenter.value!!)
        isExist.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnBookmark.setText(R.string.unbookmark)
            } else {
                binding.btnBookmark.setText(R.string.bookmark)
            }
        }
    }


    private fun updateStatus() {
        val faskes = sharedViewModel.healthCenter.value!!
        Log.v("TESTING", faskes.toString())

        if(faskes.status == "Siap Vaksinasi"){
            binding.ivSimbol.setImageResource(R.drawable.ceklistvaksin_foreground)
        }
        else {
            binding.ivSimbol.setImageResource(R.drawable.tidak_siap_vaksin_foreground)
        }
    }
}