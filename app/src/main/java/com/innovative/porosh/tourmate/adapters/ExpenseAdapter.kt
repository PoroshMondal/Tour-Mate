package com.innovative.porosh.tourmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.innovative.porosh.tourmate.model.ExpenseModel
import com.innovative.porosh.tourmate.databinding.ExpenseRowItemBinding

class ExpenseAdapter: ListAdapter<ExpenseModel, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffUtil()) {

    class ExpenseViewHolder(val binding: ExpenseRowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expenseModel: ExpenseModel){
            binding.model = expenseModel
        }
    }

    class ExpenseDiffUtil: DiffUtil.ItemCallback<ExpenseModel>(){
        override fun areItemsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem.expenseId == newItem.expenseId
        }

        override fun areContentsTheSame(oldItem: ExpenseModel, newItem: ExpenseModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}