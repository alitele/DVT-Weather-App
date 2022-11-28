package com.ciklum.weatherapp.features.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ciklum.weatherapp.R
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.databinding.FragmentFavouritesBinding
import com.ciklum.weatherapp.features.favourites.viewmodel.FavouritesViewModel
import com.ciklum.weatherapp.features.main.ui.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class Favourites : BaseFragment() {

    private lateinit var _binding: FragmentFavouritesBinding
    private val binding get() = _binding
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

    private fun pickLocation() {
        val cities = resources.getStringArray(R.array.city_list)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.title_city_picker))
            .setItems(cities) { _, which ->
                // Respond to item chosen
                viewModel.addNewFavourite(
                    LocationEntity(
                        locationName = cities[which]
                    )
                )
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

        adapterFavourites.setOnItemClickListener { location ->
            viewModel.removeNewFavourite(location)
        }

    }

    private fun fetchFavourites() {
        //Observing and setting favourites
        viewModel.getAllFavourites().observe(viewLifecycleOwner, Observer {
            adapterFavourites.differ.submitList(it.filter { f -> !f.isMyLocation })
        })
    }

}