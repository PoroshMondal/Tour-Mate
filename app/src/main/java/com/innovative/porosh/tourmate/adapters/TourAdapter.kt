package com.innovative.porosh.tourmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovative.porosh.tourmate.databinding.TourRowBinding
import com.innovative.porosh.tourmate.model.TourModel
import com.innovative.porosh.tourmate.utils.Constants

class TourAdapter(val callback: (id: String, action: String, status: Boolean) -> Unit): ListAdapter<TourModel, TourAdapter.TourViewHolder>(TourDiffCallback()){

    private lateinit var binding: TourRowBinding

    class TourViewHolder(val binding: TourRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(tourModel: TourModel) {
            binding.tourModel = tourModel
        }
    }

    class TourDiffCallback : DiffUtil.ItemCallback<TourModel>() {
        override fun areItemsTheSame(oldItem: TourModel, newItem: TourModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TourModel, newItem: TourModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        binding = TourRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        holder.bind(getItem(position))
        binding.tourRowItem.setOnClickListener {
            callback(getItem(position).id!!, Constants.DETAILS_SCREEN, false)
        }
        binding.rowTourComplete.setOnClickListener {
            callback(getItem(position).id!!, Constants.TOUR_STATUS_UPDATE, !getItem(position).isCompleted)
        }
    }

}