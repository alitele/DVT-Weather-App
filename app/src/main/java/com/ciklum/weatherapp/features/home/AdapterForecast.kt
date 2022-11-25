package com.ciklum.weatherapp.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.databinding.ItemForecastBinding

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
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * DIFF CALLBACK
     */
    private val differCallback = object : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(
            oldItem: Forecast,
            newItem: Forecast
        ): Boolean {
            return oldItem.areBothObjectsSame(oldItem, newItem)
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

}