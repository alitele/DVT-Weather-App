package com.ciklum.weatherapp.features.favourites.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.databinding.ItemFavouriteBinding
import com.ciklum.weatherapp.databinding.ItemForecastBinding
import com.ciklum.weatherapp.features.favourites.model.Favourite
import com.ciklum.weatherapp.features.home.model.Forecast

class AdapterFavourites() :
    RecyclerView.Adapter<AdapterFavourites.FavouriteViewHolder>() {

    /**
     * VIEW HOLDER
     */
    inner class FavouriteViewHolder(val binding: ItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * ADAPTER METHODS
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            ItemFavouriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        with(holder.binding) {
            val currentObj = differ.currentList[position]
            tvLocation.text =currentObj.location
            ivDelete.setOnClickListener { itemOnClick(currentObj) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * DIFF CALLBACK
     */
    private val differCallback = object : DiffUtil.ItemCallback<Favourite>() {
        override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean {
            return oldItem.location == newItem.location
        }

        override fun areContentsTheSame(
            oldItem: Favourite,
            newItem: Favourite
        ): Boolean {
            return oldItem.areBothObjectsSame(oldItem, newItem)
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    /**
     * ITEM CLICK LISTENERS
     */
    private var onItemClickListener: ((favourite: Favourite) -> Unit)? = null
    fun setOnItemClickListener(listener: (favourite: Favourite) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * HELPER FUNCTIONS
     */
    private fun itemOnClick(favourite: Favourite) {
        onItemClickListener?.let { it(favourite) }
    }


}