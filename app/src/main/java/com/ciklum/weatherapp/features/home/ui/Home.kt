package com.ciklum.weatherapp.features.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.R
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.databinding.FragmentHomeBinding
import com.ciklum.weatherapp.extentions.gone
import com.ciklum.weatherapp.extentions.notNull
import com.ciklum.weatherapp.extentions.visible
import com.ciklum.weatherapp.features.home.viewmodel.HomeViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterForecast: AdapterForecast
    private val viewModel by viewModel<HomeViewModel>()
    private val binding get() = _binding!!
    private var locations: List<LocationEntity> = emptyList()
    private var currentIndex: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initListeners()
        setupRecyclerView()
        observeData()
        return root
    }

    private fun setupRecyclerView() {
        with(binding.rvForecast)
        {
            adapterForecast = AdapterForecast()
            adapter = adapterForecast
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun initListeners() {
        with(binding)
        {
            ivLeft.setOnClickListener {
                if (currentIndex > 0) {
                    currentIndex--
                    fetchData()
                    ivRight.isEnabled = true
                } else {
                    ivLeft.isEnabled = true
                }
            }

            ivRight.setOnClickListener {
                if (currentIndex < locations.size - 1) {
                    currentIndex++
                    fetchData()
                    ivLeft.isEnabled = true
                } else {
                    ivRight.isEnabled = false
                }
            }
        }
    }

    private fun fetchData() {
        binding.progressBar.show()
        viewModel.getWeatherForLocation(locations[currentIndex].id)
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {

        viewModel.getAllFavourites().observe(viewLifecycleOwner, Observer {
            locations = it
            if (locations.isNotEmpty()) {
                if (currentIndex == -1) {
                    currentIndex = 0
                    fetchData()
                }
            }
            Log.d("TAG", "observeData: $it")
        })

        viewModel.locationWeather.observe(viewLifecycleOwner, Observer { it ->
            binding.progressBar.hide()
            it.notNull {
                binding.separator.visible()
                binding.tvTitleMin.visible()
                binding.tvTitleMax.visible()
                binding.tvTitleCurrent.visible()
                binding.tvWaiting.gone()
                binding.llLocationSwitch.visible()
                binding.tvLocation.visible()
                binding.progressBar.hide()

                if (it.isMyLocation)
                    binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_current_location
                        ), null, null, null
                    )
                else
                    binding.tvLocation.setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fav
                        ), null, null, null
                    )

                "${it.min}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureMin.text = it
                }
                "${it.max}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureMax.text = it
                }
                "${it.temp}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureCurrent.text = it
                }
                "${it.temp}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperature.text = it
                }
                binding.tvCondition.text =
                    it.description.replaceFirstChar(Char::titlecase)


                when (it.condition) {
                    "01n", "01d", "50n", "50d" -> {
                        binding.ivCondition.setBackgroundResource(R.drawable.sea_cloudy)
                        binding.containerMain.setBackgroundColor(resources.getColor(R.color.cloudy))
                    }
                    "02n", "02d", "03n", "03d", "04n", "04d" -> {
                        binding.ivCondition.setBackgroundResource(R.drawable.sea_sunnypng)
                        binding.containerMain.setBackgroundColor(resources.getColor(R.color.sunny))
                    }
                    "09n", "09d", "10n", "10d", "11n", "11d", "13n", "13d" -> {
                        binding.ivCondition.setBackgroundResource(R.drawable.sea_rainy)
                        binding.containerMain.setBackgroundColor(resources.getColor(R.color.rainy))
                    }
                }

                if (it.isMyLocation)
                    binding.tvLocation.text = "Current Location"
                else
                    binding.tvLocation.text = it.locationName

                viewModel.getForecastForLocation(locations[currentIndex].id)
            }
        })

        viewModel.locationForecast.observe(viewLifecycleOwner) {
            binding.tvWaiting.gone()
            binding.progressBar.hide()
            adapterForecast.differ.submitList(it.list)
        }

        viewModel.onToggleProgress.observe(viewLifecycleOwner) {
            if (it)
                binding.progressBar.show()
            else
                binding.progressBar.hide()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}