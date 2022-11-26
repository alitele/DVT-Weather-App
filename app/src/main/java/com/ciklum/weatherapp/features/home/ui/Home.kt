package com.ciklum.weatherapp.features.home.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.R
import com.ciklum.weatherapp.databinding.FragmentHomeBinding
import com.ciklum.weatherapp.extentions.notNull
import com.ciklum.weatherapp.features.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterForecast: AdapterForecast
    private val viewModel by viewModel<HomeViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        fetchData()
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

    private fun fetchData() {
        viewModel.fetchCurrentWeather()
        viewModel.fetchForeCastWeather()
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
        viewModel.onFetchForecast.observe(viewLifecycleOwner) {
            adapterForecast.differ.submitList(it.list)
        }

        viewModel.onFetchWeather.observe(viewLifecycleOwner)
        {
            it.notNull {
                "${it.main?.tempMin.toString()}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureMin.text = it
                }
                "${it.main?.tempMax.toString()}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureMax.text = it
                }
                "${it.main?.temp.toString()}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperatureCurrent.text = it
                }
                "${it.main?.temp.toString()}${resources.getString(R.string.degree_symbol)}".also {
                    binding.tvTemperature.text = it
                }
                binding.tvCondition.text =
                    it.weather[0].description?.replaceFirstChar(Char::titlecase)
            }

            when (it.weather[0].icon) {
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

            binding.tvLocation.text = it.name
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