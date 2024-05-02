package com.innovative.porosh.tourmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innovative.porosh.tourmate.databinding.MomentRowBinding
import com.innovative.porosh.tourmate.model.MomentModel

class MomentAdapter: ListAdapter<MomentModel,MomentAdapter.MomentViewHolder>(MomentDiffCallback()) {

    class MomentViewHolder(val binding: MomentRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(momentModel: MomentModel){
            Glide.with(binding.root.context)
                .load(momentModel.imageUrl)
                .into(binding.momentRowImageView)
        }
    }

    class MomentDiffCallback: DiffUtil.ItemCallback<MomentModel>(){
        override fun areItemsTheSame(oldItem: MomentModel, newItem: MomentModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MomentModel, newItem: MomentModel): Boolean {
            return  oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val binding = MomentRowBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MomentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
    }

}