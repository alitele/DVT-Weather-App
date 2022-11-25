package com.ciklum.weatherapp.features.addlocation.mylocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ciklum.weatherapp.databinding.FragmentHomeBinding
import com.ciklum.weatherapp.features.home.AdapterForecast
import com.ciklum.weatherapp.features.home.Forecast
import org.koin.android.ext.android.bind

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterForecast: AdapterForecast

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        setData()
        return root
    }

    private fun setData() {
        with(binding.rvForecast)
        {
            adapterForecast = AdapterForecast()
            adapter = adapterForecast
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        val data: MutableList<Forecast> = mutableListOf<Forecast>()
        for (i in 0..7)
            data.add(Forecast("Day $i", "10*${i}", i))
        adapterForecast.differ.submitList(data)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}