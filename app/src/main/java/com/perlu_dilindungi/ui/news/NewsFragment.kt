package com.perlu_dilindungi.ui.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perlu_dilindungi.databinding.FragmentNewsBinding
import com.perlu_dilindungi.ui.qrScanner.QRScannerActivity

class NewsFragment : Fragment() {

    // The view model that this fragment used.
    private val viewModel: NewsViewModel by viewModels()

    // Binding attribute
    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate and binding.
        _binding = FragmentNewsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the NewsViewModel
        binding.viewModel = viewModel

        // Sets the layout manager
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create the new Adapter.
        val newsAdapter = NewsAdapter {
            // Check that link exist.
            if (it.link.isEmpty()) {
                Toast.makeText(view.context, "Cannot read the News", Toast.LENGTH_SHORT).show()
                return@NewsAdapter
            }

            val action = NewsFragmentDirections.actionNavigationNewsToNewsWebFragment(
                it.link[0]
            )
            view.findNavController().navigate(action)
        }

        // Bind the Adapter.
        binding.recyclerView.adapter = newsAdapter

        binding.fabScanQr.setOnClickListener {
            val intent = Intent(this.context, QRScannerActivity::class.java)
            startActivity(intent);
        }

    }
}