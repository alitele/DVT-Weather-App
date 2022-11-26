package com.ciklum.weatherapp.features.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciklum.weatherapp.R
import com.ciklum.weatherapp.features.main.ui.BaseFragment
import com.ciklum.weatherapp.databinding.FragmentFavouritesBinding
import com.ciklum.weatherapp.features.favourites.model.Favourite
import com.ciklum.weatherapp.features.favourites.viewmodel.FavouritesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class Favourites : BaseFragment() {

    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var root: View
    private lateinit var adapterFavourites: AdapterFavourites
    private val viewModel by viewModel<FavouritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        root = binding.root

        init()
        setupRecyclerView()
        fetchFavourites()

        return root
    }

    private fun init() {
        binding.fabAdd.setOnClickListener { pickLocation() }
    }

    private fun pickLocation()
    {
        val cities= resources.getStringArray(R.array.city_list)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_city_picker))
            .setItems(cities) { dialog, which ->
                // Respond to item chosen
                viewModel.addNewFavourite(Favourite(which, cities[which]))
            }
            .show()
    }

    private fun setupRecyclerView() {

        with(binding.rvFavourites)
        {
            adapterFavourites = AdapterFavourites()
            adapter = adapterFavourites
            layoutManager = LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.RecyclerView.VERTICAL,
                false
            )

        }

        adapterFavourites.setOnItemClickListener { favourite ->
            viewModel.removeNewFavourite(favourite)
        }

    }

    private fun fetchFavourites() {
        //Getting favourites from store
        viewModel.fetchFavourites()

        //Observing and setting favourites
        viewModel.onFetchFavourites.observe(viewLifecycleOwner) {
            adapterFavourites.differ.submitList(it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}