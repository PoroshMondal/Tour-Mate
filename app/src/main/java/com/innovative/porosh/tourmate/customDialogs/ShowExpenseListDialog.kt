package com.innovative.porosh.tourmate.customDialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.adapters.ExpenseAdapter
import com.innovative.porosh.tourmate.data.model.ExpenseModel

class ShowExpenseListDialog(private val list: List<ExpenseModel>): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.view_expense_layout,null)
        val recyclerView: RecyclerView = layout.findViewById(R.id.expenseRV)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ExpenseAdapter().apply {
            submitList(list)
        }
        recyclerView.adapter = adapter

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.expense_list))
            .setView(layout)
            .setNegativeButton(getString(R.string.close),null)
        return builder.show()
    }
}