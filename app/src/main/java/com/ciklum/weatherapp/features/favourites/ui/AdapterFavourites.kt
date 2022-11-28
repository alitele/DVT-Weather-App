package com.ciklum.weatherapp.features.favourites.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.databinding.ItemFavouriteBinding

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
            tvLocation.text = currentObj.locationName
            ivDelete.setOnClickListener { itemOnClick(currentObj) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * DIFF CALLBACK
     */
    private val differCallback = object : DiffUtil.ItemCallback<LocationEntity>() {
        override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationEntity,
            newItem: LocationEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    /**
     * ITEM CLICK LISTENERS
     */
    private var onItemClickListener: ((locationEntity: LocationEntity) -> Unit)? = null
    fun setOnItemClickListener(listener: (locationEntity: LocationEntity) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * HELPER FUNCTIONS
     */
    private fun itemOnClick(locationEntity: LocationEntity) {
        onItemClickListener?.let { it(locationEntity) }
    }

}