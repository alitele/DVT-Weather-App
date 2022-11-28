package com.ciklum.weatherapp.features.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.R
import com.ciklum.weatherapp.databinding.ItemForecastBinding
import com.ciklum.weatherapp.features.home.model.Weather
import com.ciklum.weatherapp.utils.Helper

class AdapterForecast() :
    RecyclerView.Adapter<AdapterForecast.ForecastViewHolder>() {

    /**
     * VIEW HOLDER
     */
    inner class ForecastViewHolder(val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * ADAPTER METHODS
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        return ForecastViewHolder(
            ItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {

        with(holder.binding) {
            val currentObj = differ.currentList[position]
            tvDay.text = Helper.getDateString(currentObj.dt)

            "${currentObj.main?.temp.toString()}${
                tvTemperature.context.resources.getString(
                    R.string.degree_symbol
                )
            }".also { tvTemperature.text = it }

            when (currentObj.weather[0].icon) {
                "01n", "01d", "50n", "50d" -> ivIcon.setImageResource(R.drawable.clear)
                "02n", "02d", "03n", "03d", "04n", "04d" -> ivIcon.setImageResource(R.drawable.partlysunny)
                "09n", "09d", "10n", "10d", "11n", "11d", "13n", "13d" -> ivIcon.setImageResource(R.drawable.rain)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * DIFF CALLBACK
     */
    private val differCallback = object : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Weather,
            newItem: Weather
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

}